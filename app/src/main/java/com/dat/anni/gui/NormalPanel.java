package com.dat.anni.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dat.anni.util.UiUtils;

public class NormalPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private Font buttonFont;
	private JLabel lbTitle;
	private JButton btHome, btScores;

	public NormalPanel() {
		super("/gifs/mine.gif");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 20f);
	}

	private void addComps() {
		lbTitle = new JLabel("Lời Tác Giả");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 50));
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(360, 100, 280, 80);
		add(lbTitle);

		btHome = new JButton("Home");
		btHome.setFont(buttonFont);
		btHome.setForeground(Color.BLACK);
		btHome.setBackground(new Color(240, 248, 255));
		btHome.setBounds(400, 520, 140, 50);
		add(btHome);

		btScores = new JButton("Scores");
		btScores.setFont(buttonFont);
		btScores.setForeground(Color.BLACK);
		btScores.setBackground(new Color(240, 248, 255));
		btScores.setBounds(560, 520, 140, 50);
		add(btScores);
	}

	private void addEvents() {
		btHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.HOME);
			}
		});

		btScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.SCORES_HUB);
			}
		});
	}

	protected void paintComponent(Graphics g) {
super.paintComponent(g);

		// Vẽ nền đen mờ
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Kích thước và vị trí của hình chữ nhật bo tròn
		int rectWidth = (int) (getWidth() * 0.8); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.8); // Chiếm 80% chiều cao
		int rectX = (getWidth() - rectWidth) / 2; // Căn giữa theo chiều ngang
		int rectY = (getHeight() - rectHeight) / 2; // Căn giữa theo chiều dọc

		// Thiết lập độ trong suốt
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Độ trong suốt 70%
		g2d.setColor(new Color(0, 0, 0)); // Màu đen

		// Vẽ hình chữ nhật bo tròn
		g2d.fill(new RoundRectangle2D.Float(rectX, rectY, rectWidth, rectHeight, 50, 50));

		g2d.dispose();
	}

}
