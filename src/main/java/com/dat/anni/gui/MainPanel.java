package main.java.com.dat.anni.gui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

import main.java.com.dat.anni.game.chormedinosaur.ChromeDinosaurPanel;
import main.java.com.dat.anni.game.chormedinosaur.Dino_RulePanel;
import main.java.com.dat.anni.game.chormedinosaur.Dino_StartPanel;
import main.java.com.dat.anni.game.flappybird.FlappyBirdPanel;
import main.java.com.dat.anni.game.flappybird.Flappy_RulePanel;
import main.java.com.dat.anni.game.flappybird.Flappy_StartPanel;
import main.java.com.dat.anni.game.matchcard.MatchCardPanel;
import main.java.com.dat.anni.game.matchcard.Match_RulePanel;
import main.java.com.dat.anni.game.matchcard.Match_StartPanel;
import main.java.com.dat.anni.game.minesweeper.Mines_RulePanel;
import main.java.com.dat.anni.game.minesweeper.Mines_StartPanel;
import main.java.com.dat.anni.game.minesweeper.MinesweeperPanel;
import main.java.com.dat.anni.game.pacman.PacManPanel;
import main.java.com.dat.anni.game.pacman.Pac_RulePanel;
import main.java.com.dat.anni.game.pacman.Pac_StartPanel;
import main.java.com.dat.anni.game.snake.SnakeGamePanel;
import main.java.com.dat.anni.game.snake.Snake_RulePanel;
import main.java.com.dat.anni.game.snake.Snake_StartPanel;
import main.java.com.dat.anni.game.spaceinvaders.SpaceInvadersPanel;
import main.java.com.dat.anni.game.spaceinvaders.Space_RulePanel;
import main.java.com.dat.anni.game.spaceinvaders.Space_StartPanel;
import main.java.com.dat.anni.game.whacamole.WhacAMolePanel;
import main.java.com.dat.anni.game.whacamole.Whac_RulePanel;
import main.java.com.dat.anni.game.whacamole.Whac_StartPanel;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private StartPanel startPanel;
	private MenuPanel menuPanel;
	private SpecialPanel specialPanel;
	private NormalPanel normalPanel;
	private Special2Panel special2Panel;
	private Special3Panel special3Panel;
	private GamePanel gamePanel;
	private Game2Panel game2Panel;
	private Pac_StartPanel pac_StartPanel;
	private Pac_RulePanel pac_RulePanel;
	private PacManPanel pacManPanel;
	private Whac_StartPanel whac_StartPanel;
	private WhacAMolePanel whacAMolePanel;
	private Whac_RulePanel whac_RulePanel;
	private Match_StartPanel match_StartPanel;
	private Match_RulePanel match_RulePanel;
	private MatchCardPanel matchCardPanel;
	private Space_StartPanel space_StartPanel;
	private Space_RulePanel space_RulePanel;
	private SpaceInvadersPanel spaceInvadersPanel;
	private ChromeDinosaurPanel chromeDinosaurPanel;
	private Dino_StartPanel dino_StartPanel;
	private Dino_RulePanel dino_RulePanel;
	private FlappyBirdPanel flappyBirdPanel;
	private Flappy_StartPanel flappy_StartPanel;
	private Flappy_RulePanel flappy_RulePanel;
	private Mines_StartPanel mines_StartPanel;
	private MinesweeperPanel minesweeperPanel;
	private Mines_RulePanel mines_RulePanel;
	private SnakeGamePanel snakeGamePanel;
	private Snake_StartPanel snake_StartPanel;
	private Snake_RulePanel snakeRulePanel;

	public MainPanel() {
		initPanel();
		addComps();
		addEvents();
	}

	private void initPanel() {
		setBackground(Color.WHITE);
		setLayout(new CardLayout());
	}

	private void addComps() {
		startPanel = new StartPanel();
		startPanel.setMainPanel(this);
		add(startPanel);

		menuPanel = new MenuPanel();
		menuPanel.setMainPanel(this);
		add(menuPanel);

		specialPanel = new SpecialPanel();
		specialPanel.setMainPanel(this);
		add(specialPanel);

		normalPanel = new NormalPanel();
		normalPanel.setMainPanel(this);
		add(normalPanel);

		special2Panel = new Special2Panel();
		special2Panel.setMainPanel(this);
		add(special2Panel);

		special3Panel = new Special3Panel();
		special3Panel.setMainPanel(this);
		add(special3Panel);

		gamePanel = new GamePanel();
		gamePanel.setMainPanel(this);
		add(gamePanel);

		game2Panel = new Game2Panel();
		game2Panel.setMainPanel(this);
		add(game2Panel);

		pac_StartPanel = new Pac_StartPanel();
		pac_StartPanel.setMainPanel(this);
		add(pac_StartPanel);

		pac_RulePanel = new Pac_RulePanel();
		pac_RulePanel.setMainPanel(this);
		add(pac_RulePanel);

		pacManPanel = new PacManPanel();
		pacManPanel.setMainPanel(this);
		add(pacManPanel);

		whac_StartPanel = new Whac_StartPanel();
		whac_StartPanel.setMainPanel(this);
		add(whac_StartPanel);

		whacAMolePanel = new WhacAMolePanel();
		whacAMolePanel.setMainPanel(this);
		add(whacAMolePanel);

		whac_RulePanel = new Whac_RulePanel();
		whac_RulePanel.setMainPanel(this);
		add(whac_RulePanel);

		match_StartPanel = new Match_StartPanel();
		match_StartPanel.setMainPanel(this);
		add(match_StartPanel);

		match_RulePanel = new Match_RulePanel();
		match_RulePanel.setMainPanel(this);
		add(match_RulePanel);

		matchCardPanel = new MatchCardPanel();
		matchCardPanel.setMainPanel(this);
		add(matchCardPanel);

		space_StartPanel = new Space_StartPanel();
		space_StartPanel.setMainPanel(this);
		add(space_StartPanel);

		space_RulePanel = new Space_RulePanel();
		space_RulePanel.setMainPanel(this);
		add(space_RulePanel);

		spaceInvadersPanel = new SpaceInvadersPanel();
		spaceInvadersPanel.setMainPanel(this);
		add(spaceInvadersPanel);

		dino_StartPanel = new Dino_StartPanel();
		dino_StartPanel.setMainPanel(this);
		add(dino_StartPanel);

		dino_RulePanel = new Dino_RulePanel();
		dino_RulePanel.setMainPanel(this);
		add(dino_RulePanel);

		chromeDinosaurPanel = new ChromeDinosaurPanel();
		chromeDinosaurPanel.setMainPanel(this);
		add(chromeDinosaurPanel);

		flappyBirdPanel = new FlappyBirdPanel();
		flappyBirdPanel.setMainPanel(this);
		add(flappyBirdPanel);

		flappy_StartPanel = new Flappy_StartPanel();
		flappy_StartPanel.setMainPanel(this);
		add(flappy_StartPanel);

		flappy_RulePanel = new Flappy_RulePanel();
		flappy_RulePanel.setMainPanel(this);
		add(flappy_RulePanel);

		snake_StartPanel = new Snake_StartPanel();
		snake_StartPanel.setMainPanel(this);
		add(snake_StartPanel);

		snakeGamePanel = new SnakeGamePanel();
		snakeGamePanel.setMainPanel(this);
		add(snakeGamePanel);

		snakeRulePanel = new Snake_RulePanel();
		snakeRulePanel.setMainPanel(this);
		add(snakeRulePanel);

		mines_StartPanel = new Mines_StartPanel();
		mines_StartPanel.setMainPanel(this);
		add(mines_StartPanel);

		minesweeperPanel = new MinesweeperPanel();
		minesweeperPanel.setMainPanel(this);
		add(minesweeperPanel);

		mines_RulePanel = new Mines_RulePanel();
		mines_RulePanel.setMainPanel(this);
		add(mines_RulePanel);
	}

	private void addEvents() {

	}

	public void showStartPanel() {
		startPanel.setVisible(true);
	}

	public void showMenuPanel() {
		menuPanel.setVisible(true);
	}

	public void showSpecialPanel() {
		specialPanel.setVisible(true);
	}

	public void showNormalPanel() {
		normalPanel.setVisible(true);
	}

	public void showSpecial2Panel() {
		special2Panel.setVisible(true);
	}

	public void showSpecial3Panel() {
		special3Panel.setVisible(true);
	}

	public void showGamePanel() {
		gamePanel.setVisible(true);
	}

	public void showGame2Panel() {
		game2Panel.setVisible(true);
	}

	public void showPac_StartPanel() {
		pac_StartPanel.setVisible(true);
	}

	public void showPac_RulePanel() {
		pac_RulePanel.setVisible(true);
	}

	public void showPacManPanel() {
		pacManPanel.setVisible(true);
	}

	public void showWhac_StartPanel() {
		whac_StartPanel.setVisible(true);
	}

	public void showWhacAMolePanel() {
		whacAMolePanel.setVisible(true);
	}

	public void showWhac_RulePanel() {
		whac_RulePanel.setVisible(true);
	}

	public void showMatch_StartPanel() {
		match_StartPanel.setVisible(true);
	}

	public void showMatch_RulePanel() {
		match_RulePanel.setVisible(true);
	}

	public void showMatchCardPanel() {
		matchCardPanel.setVisible(true);
	}

	public void showSpace_StartPanel() {
		space_StartPanel.setVisible(true);
	}

	public void showSpace_RulePanel() {
		space_RulePanel.setVisible(true);
	}

	public void showSpaceInvadersPanel() {
		spaceInvadersPanel.setVisible(true);
	}

	public void showDino_StartPanel() {
		dino_StartPanel.setVisible(true);
	}

	public void showDino_RulePanel() {
		dino_RulePanel.setVisible(true);
	}

	public void showChromeDinosaurPanel() {
		chromeDinosaurPanel.setVisible(true);
	}

	public void showFlappyBirdPanel() {
		flappyBirdPanel.setVisible(true);
	}

	public void showFlappy_StartPanel() {
		flappy_StartPanel.setVisible(true);
	}

	public void showFlappy_RulePanel() {
		flappy_RulePanel.setVisible(true);
	}

	public void showMines_StartPanel() {
		mines_StartPanel.setVisible(true);
	}

	public void showMinesweeperPanel() {
		minesweeperPanel.setVisible(true);
	}

	public void showMines_RulePanel() {
		mines_RulePanel.setVisible(true);
	}

	public void showSnakeGamePanel() {
		snakeGamePanel.setVisible(true);
	}

	public void showSnake_StartPanel() {
		snake_StartPanel.setVisible(true);
	}

	public void showSnake_RulePanel() {
		snakeRulePanel.setVisible(true);
	}

	public void setLbLetter(String string) {
		normalPanel.setLbLetter(string);
	}

}
