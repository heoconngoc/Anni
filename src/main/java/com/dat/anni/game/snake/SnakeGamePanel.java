package com.dat.anni.game.snake;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;
import com.dat.anni.gui.Navigable;

import com.dat.anni.util.UiUtils;

public class SnakeGamePanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private SnakeGame snakeGame;
	private JButton btBack;
	private Font buttonFont;

	public SnakeGamePanel() {
		super("/imgs/backgroundgame.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		snakeGame = new SnakeGame();
		snakeGame.setBounds(160, 40, 800, 600);
		add(snakeGame);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setBounds(30, 50, 100, 45);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.SNAKE_START);
			}
		});
	}

	@Override
	public void onLeave() {
		snakeGame.restartGame();
	}


}
