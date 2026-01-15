package main.java.com.dat.anni.game.spaceinvaders;

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

public class SpaceInvadersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private MainPanel main;
	private SpaceInvaders spaceInvaders;
	private JButton btBack;
	private Image backgroundImage;
	private Font buttonFont;

	public SpaceInvadersPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/55869d40bf6c74a9e16bba88dfa0e700_enhanced.jpg"))
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
		spaceInvaders = new SpaceInvaders();
		spaceInvaders.setBounds(244, 80, spaceInvaders.getPreferredSize().width,
				spaceInvaders.getPreferredSize().height);
		add(spaceInvaders);

		btBack = new JButton("Back");
		btBack.setBounds(72, 85, 100, 55);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSpace_StartPanel();
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
