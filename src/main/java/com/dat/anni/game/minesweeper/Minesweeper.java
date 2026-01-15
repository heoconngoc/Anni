package main.java.com.dat.anni.game.minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Minesweeper extends JPanel {
	private static final long serialVersionUID = 1L;

	private class MineTile extends JButton {
		private static final long serialVersionUID = 1L;
		int r;
		int c;

		public MineTile(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	int numRows = 10;
	int numCols = 10;
	int mineCount = 10;
	int tileSize;

	JLabel textLabel = new JLabel();
	JPanel boardPanel = new JPanel();
	JComboBox<String> modeSelector;
	JButton playAgainButton = new JButton("Play Again!");

	MineTile[][] board;
	ArrayList<MineTile> mineList;
	Random random = new Random();
	boolean gameOver = false;
	int flagsLeft; // Số cờ còn lại

	public Minesweeper() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(560, 660)); // Panel lớn hơn để chứa cả combo box và bảng

		// Label hiển thị trạng thái trò chơi
		textLabel.setFont(new Font("Arial", Font.BOLD, 20));
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setText("Số cờ: " + mineCount);

		// Combo box cho chế độ
		modeSelector = new JComboBox<>(new String[] { "Dễ", "Vừa", "Khó" });
		modeSelector.addActionListener(e -> updateMode());

		// Nút Play Again
		playAgainButton.setFont(new Font("Arial", Font.BOLD, 18));
		playAgainButton.setVisible(false); // Ẩn nút ban đầu
		playAgainButton.addActionListener(e -> {
			updateMode(); // Khởi động lại trò chơi theo chế độ hiện tại
		});

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(textLabel, BorderLayout.CENTER);
		topPanel.add(modeSelector, BorderLayout.EAST);
		topPanel.add(playAgainButton, BorderLayout.WEST);
		add(topPanel, BorderLayout.NORTH);

		// Bảng trò chơi
		boardPanel.setLayout(new GridLayout(numRows, numCols));
		add(boardPanel, BorderLayout.CENTER);

		updateMode();
	}

	void updateMode() {
		String mode = (String) modeSelector.getSelectedItem();
		switch (mode) {
		case "Dễ" -> {
			numRows = 10;
			numCols = 10;
			mineCount = 10;
		}
		case "Vừa" -> {
			numRows = 15;
			numCols = 15;
			mineCount = 40;
		}
		case "Khó" -> {
			numRows = 20;
			numCols = 20;
			mineCount = 99;
		}
		}
		flagsLeft = mineCount;
		tileSize = Math.min(560 / numCols, 560 / numRows);
		resetBoard();
		setMines();
	}

	void resetBoard() {
		boardPanel.removeAll();
		boardPanel.setLayout(new GridLayout(numRows, numCols));
		board = new MineTile[numRows][numCols];

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				MineTile tile = new MineTile(r, c);
				board[r][c] = tile;

				tile.setFocusable(false);
				tile.setMargin(new Insets(0, 0, 0, 0));
				tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, tileSize / 2));
				tile.setPreferredSize(new Dimension(tileSize, tileSize));

				tile.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (gameOver) {
							return;
						}
						MineTile tile = (MineTile) e.getSource();

						// Left click
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (tile.getText().isEmpty()) {
								if (mineList.contains(tile)) {
									revealMines();
									return;
								} else {
									checkMine(tile.r, tile.c);
								}
							}
						}
						// Right click
						else if (e.getButton() == MouseEvent.BUTTON3) {
							// Kiểm tra số cờ còn lại
							if (tile.getText().isEmpty() && flagsLeft > 0) {
								tile.setText("🚩");
								flagsLeft--;
							} else if (tile.getText().equals("🚩")) {
								tile.setText("");
								flagsLeft++;
							}

						}
						if (!isWin()) {
							// Cập nhật số cờ còn lại sau mỗi thao tác
							textLabel.setText("Số cờ: " + flagsLeft);
						}
					}
				});
				boardPanel.add(tile);
			}
		}
		boardPanel.revalidate();
		boardPanel.repaint();
		gameOver = false;
		textLabel.setText("Số cờ: " + mineCount);
		playAgainButton.setVisible(false); // Ẩn nút khi trò chơi bắt đầu lại
	}

	void setMines() {
		mineList = new ArrayList<>();
		int minesLeft = mineCount;

		while (minesLeft > 0) {
			int r = random.nextInt(numRows);
			int c = random.nextInt(numCols);

			MineTile tile = board[r][c];
			if (!mineList.contains(tile)) {
				mineList.add(tile);
				minesLeft--;
			}
		}
	}

	void revealMines() {
		for (MineTile tile : mineList) {
			tile.setText("💣");
		}
		gameOver = true;
		textLabel.setText("Game Over!");
		playAgainButton.setVisible(true); // Hiển thị nút Play Again khi kết thúc trò chơi
	}

	void checkMine(int r, int c) {
		if (r < 0 || r >= numRows || c < 0 || c >= numCols || !board[r][c].isEnabled()) {
			return;
		}
		MineTile tile = board[r][c];
		tile.setEnabled(false);

		int minesFound = countSurroundingMines(r, c);
		if (minesFound > 0) {
			tile.setText(Integer.toString(minesFound));
		} else {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					checkMine(r + i, c + j);
				}
			}
		}

		if (isWin()) {
			textLabel.setText("You Win!");
			gameOver = true;
			playAgainButton.setVisible(true); // Hiển thị nút Play Again khi thắng
		}
	}

	int countSurroundingMines(int r, int c) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int nr = r + i;
				int nc = c + j;
				if (nr >= 0 && nr < numRows && nc >= 0 && nc < numCols && mineList.contains(board[nr][nc])) {
					count++;
				}
			}
		}
		return count;
	}

	boolean isWin() {
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				if (!mineList.contains(board[r][c]) && board[r][c].isEnabled()) {
					return false;
				}
			}
		}
		return true;
	}
}