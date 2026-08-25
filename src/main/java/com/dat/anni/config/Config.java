package com.dat.anni.config;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Đọc cấu hình từ file .env (nằm ở thư mục chạy app).
 * Thiếu .env thì app vẫn chạy — chỉ mất tính năng login đặc biệt.
 */
public final class Config {

	private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

	public static final Set<String> VALID_USERS = parseUsers(get("APP_VALID_USERS", ""));
	public static final String APP_PASSWORD = get("APP_PASSWORD", "");

	private Config() {
	}

	static Set<String> parseUsers(String raw) {
		if (raw == null || raw.isBlank()) {
			return Set.of();
		}
		return Arrays.stream(raw.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(s -> s.toLowerCase(Locale.ROOT))
				.collect(Collectors.toUnmodifiableSet());
	}

	private static String get(String key, String fallback) {
		String value = DOTENV.get(key);
		return (value == null || value.isBlank()) ? fallback : value.trim();
	}
}
