package Tetris;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AutoFall extends Thread {
    private int interval;
    private int level;
    private boolean state;
    private Timer timer;
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;

    AutoFall(PlayBoard playBoard, ScoreSystem scoreSystem)
    {
        this.playBoard = playBoard;
        this.scoreSystem = scoreSystem;
        this.interval = 1000;
        this.level = 0;
        this.state = false;
        this.timer = new Timer(interval, listener);
        timer.start();
    }

    ActionListener listener = event -> {
        if(state) {
            playBoard.moveDown();
        }
    };

    public void play()
    {
        state = true;
    }

    public void pause()
    {
        state = false;
    }
}
