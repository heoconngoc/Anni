package com.dat.anni.data;

import java.util.List;

import com.dat.anni.data.repo.ScoreRepository;
import com.dat.anni.data.repo.UserRepository;
import com.dat.anni.data.sqlite.SqliteScoreRepository;
import com.dat.anni.data.sqlite.SqliteUserRepository;

/**
 * Facade cho GUI/game: ghi điểm và đọc bảng xếp hạng.
 * Phase 8 chỉ cần thay implementation bên dưới bằng bản HTTP — GUI không đổi.
 */
public final class ScoreService {

	private static ScoreService instance;

	private final UserRepository users;
	private final ScoreRepository scores;

	private ScoreService(UserRepository users, ScoreRepository scores) {
		this.users = users;
		this.scores = scores;
	}

	public static synchronized ScoreService get() {
		if (instance == null) {
			instance = new ScoreService(new SqliteUserRepository(), new SqliteScoreRepository());
		}
		return instance;
	}

	/** Ghi một lượt chơi của người dùng hiện tại (bỏ qua nếu điểm <= 0). */
	public void record(GameCatalog game, int score) {
		if (score <= 0) {
			return;
		}
		long userId = users.upsert(AppSession.currentUser());
		scores.save(userId, game.id(), score);
	}

	/** Top bảng xếp hạng của một game. */
	public List<ScoreEntry> top(GameCatalog game, int limit) {
		return scores.top(game.id(), limit);
	}

	/** Điểm cao nhất của người dùng hiện tại trong một game (0 nếu chưa có). */
	public int bestForCurrentUser(GameCatalog game) {
		long userId = users.upsert(AppSession.currentUser());
		return scores.best(userId, game.id()).orElse(0);
	}
}
