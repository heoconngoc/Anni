package com.dat.anni.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.dat.anni.data.AppSession;
import com.dat.anni.util.UiUtils;

/**
 * Màn hình chính sau khi vào tên: chơi game, xem bảng xếp hạng, đọc thư cảm ơn.
 */
public class HomePanel extends BasePanel implements Navigable {

	private static final long serialVersionUID = 1L;

	private JLabel lbGreeting;

	public HomePanel() {
		super("/gifs/mine.gif");
		addComps();
	}

	private void addComps() {
		JLabel lbTitle1 = new JLabel("JUST FOR YOU");
		lbTitle1.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 34f));
		lbTitle1.setForeground(new Color(255, 165, 0));
		lbTitle1.setBounds(300, 110, 500, 50);
		add(lbTitle1);

		JLabel lbTitle2 = new JLabel("ARCADE");
		lbTitle2.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 46f));
		lbTitle2.setForeground(Color.CYAN);
		lbTitle2.setBounds(360, 165, 400, 70);
		add(lbTitle2);

		lbGreeting = new JLabel("", javax.swing.SwingConstants.CENTER);
		lbGreeting.setFont(UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 24f));
		lbGreeting.setForeground(Color.WHITE);
		lbGreeting.setBounds(250, 260, 500, 40);
		add(lbGreeting);

		addMenuButton("PLAY", 330, () -> main.show(MainPanel.GAMES_PAGE_1));
		addMenuButton("HIGH SCORES", 400, () -> main.show(MainPanel.SCORES_HUB));
		addMenuButton("THANK-YOU LETTER", 470, () -> main.show(MainPanel.LETTER_THANKS));
		addMenuButton("LOGOUT", 540, () -> {
			com.dat.anni.data.AppSession.logout();
			main.show(MainPanel.MENU);
		});
	}

	private void addMenuButton(String text, int y, Runnable action) {
		JButton bt = new JButton(text);
		bt.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f));
		bt.setForeground(Color.BLACK);
		bt.setBackground(new Color(240, 248, 255));
		bt.setBounds(340, y, 320, 56);
		bt.addActionListener(e -> action.run());
		add(bt);
	}

	@Override
	public void onEnter() {
		lbGreeting.setText("Hi, " + AppSession.currentUser() + "! What shall we play today?");
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
		int rectWidth = (int) (getWidth() * 0.62);
		int rectHeight = (int) (getHeight() * 0.78);
		int rectX = (getWidth() - rectWidth) / 2;
		int rectY = (getHeight() - rectHeight) / 2;
		g2d.setComposite(java.awt.AlphaComposite.getInstance(
				java.awt.AlphaComposite.SRC_OVER, 0.55f));
		g2d.setColor(Color.BLACK);
		g2d.fill(new java.awt.geom.RoundRectangle2D.Float(
				rectX, rectY, rectWidth, rectHeight, 50, 50));
		g2d.dispose();
	}
}
