package com.dat.anni.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Server chết → client tự rơi về SQLite local, không crash.
 */
class FallbackScoreStoreTest {

	private static class BrokenStore implements ScoreStore {
		@Override
		public void record(String username, String gameId, int score) {
			throw new IllegalStateException("server chet");
		}

		@Override
		public List<ScoreEntry> top(String gameId, int limit) {
			throw new IllegalStateException("server chet");
		}

		@Override
		public int best(String username, String gameId) {
			throw new IllegalStateException("server chet");
		}
	}

	@Test
	void fallsBackWhenPrimaryFails() {
		ScoreStore fallback = new FallbackScoreStore(
				new BrokenStore(),
				new ScoreStore() {
					@Override
					public void record(String username, String gameId, int score) {
						// ok
					}

					@Override
					public List<ScoreEntry> top(String gameId, int limit) {
						return List.of(new ScoreEntry("offline", 7));
					}

					@Override
					public int best(String username, String gameId) {
						return 9;
					}
				});

		fallback.record("dat", "SNAKE", 42);
		assertEquals(List.of(new ScoreEntry("offline", 7)), fallback.top("SNAKE", 3));
		assertEquals(9, fallback.best("dat", "SNAKE"));
		assertTrue(true);
	}
}
