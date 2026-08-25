package com.dat.anni.game.flappybird;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;
import com.dat.anni.gui.Navigable;

import com.dat.anni.util.UiUtils;

public class FlappyBirdPanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private FlappyBird flappyBird;
	private JButton btBack;
	private Font buttonFont;

	public FlappyBirdPanel() {
		super("/imgs/4e5889a0f34c62d884755c3db20c0893_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		flappyBird = new FlappyBird();
		flappyBird.setBounds(320, 10, flappyBird.getPreferredSize().width, flappyBird.getPreferredSize().height);
		add(flappyBird);

		btBack = new JButton("Back");
		btBack.setBounds(60, 20, 100, 55);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.FLAPPY_START);
			}
		});
	}



	@Override
	public void onEnter() {
		flappyBird.resetGame();
		flappyBird.startTimers();
		flappyBird.requestFocusInWindow();
	}

	@Override
	public void onLeave() {
		flappyBird.resetGame();
	}

}
