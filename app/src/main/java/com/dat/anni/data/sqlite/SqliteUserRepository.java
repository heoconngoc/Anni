package com.dat.anni.data.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dat.anni.data.Database;
import com.dat.anni.data.repo.UserRepository;

public class SqliteUserRepository implements UserRepository {

	@Override
	public long upsert(String username) {
		String name = username == null ? "" : username.trim();
		try (PreparedStatement insert = Database.get()
				.prepareStatement("INSERT INTO users(username) VALUES (?) ON CONFLICT(username) DO NOTHING")) {
			insert.setString(1, name);
			insert.executeUpdate();
		} catch (Exception e) {
			throw new IllegalStateException("Lỗi upsert user: " + e.getMessage(), e);
		}
		try (PreparedStatement select = Database.get()
				.prepareStatement("SELECT id FROM users WHERE username = ?")) {
			select.setString(1, name);
			try (ResultSet rs = select.executeQuery()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
				throw new IllegalStateException("Không tìm thấy user vừa upsert: " + name);
			}
		} catch (IllegalStateException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException("Lỗi tra user: " + e.getMessage(), e);
		}
	}
}
