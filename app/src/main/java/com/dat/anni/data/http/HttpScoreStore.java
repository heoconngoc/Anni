package com.dat.anni.data.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.ScoreStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Store gọi REST API của server (Phase 7). Báo lỗi qua StoreException để
 * FallbackScoreStore kịp rơi về SQLite khi server không truy cập được.
 */
public class HttpScoreStore implements ScoreStore {

	/** Lỗi mạng/HTTP gom về một loại để fallback dễ dàng. */
	public static class StoreException extends RuntimeException {
		public StoreException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	private final String baseUrl;
	private final HttpClient client = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(2))
			.build();
	private final ObjectMapper mapper = new ObjectMapper();

	public HttpScoreStore(String baseUrl) {
		this.baseUrl = baseUrl.endsWith("/")
				? baseUrl.substring(0, baseUrl.length() - 1)
				: baseUrl;
	}

	public void record(String username, String gameId, int score) {
		String body = "{\"username\":\"%s\",\"gameId\":\"%s\",\"score\":%d}".formatted(
				jsonEscape(username), jsonEscape(gameId), score);
		post("/scores", body);
	}

	public List<ScoreEntry> top(String gameId, int limit) {
		JsonNode arr = getJson("/games/" + encode(gameId) + "/top?limit=" + limit);
		List<ScoreEntry> result = new ArrayList<>();
		for (JsonNode node : arr) {
			result.add(new ScoreEntry(node.path("username").asText(""), node.path("score").asInt()));
		}
		return result;
	}

	public int best(String username, String gameId) {
		JsonNode obj = getJson("/users/" + encode(username) + "/best/" + encode(gameId));
		return obj.path("best").asInt(0);
	}

	private JsonNode getJson(String path) {
		try {
			HttpResponse<String> res = send(HttpRequest.newBuilder()
					.uri(URI.create(baseUrl + path))
					.timeout(Duration.ofSeconds(3))
					.GET());
			if (res.statusCode() / 100 != 2) {
				throw new StoreException("HTTP " + res.statusCode() + " cho " + path, null);
			}
			return mapper.readTree(res.body());
		} catch (java.io.IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			throw new StoreException("Lỗi gọi API " + path + ": " + e.getMessage(), e);
		}
	}

	private void post(String path, String jsonBody) {
		try {
			HttpResponse<String> res = send(HttpRequest.newBuilder()
					.uri(URI.create(baseUrl + path))
					.timeout(Duration.ofSeconds(3))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(jsonBody)));
			if (res.statusCode() / 100 != 2) {
				throw new StoreException("HTTP " + res.statusCode() + " cho POST " + path, null);
			}
		} catch (java.io.IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			throw new StoreException("Lỗi gọi API POST " + path + ": " + e.getMessage(), e);
		}
	}

	private HttpResponse<String> send(HttpRequest.Builder builder)
			throws java.io.IOException, InterruptedException {
		return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
	}

	private static String encode(String raw) {
		return java.net.URLEncoder.encode(raw == null ? "" : raw, java.nio.charset.StandardCharsets.UTF_8);
	}

	private static String jsonEscape(String raw) {
		StringBuilder sb = new StringBuilder();
		for (char c : (raw == null ? "" : raw).toCharArray()) {
			switch (c) {
				case '"' -> sb.append("\\\"");
				case '\\' -> sb.append("\\\\");
				case '\n' -> sb.append("\\n");
				case '\r' -> sb.append("\\r");
				case '\t' -> sb.append("\\t");
				default -> {
					if (c < 0x20) {
						sb.append(String.format("\\u%04x", (int) c));
					} else {
						sb.append(c);
					}
				}
			}
		}
		return sb.toString();
	}
}
