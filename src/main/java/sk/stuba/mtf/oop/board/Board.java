package sk.stuba.mtf.oop.board;

import sk.stuba.mtf.oop.tile.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private Tile[][] board;
    private String correctAnswer;

    public Board(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        this.setBackground(Color.DARK_GRAY);
        this.getCorrectAnswer();
    }

    private void initializeBoard(int dimension) {
        this.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < board.length; i++) {
            board[0][i] = new Tile();
        }

        for (int i = 1; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Tile(board[0][j]);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.add(this.board[i][j]);
            }
        }
    }
    private void getCorrectAnswer() {
        String colorCombination = "";
        for (Tile tile : board[0]) {
            colorCombination += tile.getColorName().substring(0,1);
        }
        correctAnswer = colorCombination.toUpperCase();
    }
    public boolean validateGuess(String guess, int currentRow) {
        ArrayList<Tile> currentRowTiles = new ArrayList<Tile>();
        for (Tile tile : board[currentRow]) {
            currentRowTiles.add(tile);
        }
        boolean success = true;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == currentRowTiles.get(i).getColorName().toUpperCase().charAt(0)) {
                currentRowTiles.get(i).setState(State.CORRECT);
            } else if (correctAnswer.contains(String.valueOf(guess.charAt(i)))) {
                currentRowTiles.get(i).setState(State.WRONG_PLACE);
                success = false;
            } else {
                currentRowTiles.get(i).setState(State.WRONG);
                success = false;
            }
            currentRowTiles.get(i).setTileColor(guess.charAt(i));
        }
        return success ? true : false;
    }

    public void revealTiles(int currentRow) {
        for (Tile tile : board[currentRow]) {
            tile.setRevealed(true);
        }
        this.repaint();
    }
}
