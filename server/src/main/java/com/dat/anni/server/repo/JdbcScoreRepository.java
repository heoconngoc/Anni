package com.dat.anni.server.repo;

import java.util.List;
import java.util.OptionalInt;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.repo.ScoreRepository;

@Repository
public class JdbcScoreRepository implements ScoreRepository {

	private final JdbcTemplate jdbc;

	public JdbcScoreRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public void save(long userId, String gameId, int score) {
		jdbc.update("INSERT INTO scores(user_id, game_id, score) VALUES (?, ?, ?)",
				userId, gameId, score);
	}

	@Override
	public List<ScoreEntry> top(String gameId, int limit) {
		return jdbc.query(
				"SELECT u.username AS username, MAX(s.score) AS best "
				+ "FROM scores s JOIN users u ON u.id = s.user_id "
				+ "WHERE s.game_id = ? "
				+ "GROUP BY u.username ORDER BY best DESC LIMIT ?",
				(rs, i) -> new ScoreEntry(rs.getString("username"), rs.getInt("best")),
				gameId, limit);
	}

	@Override
	public OptionalInt best(long userId, String gameId) {
		List<Integer> rows = jdbc.queryForList(
				"SELECT MAX(score) FROM scores WHERE user_id = ? AND game_id = ?",
				Integer.class, userId, gameId);
		if (rows.isEmpty() || rows.get(0) == null) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(rows.get(0));
	}
}
