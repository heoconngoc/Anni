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

import com.dat.anni.config.Config;

import com.dat.anni.util.UiUtils;

public class Special2Panel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private Font buttonFont, normalFont;
	private JLabel lbLetter;
	private JButton btBack, btNext;

	public Special2Panel() {
		super("/gifs/mine.gif");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);

		normalFont = UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 18f);
	}

	private void addComps() {
		lbLetter = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ Config.formatLetter(Config.SPECIAL_LETTER_2, "") + "</div>" + "</html>");
		lbLetter.setFont(normalFont);
		lbLetter.setForeground(Color.WHITE);
		lbLetter.setBounds(120, 60, 750, 500);
		add(lbLetter);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(390, 550, 100, 40);
		add(btBack);

		btNext = new JButton("Next");
		btNext.setFont(buttonFont);
		btNext.setForeground(Color.BLACK);
		btNext.setBackground(new Color(240, 248, 255));
		btNext.setBounds(510, 550, 100, 40);
		add(btNext);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.SPECIAL_1);
			}
		});

		btNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.SPECIAL_3);
			}
		});
	}

	protected void paintComponent(Graphics g) {
super.paintComponent(g);

		// Vẽ nền đen mờ
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Kích thước và vị trí của hình chữ nhật bo tròn
		int rectWidth = (int) (getWidth() * 0.85); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.85); // Chiếm 80% chiều cao
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
