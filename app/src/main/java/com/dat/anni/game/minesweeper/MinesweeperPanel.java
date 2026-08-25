package com.dat.anni.game.minesweeper;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;

import com.dat.anni.util.UiUtils;

public class MinesweeperPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private Minesweeper minesweeper;
	private JButton btBack;
	private Font buttonFont;

	public MinesweeperPanel() {
		super("/imgs/e925e468916d3f8a8d336bfb619f03f1_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		minesweeper = new Minesweeper();
		minesweeper.setBounds(200, 0, 560, 665);
		add(minesweeper);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setBounds(50, 40, 100, 45);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				minesweeper.resetBoard();
				minesweeper.setMines();
				main.show(MainPanel.MINES_START);
			}
		});
	}


}
