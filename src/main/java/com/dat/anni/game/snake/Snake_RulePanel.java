package com.dat.anni.game.snake;

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

public class Snake_RulePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btBack;
	private JLabel lbTitle, lbRule;
	private Font buttonFont, normalFont;

	public Snake_RulePanel() {
		super("/imgs/Snake_OG-logo.jpg");
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
		lbTitle.setBounds(420, 120, 160, 40);
		add(lbTitle);

		lbRule = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ "Nhiệm vụ của bạn là điều khiển rắn để ăn thức ăn và trở nên dài hơn." + "<br>"
				+ "Hãy sử dụng các phím mũi tên để di chuyển rắn và ăn thức ăn.<br>"
				+ "Mỗi lần rắn ăn thức ăn, thân rắn sẽ dài ra và điểm số tăng lên.<br>"
				+ "Bạn sẽ thua khi để rắn của bạn va vào tường hoặc đâm vào thân rắn.<br>"
				+ "Chúc bạn chơi vui vẻ và có được số điểm cao nhất!" + "</div>" + "</html>");
		lbRule.setFont(normalFont);
		lbRule.setForeground(Color.WHITE);
		lbRule.setBounds(190, 185, 620, 300);
		add(lbRule);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(440, 500, 120, 50);
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


}
