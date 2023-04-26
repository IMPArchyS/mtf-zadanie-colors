package sk.stuba.mtf.oop.board;

import sk.stuba.mtf.oop.tile.*;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private Tile[][] board;

    public Board(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        this.setBackground(Color.DARK_GRAY);
    }

    private void initializeBoard(int dimension) {
        this.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Tile(i,j);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.add(this.board[j][i]);
            }
        }
    }
    public boolean checkWin() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!board[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
}
