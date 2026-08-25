package com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.dat.anni.data.AppSession;
import com.dat.anni.data.GameCatalog;
import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.ScoreService;
import com.dat.anni.util.UiUtils;

/**
 * Bảng xếp hạng: lưới 6 game × top-3, kèm điểm tốt nhất của người chơi hiện tại.
 */
public class ScoreHubPanel extends BasePanel implements Navigable {

	private static final long serialVersionUID = 1L;

	private static final Color COL_GOLD = new Color(255, 215, 0);
	private static final Color COL_SILVER = new Color(192, 192, 192);
	private static final Color COL_BRONZE = new Color(205, 127, 50);
	private static final Color COL_EMPTY = new Color(110, 118, 140);
	private static final Color COL_GAME = new Color(255, 209, 102);
	private static final Color COL_HEADER = new Color(136, 146, 176);

	private final Font gameFont = UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 20f);
	private final Font cellFont = UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 17f);

	private final JLabel[] gameLabels = new JLabel[GameCatalog.values().length];
	private final JLabel[][] podium = new JLabel[GameCatalog.values().length][3];
	private JLabel lbPlayer;

	public ScoreHubPanel() {
		super(null);
		setBackground(new Color(16, 20, 36));
		addComps();
	}

	private void addComps() {
		JLabel lbTitle = new JLabel("HIGH SCORES", JLabel.CENTER);
		lbTitle.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 30f));
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(250, 28, 500, 55);
		add(lbTitle);

		lbPlayer = new JLabel("", JLabel.CENTER);
		lbPlayer.setFont(UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 18f));
		lbPlayer.setForeground(new Color(127, 255, 212));
		lbPlayer.setBounds(250, 86, 500, 28);
		add(lbPlayer);

		String[] headers = {"GAME", "1ST", "2ND", "3RD"};
		int[] headerX = {70, 340, 550, 760};
		for (int i = 0; i < headers.length; i++) {
			JLabel lbHeader = new JLabel(headers[i], JLabel.CENTER);
			lbHeader.setFont(gameFont.deriveFont(Font.BOLD, 15f));
			lbHeader.setForeground(COL_HEADER);
			lbHeader.setBounds(headerX[i], 124, i == 0 ? 240 : 200, 26);
			add(lbHeader);
		}

		GameCatalog[] games = GameCatalog.values();
		int y = 158;
		int rowHeight = 74;
		for (int i = 0; i < games.length; i++) {
			JLabel lbGame = new JLabel();
			lbGame.setFont(gameFont);
			lbGame.setForeground(COL_GAME);
			lbGame.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			lbGame.setBounds(70, y, 250, rowHeight);
			add(lbGame);
			gameLabels[i] = lbGame;

			for (int p = 0; p < 3; p++) {
				JLabel cell = new JLabel("", JLabel.CENTER);
				cell.setFont(cellFont);
				cell.setForeground(p == 0 ? COL_GOLD : p == 1 ? COL_SILVER : COL_BRONZE);
				cell.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
				cell.setBounds(340 + p * 210, y, 200, rowHeight);
				add(cell);
				podium[i][p] = cell;
			}
			y += rowHeight;
		}

		JButton btHome = new JButton("BACK");
		btHome.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 15f));
		btHome.setForeground(Color.BLACK);
		btHome.setBackground(new Color(240, 248, 255));
		btHome.setBounds(430, 622, 140, 48);
		btHome.addActionListener(e -> main.show(MainPanel.HOME));
		add(btHome);
	}

	@Override
	public void onEnter() {
		ScoreService service = ScoreService.get();
		GameCatalog[] games = GameCatalog.values();

		lbPlayer.setText("Player: " + AppSession.currentUser());

		for (int i = 0; i < games.length; i++) {
			GameCatalog game = games[i];
			List<ScoreEntry> top = service.top(game, 3);
			int mine = service.bestForCurrentUser(game);

			String mineHtml = mine > 0
					? "<small><font color='#7fffd4'>you: " + mine + "</font></small>"
					: "<small><font color='" + Integer.toHexString(COL_EMPTY.getRGB() & 0xFFFFFF)
							+ "'>not played yet</font></small>";
			gameLabels[i].setText("<html><div style='text-align:left'><b>"
					+ game.displayName() + "</b><br>" + mineHtml + "</div></html>");

			for (int p = 0; p < 3; p++) {
				if (p < top.size()) {
					ScoreEntry entry = top.get(p);
					podium[i][p].setText("<html><b>" + escape(entry.username())
							+ "</b><br><span style='font-size:11px'>" + entry.score()
							+ " pts</span></html>");
				} else {
					podium[i][p].setForeground(COL_EMPTY);
					podium[i][p].setText("<html>—</html>");
				}
			}
		}
	}

	private static String escape(String raw) {
		return raw.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
