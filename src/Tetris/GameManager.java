package Tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameManager {
    private Window window;
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;
    private NextElementPanel nextElementPanel;
    private ScoreView scoreView;
    private ControlsView controlsView;
    private AutoFall autoFall;
    private MenuPanel menuPanel;
    private GameOverView gameOverView;
    private boolean activeState = true;

    GameManager()
    {
        this.window = new Window();
        this.scoreSystem = new ScoreSystem();
        this.playBoard = new PlayBoard(scoreSystem, this);
        this.nextElementPanel = new NextElementPanel(playBoard);
        this.scoreView = new ScoreView(scoreSystem);
        this.autoFall = new AutoFall(playBoard, scoreSystem);
        autoFall.start();
        this.scoreSystem.setScoreView(scoreView);
        this.playBoard.setNextElementPanel(nextElementPanel);
        this.controlsView = new ControlsView(autoFall, playBoard);
        HighScoreView highScoreView = new HighScoreView();
        this.menuPanel = new MenuPanel(playBoard, autoFall, highScoreView);
        this.gameOverView = new GameOverView(this, scoreSystem);
        menuPanel.setGameManager(this);
        window.setGlassPane(gameOverView);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(nextElementPanel);
        sidePanel.add(scoreView);
        sidePanel.add(controlsView);

        JLayeredPane layeredPane = new JLayeredPane();
        playBoard.setBounds(0,0,300, 600);
        sidePanel.setBounds(300, 0, 200, 600);
        menuPanel.setBounds(0,0,500,600);
        gameOverView.setBounds(0, 0, 500, 600);
        highScoreView.setBounds(0, 0, 500, 600);
        layeredPane.add(playBoard, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.POPUP_LAYER);
        layeredPane.add(highScoreView, JLayeredPane.DRAG_LAYER);
        window.add(layeredPane);

        ToggleAction toggleMenu = new ToggleAction(menuPanel);
        InputMap imap = layeredPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ESCAPE"), "toggle.menu");
        ActionMap amap = layeredPane.getActionMap();
        amap.put("toggle.menu", toggleMenu);
    }

    private class ToggleAction extends AbstractAction {
        private MenuPanel menuPanel;

        ToggleAction(MenuPanel menuPanel) {
            this.menuPanel = menuPanel;
        }
        public void actionPerformed(ActionEvent event) {
            if (activeState)
                menuPanel.setMenuVisibility();
        }
    }

    public void closeGame() {
        System.exit(0);
    }

    public void gameOver() {
        autoFall.pause();
        gameOverView.setVisible(true);
        playBoard.repaint();
        controlsView.setActive(false);
        activeState = false;
        DbConnection.saveHighestScore(scoreSystem.getHighScore());
    }

    public void resume() {
        activeState = true;
        menuPanel.setMenuVisibility();
        playBoard.reset();
        controlsView.setActive(true);
        autoFall.setInterval(1000);
    }
}
