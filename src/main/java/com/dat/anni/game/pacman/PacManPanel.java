package com.dat.anni.game.pacman;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;
import com.dat.anni.gui.Navigable;

import com.dat.anni.util.UiUtils;

public class PacManPanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private PacMan pacMan;
	private JButton btBack;
	private Font buttonFont;

	public PacManPanel() {
		super("/imgs/46d455eeaefbe9261373490c47c02f69_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		pacMan = new PacMan();
		pacMan.setBounds(200, 0, pacMan.getPreferredSize().width, pacMan.getPreferredSize().height);
		add(pacMan);

		btBack = new JButton("Back");
		btBack.setBounds(850, 580, 100, 50);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pacMan.keyReleased(e);
			}
		});
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.PAC_START);
			}
		});
	}

	@Override
	public void onEnter() {
		pacMan.resumeGame();
		pacMan.requestFocusInWindow();
	}

	@Override
	public void onLeave() {
		pacMan.resetGame();
	}


}
