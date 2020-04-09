package Tetris;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuPanel extends JPanel {
    private int DEFAULT_WIDTH = 500;
    private int DEFAULT_HEIGHT = 600;

    MenuPanel() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0,20,0);

        JButton play = new JButton("PLAY");
        play.setPreferredSize(new Dimension(180, 50));
        play.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));

        JButton highScores = new JButton("HIGH SCORES");
        highScores.setPreferredSize(new Dimension(180, 50));
        highScores.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));

        JButton quit = new JButton("EXIT");
        quit.setPreferredSize(new Dimension(180, 50));
        quit.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));

        add(play, gbc);
        add(highScores, gbc);
        add(quit, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image background = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\menubg.png").getImage();
        g2.drawImage(background, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_WIDTH);
    }

}
