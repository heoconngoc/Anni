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

	public static final String GUEST_LETTER = get("LETTER_GUEST", "Chào mừng {name} đến với Just For You Arcade!");
	public static final String SPECIAL_LETTER_1 = get("LETTER_SPECIAL_1", "");
	public static final String SPECIAL_LETTER_2 = get("LETTER_SPECIAL_2", "");
	public static final String SPECIAL_LETTER_3 = get("LETTER_SPECIAL_3", "");

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

	/**
	 * Chuyển template thư thành HTML body: "\n" → xuống dòng, "{name}" → tên người dùng.
	 */
	public static String formatLetter(String template, String name) {
		if (template == null) {
			return "";
		}
		return template.replace("\\n", "<br>").replace("{name}", name == null ? "" : name);
	}

	private static String get(String key, String fallback) {
		String value = DOTENV.get(key);
		return (value == null || value.isBlank()) ? fallback : value.trim();
	}
}
