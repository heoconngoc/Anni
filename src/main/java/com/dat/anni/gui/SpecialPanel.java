package com.dat.anni.gui;

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

public class SpecialPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Font buttonFont, normalFont;
	private JLabel lbLetter;
	private JButton btBack, btNext;
	private MainPanel main;

	public SpecialPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		backgroundImage = new ImageIcon(getClass().getResource("/gifs/mine.gif")).getImage();
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

		try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Oswald-VariableFont_wght.ttf")) {
			if (fontStream != null) {
				normalFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(18f);
			} else {
				throw new IOException("Font không tìm thấy");
			}
		} catch (Exception e) {
			e.printStackTrace();
			normalFont = new Font("Arial", Font.PLAIN, 20);
		}
	}

	private void addComps() {
		lbLetter = new JLabel("<html>" + "<div style='line-height: 1.5;'>" + "Hello heo con!<br>"
				+ "Cuối cùng cũng mở được rồi nhỉ! dat tự hỏi bé có mất nhiều thời gian để mở không nữa, chắc là không đâu ha!<br>"
				+ "Hôm nay là 25/01/2025. Là ngày gì nhỉ heo? Nhớ không, thử không nhớ xem, hừ !? Đùa thôi, kiểu gì bé chả nhớ đúng không. Thời gian trôi qua nhanh thật nhỉ. dat không tin đã là 1 năm rồi đó. Nhớ mới ngày nào còn rung rinh khi thấy bé lon ton ở sân bay Narita. Hồi đó mình chỉ là bạn bè bình thường thôi (mà hình như bé còn ghét dat nữa cơ, trời ơi, tại sao, dat làm gì nên tội), giờ quay đi quay lại, cô bé lon ton ngày nào giờ đã bên cạnh dat rồi.<br>"
				+ "Chưa bao giờ dat nghĩ thời gian là vấn đề quan trọng, nhưng 1 năm cũng đáng để chúng ta cùng nhau nhìn lại nhỉ, đúng không? Ít nhất nó cũng là 1/18 cuộc đời của chúng ta rồi. 1 năm vừa qua, cùng với bé, dat đã làm được vô số điều lần đầu tiên. Mình đã cùng nhau ngồi nói chuyện ở sau hội trường, cùng nhau đi study date, cùng nhau đi chơi phố cổ, cùng đi tàu trên cao… Mình đã gặp nhau lúc sáng sớm khi mặt trời chưa mọc, gặp nhau lúc giữa trưa nắng gắt hay thậm chí cả lúc đêm khuya… Mình cùng nhau nắm tay đi dưới ánh nắng chói chang khi mùa hè, hay lái xe cùng nhau lúc mưa lạnh mùa đông. Dù dat không nhớ ngày hôm đó bé đã hát gì sau lưng dat, nhưng chắc chắn, dat sẽ không bao giờ quên cảm giác ngày hôm đó. Khi bé ôm dat, dựa vào lưng dat, và hát ở đằng sau dat. Ngày hôm đó dù trời mưa lạnh, dù tay chân dat đã mất cảm giác vì lạnh, nhưng có bé, lòng dat ấm áp đến kì lạ.<br>"
				+ "Ấn nút next để đọc tiếp, bé nhé!" + "</div>" + "</html>");
		lbLetter.setFont(normalFont);
		lbLetter.setForeground(Color.WHITE);
		lbLetter.setBounds(120, 60, 750, 500);
		add(lbLetter);

		btBack = new JButton("Back");
		btBack.setFont(buttonFont);
		btBack.setForeground(Color.BLACK);
		btBack.setBackground(new Color(240, 248, 255));
		btBack.setBounds(390, 550, 100, 40);
		add(btBack);

		btNext = new JButton("Next");
		btNext.setFont(buttonFont);
		btNext.setForeground(Color.BLACK);
		btNext.setBackground(new Color(240, 248, 255));
		btNext.setBounds(510, 550, 100, 40);
		add(btNext);
	}

	private void addEvents() {
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMenuPanel();
				setVisible(false);
			}
		});

		btNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSpecial2Panel();
				setVisible(false);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Vẽ hình nền GIF
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Độ trong suốt 70%
		g2d.setColor(new Color(0, 0, 0)); // Màu đen

		// Vẽ hình chữ nhật bo tròn
		g2d.fill(new RoundRectangle2D.Float(rectX, rectY, rectWidth, rectHeight, 50, 50));

		g2d.dispose();
	}

	public void setMainPanel(MainPanel main) {
		this.main = main;
	}
}
