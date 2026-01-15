package main.java.com.dat.anni.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private MainPanel main;
	private JLabel lbTitle1, lbTitle2;
	private JButton btStart, btMenu;
	private Font buttonFont, title1Font, title2Font;

	public StartPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/gifs/mine.gif")).getImage();
		setLayout(null);

		// Tải font từ tài nguyên
		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(28f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buttonFont = new Font("Arial", Font.PLAIN, 20);
		}

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				title1Font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(50f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			title1Font = new Font("Arial", Font.PLAIN, 20);
		}

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				title2Font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(70f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			title2Font = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		lbTitle1 = new JLabel("JUST FOR YOU");
		lbTitle1.setFont(title1Font); // Áp dụng font cho JLabel
		lbTitle1.setForeground(new Color(255, 165, 0));
		lbTitle1.setBounds(190, 130, 700, 80); // Căn giữa với một chút khoảng cách
		add(lbTitle1);

		lbTitle2 = new JLabel("ARACADE");
		lbTitle2.setFont(title2Font); // Áp dụng font cho JLabel
		lbTitle2.setForeground(Color.CYAN);// Sử dụng màu cyan cho tiêu đề thứ hai
		lbTitle2.setBounds(270, 220, 700, 80); // Căn giữa với khoảng cách phù hợp
		add(lbTitle2);

		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.BLACK); // Màu chữ của nút
		btStart.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btStart.setBounds(350, 400, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btMenu = new JButton("MENU");
		btMenu.setFont(buttonFont); // Áp dụng font cho JButton
		btMenu.setForeground(Color.BLACK); // Màu chữ của nút
		btMenu.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btMenu.setBounds(350, 490, 300, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btMenu);

	}

	private void addEvents() {
		btMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMenuPanel();
				setVisible(false);
			}
		});

		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showGamePanel();
				setVisible(false);
			}
		});
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
