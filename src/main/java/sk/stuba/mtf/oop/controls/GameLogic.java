package sk.stuba.mtf.oop.controls;

import lombok.Getter;
import sk.stuba.mtf.oop.board.Board;
import sk.stuba.mtf.oop.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {
    private static final int INITIAL_BOARD_SIZE = 5;
    private static final String COLORS[] = {"blue", "orange", "yellow", "pink"};
    private static final String HELP = "Available colors";
    private static final String RESTART = "Restart";
    private static final String GUESS = "Guess";
    private JFrame mainGame;
    private Board currentBoard;
    @Getter
    private JLabel infoLabel;
    private double percentage;
    private int correctGuesses;
    private int totalGuesses;
    private int currentRow;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.percentage = 100;
        this.correctGuesses = 0;
        this.totalGuesses = 0;
        this.currentRow = 0; 
        this.initializeNewBoard(INITIAL_BOARD_SIZE);
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
        this.infoLabel.setText(String.format("Percentage : %.2f%% - Correct guesses: %d - Total guesses: %d", this.percentage, this.correctGuesses, this.totalGuesses));
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.currentRow = 0; 
        this.initializeNewBoard(INITIAL_BOARD_SIZE);
        this.mainGame.add(this.currentBoard);
        this.updateInfoLabels();
    }

    private void helpPopup() {
        JOptionPane.showMessageDialog(null, "Available colors:\nblue\norange\nyellow\npink", "Logik",JOptionPane.INFORMATION_MESSAGE);
    }
    private void guess() {
        String combinationGuess = JOptionPane.showInputDialog(null, "Type in the color combination\n(Only Starting letters):", "Your guess", JOptionPane.QUESTION_MESSAGE);
        if (combinationGuess == null || combinationGuess.length() != 5) {
            JOptionPane.showMessageDialog(null, "Wrong input\nThe guess must be the size of the row\n(only Starting letters of colors)", "Logik", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean[] validColor = new boolean[INITIAL_BOARD_SIZE];
        for (int i = 0; i < combinationGuess.length(); i++) {
            validColor[i] = false;
            for (String color : COLORS) {
                if (color.startsWith(String.valueOf(combinationGuess.toLowerCase().charAt(i)))) {
                    validColor[i] = true;
                    break;
                }
            }
        }
        for (int i = 0; i < validColor.length; i++) {
            if (!validColor[i]) {
                JOptionPane.showMessageDialog(null, "Wrong input\nThe guess must consist of valid colors\n(only Starting letters of colors)", "Logik", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        combinationGuess = combinationGuess.toUpperCase();
        boolean hasWon = currentBoard.validateGuess(combinationGuess, currentRow);
        this.currentBoard.revealTiles(currentRow);
        currentRow++;
        this.updateInfoLabels();
        this.endPopUp(hasWon);
    }

    private void endPopUp(boolean hasWon) { 
        if (currentRow == INITIAL_BOARD_SIZE || hasWon) {
            this.totalGuesses++; 
            int choice;
            if (hasWon) {
                this.correctGuesses++;
                choice = JOptionPane.showOptionDialog(null, "You won!\nPlay again?", "Logik",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            } else {
                choice = JOptionPane.showOptionDialog(null, "Game Over!\nPlay again?", "Logik",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            }
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
        } else if (pressedButton.getText().equals(GUESS)) {
            this.guess();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ENTER:
                this.guess();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                System.exit(0);
        }
    }
}
