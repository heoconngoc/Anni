package com.dat.anni.gui;

import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.dat.anni.util.UiUtils;

/**
 * Một màn hình thư chung: nội dung lấy từ supplier lúc onEnter (để placeholder
 * {name} luôn theo người chơi hiện tại), một nút duy nhất điều hướng tiếp.
 */
public class LetterPanel extends BasePanel implements Navigable {

	private static final long serialVersionUID = 1L;

	private final Supplier<String> content;
	private final String buttonText;
	private final String nextCard;

	private JLabel lbText;

	public LetterPanel(Supplier<String> content, String buttonText, String nextCard) {
		super("/gifs/mine.gif");
		this.content = content;
		this.buttonText = buttonText;
		this.nextCard = nextCard;
		addComps();
	}

	private void addComps() {
		lbText = new JLabel();
		lbText.setFont(UiUtils.loadFont("/fonts/Oswald-VariableFont_wght.ttf", 18f));
		lbText.setForeground(java.awt.Color.WHITE);
		lbText.setBounds(140, 80, 720, 480);
		lbText.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		add(lbText);

		JButton btNext = new JButton(buttonText);
		btNext.setFont(UiUtils.loadFont("/fonts/PressStart2P-Regular.ttf", 16f));
		btNext.setForeground(java.awt.Color.BLACK);
		btNext.setBackground(new java.awt.Color(240, 248, 255));
		btNext.setBounds(400, 590, 200, 50);
		btNext.addActionListener(e -> main.show(nextCard));
		add(btNext);
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
		g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
				java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
		int rectWidth = (int) (getWidth() * 0.88);
		int rectHeight = (int) (getHeight() * 0.92);
		int rectX = (getWidth() - rectWidth) / 2;
		int rectY = (getHeight() - rectHeight) / 2;
		g2d.setComposite(java.awt.AlphaComposite.getInstance(
				java.awt.AlphaComposite.SRC_OVER, 0.85f));
		g2d.setColor(java.awt.Color.BLACK);
		g2d.fill(new java.awt.geom.RoundRectangle2D.Float(
				rectX, rectY, rectWidth, rectHeight, 50, 50));
		g2d.dispose();
	}

	@Override
	public void onEnter() {
		String body = content.get()
				.replace("<br>", "\n")   // thống nhất xuống dòng từ formatLetter
				.replace("&", "&amp;")   // thoát HTML trước khi bọc
				.replace("\n", "<br>");
		lbText.setText("<html><div style='line-height: 1.3; font-size: 14px;'>" + body + "</div></html>");
	}
}
