package com.dat.anni.game.minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;

import com.dat.anni.util.UiUtils;

public class Mines_StartPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btStart, btRule, btBackToGameList;
	private Font buttonFont, titleFont;
	private JLabel lbTitle;

	public Mines_StartPanel() {
		super("/imgs/xBOEFj.jpeg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		// Tải font từ tài nguyên
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 28f);

		titleFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 70f);
	}

	private void addComps() {
		lbTitle = new JLabel("MINESWEEPER");
		lbTitle.setFont(titleFont); // Áp dụng font cho JLabel
		lbTitle.setForeground(new Color(0, 51, 102)); // Màu chữ
		lbTitle.setOpaque(true); // Bật hiển thị nền
		lbTitle.setBackground(Color.WHITE); // Đặt nền trắng
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
		lbTitle.setBounds(110, 140, 780, 90); // Kích thước và vị trí
		add(lbTitle);

		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.BLACK); // Màu chữ của nút
		btStart.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btStart.setBounds(350, 330, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btRule = new JButton("RULE");
		btRule.setFont(buttonFont); // Áp dụng font cho JButton
		btRule.setForeground(Color.BLACK); // Màu chữ của nút
		btRule.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btRule.setBounds(350, 410, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btRule);

		btBackToGameList = new JButton("DASHBOARD");
		btBackToGameList.setFont(buttonFont); // Áp dụng font cho JButton
		btBackToGameList.setForeground(Color.BLACK); // Màu chữ của nút
		btBackToGameList.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btBackToGameList.setBounds(350, 490, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btBackToGameList);
	}

	private void addEvents() {
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.MINES_GAME);
			}
		});

		btRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.MINES_RULES);
			}
		});

		btBackToGameList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.GAMES_PAGE_2);
			}
		});
	}


}
