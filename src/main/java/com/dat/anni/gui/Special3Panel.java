package com.dat.anni.gui;

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

public class Special3Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Font buttonFont, normalFont;
	private JLabel lbLetter;
	private JButton btBack, btBackToMenu;
	private MainPanel main;

	public Special3Panel() {
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

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Oswald-VariableFont_wght.ttf")) {
			if (fontStream != null) {
				normalFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(18f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			normalFont = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		lbLetter = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ "Nhớ là dat luôn bên cạnh bé. luôn đứng về phía bé. Nếu có điều gì bé tự hỏi có nên kể dat nghe không, đừng nghĩ, kể dat luôn nhé! Đừng lo bất cứ điều gì. Vẫn là những lời nhắc nhở cũ thôi, nhưng mà bé đừng có ngó lơ và để đó nhớ. Nhớ là ăn no, ngủ đủ, và cố gắng học tập để theo đuổi ước mơ nhé. dat tin, dù mất bao lâu thời gian, bé cũng sẽ đạt được ước mơ đó! <br>"
				+ "<br>"
				+ "Trò chơi này dat làm riêng cho bé. Đây là lần đầu tiên dat làm một dự án to, và chi tiết đến vậy. Mặc dù trông nó đơn giản vậy thui, nhưng mà với một con gà như dat thì cũng là một câu chuyện lớn đó. Lâu lâu hãy mở nó lên chơi nhé, khi chán, khi không biết làm gì, khi buồn, khi nhớ dat. Khi nào có điều muốn nói mà không dám nói thì ngồi chơi, chơi chán rồi thì gọi kể dat nghe nhé!<br>"
				+ "<br>" + "dat yêu bé" + "<br>" + "<br>" + "Amherst, 25/01/2024." + "<br>" + "<br>" + "dat" + "</div>"
				+ "</html>");
		lbLetter.setFont(normalFont);
		lbLetter.setForeground(Color.WHITE);
		lbLetter.setBounds(120, 60, 750, 500);
		add(lbLetter);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(390, 550, 100, 40);
		add(btBack);

		btBackToMenu = new JButton("Menu");
		btBackToMenu.setFont(buttonFont);
		btBackToMenu.setForeground(Color.BLACK);
		btBackToMenu.setBackground(new Color(240, 248, 255));
		btBackToMenu.setBounds(510, 550, 100, 40);
		add(btBackToMenu);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSpecial2Panel();
				setVisible(false);
			}
		});

		btBackToMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showStartPanel();
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
		int rectWidth = (int) (getWidth() * 0.85); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.85); // Chiếm 80% chiều cao
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
