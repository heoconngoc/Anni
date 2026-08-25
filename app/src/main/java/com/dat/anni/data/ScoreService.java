package com.dat.anni.data;

import java.util.List;

import com.dat.anni.config.Config;
import com.dat.anni.data.http.HttpScoreStore;

/**
 * Facade cho GUI/game: ghi điểm và đọc bảng xếp hạng.
 * Phase 8: nếu .env có API_URL thì ưu tiên HTTP tới server, lỗi thì
 * tự rơi về SQLite local. Không có API_URL → thuần offline như cũ.
 */
public final class ScoreService {

	private static ScoreService instance;

	private final ScoreStore store;

	private ScoreService(ScoreStore store) {
		this.store = store;
	}

	public static synchronized ScoreService get() {
		if (instance == null) {
			ScoreStore local = new SqliteScoreStore();
			String apiUrl = Config.API_URL;
			instance = new ScoreService(apiUrl.isBlank()
					? local
					: new FallbackScoreStore(new HttpScoreStore(apiUrl), local));
		}
		return instance;
	}

	/** Ghi một lượt chơi của người dùng hiện tại (bỏ qua nếu điểm <= 0). */
	public void record(GameCatalog game, int score) {
		if (score <= 0) {
			return;
		}
		store.record(AppSession.currentUser(), game.id(), score);
	}

	/** Top bảng xếp hạng của một game. */
	public List<ScoreEntry> top(GameCatalog game, int limit) {
		return store.top(game.id(), limit);
	}

	/** Điểm cao nhất của người dùng hiện tại trong một game (0 nếu chưa có). */
	public int bestForCurrentUser(GameCatalog game) {
		return store.best(AppSession.currentUser(), game.id());
	}
}
