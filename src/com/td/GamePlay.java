package com.td;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = (int) (Math.random() * 450);
    private int ballposY = 350;
    private int ballXdir = ballposX > 250 ? 1 : -1;
    private int ballYdir = -2;

    private MapGenerator brickMatrix;

    public GamePlay() {
        brickMatrix = new MapGenerator(4, 10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        brickMatrix.draw((Graphics2D)g);

        // border
        g.setColor(Color.white);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // paddle
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(Color.lightGray);
        g.fillOval(ballposX, ballposY, 20, 20);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Your Score: " + score, 490, 25);

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Game over, final score: " + score, 250, 300);

            g.drawString("Press [Enter] to restart", 260, 320);

        }

        g.dispose();
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            if(new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            for (int i = 0; i < brickMatrix.map.length; i++) {
                for(int j = 0; j < brickMatrix.map[0].length; j++) {
                    if (brickMatrix.map[i][j] > 0) {
                        int brickX = j*brickMatrix.brickWidth + 80;
                        int brickY = i*brickMatrix.brickHeight + 50;
                        int brickWidth = brickMatrix.brickWidth;
                        int brickHeight = brickMatrix.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            brickMatrix.setBrickValue(0, i, j);
                            totalBricks -= 1;
                            score+=5;

                            if (ballposX + 19 <= brickRect.x || ballposY + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0) {
                ballXdir = -ballXdir;
            } // bounce off the walls
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }

            if(ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void moveRight() {
        play = true;
        playerX +=20;
    }

    public void moveLeft() {
        play = true;
        playerX -=20;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
