package com.dat.anni.data;

import java.util.List;

/**
 * Thử store chính (HTTP) trước; lỗi thì rơi về store dự phòng (SQLite)
 * để app vẫn chơi được offline.
 */
public class FallbackScoreStore implements ScoreStore {

	private final ScoreStore primary;
	private final ScoreStore secondary;

	public FallbackScoreStore(ScoreStore primary, ScoreStore secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}

	@Override
	public void record(String username, String gameId, int score) {
		try {
			primary.record(username, gameId, score);
		} catch (RuntimeException e) {
			secondary.record(username, gameId, score);
		}
	}

	@Override
	public List<ScoreEntry> top(String gameId, int limit) {
		try {
			return primary.top(gameId, limit);
		} catch (RuntimeException e) {
			return secondary.top(gameId, limit);
		}
	}

	@Override
	public int best(String username, String gameId) {
		try {
			return primary.best(username, gameId);
		} catch (RuntimeException e) {
			return secondary.best(username, gameId);
		}
	}
}
