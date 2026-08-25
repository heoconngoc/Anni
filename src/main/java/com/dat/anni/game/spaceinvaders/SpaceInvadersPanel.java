package com.dat.anni.game.spaceinvaders;

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

public class SpaceInvadersPanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;

	private SpaceInvaders spaceInvaders;
	private JButton btBack;
	private Font buttonFont;

	public SpaceInvadersPanel() {
		super("/imgs/55869d40bf6c74a9e16bba88dfa0e700_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		spaceInvaders = new SpaceInvaders();
		spaceInvaders.setBounds(244, 80, spaceInvaders.getPreferredSize().width,
				spaceInvaders.getPreferredSize().height);
		add(spaceInvaders);

		btBack = new JButton("Back");
		btBack.setBounds(72, 85, 100, 55);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.SPACE_START);
			}
		});
	}

	@Override
	public void onEnter() {
		spaceInvaders.resetGame();
		spaceInvaders.startTimers();
	}

	@Override
	public void onLeave() {
		spaceInvaders.resetGame();
	}


}
