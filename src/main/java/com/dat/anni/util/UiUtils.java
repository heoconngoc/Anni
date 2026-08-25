package com.dat.anni.util;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Tiện ích UI dùng chung. Font được cache theo đường dẫn để tránh nạp lại
 * TTF nhiều lần từ các panel khác nhau.
 */
public final class UiUtils {

	private static final String FALLBACK_FONT = "Arial";
	private static final Map<String, Font> BASE_FONTS = new HashMap<>();

	private UiUtils() {
	}

	public static Font loadFont(String path, float size) {
		return BASE_FONTS.computeIfAbsent(path, UiUtils::loadBaseFont).deriveFont(size);
	}

	private static Font loadBaseFont(String path) {
		try (InputStream stream = UiUtils.class.getResourceAsStream(path)) {
			if (stream != null) {
				return Font.createFont(Font.TRUETYPE_FONT, stream);
			}
			System.err.println("Không tìm thấy font " + path + ", fallback " + FALLBACK_FONT);
		} catch (Exception e) {
			System.err.println("Lỗi nạp font " + path + ": " + e.getMessage() + ", fallback " + FALLBACK_FONT);
		}
		return new Font(FALLBACK_FONT, Font.PLAIN, 12);
	}
}
