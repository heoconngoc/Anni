package main.java.com.dat.anni.game.whacamole;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WhacAMole extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel textLabel; // Hiển thị điểm hiện tại
	private JLabel highScoreLabel; // Hiển thị điểm cao nhất
	private JPanel boardPanel;
	private JButton[] board = new JButton[9];
	private ImageIcon moleIcon;
	private ImageIcon plantIcon;

	private JButton currMoleTile;
	private Set<JButton> currPlantTiles = new HashSet<>(); // Tập hợp các ô hoa ăn thịt
	private Timer moleTimer;
	private Timer plantTimer;
	private JButton btRestart;
	private int score = 0;
	private int highScore = 0; // Điểm cao nhất
	private int turnCount = 0; // Đếm số lượt
	private Random random = new Random();

	public WhacAMole() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));

		initGameComponents();
		startTimers();
		loadHighScore();
	}

	private void initGameComponents() {
		// Panel trên cùng chứa điểm số và điểm cao nhất
		JPanel topPanel = new JPanel(new GridLayout(2, 1));

		// Label hiển thị điểm cao nhất
		highScoreLabel = new JLabel("High Score: " + highScore, JLabel.CENTER);
		highScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
		topPanel.add(highScoreLabel);

		// Label hiển thị điểm hiện tại
		textLabel = new JLabel("Score: " + score, JLabel.CENTER);
		textLabel.setFont(new Font("Arial", Font.BOLD, 24));
		topPanel.add(textLabel);

		add(topPanel, BorderLayout.NORTH);

		// Bảng chơi (3x3)
		boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		add(boardPanel, BorderLayout.CENTER);

		// Icon hình ảnh
		moleIcon = new ImageIcon(new ImageIcon(getClass().getResource("/imgs/monty.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		plantIcon = new ImageIcon(new ImageIcon(getClass().getResource("/imgs/piranha.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

		// Tạo các nút trong bảng
		for (int i = 0; i < 9; i++) {
			JButton tile = new JButton();
			tile.setFocusable(false);
			board[i] = tile;
			boardPanel.add(tile);

			tile.addActionListener(e -> handleTileClick(tile));
		}

		// Nút Restart
		btRestart = new JButton("Restart");
		btRestart.setFont(new Font("Arial", Font.BOLD, 16));
		btRestart.setVisible(false); // Ẩn nút khi game đang diễn ra
		btRestart.addActionListener(e -> restartGame());
		add(btRestart, BorderLayout.SOUTH);
	}

	private void handleTileClick(JButton tile) {
		if (tile == currMoleTile) {
			score += 5;
			textLabel.setText("Score: " + score);
			turnCount++; // Tăng lượt sau mỗi lần nhấn đúng chuột

			// Cập nhật điểm cao nhất nếu cần
			if (score > highScore) {
				highScore = score;
				saveHighScore(); // Lưu điểm cao mới
				highScoreLabel.setText("High Score: " + highScore);
			}
		} else if (currPlantTiles.contains(tile)) {
			stopTimers();
			textLabel.setText("Game Over! Final Score: " + score);
			showRestartButton();
		}
	}

	private void startTimers() {
		moleTimer = new Timer(1000, e -> spawnMole());
		plantTimer = new Timer(1500, e -> spawnPlants());
		moleTimer.start();
		plantTimer.start();
	}

	private void spawnMole() {
		if (currMoleTile != null)
			currMoleTile.setIcon(null);
		currMoleTile = board[random.nextInt(9)];
		currMoleTile.setIcon(moleIcon);
	}

	private void spawnPlants() {
		// Xóa các hoa hiện tại
		for (JButton tile : currPlantTiles) {
			tile.setIcon(null);
		}
		currPlantTiles.clear();

		// Tăng số hoa theo số lượt chơi
		int numPlants = Math.min(1 + turnCount / 4, 8); // Tăng dần, tối đa 8 hoa

		while (currPlantTiles.size() < numPlants) {
			JButton tile = board[random.nextInt(9)];

			// Chỉ thêm ô mới nếu không trùng với chuột hoặc hoa đã có
			if (tile != currMoleTile && !currPlantTiles.contains(tile)) {
				currPlantTiles.add(tile);
				tile.setIcon(plantIcon);
			}
		}
	}

	public void stopTimers() {
		moleTimer.stop();
		plantTimer.stop();
	}

	private void showRestartButton() {
		btRestart.setVisible(true);
	}

	public void restartGame() {
		// Reset các trạng thái trò chơi
		score = 0;
		turnCount = 0;
		textLabel.setText("Score: " + score);

		if (currMoleTile != null)
			currMoleTile.setIcon(null);
		for (JButton tile : currPlantTiles) {
			tile.setIcon(null);
		}
		currPlantTiles.clear();

		// Ẩn nút Restart
		btRestart.setVisible(false);

		// Bắt đầu lại các bộ đếm thời gian
		startTimers();
	}

	// Lưu điểm cao vào Preferences
	private void saveHighScore() {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		prefs.putInt("WhacHighScore", highScore); // Lưu điểm cao
	}

	// Tải điểm cao từ Preferences
	private void loadHighScore() {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		highScore = prefs.getInt("WhacHighScore", 0); // Tải điểm cao (mặc định là 0 nếu không tìm thấy)
		highScoreLabel.setText("High Score: " + highScore);
	}

	public void newGame() {
		stopTimers();
		showRestartButton();
	}
}
