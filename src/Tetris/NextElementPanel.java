package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class NextElementPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private PlayBoard playBoard;

    NextElementPanel(PlayBoard playBoard)
    {
        this.playBoard = playBoard;
        setOpaque(true);
    }

    public void paintComponent(Graphics gn)
    {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;

        setBackground(Color.green);

        Element next = playBoard.getNext();
        for(Rectangle2D r: next.getShape())
        {
            r.setRect(r.getMinX()-50, r.getMinY()+20, r.getWidth(), r.getHeight());
            g.setColor(next.getColor());
            g.fill(r);
            g.setColor(Color.DARK_GRAY);
            g.draw(r);
        }

    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
