package Tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Klasa zawierajaca wszystkie potrzebne instancje wszystkich pozostalych klas, warstowwy panel zawierajacy menu oraz grę.
 * Zawiera glassPanel zawierajacy panel wyswietlanu po przegraniu rozgrywki.
 */
public class GameManager {
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;
    private ControlsView controlsView;
    private AutoFall autoFall;
    private MenuPanel menuPanel;
    private GameOverView gameOverView;
    private boolean activeState = true;

    /**
     * Konstuktor tworzacy niezbedne obiekty.
     */
    GameManager() {
        Window window = new Window();
        this.scoreSystem = new ScoreSystem();
        this.playBoard = new PlayBoard(scoreSystem, this);
        NextElementPanel nextElementPanel = new NextElementPanel(playBoard);
        ScoreView scoreView = new ScoreView(scoreSystem);
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
        playBoard.setBounds(0, 0, 300, 600);
        sidePanel.setBounds(300, 0, 200, 600);
        menuPanel.setBounds(0, 0, 500, 600);
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

    /**
     * Klasa pozwalajaca na pokazanie / ukrycie panelu menu gry.
     */
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

    /**
     * Metoda konczaca program.
     */
    public void closeGame() {
        System.exit(0);
    }

    /**
     * Metoda wywolywana po przegraniu gry.
     * Uwidacznia panel gameOverView.
     * Dezaktywuje przyciski sterowania gra i menu.
     * Zatrzymuje autoOpadanie.
     * Zapisuje najwyzszy wynik do Bazy Danych.
     */
    public void gameOver() {
        autoFall.pause();
        gameOverView.setVisible(true);
        playBoard.repaint();
        controlsView.setActive(false);
        activeState = false;
        DbConnection.saveHighestScore(scoreSystem.getHighScore());
    }

    /**
     * Resetuje rozgrywke po przegranej.
     * Czysci plansze.
     * Aktywuje menu.
     * Aktywuje przyciski.
     */
    public void resume() {
        activeState = true;
        menuPanel.setMenuVisibility();
        playBoard.reset();
        controlsView.setActive(true);
        autoFall.setInterval(1000);
    }
}
