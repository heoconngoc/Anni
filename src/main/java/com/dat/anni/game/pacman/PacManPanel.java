package main.java.com.dat.anni.game.pacman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.dat.anni.gui.MainPanel;

public class PacManPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private PacMan pacMan;
	private JButton btBack;
	private Image backgroundImage;
	private Font buttonFont;

	public PacManPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/46d455eeaefbe9261373490c47c02f69_enhanced.jpg"))
				.getImage();
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
	}

	private void addComps() {
		pacMan = new PacMan();
		pacMan.setBounds(200, 0, pacMan.getPreferredSize().width, pacMan.getPreferredSize().height);
		add(pacMan);

		btBack = new JButton("Back");
		btBack.setBounds(850, 580, 100, 50);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pacMan.keyReleased(e);
			}
		});
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showPac_StartPanel();
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
