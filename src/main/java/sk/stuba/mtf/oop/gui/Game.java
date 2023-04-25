package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame("Water Pipes");
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

        JButton buttonCheckWin = new JButton("Check Route");
        buttonCheckWin.addActionListener(logic);
        buttonCheckWin.setFocusable(false);


        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);

        sideMenu.setLayout(new GridLayout(2, 2));
        sideMenu.add(buttonCheckWin);
        sideMenu.add(buttonRestart);
        sideMenu.add(logic.getInfoLabel());
        sideMenu.add(slider);
        frame.add(sideMenu, BorderLayout.PAGE_START);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}
