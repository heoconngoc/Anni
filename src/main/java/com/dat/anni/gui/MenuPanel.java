package main.java.com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private JButton btBack, btLogin;
	private JLabel lbTitle, lbUser, lbPass, lbWarning, lbWrong, lbHint, lbWarningPass;
	private JTextField tfUser;
	private JPasswordField tfPass;
	private Font buttonFont, titleFont, normalFont;
	private MainPanel main;
	private int loginTimes, wrongTimes;

	public MenuPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		loginTimes = 0;
		wrongTimes = 0;
		backgroundImage = new ImageIcon(getClass().getResource("/gifs/mine.gif")).getImage();
		setLayout(null);

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(22f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buttonFont = new Font("Arial", Font.PLAIN, 30);
		}

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				titleFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(60f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			titleFont = new Font("Arial", Font.PLAIN, 20);
		}

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/RubikMonoOne-Regular.ttf")) {
			if (fontStream != null) {
				normalFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(20f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			normalFont = new Font("Arial", Font.PLAIN, 16);
		}
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
				main.showStartPanel();
				setVisible(false);
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

				Set<String> VALID_USERS = Set.of("chau", "hbc", "châu", "be", "bé");
				if (VALID_USERS.contains(user)) {
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
						if ("2501".equals(pass)) {
							loginTimes = 0;
							wrongTimes = 0;
							resetFields();
							showPasswordFields(false);
							main.showSpecialPanel();
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
					main.setLbLetter("<html>" + "<div style='line-height: 1.5;'>" + "Xin chào " + tfUser.getText() + ","
							+ "<br>" + "Tôi là Phạm Quốc Đạt.<br>"
							+ "Tôi đã khởi động dự án J4U Aracade từ ngày 15/01/2025.<br>"
							+ "Dự án này đã được tôi phát hành vào ngày 25/01/2025.<br>"
							+ "Nếu bạn có đóng góp hay sửa đổi hãy liên hệ tôi nhé!<br>" + "Hy vọng " + tfUser.getText()
							+ " sẽ có thời gian tuyệt vời bên dự án trò chơi Just For You Arcade của tôi!" + "</div>"
							+ "</html>");
					main.showNormalPanel();
					resetFields();

				}
			}
		});

	}

	private void resetFields() {
		setVisible(false);
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Vẽ hình nền GIF
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
