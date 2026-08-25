package com.dat.anni.gui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * D13: moi wrapper game dieu khien bang phim PHAI goi requestFocusInWindow()
 * len component game ben trong onEnter(). Day la loi da xay ra that (ban phim
 * chet sau khi doi sang CardLayout named navigation).
 */
class WrapperFocusConventionTest {

	private static final String[] KEY_DRIVEN_WRAPPERS = {
			"src/main/java/com/dat/anni/game/pacman/PacManPanel.java",
			"src/main/java/com/dat/anni/game/spaceinvaders/SpaceInvadersPanel.java",
			"src/main/java/com/dat/anni/game/chormedinosaur/ChromeDinosaurPanel.java",
			"src/main/java/com/dat/anni/game/flappybird/FlappyBirdPanel.java",
			"src/main/java/com/dat/anni/game/snake/SnakeGamePanel.java"
	};

	private static final Pattern ON_ENTER_BODY =
			Pattern.compile("public void onEnter\\(\\)\\s*\\{(.*?)\\n\\t\\}", Pattern.DOTALL);

	@Test
	void everyKeyDrivenWrapperGrantsFocusInOnEnter() throws IOException {
		for (String file : KEY_DRIVEN_WRAPPERS) {
			Path path = Path.of(file);
			assertTrue(Files.exists(path), "Khong tim thay source: " + file);
			String src = Files.readString(path);

			Matcher m = ON_ENTER_BODY.matcher(src);
			assertTrue(m.find(), file + " phai override onEnter()");
			assertTrue(m.group(1).contains("requestFocusInWindow()"),
					file + ": onEnter() phai goi requestFocusInWindow() (xem D13)");
		}
	}

	@Test
	void mouseDrivenWrappersAreNotRequiredToGrantFocus() {
		for (String file : new String[] {
				"src/main/java/com/dat/anni/game/whacamole/WhacAMolePanel.java",
				"src/main/java/com/dat/anni/game/minesweeper/MinesweeperPanel.java",
				"src/main/java/com/dat/anni/game/matchcard/MatchCardPanel.java"
		}) {
			Path path = Path.of(file);
			assertTrue(Files.exists(path), "Khong tim thay source: " + file);
		}
	}
}
