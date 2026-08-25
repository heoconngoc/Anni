package com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.dat.anni.data.GameCatalog;
import com.dat.anni.data.ScoreEntry;
import com.dat.anni.data.ScoreService;
import com.dat.anni.util.UiUtils;

/**
 * Bảng xếp hạng: top người chơi mỗi game + điểm tốt nhất của người hiện tại.
 */
public class ScoreHubPanel extends BasePanel implements Navigable {

	private static final long serialVersionUID = 1L;

	private final Font titleFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 40f);
	private final Font normalFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 14f);
	private final Font smallFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 12f);

	private final JLabel[] rows = new JLabel[GameCatalog.values().length];

	public ScoreHubPanel() {
		super(null);
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		setBackground(new Color(20, 24, 40));
	}

	private void addComps() {
		JLabel lbTitle = new JLabel("HIGH SCORES", JLabel.CENTER);
		lbTitle.setFont(titleFont);
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(300, 60, 400, 80);
		add(lbTitle);

		GameCatalog[] games = GameCatalog.values();
		int rowHeight = 62;
		int y = 170;
		for (int i = 0; i < games.length; i++) {
			rows[i] = new JLabel();
			rows[i].setFont(normalFont);
			rows[i].setForeground(Color.WHITE);
			rows[i].setBounds(230, y, 560, rowHeight);
			add(rows[i]);
			y += rowHeight + 6;
		}

		JButton btBack = new JButton("Back");
		btBack.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f));
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(430, 600, 140, 50);
		btBack.addActionListener(e -> main.show(MainPanel.MENU));
		add(btBack);
	}

	private void addEvents() {
		// sự kiện duy nhất (nút Back) đã gắn trực tiếp ở addComps
	}

	@Override
	public void onEnter() {
		ScoreService service = ScoreService.get();
		GameCatalog[] games = GameCatalog.values();
		for (int i = 0; i < games.length; i++) {
			rows[i].setText(renderRow(service, games[i]));
		}
	}

	private String renderRow(ScoreService service, GameCatalog game) {
		List<ScoreEntry> top = service.top(game, 3);
		int mine = service.bestForCurrentUser(game);
		StringBuilder html = new StringBuilder("<html><b>");
		html.append(game.displayName()).append("</b> &nbsp; ");
		if (top.isEmpty()) {
			html.append("<font color='gray'>chua co du lieu</font>");
		} else {
			for (int i = 0; i < top.size(); i++) {
				ScoreEntry entry = top.get(i);
				html.append(i + 1).append(". ").append(entry.username()).append(": ").append(entry.score())
						.append(" &nbsp; ");
			}
		}
		if (mine > 0) {
			html.append("<br><font color='#7fffd4' size='" + (int) (smallFont.getSize()) + "'>ban: ")
					.append(mine).append("</font>");
		}
		html.append("</html>");
		return html.toString();
	}
}
