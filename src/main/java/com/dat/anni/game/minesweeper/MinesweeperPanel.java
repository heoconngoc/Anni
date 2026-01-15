package main.java.com.dat.anni.game.minesweeper;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.dat.anni.gui.MainPanel;

public class MinesweeperPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private Minesweeper minesweeper;
	private JButton btBack;
	private Image backgroundImage;
	private Font buttonFont;

	public MinesweeperPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/e925e468916d3f8a8d336bfb619f03f1_enhanced.jpg"))
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
		minesweeper = new Minesweeper();
		minesweeper.setBounds(200, 0, 560, 665);
		add(minesweeper);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setBounds(50, 40, 100, 45);
		btBack.setFocusable(false);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMines_StartPanel();
				minesweeper.resetBoard();
				minesweeper.setMines();
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
