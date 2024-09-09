package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private int dots;
    private Timer timer;
    private boolean inGame = true;

    private final int All_dots = 900;
    private final int Dot_size = 10;
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private int score = 0;

    private final int[] x = new int[All_dots];
    private final int[] y = new int[All_dots];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private Image apple;
    private Image dot;
    private Image head;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        loadImages();
        initGame();
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icon/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icon/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icon/head.png"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 4;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * Dot_size;
        }

        locateApple();

        timer = new Timer(200, this);
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = r * Dot_size;

        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = r * Dot_size;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        drawScore(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game over!";
        Font font = new Font("SansSerif", Font.BOLD, 15);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrics.stringWidth(msg)) / 2, 300 / 2);
    }

    public void drawScore(Graphics g) {
        String scoreStr = "Score: " + score;
        Font font = new Font("SansSerif", Font.BOLD, 15);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(scoreStr, 10, 20); // Display the score at position (10, 20)
    }
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {
            x[0] = x[0] - Dot_size;
        }
        if (rightDirection) {
            x[0] = x[0] + Dot_size;
        }
        if (upDirection) {
            y[0] = y[0] - Dot_size;
        }
        if (downDirection) {
            y[0] = y[0] + Dot_size;
        }
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (y[0] >= 300 || y[0] < 0 || x[0] >= 300 || x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
