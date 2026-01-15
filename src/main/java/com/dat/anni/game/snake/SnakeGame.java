package main.java.com.dat.anni.game.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private final int tileSize = 40; // Kích thước mỗi ô
	private final int gridSize = 15; // Số ô mỗi chiều
	private final int boardWidth = (gridSize + 5) * tileSize; // Chiều rộng bảng
	private final int boardHeight = gridSize * tileSize; // Chiều cao bảng

	private Timer gameLoop;
	private Tile snakeHead;
	private ArrayList<Tile> snakeBody;
	private Tile food;
	private int velocityX, velocityY;
	private Random random;
	private boolean gameOver = false;
	private boolean gameStarted = false;
	private int score = 0; // Điểm số
	private int highScore = 0; // Điểm cao nhất
	private int foodValue = 1; // Giá trị ban đầu của thức ăn (điểm)

	public SnakeGame() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);

		// Tải điểm cao từ Preferences
		loadHighScore();

		// Initialize snake and food
		snakeHead = new Tile(gridSize / 2, gridSize / 2);
		snakeBody = new ArrayList<>();
		food = new Tile(0, 0);
		random = new Random();
		placeFood();

		velocityX = 1;
		velocityY = 0;

		// Game loop timer
		gameLoop = new Timer(150, this); // Điều chỉnh tốc độ game tại đây
	}

	public void placeFood() {
		food.x = random.nextInt(gridSize);
		food.y = random.nextInt(gridSize);
	}

	// Phương thức lưu điểm cao vào Preferences
	private void saveHighScore() {
		Preferences prefs = Preferences.userNodeForPackage(SnakeGame.class);
		prefs.putInt("SnakehighScore", highScore); // Lưu điểm cao
	}

	// Phương thức tải điểm cao từ Preferences
	private void loadHighScore() {
		Preferences prefs = Preferences.userNodeForPackage(SnakeGame.class);
		highScore = prefs.getInt("SnakehighScore", 0); // Tải điểm cao (mặc định là 0 nếu không tìm thấy)
	}

	public void move() {
		if (gameOver)
			return;

		int newX = snakeHead.x + velocityX;
		int newY = snakeHead.y + velocityY;

		// Kiểm tra nếu rắn vượt ra ngoài lưới
		if (newX < 0 || newY < 0 || newX >= gridSize || newY >= gridSize) {
			gameOver = true;
			return;
		}

		// Kiểm tra nếu rắn tự va vào mình
		for (Tile part : snakeBody) {
			if (part.x == newX && part.y == newY) {
				gameOver = true;
				return;
			}
		}

		// Di chuyển đầu rắn
		snakeBody.add(0, new Tile(snakeHead.x, snakeHead.y)); // Thêm phần thân
		snakeHead.x = newX;
		snakeHead.y = newY;

		// Kiểm tra nếu rắn ăn thức ăn
		if (snakeHead.x == food.x && snakeHead.y == food.y) {
			score += foodValue; // Tăng điểm khi ăn thức ăn
			if (score > highScore) {
				highScore = score; // Cập nhật điểm cao nhất
				saveHighScore(); // Lưu điểm cao vào Preferences
			}
			foodValue += 3; // Tăng giá trị thức ăn sau mỗi lần ăn
			placeFood();
			increaseSpeed(); // Tăng tốc độ sau mỗi lần ăn
		} else {
			snakeBody.remove(snakeBody.size() - 1); // Loại bỏ phần đuôi
		}
	}

	// Hàm để tăng tốc độ trò chơi
	private void increaseSpeed() {
		int delay = gameLoop.getDelay();
		if (delay > 100) {
			gameLoop.setDelay(delay - 5); // Giảm thời gian giữa các vòng lặp
		}
	}

	public void draw(Graphics g) {
		// Vẽ ô lưới
		g.setColor(new Color(15, 25, 35));
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
			}
		}

		// Vẽ đường viền lưới
		g.setColor(Color.black); // Đường viền màu đen
		for (int i = 0; i <= gridSize; i++) {
			g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); // Đường thẳng đứng
			g.drawLine(0, i * tileSize, boardWidth, i * tileSize); // Đường thẳng ngang
		}

		// Vẽ thức ăn
		g.setColor(Color.red);
		g.fillRect(food.x * tileSize + 10, food.y * tileSize + 10, tileSize - 20, tileSize - 20);

		// Vẽ rắn
		g.setColor(Color.green);
		g.fillRect(snakeHead.x * tileSize + 2, snakeHead.y * tileSize + 2, tileSize - 4, tileSize - 4);
		for (Tile part : snakeBody) {
			g.fillRect(part.x * tileSize + 2, part.y * tileSize + 2, tileSize - 4, tileSize - 4);
		}

		// Vẽ thông báo game over
		if (gameOver) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 35));
			g.drawString("Game Over", 205, 275);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Press 'R' to Play Again", 165, 315);
		}

		// Vẽ thông báo bắt đầu game
		if (!gameStarted && !gameOver) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Press an Arrow Key to Start", 100, 310);
		}

		// Vẽ điểm số và điểm cao
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Score: " + score, boardWidth - 175, 265);
		g.drawString("Highscore: " + highScore, boardWidth - 175, 315);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gameOver && gameStarted) {
			move();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (!gameStarted) {
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT
					|| key == KeyEvent.VK_RIGHT) {
				gameStarted = true;
				gameLoop.start();
			}
		}
		if (gameStarted) {
			if (key == KeyEvent.VK_UP && velocityY == 0) {
				velocityX = 0;
				velocityY = -1;
			} else if (key == KeyEvent.VK_DOWN && velocityY == 0) {
				velocityX = 0;
				velocityY = 1;
			} else if (key == KeyEvent.VK_LEFT && velocityX == 0) {
				velocityX = -1;
				velocityY = 0;
			} else if (key == KeyEvent.VK_RIGHT && velocityX == 0) {
				velocityX = 1;
				velocityY = 0;
			}
		}

		if (key == KeyEvent.VK_R && gameOver) {
			restartGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void restartGame() {
		snakeHead = new Tile(gridSize / 2, gridSize / 2);
		snakeBody.clear();
		placeFood();
		velocityX = 0;
		velocityY = 0;
		score = 0; // Reset điểm khi bắt đầu lại
		foodValue = 1; // Reset giá trị thức ăn khi bắt đầu lại
		gameOver = false;
		gameStarted = false;
		gameLoop.stop();
		repaint();
	}

	// Class Tile đại diện cho từng ô
	static class Tile {
		int x, y;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
}
