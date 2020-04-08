package Tetris;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private int DEFAULT_WIDTH = 500;
    private int DEFAULT_HEIGHT = 600;

    MenuPanel() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(false);
        setOpaque(true);
        add(new JButton("HALO"));
        setBackground(new Color(50,50,50,220));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(1f,0f,0f,.2f));
        g2.drawRect(0,0,500,200);
        g2.drawString("ELOOOOOOO", 0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_WIDTH);
    }
}
