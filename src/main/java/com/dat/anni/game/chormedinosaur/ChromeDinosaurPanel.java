package main.java.com.dat.anni.game.chormedinosaur;

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

public class ChromeDinosaurPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private Image backgroundImage;
	private ChromeDinosaur chromeDinosaur;
	private JButton btBack;
	private Font buttonFont;

	public ChromeDinosaurPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/f86ae763a92a096279d5de6388f6bce0_enhanced.jpg"))
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
		chromeDinosaur = new ChromeDinosaur();
		chromeDinosaur.setBounds(125, 180, chromeDinosaur.getPreferredSize().width,
				chromeDinosaur.getPreferredSize().height);
		add(chromeDinosaur);

		btBack = new JButton("Back");
		btBack.setBounds(775, 550, 100, 40);
		btBack.setFont(buttonFont);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showDino_StartPanel();
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
