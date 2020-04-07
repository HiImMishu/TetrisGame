package Tetris;

import javax.swing.*;
import java.awt.*;

public class ControlsView extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private JButton pause;
    private JButton play;
    private JButton restart;
    private AutoFall autoFall;
    private PlayBoard playBoard;

    ControlsView(AutoFall autoFall, PlayBoard playBoard) {
        this.autoFall = autoFall;
        this.playBoard = playBoard;

        ImageIcon playIcon = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\play.png");
        Image playImg = playIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.play = new JButton(new ImageIcon(playImg));
        play.setPreferredSize(new Dimension(55 ,55));
        play.setMargin(new Insets(110, 110, 110, 110));
        this.play.addActionListener(event -> {
            autoFall.play();
            playBoard.play();
        });

        ImageIcon pauseIcon = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\pause.png");
        Image pauseImg = pauseIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.pause = new JButton(new ImageIcon(pauseImg));
        pause.setPreferredSize(new Dimension(55 ,55));
        this.pause.addActionListener(event -> {
            autoFall.pause();
            playBoard.pause();
        });

        ImageIcon restartIcon = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\restart.png");
        Image restartImage = restartIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.restart = new JButton(new ImageIcon(restartImage));
        restart.setPreferredSize(new Dimension(55 ,55));
        this.restart.addActionListener(event -> {
            playBoard.reset();
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weightx = 10;
        constraints.weighty = 10;
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(pause, constraints);
        constraints.gridx = 3;
        constraints.gridy = 1;
        add(play, constraints);
        constraints.gridx = 4;
        constraints.gridy = 1;
        add(restart, constraints);
    }

    public void paintComponent(Graphics gn) {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(Color.DARK_GRAY);

        Image background = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\ar.png").getImage();
        g.drawImage(background, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
