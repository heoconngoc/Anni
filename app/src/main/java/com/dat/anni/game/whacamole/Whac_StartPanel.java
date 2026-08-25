package com.dat.anni.game.whacamole;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;;

import com.dat.anni.util.UiUtils;

public class Whac_StartPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btStart, btRule, btBackToGameList;
	private Font buttonFont;

	public Whac_StartPanel() {
		super("/imgs/whack-a-mole-Photoroom_enhanced.png");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		// Tải font từ tài nguyên
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 23f);
	}

	private void addComps() {
		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.black); // Màu chữ của nút
		btStart.setBackground(Color.YELLOW); // Nền nút màu trắng
		btStart.setBounds(395, 455, 210, 95); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btRule = new JButton("RULE");
		btRule.setFont(buttonFont); // Áp dụng font cho JButton
		btRule.setForeground(Color.black); // Màu chữ của nút
		btRule.setBackground(Color.YELLOW); // Nền nút màu trắng
		btRule.setBounds(625, 475, 250, 55); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btRule);

		btBackToGameList = new JButton("DASHBOARD");
		btBackToGameList.setFont(buttonFont); // Áp dụng font cho JButton
		btBackToGameList.setForeground(Color.black); // Màu chữ của nút
		btBackToGameList.setBackground(Color.YELLOW);
		btBackToGameList.setBounds(125, 475, 250, 55); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btBackToGameList);
	}

	private void addEvents() {
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.WHAC_GAME);
			}
		});

		btRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.WHAC_RULES);
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
