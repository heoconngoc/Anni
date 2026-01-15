//package com.dat.anni.game.matchcard;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.Timer;
//
//public class MatchCards {
//	class Card {
//		String cardName;
//		ImageIcon cardImageIcon;
//
//		Card(String cardName, ImageIcon cardImageIcon) {
//			this.cardName = cardName;
//			this.cardImageIcon = cardImageIcon;
//		}
//
//		public String toString() {
//			return cardName;
//		}
//	}
//
//	String[] cardList = { // track cardNames
//			"darkness", "double", "fairy", "fighting", "fire", "grass", "lightning", "metal", "psychic", "water" };
//
//	int rows = 4;
//	int columns = 5;
//	int cardWidth = 90;
//	int cardHeight = 128;
//
//	ArrayList<Card> cardSet; // create a deck of cards with cardNames and cardImageIcons
//	ImageIcon cardBackImageIcon;
//
//	int boardWidth = columns * cardWidth; // 5*128 = 640px
//	int boardHeight = rows * cardHeight; // 4*90 = 360px
//
//	JFrame frame = new JFrame("Pokemon Match Cards");
//	JLabel textLabel = new JLabel();
//	JPanel textPanel = new JPanel();
//	JPanel boardPanel = new JPanel();
//	JPanel restartGamePanel = new JPanel();
//	JButton restartButton = new JButton();
//
//	int errorCount = 0;
//	ArrayList<JButton> board;
//	Timer hideCardTimer;
//	boolean gameReady = false;
//	JButton card1Selected;
//	JButton card2Selected;
//
//	MatchCards() {
//		setupCards();
//		shuffleCards();
//
//		// frame.setVisible(true);
//		frame.setLayout(new BorderLayout());
//		frame.setSize(boardWidth, boardHeight);
//		frame.setLocationRelativeTo(null);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		// error text
//		textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
//		textLabel.setHorizontalAlignment(JLabel.CENTER);
//		textLabel.setText("Errors: " + Integer.toString(errorCount));
//
//		textPanel.setPreferredSize(new Dimension(boardWidth, 30));
//		textPanel.add(textLabel);
//		frame.add(textPanel, BorderLayout.NORTH);
//
//		// card game board
//		board = new ArrayList<JButton>();
//		boardPanel.setLayout(new GridLayout(rows, columns));
//		for (int i = 0; i < cardSet.size(); i++) {
//			JButton tile = new JButton();
//			tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
//			tile.setOpaque(true);
//			tile.setIcon(cardSet.get(i).cardImageIcon);
//			tile.setFocusable(false);
//			tile.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					if (!gameReady) {
//						return;
//					}
//					JButton tile = (JButton) e.getSource();
//					if (tile.getIcon() == cardBackImageIcon) {
//						if (card1Selected == null) {
//							card1Selected = tile;
//							int index = board.indexOf(card1Selected);
//							card1Selected.setIcon(cardSet.get(index).cardImageIcon);
//						} else if (card2Selected == null) {
//							card2Selected = tile;
//							int index = board.indexOf(card2Selected);
//							card2Selected.setIcon(cardSet.get(index).cardImageIcon);
//
//							if (card1Selected.getIcon() != card2Selected.getIcon()) {
//								errorCount += 1;
//								textLabel.setText("Errors: " + Integer.toString(errorCount));
//								hideCardTimer.start();
//							} else {
//								card1Selected = null;
//								card2Selected = null;
//							}
//						}
//					}
//				}
//			});
//			board.add(tile);
//			boardPanel.add(tile);
//		}
//		frame.add(boardPanel);
//
//		// restart game button
//		restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
//		restartButton.setText("Restart Game");
//		restartButton.setPreferredSize(new Dimension(boardWidth, 30));
//		restartButton.setFocusable(false);
//		restartButton.setEnabled(false);
//		restartButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (!gameReady) {
//					return;
//				}
//
//				gameReady = false;
//				restartButton.setEnabled(false);
//				card1Selected = null;
//				card2Selected = null;
//				shuffleCards();
//
//				// re assign buttons with new cards
//				for (int i = 0; i < board.size(); i++) {
//					board.get(i).setIcon(cardSet.get(i).cardImageIcon);
//				}
//
//				errorCount = 0;
//				textLabel.setText("Errors: " + Integer.toString(errorCount));
//				hideCardTimer.start();
//			}
//		});
//		restartGamePanel.add(restartButton);
//		frame.add(restartGamePanel, BorderLayout.SOUTH);
//
//		frame.pack();
//		frame.setVisible(true);
//
//		// start game
//		hideCardTimer = new Timer(1500, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				hideCards();
//			}
//		});
//		hideCardTimer.setRepeats(false);
//		hideCardTimer.start();
//
//	}
//
//	void setupCards() {
//		cardSet = new ArrayList<Card>();
//		for (String cardName : cardList) {
//			// load each card image
//			Image cardImg = new ImageIcon(getClass().getResource("/imgs/" + cardName + ".jpg")).getImage();
//			ImageIcon cardImageIcon = new ImageIcon(
//					cardImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
//
//			// create card object and add to cardSet
//			Card card = new Card(cardName, cardImageIcon);
//			cardSet.add(card);
//		}
//		cardSet.addAll(cardSet);
//
//		// load the back card image
//		Image cardBackImg = new ImageIcon(getClass().getResource("/imgs/back.jpg")).getImage();
//		cardBackImageIcon = new ImageIcon(
//				cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
//	}
//
//	void shuffleCards() {
//		System.out.println(cardSet);
//		// shuffle
//		for (int i = 0; i < cardSet.size(); i++) {
//			int j = (int) (Math.random() * cardSet.size()); // get random index
//			// swap
//			Card temp = cardSet.get(i);
//			cardSet.set(i, cardSet.get(j));
//			cardSet.set(j, temp);
//		}
//		System.out.println(cardSet);
//	}
//
//	void hideCards() {
//		if (gameReady && card1Selected != null && card2Selected != null) { // only flip 2 cards
//			card1Selected.setIcon(cardBackImageIcon);
//			card1Selected = null;
//			card2Selected.setIcon(cardBackImageIcon);
//			card2Selected = null;
//		} else { // flip all cards face down
//			for (int i = 0; i < board.size(); i++) {
//				board.get(i).setIcon(cardBackImageIcon);
//			}
//			gameReady = true;
//			restartButton.setEnabled(true);
//		}
//	}
//}

