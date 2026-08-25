package com.dat.anni.data;

import java.util.List;

/**
 * Nguồn lưu trữ điểm phía client. Phase 8: có thể là SQLite local hoặc
 * HTTP tới server. GUI vẫn chỉ đụng ScoreService (D10).
 */
public interface ScoreStore {

	void record(String username, String gameId, int score);

	List<ScoreEntry> top(String gameId, int limit);

	/** Điểm cao nhất của user trong game (0 nếu chưa có). */
	int best(String username, String gameId);
}
