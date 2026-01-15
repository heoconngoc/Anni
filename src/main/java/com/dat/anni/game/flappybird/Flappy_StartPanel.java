package main.java.com.dat.anni.game.flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.dat.anni.gui.MainPanel;;

public class Flappy_StartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private JButton btStart, btRule, btBackToGameList;
	private Image backgroundImage;
	private Font buttonFont;

	public Flappy_StartPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/Screenshot 2025-01-23 at 22,33,25_enhanced.png"))
				.getImage();
		setLayout(null);

		// Tải font từ tài nguyên
		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(23f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buttonFont = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.BLACK); // Màu chữ của nút
		btStart.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btStart.setBounds(375, 500, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btRule = new JButton("RULE");
		btRule.setFont(buttonFont); // Áp dụng font cho JButton
		btRule.setForeground(Color.BLACK); // Màu chữ của nút
		btRule.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btRule.setBounds(645, 500, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btRule);

		btBackToGameList = new JButton("DASHBOARD");
		btBackToGameList.setFont(buttonFont); // Áp dụng font cho JButton
		btBackToGameList.setForeground(Color.BLACK); // Màu chữ của nút
		btBackToGameList.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btBackToGameList.setBounds(105, 500, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btBackToGameList);
	}

	private void addEvents() {
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showFlappyBirdPanel();
				setVisible(false);
			}
		});

		btRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showFlappy_RulePanel();
				setVisible(false);
			}
		});

		btBackToGameList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showGame2Panel();
				setVisible(false);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
