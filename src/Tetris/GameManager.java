package Tetris;

import java.awt.*;
import javax.swing.*;

public class GameManager {
    private Window window;
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;
    private NextElementPanel nextElementPanel;
    private ScoreView scoreView;
    private ControlsView controlsView;
    private AutoFall autoFall;

    GameManager()
    {
        this.window = new Window();
        this.scoreSystem = new ScoreSystem();
        this.playBoard = new PlayBoard(scoreSystem);
        this.nextElementPanel = new NextElementPanel(playBoard);
        this.scoreView = new ScoreView(scoreSystem);
        this.autoFall = new AutoFall(playBoard, scoreSystem);
        this.scoreSystem.setScoreView(scoreView);
        this.playBoard.setNextElementPanel(nextElementPanel);
        this.controlsView = new ControlsView(autoFall, playBoard);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(nextElementPanel);
        sidePanel.add(scoreView);
        sidePanel.add(controlsView);

        MenuPanel menuPanel = new MenuPanel();
        JLayeredPane layeredPane = new JLayeredPane();
        playBoard.setBounds(0,0,300, 600);
        sidePanel.setBounds(300, 0, 200, 600);
        menuPanel.setBounds(0,0,500,600);
        layeredPane.add(playBoard, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.POPUP_LAYER);
        window.add(layeredPane);
    }
}
