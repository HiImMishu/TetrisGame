package Tetris;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private static final int DEFAULT_WIDTH = 514;
    private static final int DEFAULT_HEIGHT = 637;

    public Window() {
        setTitle("Tetris");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);
    }
}
