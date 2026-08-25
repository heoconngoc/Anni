package com.dat.anni.data.repo;

/**
 * Repository người chơi. Trả về id nội bộ của user, tự tạo mới nếu chưa có.
 */
public interface UserRepository {

	long upsert(String username);
}
