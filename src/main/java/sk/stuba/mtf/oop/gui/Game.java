package sk.stuba.mtf.oop.gui;

import sk.stuba.mtf.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame("Logik");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 820);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        GameLogic logic = new GameLogic(frame);
        frame.addKeyListener(logic);

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.LIGHT_GRAY);

        JButton buttonRestart = new JButton("Restart");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);

        JButton buttonAvailableColors = new JButton("Available colors");
        buttonAvailableColors.addActionListener(logic);
        buttonAvailableColors.setFocusable(false);

        JButton buttonGuesser = new JButton("Guess");
        buttonGuesser.addActionListener(logic);
        buttonGuesser.setFocusable(false);


        sideMenu.setLayout(new GridLayout(2, 2));
        sideMenu.add(buttonAvailableColors);
        sideMenu.add(buttonRestart);
        sideMenu.add(buttonGuesser);
        sideMenu.add(logic.getInfoLabel());
        frame.add(sideMenu, BorderLayout.PAGE_START);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}
