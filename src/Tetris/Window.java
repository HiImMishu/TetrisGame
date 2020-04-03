package Tetris;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 600;

    public Window()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Tetris");
        setResizable(false);

        var playBoard = new PlayBoard();
        playBoard.add(new TetroShape());
        add(playBoard, BorderLayout.WEST);
    }
}
