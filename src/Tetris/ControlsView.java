package Tetris;

import javax.swing.*;
import java.awt.*;

public class ControlsView extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 220;
    private JButton pause;
    private JButton play;
    private JButton restart;

    ControlsView()
    {
        this.pause = new JButton(new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\play.png"));
        this.play = new JButton("PLA");
        this.restart = new JButton("RES");

        this.setLayout(new GridBagLayout());
        add(pause, new GridBagConstraints());
        add(play, new GridBagConstraints());
        add(restart, new GridBagConstraints());
    }

    public void paintComponent(Graphics gn)
    {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(Color.PINK);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
