package com.dat.anni.game.chormedinosaur;

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

public class ChromeDinosaurPanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private ChromeDinosaur chromeDinosaur;
	private JButton btBack;
	private Font buttonFont;

	public ChromeDinosaurPanel() {
		super("/imgs/f86ae763a92a096279d5de6388f6bce0_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		chromeDinosaur = new ChromeDinosaur();
		chromeDinosaur.setBounds(125, 180, chromeDinosaur.getPreferredSize().width,
				chromeDinosaur.getPreferredSize().height);
		add(chromeDinosaur);

		btBack = new JButton("Back");
		btBack.setBounds(775, 550, 100, 40);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.DINO_START);
			}
		});
	}

	@Override
	public void onEnter() {
		chromeDinosaur.resetGame();
		chromeDinosaur.startTimers();
		chromeDinosaur.requestFocusInWindow();
	}

	@Override
	public void onLeave() {
		chromeDinosaur.resetGame();
	}


}
