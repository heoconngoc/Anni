package com.dat.anni.game.matchcard;

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

public class Match_RulePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btBack;
	private JLabel lbTitle, lbRule;
	private Font buttonFont, normalFont;

	public Match_RulePanel() {
		super("/imgs/554b572b7b1a9b88f0dcbc4c48a8b989_enhanced.jpg");
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

		lbRule = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ "Nhiệm vụ của bạn là tìm các lá bài giống nhau!" + "<br>" + "<br>"
				+ "Mỗi lần bạn được phép lật 2 lá bài, nếu giống nhau chúng sẽ ở tiếp tục ở mặt ngửa. Ngược lại, 2 lá bài đó sẽ úp xuống sau 1 giây.<br>"
				+ "<br>" + "Chúc bạn chơi vui vẻ và hoàn thành nhiệm vụ với số lần sai ít nhất!" + "</div>"
				+ "</html>");
		lbRule.setFont(normalFont);
		lbRule.setForeground(Color.WHITE);
		lbRule.setBounds(175, 115, 680, 430);
		add(lbRule);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(445, 500, 120, 45);
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


}
