package sk.stuba.mtf.oop.controls;

import lombok.Getter;
import sk.stuba.mtf.oop.board.Board;
import sk.stuba.mtf.oop.tile.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class GameLogic extends UniversalAdapter {
    public static final int INITIAL_BOARD_SIZE = 3;
    public static final String COLORS[] = {"blue", "orange", "yellow", "pink"};
    public static final String HELP = "Available colors";
    public static final String RESTART = "Restart";
    private JFrame mainGame;
    private Board currentBoard;
    @Getter
    private JLabel infoLabel;
    private int currentBoardSize;
    private double percentage;
    private int correctGuesses;
    private int totalGuesses;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.percentage = 100;
        this.correctGuesses = 0;
        this.totalGuesses = 0;
        this.currentBoardSize = INITIAL_BOARD_SIZE;
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.infoLabel = new JLabel();
        this.updateInfoLabels();
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new Board(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }

    private void updateInfoLabels() {
        if (totalGuesses == 0) {
            this.percentage = 100;
        } else {
            this.percentage = (double) correctGuesses / totalGuesses * 100;
        }
        this.infoLabel.setText(String.format("Percentage : %.2f%% - Correct guesses: %d - Current board size : %d", this.percentage, this.correctGuesses, this.currentBoardSize));
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.correctGuesses = 0;
        this.totalGuesses = 0;
        this.percentage = 100;
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.updateInfoLabels();
    }

    private void helpPopup() {
        JOptionPane.showMessageDialog(null, "Available colors:\nblue\norange\nyellow\npink", "Logik",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            this.currentBoard.repaint();
            return;
        }
        if (!((Tile) current).isRevealed()) {
            ((Tile) current).setHighlighted(true);
        }
        this.currentBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        ((Tile) current).getTileInfo();
        if (!((Tile) current).isRevealed()) {
            String colorName  = JOptionPane.showInputDialog(null, "Type in the color name:", "Your guess", JOptionPane.QUESTION_MESSAGE);
            if (!Arrays.asList(COLORS).contains(colorName)) {
                JOptionPane.showMessageDialog(null, "Not a valid color!", "Logik", JOptionPane.WARNING_MESSAGE);
                colorName = null;
            }
            if (colorName != null) {
                if (colorName.equals(((Tile) current).getColorName())) {
                    ((Tile) current).setRevealed(true);
                    ((Tile) current).setCorrectGuess(true);
                    this.correctGuesses++;
                    this.totalGuesses++;
                    this.currentBoard.repaint();
                } else {
                    this.totalGuesses++;
                    ((Tile) current).setTileColor(colorName);
                    ((Tile) current).setRevealed(true);
                    JOptionPane.showMessageDialog(null, "Wrong guess!", "Logik", JOptionPane.ERROR_MESSAGE);
                }
                if (this.currentBoard.checkWin()) {
                    int choice = JOptionPane.showOptionDialog(null, "Game Over!\nPlay again?", "Logik",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (choice == JOptionPane.YES_OPTION) {
                        this.gameRestart();
                        this.mainGame.revalidate();
                        this.mainGame.repaint();
                        this.mainGame.setFocusable(true);
                        this.mainGame.requestFocus();
                    } else {
                        this.mainGame.dispose();
                        System.exit(0);
                    }
                }
            }
        }
        this.updateInfoLabels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressedButton = new JButton();
        pressedButton = (JButton) e.getSource();
        if (pressedButton.getText().equals(HELP)) {
            this.helpPopup();
        } else if (pressedButton.getText().equals(RESTART)) {
            this.gameRestart();
            this.mainGame.revalidate();
            this.mainGame.repaint();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
            this.currentBoardSize = ((JSlider) e.getSource()).getValue();
            this.updateInfoLabels();
            this.gameRestart();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ENTER:
                this.helpPopup();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                System.exit(0);
        }
    }
}