package main.java.com.dat.anni.game.matchcard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MatchCards extends JPanel {
	private static final long serialVersionUID = 1L;

	class Card {
		String cardName;
		ImageIcon cardImageIcon;

		Card(String cardName, ImageIcon cardImageIcon) {
			this.cardName = cardName;
			this.cardImageIcon = cardImageIcon;
		}

		@Override
		public String toString() {
			return cardName;
		}
	}

	private String[] cardList = { "darkness", "double", "fairy", "fighting", "fire", "grass", "lightning", "metal",
			"psychic", "water" };
	private int rows = 4;
	private int columns = 5;
	private int cardWidth = 90;
	private int cardHeight = 128;

	private ArrayList<Card> cardSet;
	private ImageIcon cardBackImageIcon;

	private JLabel textLabel;
	private JPanel boardPanel;
	private JButton restartButton;

	private int errorCount = 0;
	private ArrayList<JButton> board;
	private Timer hideCardTimer;
	private boolean gameReady = false;
	private JButton card1Selected;
	private JButton card2Selected;

	public MatchCards() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(columns * cardWidth, rows * cardHeight + 60));
		setupCards();
		shuffleCards();
		initComponents();
		addComponents();
		startGame();
	}

	private void initComponents() {
		// Text Label
		textLabel = new JLabel("Errors: " + errorCount, JLabel.CENTER);
		textLabel.setFont(new Font("Arial", Font.PLAIN, 20));

		// Board Panel
		board = new ArrayList<>();
		boardPanel = new JPanel(new GridLayout(rows, columns));
		for (int i = 0; i < cardSet.size(); i++) {
			JButton tile = new JButton();
			tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
			tile.setOpaque(true);
			tile.setIcon(cardSet.get(i).cardImageIcon);
			tile.setFocusable(false);
			tile.addActionListener(new CardClickListener(tile, i));
			board.add(tile);
			boardPanel.add(tile);
		}

		// Restart Button
		restartButton = new JButton("Restart Game");
		restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
		restartButton.setEnabled(false);
		restartButton.addActionListener(e -> restartGame());
	}

	private void addComponents() {
		// Add components to panel
		add(textLabel, BorderLayout.NORTH);
		add(boardPanel, BorderLayout.CENTER);

		JPanel restartPanel = new JPanel();
		restartPanel.add(restartButton);
		add(restartPanel, BorderLayout.SOUTH);
	}

	private void setupCards() {
		cardSet = new ArrayList<>();
		for (String cardName : cardList) {
			Image cardImg = new ImageIcon(getClass().getResource("/imgs/" + cardName + ".jpg")).getImage();
			ImageIcon cardImageIcon = new ImageIcon(
					cardImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
			cardSet.add(new Card(cardName, cardImageIcon));
		}
		cardSet.addAll(cardSet);

		Image cardBackImg = new ImageIcon(getClass().getResource("/imgs/back.jpg")).getImage();
		cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
	}

	private void shuffleCards() {
		for (int i = 0; i < cardSet.size(); i++) {
			int j = (int) (Math.random() * cardSet.size());
			Card temp = cardSet.get(i);
			cardSet.set(i, cardSet.get(j));
			cardSet.set(j, temp);
		}
	}

	private void startGame() {
		hideCardTimer = new Timer(1500, e -> hideCards());
		hideCardTimer.setRepeats(false);
		hideCardTimer.start();
	}

	private void hideCards() {
		if (gameReady && card1Selected != null && card2Selected != null) {
			card1Selected.setIcon(cardBackImageIcon);
			card1Selected = null;
			card2Selected.setIcon(cardBackImageIcon);
			card2Selected = null;
		} else {
			for (JButton button : board) {
				button.setIcon(cardBackImageIcon);
			}
			gameReady = true;
			restartButton.setEnabled(true);
		}
	}

	private void restartGame() {
		gameReady = false;
		restartButton.setEnabled(false);
		card1Selected = null;
		card2Selected = null;
		shuffleCards();
		for (int i = 0; i < board.size(); i++) {
			board.get(i).setIcon(cardSet.get(i).cardImageIcon);
		}
		errorCount = 0;
		textLabel.setText("Errors: " + errorCount);
		hideCardTimer.start();
	}

	private class CardClickListener implements ActionListener {
		private final JButton tile;
		private final int index;

		public CardClickListener(JButton tile, int index) {
			this.tile = tile;
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!gameReady)
				return;

			if (tile.getIcon() == cardBackImageIcon) {
				if (card1Selected == null) {
					card1Selected = tile;
					card1Selected.setIcon(cardSet.get(index).cardImageIcon);
				} else if (card2Selected == null) {
					card2Selected = tile;
					card2Selected.setIcon(cardSet.get(index).cardImageIcon);

					if (!card1Selected.getIcon().equals(card2Selected.getIcon())) {
						errorCount++;
						textLabel.setText("Errors: " + errorCount);
						hideCardTimer.start();
					} else {
						card1Selected = null;
						card2Selected = null;
					}
				}
			}
		}
	}
}
