package com.dat.anni.gui;

/**
 * Panel đăng ký với MainPanel có thể nhận thông báo khi được vào / rời khỏi màn hình,
 * dùng để khởi động hoặc dừng game loop (Timer) đúng lúc.
 */
public interface Navigable {

	default void onEnter() {
	}

	default void onLeave() {
	}
}
