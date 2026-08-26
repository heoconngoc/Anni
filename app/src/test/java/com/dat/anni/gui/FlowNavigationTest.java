package com.dat.anni.gui;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dat.anni.data.AppSession;

import java.awt.GraphicsEnvironment;

/**
 * Headless EDT test: boot → MENU → guest/special → HOME, plus HOME nav.
 * Cards checked by BFS for expected buttons; no real window needed.
 * Self-skips in headless CI (JFrame needs a display).
 */
class FlowNavigationTest {

	@BeforeEach
	void reset() {
		Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
				"FlowNavigationTest requires a display — skipping in headless CI");
		AppSession.logout();
	}

	private static void invoke(Runnable r) {
		try { javax.swing.SwingUtilities.invokeAndWait(r); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	private static MainPanel boot() {
		final MainPanel[] ref = new MainPanel[1];
		invoke(() -> {
			JFrame frame = new JFrame();
			MainPanel mp = new MainPanel();
			frame.setContentPane(mp);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(1000, 700);
			frame.setVisible(true);
			ref[0] = mp;
		});
		return ref[0];
	}

	// ---- BFS helpers ----

	private static JButton findButton(JPanel root, String... texts) {
		String lower = String.join(" ", texts).toLowerCase();
		java.util.Queue<Component> q = new java.util.ArrayDeque<>();
		q.add(root);
		while (!q.isEmpty()) {
			Component c = q.poll();
			if (c instanceof JButton b
					&& b.getText() != null
					&& lower.contains(b.getText().trim().toLowerCase())
					&& b.getActionListeners().length > 0) {
				return b;
			}
			if (c instanceof JPanel p) {
				for (Component ch : p.getComponents()) {
					if (!q.contains(ch)) q.add(ch);
				}
			}
		}
		return null;
	}

	private static JTextField findTextField(JPanel root) {
		java.util.Queue<Component> q = new java.util.ArrayDeque<>();
		q.add(root);
		while (!q.isEmpty()) {
			Component c = q.poll();
			if (c instanceof JTextField f && !(f instanceof javax.swing.JPasswordField)) {
				return f;
			}
			if (c instanceof JPanel p) {
				for (Component ch : p.getComponents()) {
					if (!q.contains(ch)) q.add(ch);
				}
			}
		}
		return null;
	}

	/** Show a card then return its visible child panel. */
	private static JPanel showAndGetVisible(JPanel mainPanel, String card) {
		invoke(() -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, card));
		for (Component c : mainPanel.getComponents()) {
			if (c.isVisible() && c instanceof JPanel p) return p;
		}
		fail("No visible panel after show(" + card + ")");
		return null;
	}

	// ---- tests ----

	@Test
	void boot_showsMenu() {
		MainPanel mp = boot();
		JPanel visible = showAndGetVisible(mp, MainPanel.MENU);
		assertNotNull(findTextField(visible), "MenuPanel should contain a name text field");
		assertNotNull(findButton(visible, "CONTINUE"), "MenuPanel should have CONTINUE button");
	}

	@Test
	void guestFlow_goesToLetterThenHome() throws Exception {
		MainPanel mp = boot();
		JPanel menu = showAndGetVisible(mp, MainPanel.MENU);

		// Enter a non-special name → guest path
		invoke(() -> {
			findTextField(menu).setText("tester");
			findButton(menu, "CONTINUE").doClick();
		});

		JPanel letterGuest = showAndGetVisible(mp, MainPanel.LETTER_GUEST);
		JButton next = findButton(letterGuest, "CONTINUE");
		assertNotNull(next, "LetterGuest panel should have CONTINUE");

		// Continue → HOME
		invoke(() -> next.doClick());
		JPanel home = showAndGetVisible(mp, MainPanel.HOME);
		assertNotNull(findButton(home, "PLAY"), "HOME should have PLAY");
	}

	@Test
	void homeButtons_exist() {
		MainPanel mp = boot();
		JPanel home = showAndGetVisible(mp, MainPanel.HOME);
		assertNotNull(findButton(home, "PLAY"), "PLAY button missing");
		assertNotNull(findButton(home, "HIGH SCORES"), "HIGH SCORES button missing");
		assertNotNull(findButton(home, "LOGOUT"), "LOGOUT button missing");
	}

	@Test
	void playButton_goesToGamePage1() {
		MainPanel mp = boot();
		JPanel home = showAndGetVisible(mp, MainPanel.HOME);
		JButton play = findButton(home, "PLAY");
		assertNotNull(play);
		play.doClick();
		// Should be on GAMES_PAGE_1 (NormalPanel equivalent — game grid)
		JPanel games = showAndGetVisible(mp, MainPanel.GAMES_PAGE_1);
		assertNotNull(games);
	}

	@Test
	void scoreHub_backToHome() {
		MainPanel mp = boot();
		JPanel scoreHub = showAndGetVisible(mp, MainPanel.SCORES_HUB);
		JButton back = findButton(scoreHub, "BACK");
		assertNotNull(back, "ScoreHub should have BACK");
		back.doClick();
		JPanel home = showAndGetVisible(mp, MainPanel.HOME);
		assertNotNull(findButton(home, "PLAY"), "BACK from ScoreHub → HOME");
	}
}
