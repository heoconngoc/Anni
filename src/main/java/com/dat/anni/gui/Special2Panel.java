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

public class Special2Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Font buttonFont, normalFont;
	private JLabel lbLetter;
	private JButton btBack, btNext;
	private MainPanel main;

	public Special2Panel() {
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
		lbLetter = new JLabel("<html>" + "<div style='line-height: 1.5;'>"
				+ "1 năm vừa qua, tình yêu của mình có lên có xuống. dat dần nhận ra rằng mình không quá hợp nhau, thậm chí ở nhiều việc quan điểm của chúng ta là đối lập. Đương nhiên là vậy rồi, 17 năm qua, chúng ta sinh ra, lớn lên, tiếp xúc ở những môi trường hoàn toàn khác nhau. Nếu chúng ta có chút khác biệt, cũng là điều dễ hiểu nhỉ! Bé có nghĩ vậy không? Nhưng dat cũng dần nhận ra rằng, người ta yêu nhau không phải khi tâm đầu ý hợp, giống nhau 100% từ việc nhỏ đến việc lớn. Mà người ta yêu nhau khi sẵn sàng thay đổi vì nhau. dat hiểu ra điều đó, và dat tin điều đó là đúng. Bởi mỗi khi mình gặp vấn đề, dù trong lòng dat có vô số bất mãn muốn nói ra. Nhưng chỉ cần dat nhìn đôi mắt đỏ hoe, rưng rưng của bé, mọi điều muốn nói đó liền biến mất. dat chỉ còn biết nói đó là lỗi của dat, và tìm cách để bé hết khóc. Dù nghe khó tin, đến dat cũng không tin được mà, nhưng nhìn bé khóc, lòng dat khó chịu đến kì lạ. Dù dat không thích nghe theo ý kiến của người khác, nhưng với bé, nếu dat hiểu điều đó khiến bé buồn lòng, dat sẽ thay đổi. <br>"
				+ "Cảm ơn bé vì đã đến bên dat, ở cạnh dat bất chấp bất cứ điều gì tồi tệ đã xảy ra trong 1 năm qua. Cảm ơn bé vì đã khiến dat cười, cho dat lời khuyên, cho dat động lực để trở nên tốt hơn. Cảm ơn bé vì mọi thứ. dat sẽ không coi 1 năm là một dấu mốc để đánh giấu điều gì đó. Bởi mỗi ngày dat đều thầm biết ơn vì bé vẫn ở đây. Bé đừng nghĩ, chỉ vì 1 năm đã qua cùng nhau, mà dat sẽ cho rằng bé là hiển nhiên và dat sẽ không bao giờ mất bé. Với dat, bé luôn là kho báu như lúc dat còn theo đuổi bé. Không, đúng hơn phải là kho báu ngày càng giá trị theo thời gian, vì qua thời gian, dat càng thêm yêu bé mà. Nhớ đó! (Ấn nút Next tiếp nào) <br>"
				+ "" + "</div>" + "</html>");
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
				main.showSpecialPanel();
				setVisible(false);
			}
		});

		btNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSpecial3Panel();
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
