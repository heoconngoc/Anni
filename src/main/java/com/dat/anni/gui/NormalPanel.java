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

public class NormalPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Font buttonFont, normalFont;
	private JLabel lbTitle, lbLetter;
	private JButton btBack, btBackToMenu;
	private MainPanel main;

	public NormalPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/gifs/mine.gif")).getImage();
		setLayout(null);

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(20f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buttonFont = new Font("Arial", Font.PLAIN, 30);
		}

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Oswald-VariableFont_wght.ttf")) {
			if (fontStream != null) {
				normalFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(24f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			normalFont = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		lbTitle = new JLabel("Lời Tác Giả");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 50));
		lbTitle.setForeground(Color.CYAN);
		lbTitle.setBounds(360, 100, 280, 80);
		add(lbTitle);

		lbLetter = new JLabel();
		lbLetter.setFont(normalFont);
		lbLetter.setForeground(Color.WHITE);
		lbLetter.setBounds(200, 185, 600, 300);
		add(lbLetter);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(370, 520, 120, 50);
		add(btBack);

		btBackToMenu = new JButton("Menu");
		btBackToMenu.setFont(buttonFont);
		btBackToMenu.setForeground(Color.BLACK);
		btBackToMenu.setBackground(new Color(240, 248, 255));
		btBackToMenu.setBounds(510, 520, 120, 50);
		add(btBackToMenu);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMenuPanel();
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
		int rectWidth = (int) (getWidth() * 0.8); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.8); // Chiếm 80% chiều cao
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

	public void setLbLetter(String string) {
		lbLetter.setText(string);
	}
}
