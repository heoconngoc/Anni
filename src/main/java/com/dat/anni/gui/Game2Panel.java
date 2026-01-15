package main.java.com.dat.anni.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game2Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private JButton btBack;
	private Font buttonFont, normalFont;
	private MainPanel main;
	private JButton[] gameButtons;

	public Game2Panel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/gifs/mine.gif")).getImage();
		setLayout(null);

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(16f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buttonFont = new Font("Arial", Font.PLAIN, 20);
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
		btBack.setBounds(450, 570, 100, 40);
		add(btBack);

		gameButtons = new JButton[4];
		String[] gameNames = { "Flappy Bird", "Whac A Mole", "Minesweeper", "Snake" }; // Tên trò chơi
		ImageIcon[] icons = { new ImageIcon(getClass().getResource("/icons/Flappy Bird Icon.jpg")),
				new ImageIcon(getClass().getResource("/icons/Whac A Mole Icon.jpg")),
				new ImageIcon(getClass().getResource("/icons/Minesweeper Icon.jpg")),
				new ImageIcon(getClass().getResource("/icons/Snake Icon.jpg")) };

		int rectWidth = (int) (900 * 0.85); // Chiều rộng hình chữ nhật đen mờ
		int rectHeight = (int) (600 * 0.85); // Chiều cao hình chữ nhật đen mờ
		int rectX = (1000 - rectWidth) / 2; // Vị trí X căn giữa
		int rectY = (700 - rectHeight) / 2;

		int buttonWidth = rectWidth / 2 - 20; // Chiều rộng mỗi nút
		int buttonHeight = rectHeight / 2 - 20; // Chiều cao mỗi nút
		int spacing = 20;

		for (int i = 0; i < gameButtons.length; i++) {
			gameButtons[i] = new JButton();
			gameButtons[i].setFont(normalFont);
			gameButtons[i].setFocusPainted(false);
			gameButtons[i].setLayout(null); // Tắt layout của nút để tự quản lý vị trí các thành phần bên trong

			// Icon
			JLabel iconLabel = new JLabel(icons[i]);
			iconLabel.setBounds(0, 0, buttonWidth, buttonHeight - 50); // Căn chỉnh icon
			gameButtons[i].add(iconLabel);

			// Tên game
			JLabel nameLabel = new JLabel(gameNames[i], JLabel.CENTER);
			nameLabel.setFont(normalFont);
			nameLabel.setOpaque(true);
			nameLabel.setBackground(Color.WHITE);
			nameLabel.setForeground(Color.BLACK);
			nameLabel.setBounds(0, buttonHeight - 50, buttonWidth - 20, 30); // Đặt ở cuối nút
			gameButtons[i].add(nameLabel);

			gameButtons[i].setBounds(rectX + spacing + (i % 2) * (buttonWidth + spacing), // X vị trí
					rectY + spacing + (i / 2) * (buttonHeight + spacing) - 35, // Y vị trí
					buttonWidth - 20, buttonHeight - 20);

			add(gameButtons[i]);

		}
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showGamePanel();
				setVisible(false);
			}
		});

		gameButtons[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showFlappy_StartPanel();
				setVisible(false);
			}
		});

		gameButtons[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showWhac_StartPanel();
				setVisible(false);
			}
		});

		gameButtons[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMines_StartPanel();
				setVisible(false);
			}
		});

		gameButtons[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSnake_StartPanel();
				setVisible(false);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Vẽ hình nền GIF
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}

		// Vẽ nền đen mờ
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Kích thước và vị trí của hình chữ nhật bo tròn
		int rectWidth = (int) (getWidth() * 0.87); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.87); // Chiếm 80% chiều cao
		int rectX = (getWidth() - rectWidth) / 2; // Căn giữa theo chiều ngang
		int rectY = (getHeight() - rectHeight) / 2; // Căn giữa theo chiều dọc

		// Thiết lập độ trong suốt
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Độ trong suốt 70%
		g2d.setColor(new Color(0, 0, 0)); // Màu đen

		// Vẽ hình chữ nhật bo tròn
		g2d.fill(new RoundRectangle2D.Float(rectX, rectY, rectWidth, rectHeight, 50, 50));

		g2d.dispose();
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
