package se.codetests;

import se.codetests.game_of_life.GameConfig;
import se.codetests.game_of_life.GameOfLife;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(60, 10, 500);

        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(JOptionPane.getRootFrame(), "Click on screen to create a pattern of living cells \n" +
                        "Press space to toggle simulation \n" +
                        "Press r to reset board \n" +
                        "Press escape to to quit",
                "Game Of Life", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        new GameOfLife(gameConfig);


    }
}
