package com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dat.anni.util.UiUtils;

public class StartPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JLabel lbTitle1, lbTitle2;
	private JButton btStart, btMenu;
	private Font buttonFont, title1Font, title2Font;

	public StartPanel() {
		super("/gifs/mine.gif");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		// Tải font từ tài nguyên
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 28f);

		title1Font = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 50f);

		title2Font = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 70f);
	}

	private void addComps() {
		lbTitle1 = new JLabel("JUST FOR YOU");
		lbTitle1.setFont(title1Font); // Áp dụng font cho JLabel
		lbTitle1.setForeground(new Color(255, 165, 0));
		lbTitle1.setBounds(190, 130, 700, 80); // Căn giữa với một chút khoảng cách
		add(lbTitle1);

		lbTitle2 = new JLabel("ARACADE");
		lbTitle2.setFont(title2Font); // Áp dụng font cho JLabel
		lbTitle2.setForeground(Color.CYAN);// Sử dụng màu cyan cho tiêu đề thứ hai
		lbTitle2.setBounds(270, 220, 700, 80); // Căn giữa với khoảng cách phù hợp
		add(lbTitle2);

		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.BLACK); // Màu chữ của nút
		btStart.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btStart.setBounds(350, 400, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btMenu = new JButton("MENU");
		btMenu.setFont(buttonFont); // Áp dụng font cho JButton
		btMenu.setForeground(Color.BLACK); // Màu chữ của nút
		btMenu.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btMenu.setBounds(350, 490, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btMenu);

	}

	private void addEvents() {
		btMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.MENU);
			}
		});

		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.GAMES_PAGE_1);
			}
		});
	}


}
