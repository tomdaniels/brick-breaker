package com.td;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gamePLay = new GamePlay();

        // initialize the window
        obj.setBounds(10,10,1500,900);
        obj.setTitle("Brick Breaker");
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
