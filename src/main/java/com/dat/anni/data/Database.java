package com.dat.anni.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.dat.anni.config.Config;

/**
 * Kết nối SQLite + khởi tạo schema. Một connection duy nhất cho cả app
 * (desktop, đơn luồng EDT là chính) — đủ dùng tới Phase 8.
 */
public final class Database {

	private static Connection connection;
	private static String overridePath;

	private Database() {
	}

	/** Chỉ dùng trong unit test: trỏ database sang file khác và mở lại từ đầu. */
	public static synchronized void usePath(String path) {
		overridePath = path;
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception ignored) {
				// đóng tạm, lỗi bỏ qua
			}
			connection = null;
		}
	}

	public static synchronized Connection get() {
		if (connection == null) {
			open();
		}
		return connection;
	}

	private static void open() {
		try {
			Path path = Path.of(overridePath != null ? overridePath : Config.DB_PATH);
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			try (Statement stmt = connection.createStatement()) {
				stmt.executeUpdate("""
						CREATE TABLE IF NOT EXISTS users (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							username TEXT NOT NULL UNIQUE,
							created_at TEXT NOT NULL DEFAULT (datetime('now'))
						)""");
				stmt.executeUpdate("""
						CREATE TABLE IF NOT EXISTS scores (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							user_id INTEGER NOT NULL REFERENCES users(id),
							game_id TEXT NOT NULL,
							score INTEGER NOT NULL,
							played_at TEXT NOT NULL DEFAULT (datetime('now'))
						)""");
				stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_scores_game ON scores(game_id, score DESC)");
			}
		} catch (Exception e) {
			throw new IllegalStateException("Không mở được database: " + e.getMessage(), e);
		}
	}
}
