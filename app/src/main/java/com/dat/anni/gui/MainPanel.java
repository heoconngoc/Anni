package com.dat.anni.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.dat.anni.game.chormedinosaur.ChromeDinosaurPanel;
import com.dat.anni.game.chormedinosaur.Dino_RulePanel;
import com.dat.anni.game.chormedinosaur.Dino_StartPanel;
import com.dat.anni.game.flappybird.FlappyBirdPanel;
import com.dat.anni.game.flappybird.Flappy_RulePanel;
import com.dat.anni.game.flappybird.Flappy_StartPanel;
import com.dat.anni.game.matchcard.MatchCardPanel;
import com.dat.anni.game.matchcard.Match_RulePanel;
import com.dat.anni.game.matchcard.Match_StartPanel;
import com.dat.anni.game.minesweeper.Mines_RulePanel;
import com.dat.anni.game.minesweeper.Mines_StartPanel;
import com.dat.anni.game.minesweeper.MinesweeperPanel;
import com.dat.anni.game.pacman.PacManPanel;
import com.dat.anni.game.pacman.Pac_RulePanel;
import com.dat.anni.game.pacman.Pac_StartPanel;
import com.dat.anni.game.snake.SnakeGamePanel;
import com.dat.anni.game.snake.Snake_RulePanel;
import com.dat.anni.game.snake.Snake_StartPanel;
import com.dat.anni.game.spaceinvaders.SpaceInvadersPanel;
import com.dat.anni.game.spaceinvaders.Space_RulePanel;
import com.dat.anni.game.spaceinvaders.Space_StartPanel;
import com.dat.anni.game.whacamole.WhacAMolePanel;
import com.dat.anni.game.whacamole.Whac_RulePanel;
import com.dat.anni.game.whacamole.Whac_StartPanel;
import com.dat.anni.util.SoundManager;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String START = "start";
	public static final String MENU = "menu";
	public static final String NORMAL = "normal";
	public static final String SPECIAL_1 = "special1";
	public static final String SPECIAL_2 = "special2";
	public static final String SPECIAL_3 = "special3";
	public static final String GAMES_PAGE_1 = "gamesPage1";
	public static final String GAMES_PAGE_2 = "gamesPage2";

	public static final String PAC_START = "pacStart";
	public static final String PAC_RULES = "pacRules";
	public static final String PAC_GAME = "pacGame";
	public static final String WHAC_START = "whacStart";
	public static final String WHAC_RULES = "whacRules";
	public static final String WHAC_GAME = "whacGame";
	public static final String MATCH_START = "matchStart";
	public static final String MATCH_RULES = "matchRules";
	public static final String MATCH_GAME = "matchGame";
	public static final String SPACE_START = "spaceStart";
	public static final String SPACE_RULES = "spaceRules";
	public static final String SPACE_GAME = "spaceGame";
	public static final String DINO_START = "dinoStart";
	public static final String DINO_RULES = "dinoRules";
	public static final String DINO_GAME = "dinoGame";
	public static final String FLAPPY_START = "flappyStart";
	public static final String FLAPPY_RULES = "flappyRules";
	public static final String FLAPPY_GAME = "flappyGame";
	public static final String SNAKE_START = "snakeStart";
	public static final String SNAKE_RULES = "snakeRules";
	public static final String SNAKE_GAME = "snakeGame";
	public static final String MINES_START = "minesStart";
	public static final String MINES_RULES = "minesRules";
	public static final String MINES_GAME = "minesGame";
	public static final String SCORES_HUB = "scoresHub";

	private final Map<String, Component> cards = new HashMap<>();
	private NormalPanel normalPanel;
	private String currentName;

	public MainPanel() {
		initPanel();
		addComps();
	}

	private void initPanel() {
		setBackground(Color.WHITE);
		setLayout(new CardLayout());
	}

	private void register(String name, JPanel panel) {
		if (!(panel instanceof MainPanelAware aware)) {
			throw new IllegalArgumentException("Panel phải implement MainPanelAware: " + panel.getClass().getName());
		}
		aware.setMainPanel(this);
		if (NORMAL.equals(name)) {
			normalPanel = (NormalPanel) panel;
		}
		cards.put(name, panel);
		add(panel, name);
	}

	private void addComps() {
		register(START, new StartPanel());
		register(MENU, new MenuPanel());
		register(NORMAL, new NormalPanel());
		register(SPECIAL_1, new SpecialPanel());
		register(SPECIAL_2, new Special2Panel());
		register(SPECIAL_3, new Special3Panel());
		register(GAMES_PAGE_1, new GamePanel());
		register(SCORES_HUB, new ScoreHubPanel());
		register(GAMES_PAGE_2, new Game2Panel());

		register(PAC_START, new Pac_StartPanel());
		register(PAC_RULES, new Pac_RulePanel());
		register(PAC_GAME, new PacManPanel());

		register(WHAC_START, new Whac_StartPanel());
		register(WHAC_RULES, new Whac_RulePanel());
		register(WHAC_GAME, new WhacAMolePanel());

		register(MATCH_START, new Match_StartPanel());
		register(MATCH_RULES, new Match_RulePanel());
		register(MATCH_GAME, new MatchCardPanel());

		register(SPACE_START, new Space_StartPanel());
		register(SPACE_RULES, new Space_RulePanel());
		register(SPACE_GAME, new SpaceInvadersPanel());

		register(DINO_START, new Dino_StartPanel());
		register(DINO_RULES, new Dino_RulePanel());
		register(DINO_GAME, new ChromeDinosaurPanel());

		register(FLAPPY_START, new Flappy_StartPanel());
		register(FLAPPY_RULES, new Flappy_RulePanel());
		register(FLAPPY_GAME, new FlappyBirdPanel());

		register(SNAKE_START, new Snake_StartPanel());
		register(SNAKE_RULES, new Snake_RulePanel());
		register(SNAKE_GAME, new SnakeGamePanel());

		register(MINES_START, new Mines_StartPanel());
		register(MINES_RULES, new Mines_RulePanel());
		register(MINES_GAME, new MinesweeperPanel());
	}

	public void show(String name) {
		if (name.equals(currentName)) {
			return;
		}
		if (currentName != null && cards.get(currentName) instanceof Navigable leaving) {
			leaving.onLeave();
		}
		((CardLayout) getLayout()).show(this, name);
		currentName = name;
		SoundManager.play(SoundManager.Sfx.CLICK);
		if (cards.get(name) instanceof Navigable entering) {
			entering.onEnter();
		}
	}

	public void setLbLetter(String string) {
		normalPanel.setLbLetter(string);
	}
}
