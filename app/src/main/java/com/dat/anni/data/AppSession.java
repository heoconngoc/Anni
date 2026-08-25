package com.dat.anni.data;

/**
 * Người chơi hiện tại của phiên chạy app. Được set khi đăng nhập ở MenuPanel,
 * mặc định là khách.
 */
public final class AppSession {

	private static String currentUser = "khách";

	private AppSession() {
	}

	public static void login(String username) {
		if (username != null && !username.isBlank()) {
			currentUser = username.trim();
		}
	}

	public static String currentUser() {
		return currentUser;
	}

	public static void logout() {
		currentUser = "khách";
	}
}
