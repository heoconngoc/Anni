package com.dat.anni.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.dat.anni.config.Config;
import com.dat.anni.data.AppSession;

import com.dat.anni.util.UiUtils;

/**
 * Cổng vào app: hỏi tên người chơi trước. Tên nằm trong danh sách đặc biệt
 * thì yêu cầu mật khẩu và mở thư ẩn; tên thường thì xem thư chào rồi vào Home.
 */
public class MenuPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btContinue;
	private JLabel lbTitle, lbUser, lbPass, lbWarning, lbWrong, lbHint, lbWarningPass;
	private JTextField tfUser;
	private JPasswordField tfPass;
	private boolean passwordShown;
	private int wrongTimes;

	public MenuPanel() {
		super("/gifs/mine.gif");
		addComps();
		addEvents();
	}

	private void addComps() {
		lbTitle = new JLabel("WHO'S PLAYING?", JLabel.CENTER);
		lbTitle.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 34f));
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(250, 140, 500, 60);
		add(lbTitle);

		lbUser = new JLabel("Name:");
		lbUser.setBounds(280, 290, 160, 40);
		lbUser.setForeground(new Color(25, 25, 112));
		lbUser.setBackground(Color.WHITE);
		lbUser.setFont(UiUtils.loadFont("/fonts/RubikMonoOne-Regular.ttf", 18f));
		lbUser.setOpaque(true);
		lbUser.setBorder(new LineBorder(Color.BLACK, 2));
		add(lbUser);

		tfUser = new JTextField();
		tfUser.setBounds(460, 290, 260, 40);
		tfUser.setForeground(Color.BLACK);
		tfUser.setFont(UiUtils.loadFont("/fonts/RubikMonoOne-Regular.ttf", 16f));
		add(tfUser);

		lbPass = new JLabel("Password:");
		lbPass.setBounds(280, 350, 160, 40);
		lbPass.setForeground(new Color(25, 25, 112));
		lbPass.setBackground(Color.WHITE);
		lbPass.setFont(UiUtils.loadFont("/fonts/RubikMonoOne-Regular.ttf", 14f));
		lbPass.setOpaque(true);
		lbPass.setBorder(new LineBorder(Color.BLACK, 2));
		lbPass.setVisible(false);
		add(lbPass);

		tfPass = new JPasswordField();
		tfPass.setBounds(460, 350, 260, 40);
		tfPass.setForeground(new Color(25, 25, 112));
		tfPass.setFont(UiUtils.loadFont("/fonts/RubikMonoOne-Regular.ttf", 16f));
		tfPass.setEchoChar('*');
		tfPass.setVisible(false);
		add(tfPass);

		btContinue = new JButton("CONTINUE");
		btContinue.setForeground(Color.BLACK);
		btContinue.setBackground(new Color(240, 248, 255));
		btContinue.setBounds(400, 430, 220, 54);
		btContinue.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f));
		add(btContinue);

		lbWarning = warningLabel("Please enter your name!", 355, 250);
		lbWrong = warningLabel("Invalid password, try again!", 360, 410);
		lbHint = warningLabel("Hint: the password is 4 numbers.", 320, 410);
		lbWarningPass = warningLabel("Please enter the password!", 355, 410);
	}

	private JLabel warningLabel(String text, int x, int y) {
		JLabel lb = new JLabel(text);
		lb.setForeground(Color.RED.darker());
		lb.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
		lb.setOpaque(true);
		lb.setBorder(new LineBorder(Color.BLACK, 2));
		lb.setBounds(x, y, 300, 30);
		lb.setVisible(false);
		add(lb);
		return lb;
	}

	private void addEvents() {
		btContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = tfUser.getText();
				if (input == null || input.isBlank()) {
					hideWarnings();
					lbWarning.setVisible(true);
					return;
				}
				String lower = input.trim().toLowerCase();

				if (Config.VALID_USERS.contains(lower)) {
					if (!passwordShown) {
						passwordShown = true;
						hideWarnings();
						lbPass.setVisible(true);
						tfPass.setVisible(true);
						btContinue.setBounds(400, 480, 220, 54);
						return;
					}
					String pass = new String(tfPass.getPassword());
					if (pass.isEmpty()) {
						hideWarnings();
						lbWarningPass.setVisible(true);
						return;
					}
					if (Config.APP_PASSWORD.equals(pass)) {
						AppSession.login(lower);
						resetAndGo(MainPanel.SPECIAL_1);
					} else {
						wrongTimes++;
						hideWarnings();
						if (wrongTimes < 2) {
							lbWrong.setVisible(true);
						} else {
							lbHint.setVisible(true);
						}
					}
				} else {
					AppSession.login(input.trim());
					resetAndGo(MainPanel.LETTER_GUEST);
				}
			}
		});
	}

	private void hideWarnings() {
		lbWarning.setVisible(false);
		lbWrong.setVisible(false);
		lbHint.setVisible(false);
		lbWarningPass.setVisible(false);
	}

	private void resetAndGo(String card) {
		passwordShown = false;
		wrongTimes = 0;
		tfUser.setText("");
		tfPass.setText("");
		hideWarnings();
		lbPass.setVisible(false);
		tfPass.setVisible(false);
		btContinue.setBounds(400, 430, 220, 54);
		main.show(card);
	}
}
