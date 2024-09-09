package SnakeGame;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame() {
        super("Snake Game");
        add(new Board());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
        setVisible(true); // Set the JFrame visibility to true
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SnakeGame();
        });
    }
}
