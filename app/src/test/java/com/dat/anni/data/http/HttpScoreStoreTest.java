package com.dat.anni.data.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dat.anni.data.ScoreEntry;
import com.sun.net.httpserver.HttpServer;

class HttpScoreStoreTest {

	private HttpServer server;
	private String baseUrl;
	private final AtomicReference<String> lastPostBody = new AtomicReference<>();

	@BeforeEach
	void startFakeServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress(0), 0);

		server.createContext("/api/scores", exchange -> {
			lastPostBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
			respond(exchange, 200, "{\"status\":\"ok\"}");
		});
		server.createContext("/api/games/SNAKE/top", exchange ->
				respond(exchange, 200,
						"[{\"username\":\"lan\",\"score\":30},{\"username\":\"dat\",\"score\":25}]"));
		server.createContext("/api/users/dat/best/SNAKE", exchange ->
				respond(exchange, 200, "{\"best\":42}"));

		server.start();
		baseUrl = "http://localhost:" + server.getAddress().getPort() + "/api";
	}

	@AfterEach
	void stopServer() {
		server.stop(0);
	}

	private void respond(com.sun.net.httpserver.HttpExchange exchange, int code, String body)
			throws IOException {
		byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(code, bytes.length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(bytes);
		}
	}

	@Test
	void recordPostsJsonPayload() {
		new HttpScoreStore(baseUrl).record("dat", "SNAKE", 42);
		assertEquals("{\"username\":\"dat\",\"gameId\":\"SNAKE\",\"score\":42}", lastPostBody.get());
	}

	@Test
	void topParsesEntriesInOrder() {
		List<ScoreEntry> top = new HttpScoreStore(baseUrl).top("SNAKE", 2);
		assertEquals(2, top.size());
		assertEquals(new ScoreEntry("lan", 30), top.get(0));
		assertEquals(new ScoreEntry("dat", 25), top.get(1));
	}

	@Test
	void bestReadsNumber() {
		assertEquals(42, new HttpScoreStore(baseUrl).best("dat", "SNAKE"));
	}

	@Test
	void unreachableServerThrowsStoreException() {
		HttpScoreStore store = new HttpScoreStore("http://localhost:1/api");
		try {
			store.top("SNAKE", 3);
			org.junit.jupiter.api.Assertions.fail("phai nem StoreException");
		} catch (HttpScoreStore.StoreException expected) {
			// đúng hợp đồng cho fallback
		}
	}
}
