package main.java.com.dat.anni.game.chormedinosaur;

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
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.dat.anni.gui.MainPanel;;

public class Dino_StartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private JButton btStart, btRule, btBackToGameList;
	private Image backgroundImage;
	private Font buttonFont, titleFont;
	private JLabel lbTitle;

	public Dino_StartPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/fbb9f1c41001402f53a0d3af457b5b2a_enhanced.jpg"))
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

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
			if (fontStream != null) {
				titleFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(70f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			titleFont = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		lbTitle = new JLabel("DINO");
		lbTitle.setFont(titleFont); // Áp dụng font cho JLabel
		lbTitle.setForeground(Color.WHITE); // Màu chữ
		lbTitle.setBounds(360, 140, 280, 90); // Kích thước và vị trí
		add(lbTitle);

		btStart = new JButton("START");
		btStart.setFont(buttonFont); // Áp dụng font cho JButton
		btStart.setForeground(Color.BLACK); // Màu chữ của nút
		btStart.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btStart.setBounds(375, 310, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btStart);

		btRule = new JButton("RULE");
		btRule.setFont(buttonFont); // Áp dụng font cho JButton
		btRule.setForeground(Color.BLACK); // Màu chữ của nút
		btRule.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btRule.setBounds(375, 390, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btRule);

		btBackToGameList = new JButton("DASHBOARD");
		btBackToGameList.setFont(buttonFont); // Áp dụng font cho JButton
		btBackToGameList.setForeground(Color.BLACK); // Màu chữ của nút
		btBackToGameList.setBackground(new Color(240, 248, 255)); // Nền nút màu trắng
		btBackToGameList.setBounds(375, 470, 250, 60); // Điều chỉnh vị trí nút bấm cho phù hợp
		add(btBackToGameList);

	}

	private void addEvents() {
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showChromeDinosaurPanel();
				setVisible(false);
			}
		});

		btRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showDino_RulePanel();
				setVisible(false);
			}
		});

		btBackToGameList.addActionListener(new ActionListener() {

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
		if (backgroundImage != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
