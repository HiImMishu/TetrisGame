package Tetris;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 140;
    private ScoreSystem scoreSystem;

    ScoreView(ScoreSystem scoreSystem)
    {
        this.scoreSystem = scoreSystem;
    }

    public void paintComponent(Graphics gn) {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(Color.lightGray);

        g.drawString("Score" + scoreSystem.getScore(), 20, 20);
        g.drawString("Lvl" + scoreSystem.getLvl(), 20, 40);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
