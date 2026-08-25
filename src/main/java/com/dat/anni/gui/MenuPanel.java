package com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.dat.anni.config.Config;
import com.dat.anni.data.AppSession;

import com.dat.anni.util.UiUtils;

public class MenuPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private JButton btBack, btLogin;
	private JLabel lbTitle, lbUser, lbPass, lbWarning, lbWrong, lbHint, lbWarningPass;
	private JTextField tfUser;
	private JPasswordField tfPass;
	private Font buttonFont, titleFont, normalFont;
	private int loginTimes, wrongTimes;

	public MenuPanel() {
		super("/gifs/mine.gif");
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		loginTimes = 0;
		wrongTimes = 0;
		buttonFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 22f);

		titleFont = UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 60f);

		normalFont = UiUtils.loadFont("/fonts/RubikMonoOne-Regular.ttf", 20f);
	}

	private void addComps() {
		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(335, 380, 150, 50);
		add(btBack);

		lbTitle = new JLabel("MENU");
		lbTitle.setFont(titleFont);
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(380, 130, 300, 80);
		add(lbTitle);

		lbUser = new JLabel("Username:");
		lbUser.setBounds(275, 270, 180, 40);
		lbUser.setForeground(new Color(25, 25, 112));
		lbUser.setBackground(Color.WHITE);
		lbUser.setFont(normalFont);
		lbUser.setOpaque(true); // Cho phép nền hiển thị
		lbUser.setBorder(new LineBorder(Color.BLACK, 2)); // Thêm viền màu đen, dày 2px
		add(lbUser);

		lbPass = new JLabel();
		lbPass.setText("Password:");
		lbPass.setBounds(275, 360, 180, 40);
		lbPass.setForeground(new Color(25, 25, 112));
		lbPass.setBackground(Color.WHITE);
//		lbPass.setFont(new Font("Arial", Font.PLAIN, 16));
		lbPass.setFont(normalFont);
		lbPass.setOpaque(true); // Cho phép nền hiển thị
		lbPass.setBorder(new LineBorder(Color.BLACK, 2)); // Thêm viền màu đen, dày 2px
		lbPass.setVisible(false);
		add(lbPass);

		tfUser = new JTextField();
		tfUser.setBounds(475, 270, 250, 40);
		tfUser.setForeground(Color.BLACK);
//		tfUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfUser.setFont(normalFont);
		add(tfUser);

		tfPass = new JPasswordField();
		tfPass.setBounds(475, 360, 250, 40);
		tfPass.setForeground(new Color(25, 25, 112));
//		tfPass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfPass.setFont(normalFont);
		tfPass.setEchoChar('*');
		tfPass.setVisible(false);
		add(tfPass);

		btLogin = new JButton("Login");
		btLogin.setForeground(Color.BLACK);
		btLogin.setBackground(new Color(240, 248, 255));
		btLogin.setBounds(515, 380, 150, 50);
		btLogin.setFont(buttonFont);
		add(btLogin);

		lbWarning = new JLabel("Please Enter Your Username!");
		lbWarning.setForeground(Color.RED.darker());
		lbWarning.setFont(new Font("Arial", Font.BOLD, 20));
		lbWarning.setOpaque(true); // Cho phép nền hiển thị
		lbWarning.setBorder(new LineBorder(Color.BLACK, 2));
		lbWarning.setBounds(355, 330, 285, 30);
		lbWarning.setVisible(false);
		add(lbWarning);

		lbWrong = new JLabel("Invalid Password, Try Again!");
		lbWrong.setForeground(Color.RED.darker());
		lbWrong.setFont(new Font("Arial", Font.BOLD, 20));
		lbWrong.setOpaque(true);
		lbWrong.setBorder(new LineBorder(Color.BLACK, 2));
		lbWrong.setBounds(360, 410, 280, 30);
		lbWrong.setVisible(false);
		add(lbWrong);

		lbHint = new JLabel("Invalid!  Hint: Password consists of 4 numbers.");
		lbHint.setForeground(Color.RED.darker());
		lbHint.setFont(new Font("Arial", Font.BOLD, 20));
		lbHint.setOpaque(true);
		lbHint.setBorder(new LineBorder(Color.BLACK, 2));
		lbHint.setBounds(270, 410, 460, 30);
		lbHint.setVisible(false);
		add(lbHint);

		lbWarningPass = new JLabel("Please Enter Your Password!");
		lbWarningPass.setForeground(Color.RED.darker());
		lbWarningPass.setFont(new Font("Arial", Font.BOLD, 20));
		lbWarningPass.setOpaque(true); // Cho phép nền hiển thị
		lbWarningPass.setBorder(new LineBorder(Color.BLACK, 2));
		lbWarningPass.setBounds(355, 410, 290, 30);
		lbWarningPass.setVisible(false);
		add(lbWarningPass);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginTimes = 0;
				wrongTimes = 0;
				tfUser.setText("");
				tfPass.setText("");
				lbUser.setBounds(275, 270, 180, 40);
				tfUser.setBounds(475, 270, 250, 40);
				tfPass.setBounds(475, 360, 250, 40);
				lbPass.setBounds(275, 360, 180, 40);
				btLogin.setBounds(500, 380, 150, 40);
				btBack.setBounds(320, 380, 150, 40);
				tfPass.setVisible(false);
				lbPass.setVisible(false);
				lbWarningPass.setVisible(false);
				lbWarning.setVisible(false);
				lbWrong.setVisible(false);
				lbHint.setVisible(false);
				main.show(MainPanel.START);
			}
		});

		btLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = tfUser.getText();
				if (user == null || user.trim().isEmpty()) {
					lbWarning.setVisible(true);
					return;
				}

				user = user.toLowerCase();

				if (Config.VALID_USERS.contains(user)) {
					lbWarning.setVisible(false);
					showPasswordFields(true);
					loginTimes += 1;
					String pass = new String(tfPass.getPassword());

					if ("".equals(pass) && loginTimes > 1) {
						lbWrong.setVisible(false);
						lbHint.setVisible(false);
						lbWarningPass.setVisible(true);
						return;
					}

					if (!"".equals(pass)) {
						if (Config.APP_PASSWORD.equals(pass)) {
							loginTimes = 0;
							wrongTimes = 0;
							AppSession.login(user);
							resetFields();
							showPasswordFields(false);
							main.show(MainPanel.SPECIAL_1);
						} else {
							wrongTimes += 1;
							if (wrongTimes > 0 && wrongTimes < 2) {
								lbWarningPass.setVisible(false);
								lbWarningPass.setVisible(false);
								lbWrong.setVisible(true);
							} else if (wrongTimes > 1) {
								lbWarningPass.setVisible(false);
								lbWrong.setVisible(false);
								lbHint.setVisible(true);
							}
						}

					}

				} else {
					AppSession.login(tfUser.getText());
					String letterBody = Config.formatLetter(Config.GUEST_LETTER, tfUser.getText());
					main.setLbLetter("<html>" + "<div style='line-height: 1.5;'>" + letterBody + "</div>"
							+ "</html>");
					main.show(MainPanel.NORMAL);
					resetFields();

				}
			}
		});

	}

	private void resetFields() {
		tfUser.setText("");
		tfPass.setText("");
		lbPass.setVisible(false);
		lbWarningPass.setVisible(false);
		tfPass.setVisible(false);
		lbWarning.setVisible(false);
		lbWrong.setVisible(false);
		lbHint.setVisible(false);
	}

	private void showPasswordFields(boolean b) {
		lbPass.setVisible(b);
		tfPass.setVisible(b);
		btBack.setBounds(330, b ? 450 : 380, 150, 50);
		btLogin.setBounds(510, b ? 450 : 380, 150, 50);
	}


}
