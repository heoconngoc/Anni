package com.dat.anni.server.web;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.anni.data.GameCatalog;
import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.repo.ScoreRepository;
import com.dat.anni.data.repo.UserRepository;

@RestController
@RequestMapping("/api")
public class ScoreController {

	private static final int DEFAULT_LIMIT = 3;
	private static final int MAX_LIMIT = 100;

	private final UserRepository users;
	private final ScoreRepository scores;

	public ScoreController(UserRepository users, ScoreRepository scores) {
		this.users = users;
		this.scores = scores;
	}

	public record SubmitRequest(String username, String gameId, int score) {
	}

	@PostMapping("/scores")
	public ResponseEntity<Map<String, Object>> submit(@RequestBody SubmitRequest req) {
		if (req == null || isBlank(req.username()) || isBlank(req.gameId())
				|| req.score() < 0) {
			return ResponseEntity.badRequest().body(Map.of("error", "username/gameId bat buoc, score >= 0"));
		}
		long userId = users.upsert(req.username().trim());
		scores.save(userId, req.gameId().trim(), req.score());
		return ResponseEntity.ok(Map.of("status", "ok"));
	}

	@GetMapping("/games/{gameId}/top")
	public List<ScoreEntry> top(@PathVariable String gameId,
			@RequestParam(defaultValue = "3") int limit) {
		int capped = Math.min(Math.max(limit, 1), MAX_LIMIT);
		return scores.top(gameId, capped);
	}

	@GetMapping("/users/{username}/best/{gameId}")
	public Map<String, Integer> best(@PathVariable String username,
			@PathVariable String gameId) {
		long userId = users.upsert(username.trim());
		return Map.of("best", scores.best(userId, gameId).orElse(0));
	}

	@GetMapping("/games")
	public List<Map<String, String>> games() {
		return java.util.Arrays.stream(GameCatalog.values())
				.map(g -> Map.of("id", g.id(), "displayName", g.displayName()))
				.toList();
	}

	private static boolean isBlank(String s) {
		return s == null || s.isBlank();
	}
}
