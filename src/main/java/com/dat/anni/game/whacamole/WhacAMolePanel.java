package com.dat.anni.game.whacamole;

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

public class WhacAMolePanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private WhacAMole whacAMole;
	private JButton btBack;
	private Font buttonFont;

	public WhacAMolePanel() {
		super("/imgs/b2253b41ee35f0b75547004edb28146d_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		whacAMole = new WhacAMole();
		whacAMole.setBounds(160, 35, 800, 600);
		add(whacAMole);

		btBack = new JButton("Back");
		btBack.setBounds(25, 35, 100, 50);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.WHAC_START);
			}
		});
	}



	@Override
	public void onEnter() {
		whacAMole.restartGame();
	}

	@Override
	public void onLeave() {
		whacAMole.stopTimers();
	}

}
