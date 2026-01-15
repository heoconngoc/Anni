package main.java.com.dat.anni.game.flappybird;

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

import main.java.com.dat.anni.gui.MainPanel;

public class FlappyBirdPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private FlappyBird flappyBird;
	private JButton btBack;
	private Image backgroundImage;
	private Font buttonFont;

	public FlappyBirdPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/4e5889a0f34c62d884755c3db20c0893_enhanced.jpg"))
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
		flappyBird = new FlappyBird();
		flappyBird.setBounds(320, 10, flappyBird.getPreferredSize().width, flappyBird.getPreferredSize().height);
		add(flappyBird);

		btBack = new JButton("Back");
		btBack.setBounds(60, 20, 100, 55);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showFlappy_StartPanel();
				setVisible(false);
			}
		});
	}

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
