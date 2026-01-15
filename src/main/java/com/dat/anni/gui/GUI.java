package main.java.com.dat.anni.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH_FRAME = 1000;
	private static final int HEIGHT_FRAME = 700;

	public GUI() {
		initGUI();
		addComps();
		addEvents();
	}

	private void initGUI() {
		setTitle("Just For You Chole");
		setSize(WIDTH_FRAME, HEIGHT_FRAME);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new CardLayout());
		getContentPane().setBackground(Color.WHITE);
//		try {
//			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void addComps() {
		MainPanel mainPanel = new MainPanel();
		add(mainPanel);
	}

	private void addEvents() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.META_DOWN_MASK), "closeWindow");

		getRootPane().getActionMap().put("closeWindow", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // Đóng cửa sổ
			}
		});
	}
}
