package com.dat.anni.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel cơ sở dùng chung: tham chiếu MainPanel, hình nền full-size, layout null
 * (định vị tuyệt đối theo chuẩn hiện có của project).
 */
public abstract class BasePanel extends JPanel implements MainPanelAware {

	private static final long serialVersionUID = 1L;

	protected MainPanel main;
	private final Image backgroundImage;

	protected BasePanel(String backgroundPath) {
		backgroundImage = backgroundPath == null ? null
				: new ImageIcon(getClass().getResource(backgroundPath)).getImage();
		setLayout(null);
	}


	@Override
	public void setMainPanel(MainPanel main) {
		this.main = main;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
