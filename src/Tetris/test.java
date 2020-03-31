package Tetris;

import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            var window = new Window();
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
        });
    }
}
