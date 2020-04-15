package Tetris;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AutoFall extends Thread {
    private int interval;
    private boolean state;
    private Timer timer;
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;
    private int checkPoint = 5;

    AutoFall(PlayBoard playBoard, ScoreSystem scoreSystem) {
        this.playBoard = playBoard;
        this.scoreSystem = scoreSystem;
        this.interval = 1000;
        this.state = false;
    }

    ActionListener listener = event -> {
        if (state) {
            if (scoreSystem.getLvl() >= checkPoint) {
                checkPoint += 5;
                if (interval > 100)
                    interval -= 50;
                timer.setDelay(interval);
            }
            playBoard.moveDown();
        }
    };

    @Override
    public void run() {
        this.timer = new Timer(interval, listener);
        timer.start();
    }

    public void play() {
        state = true;
    }

    public void pause() {
        state = false;
    }

    public void setInterval(int interval) {
        this.interval = interval;
        timer.setDelay(interval);
    }
}
