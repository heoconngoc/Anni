package com.dat.anni.data;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dat.anni.data.repo.ScoreRepository;
import com.dat.anni.data.repo.UserRepository;
import com.dat.anni.data.sqlite.SqliteScoreRepository;
import com.dat.anni.data.sqlite.SqliteUserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreRepositoryTest {

	@TempDir
	Path tempDir;

	private UserRepository users;
	private ScoreRepository scores;

	@BeforeEach
	void setUp() {
		Database.usePath(tempDir.resolve("test.db").toString());
		users = new SqliteUserRepository();
		scores = new SqliteScoreRepository();
	}

	@Test
	void upsertSameUserTwiceKeepsSingleRowAndStableId() {
		long first = users.upsert("chau");
		long second = users.upsert("chau");
		assertEquals(first, second);
	}

	@Test
	void topReturnsHighestPerUserOrderedDesc() {
		long chau = users.upsert("chau");
		long be = users.upsert("be");

		scores.save(chau, "SNAKE", 30);
		scores.save(chau, "SNAKE", 70);
		scores.save(be, "SNAKE", 50);
		scores.save(chau, "PACMAN", 999); // game khác không ảnh hưởng

		List<ScoreEntry> top = scores.top("SNAKE", 10);
		assertEquals(2, top.size());
		assertEquals(new ScoreEntry("chau", 70), top.get(0));
		assertEquals(new ScoreEntry("be", 50), top.get(1));
	}

	@Test
	void topRespectsLimit() {
		long a = users.upsert("a");
		users.upsert("b");
		users.upsert("c");
		scores.save(a, "SNAKE", 1);

		assertEquals(1, scores.top("SNAKE", 1).size());
		assertTrue(scores.top("SNAKE", 5).size() <= 3);
	}

	@Test
	void bestReturnsMaxScoreOrEmpty() {
		long chau = users.upsert("chau");
		scores.save(chau, "FLAPPY_BIRD", 12);
		scores.save(chau, "FLAPPY_BIRD", 40);
		scores.save(chau, "FLAPPY_BIRD", 7);

		assertEquals(40, scores.best(chau, "FLAPPY_BIRD").orElse(-1));
		assertTrue(scores.best(chau, "SNAKE").isEmpty());
	}
}
