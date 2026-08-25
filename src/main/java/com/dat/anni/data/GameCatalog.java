package com.dat.anni.data;

/**
 * Danh mục game có lưu điểm trong hệ thống.
 */
public enum GameCatalog {
	SNAKE("Snake"),
	PACMAN("Pac-Man"),
	SPACE_INVADERS("Space Invaders"),
	DINOSAUR("Chrome Dinosaur"),
	FLAPPY_BIRD("Flappy Bird"),
	WHAC_A_MOLE("Whac A Mole");

	private final String displayName;

	GameCatalog(String displayName) {
		this.displayName = displayName;
	}

	public String id() {
		return name();
	}

	public String displayName() {
		return displayName;
	}
}
