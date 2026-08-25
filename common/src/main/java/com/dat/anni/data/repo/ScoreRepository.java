package com.dat.anni.data.repo;

import java.util.List;
import java.util.OptionalInt;

import com.dat.anni.data.ScoreEntry;

/**
 * Repository điểm số. Điểm cao hơn = tốt hơn (các game thời gian/lượt đi
 * ít hơn sẽ được đưa vào ở phase sau với quy ước riêng).
 */
public interface ScoreRepository {

	void save(long userId, String gameId, int score);

	/** Top điểm cao nhất của một game (mỗi user tính theo điểm cao nhất của user đó). */
	List<ScoreEntry> top(String gameId, int limit);

	/** Điểm cao nhất của một user trong một game. */
	OptionalInt best(long userId, String gameId);
}
