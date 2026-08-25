package com.dat.anni.server.repo;

import java.util.List;
import java.util.OptionalInt;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.repo.ScoreRepository;
import com.dat.anni.data.repo.UserRepository;

/**
 * Impl JDBC chay tren H2 (dev) hoac PostgreSQL (prod) — cung schema,
 * cung hop dong voi client SQLite.
 */
@Repository
public class JdbcUserRepository implements UserRepository {

	private final JdbcTemplate jdbc;

	public JdbcUserRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	@Transactional
	public long upsert(String username) {
		List<Long> ids = jdbc.queryForList(
				"SELECT id FROM users WHERE username = ?", Long.class, username);
		if (!ids.isEmpty()) {
			return ids.get(0);
		}
		jdbc.update("INSERT INTO users(username) VALUES (?)", username);
		return jdbc.queryForObject(
				"SELECT id FROM users WHERE username = ?", Long.class, username);
	}
}
