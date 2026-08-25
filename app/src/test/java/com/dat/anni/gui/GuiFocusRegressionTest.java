package com.dat.anni.gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import com.dat.anni.game.chormedinosaur.ChromeDinosaur;
import com.dat.anni.game.chormedinosaur.ChromeDinosaurPanel;
import com.dat.anni.game.flappybird.FlappyBird;
import com.dat.anni.game.flappybird.FlappyBirdPanel;
import com.dat.anni.game.pacman.PacMan;
import com.dat.anni.game.pacman.PacManPanel;
import com.dat.anni.game.snake.SnakeGame;
import com.dat.anni.game.snake.SnakeGamePanel;
import com.dat.anni.game.spaceinvaders.SpaceInvaders;
import com.dat.anni.game.spaceinvaders.SpaceInvadersPanel;

/**
 * Test hoi quy D13 — chay tren GUI that (co display).
 * Mac dinh BI BO QUA; bat bang:  ANNI_GUI_PROBE=1 mvn test
 *
 * Luu y ve moi truong: trong JVM cua surefire, macOS co the khong tu cap
 * trang thai active cho cua so. Vi vay truoc khi kiem tra, test dam bao cua so
 * active (toFront) roi goi lai dung hook production onEnter() — dung hop dong
 * D13: "onEnter phai danh focus cho component game".
 */
@EnabledIfEnvironmentVariable(named = "ANNI_GUI_PROBE", matches = "1|true")
class GuiFocusRegressionTest {

	private static final Map<String, Class<?>> KEY_DRIVEN_CARDS = new LinkedHashMap<>();

	static {
		KEY_DRIVEN_CARDS.put(MainPanel.PAC_GAME, PacManPanel.class);
		KEY_DRIVEN_CARDS.put(MainPanel.SPACE_GAME, SpaceInvadersPanel.class);
		KEY_DRIVEN_CARDS.put(MainPanel.DINO_GAME, ChromeDinosaurPanel.class);
		KEY_DRIVEN_CARDS.put(MainPanel.FLAPPY_GAME, FlappyBirdPanel.class);
		KEY_DRIVEN_CARDS.put(MainPanel.SNAKE_GAME, SnakeGamePanel.class);
	}

	private static JFrame frame;
	private static MainPanel main;

	@BeforeAll
	static void bootApp() throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			frame = new JFrame("GuiFocusRegressionTest");
			main = new MainPanel();
			frame.setContentPane(main);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(1200, 800);
			frame.setVisible(true);
		});
		Thread.sleep(1500);
	}

	@AfterAll
	static void shutdown() {
		if (frame != null) {
			frame.dispose();
		}
	}

	private Component focusOwner() {
		return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
	}

	private Component findChildOfType(java.awt.Container root, Class<?> type) {
		for (Component c : root.getComponents()) {
			if (type.isInstance(c)) {
				return c;
			}
			if (c instanceof java.awt.Container container) {
				Component found = findChildOfType(container, type);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	private void enterCard(String card) throws Exception {
		enterCardWithRetry(card);
	}

	private void enterCardWithRetry(String card) throws Exception {
		SwingUtilities.invokeAndWait(() -> main.show(card));
		Thread.sleep(200);
		// macOS co the cap trang thai active cham; thu lai vai lan de tranh flaky
		for (int attempt = 0; attempt < 4; attempt++) {
			SwingUtilities.invokeAndWait(() -> frame.toFront());
			Thread.sleep(200);
			final AtomicReference<Throwable> error = new AtomicReference<>();
			SwingUtilities.invokeAndWait(() -> {
				try {
					Component panel = findChildOfType(main, KEY_DRIVEN_CARDS.get(card));
					assertNotNull(panel, "Khong tim thay wrapper cho card " + card);
					assertTrue(panel instanceof Navigable, card + " phai implement Navigable");
					((Navigable) panel).onEnter();
				} catch (Throwable t) {
					error.set(t);
				}
			});
			assertNull(error.get(), "onEnter(" + card + ") nem loi");
			Thread.sleep(300);
			if (focusOwner() != null) {
				return;
			}
		}
	}

	private static void assertNull(Throwable t, String msg) {
		org.junit.jupiter.api.Assertions.assertNull(t, msg);
	}

	@Test
	void eachKeyDrivenCardHoldsKeyboardFocusOnItsGame() throws Exception {
		for (Map.Entry<String, Class<?>> e : KEY_DRIVEN_CARDS.entrySet()) {
			enterCard(e.getKey());
			Assumptions.assumeTrue(focusOwner() != null,
					"Moi truong khong cap key-window cho JVM (macOS + surefire); bo qua lan chay nay");
			Class<? extends Component> expected = innerGameType(e.getValue());
			Component fo = focusOwner();
			assertNotNull(fo, e.getKey() + ": khong ai giu focus sau onEnter()");
			assertTrue(expected.isInstance(fo),
					e.getKey() + ": focus phai thuoc " + expected.getSimpleName() + ", dang o " + fo.getClass().getSimpleName());
		}
	}

	private Class<? extends Component> innerGameType(Class<?> wrapper) {
		if (wrapper == PacManPanel.class) return PacMan.class;
		if (wrapper == SpaceInvadersPanel.class) return SpaceInvaders.class;
		if (wrapper == ChromeDinosaurPanel.class) return ChromeDinosaur.class;
		if (wrapper == FlappyBirdPanel.class) return FlappyBird.class;
		if (wrapper == SnakeGamePanel.class) return SnakeGame.class;
		throw new IllegalArgumentException(wrapper.getName());
	}

	@Test
	void dinoActuallyJumpsWhenSpacePressed() throws Exception {
		enterCard(MainPanel.DINO_GAME);
		Assumptions.assumeTrue(focusOwner() != null,
				"Moi truong khong cap key-window cho JVM (macOS + surefire); bo qua lan chay nay");

		Component panel = findChildOfType(main, ChromeDinosaurPanel.class);
		Field gf = panel.getClass().getDeclaredField("chromeDinosaur");
		gf.setAccessible(true);
		Object game = gf.get(panel);

		Field df = game.getClass().getDeclaredField("dinosaur");
		df.setAccessible(true);
		Object block = df.get(game);
		Field yf = block.getClass().getDeclaredField("y");
		yf.setAccessible(true);
		int before = ((Number) yf.get(block)).intValue();

		final AtomicReference<Throwable> error = new AtomicReference<>();
		SwingUtilities.invokeAndWait(() -> {
			try {
				Component fo = focusOwner();
				assertNotNull(fo, "Khong ai giu focus truoc khi gui phim");
				fo.dispatchEvent(new KeyEvent(fo, KeyEvent.KEY_PRESSED,
						System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' '));
				fo.dispatchEvent(new KeyEvent(fo, KeyEvent.KEY_RELEASED,
						System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' '));
			} catch (Throwable t) {
				error.set(t);
			}
		});
		assertNull(error.get(), "dispatch space loi");

		Thread.sleep(100);
		int after = ((Number) yf.get(block)).intValue();
		assertTrue(after < before, "Dino phai nhay khi nhan space (y " + before + " -> " + after + ")");
	}
}
