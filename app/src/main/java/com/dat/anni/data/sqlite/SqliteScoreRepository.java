package com.dat.anni.data.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import com.dat.anni.data.Database;
import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.repo.ScoreRepository;

public class SqliteScoreRepository implements ScoreRepository {

	@Override
	public void save(long userId, String gameId, int score) {
		try (PreparedStatement ps = Database.get()
				.prepareStatement("INSERT INTO scores(user_id, game_id, score) VALUES (?, ?, ?)")) {
			ps.setLong(1, userId);
			ps.setString(2, gameId);
			ps.setInt(3, score);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new IllegalStateException("Lỗi lưu điểm: " + e.getMessage(), e);
		}
	}

	@Override
	public List<ScoreEntry> top(String gameId, int limit) {
		String sql = """
				SELECT u.username, MAX(s.score) AS best
				FROM scores s JOIN users u ON u.id = s.user_id
				WHERE s.game_id = ?
				GROUP BY s.user_id
				ORDER BY best DESC
				LIMIT ?""";
		try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
			ps.setString(1, gameId);
			ps.setInt(2, limit);
			try (ResultSet rs = ps.executeQuery()) {
				List<ScoreEntry> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new ScoreEntry(rs.getString("username"), rs.getInt("best")));
				}
				return List.copyOf(result);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Lỗi truy vấn top: " + e.getMessage(), e);
		}
	}

	@Override
	public OptionalInt best(long userId, String gameId) {
		String sql = "SELECT MAX(score) FROM scores WHERE user_id = ? AND game_id = ?";
		try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
			ps.setLong(1, userId);
			ps.setString(2, gameId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next() && rs.getObject(1) != null) {
					return OptionalInt.of(rs.getInt(1));
				}
				return OptionalInt.empty();
			}
		} catch (Exception e) {
			throw new IllegalStateException("Lỗi truy vấn best: " + e.getMessage(), e);
		}
	}
}
