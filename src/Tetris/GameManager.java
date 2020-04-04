package Tetris;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameManager {
    private Window window;
    private PlayBoard playBoard;
    private Timer timer;
    private ScoreSystem scoreSystem;
    private NextElementPanel nextElementPanel;
    private ScoreView scoreView;
    private int interval;

    GameManager()
    {
        this.window = new Window();
        this.scoreSystem = new ScoreSystem();
        this.playBoard = new PlayBoard(scoreSystem);
        this.nextElementPanel = new NextElementPanel(playBoard);
        this.scoreView = new ScoreView(scoreSystem);
        this.scoreSystem.setScoreView(scoreView);
        this.playBoard.setNextElementPanel(nextElementPanel);
        this.interval = 1000;
        this.timer = new Timer(interval, listener);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(nextElementPanel);
        sidePanel.add(scoreView);

        window.add(playBoard, BorderLayout.WEST);
        window.add(sidePanel, BorderLayout.EAST);
        window.pack();

        autoFall(false);
    }

    public void autoFall(boolean state)
    {
        if(state)
            timer.start();
        else
            timer.stop();
    }

    ActionListener listener = event -> {
        playBoard.moveDown();
        if(scoreSystem.getLvl() % 10 == 0)
            this.interval -= 900;
        timer.setDelay(interval);
        timer.start();
    };
}
