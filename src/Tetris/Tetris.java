package Tetris;

import java.awt.*;

public class Tetris {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            new GameManager();
        });
    }
}
