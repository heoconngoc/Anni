package com.dat.anni.data;

import java.util.List;

import com.dat.anni.data.sqlite.SqliteScoreRepository;
import com.dat.anni.data.sqlite.SqliteUserRepository;

/**
 * Store offline: ghi thẳng SQLite local qua tầng repo Phase 5.
 */
public class SqliteScoreStore implements ScoreStore {

	private final SqliteUserRepository users = new SqliteUserRepository();
	private final SqliteScoreRepository scores = new SqliteScoreRepository();

	@Override
	public void record(String username, String gameId, int score) {
		scores.save(users.upsert(username), gameId, score);
	}

	@Override
	public List<ScoreEntry> top(String gameId, int limit) {
		return scores.top(gameId, limit);
	}

	@Override
	public int best(String username, String gameId) {
		return scores.best(users.upsert(username), gameId).orElse(0);
	}
}
