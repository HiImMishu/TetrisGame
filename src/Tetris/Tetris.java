package Tetris;

import java.awt.*;

/**
 * Gra Tetris
 * @author Michal Misiak
 * @version 1.0
 */
public class Tetris {
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            new GameManager();
        });
    }
}
