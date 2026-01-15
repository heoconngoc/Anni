package main.java.com.dat.anni.game.minesweeper;

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

import main.java.com.dat.anni.gui.MainPanel;

public class Mines_RulePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainPanel main;
	private JButton btBack;
	private Image backgroundImage;
	private JLabel lbTitle, lbRule;
	private Font buttonFont, normalFont;

	public Mines_RulePanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/imgs/xBOEFj.jpeg")).getImage();
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
		lbTitle = new JLabel("RULE");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 55));
		lbTitle.setForeground(new Color(228, 254, 119));
		lbTitle.setBounds(423, 85, 155, 40);
		add(lbTitle);

		lbRule = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ "Nhiệm vụ của bạn là đánh dấu tất cả số bom có trong bản đồ." + "<br>"
				+ "Hãy sử dụng chuột trái để mở các ô bạn nghĩ không có bom, các ô sẽ được mở rộng đến khi gặp ô có con số!<br>"
				+ "Con số hiện thị trên ô thể hiện số bom xung quanh ô đó với phạm vi 1 ô.<br>"
				+ "Bạn có thể đánh dấu ô mà bạn nghi ngờ có bom bằng chuột phải, những ô đó sẽ không thể mở lên một khi đánh dấu trừ khi bạn huỷ đánh dấu bằng cách ấn chuột phải lần nữa.<br>"
				+ "Bạn sẽ chiến thắng khi đánh dấu thành công tất cả bom và mở hết các ô không bom!<br>"
				+ "Chúc bạn chơi vui vẻ!" + "</div>" + "</html>");
		lbRule.setFont(normalFont);
		lbRule.setForeground(Color.WHITE);
		lbRule.setBounds(175, 125, 680, 430);
		add(lbRule);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(445, 545, 120, 45);
		add(btBack);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMines_StartPanel();
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

		// Vẽ nền đen mờ
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Kích thước và vị trí của hình chữ nhật bo tròn
		int rectWidth = (int) (getWidth() * 0.85); // Chiếm 80% chiều rộng
		int rectHeight = (int) (getHeight() * 0.85); // Chiếm 80% chiều cao
		int rectX = (getWidth() - rectWidth) / 2; // Căn giữa theo chiều ngang
		int rectY = (getHeight() - rectHeight) / 2; // Căn giữa theo chiều dọc

		// Thiết lập độ trong suốt
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f)); // Độ trong suốt 70%
		g2d.setColor(new Color(0, 0, 0)); // Màu đen

		// Vẽ hình chữ nhật bo tròn
		g2d.fill(new RoundRectangle2D.Float(rectX, rectY, rectWidth, rectHeight, 50, 50));

		g2d.dispose();
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
