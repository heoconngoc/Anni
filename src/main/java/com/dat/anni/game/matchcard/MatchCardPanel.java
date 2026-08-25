package com.dat.anni.game.matchcard;

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

public class MatchCardPanel extends BasePanel implements Navigable {
	private static final long serialVersionUID = 1L;
	private MatchCards matchCards;
	private JButton btBack;
	private Font buttonFont;

	public MatchCardPanel() {
		super("/imgs/554b572b7b1a9b88f0dcbc4c48a8b989_enhanced.jpg");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f);
	}

	private void addComps() {
		matchCards = new MatchCards();
		matchCards.setBounds(275, 50, matchCards.getPreferredSize().width, matchCards.getPreferredSize().height);
		add(matchCards);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setBounds(800, 600, 100, 40);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.show(MainPanel.MATCH_START);
			}
		});
	}

	@Override
	public void onLeave() {
		matchCards.restartGame();
	}


}
