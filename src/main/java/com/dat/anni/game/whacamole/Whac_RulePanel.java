package com.dat.anni.game.whacamole;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dat.anni.gui.BasePanel;
import com.dat.anni.gui.MainPanel;
import com.dat.anni.gui.MainPanelAware;

import com.dat.anni.util.UiUtils;

public class Whac_RulePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btBack;
	private JLabel lbTitle, lbRule;
	private Font buttonFont, normalFont;

	public Whac_RulePanel() {
		super("/imgs/whack-a-mole-Photoroom_enhanced.png");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {

		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 20f);

		normalFont = UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 24f);
	}

	private void addComps() {
		lbTitle = new JLabel("RULE");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 55));
		lbTitle.setForeground(new Color(228, 254, 119));
		lbTitle.setBounds(423, 130, 155, 40);
		add(lbTitle);

		lbRule = new JLabel("<html>" + "<div style='line-height: 1.5;'>" + "Nhiệm vụ của bạn là đánh vào chú chuột."
				+ "<br>" + "<br>" + "Hãy sử dụng chuột trái để đánh vào hình chú chuột càng nhiều càng tốt!<br>"
				+ "<br>" + "Bạn sẽ thua khi đánh vào hình bông hoa ăn thịt.<br>" + "<br>"
				+ "Chúc bạn chơi vui vẻ và đạt được số điểm cao nhất!" + "</div>" + "</html>");
		lbRule.setFont(normalFont);
		lbRule.setForeground(Color.WHITE);
		lbRule.setBounds(175, 100, 680, 430);
		add(lbRule);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(445, 465, 120, 50);
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


}
