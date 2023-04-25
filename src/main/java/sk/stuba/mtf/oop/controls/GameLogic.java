package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.State;
import sk.stuba.fei.uim.oop.tile.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {
    public static final int INITIAL_BOARD_SIZE = 8;
    public static final String CHECK_ROUTE = "Check Route";
    public static final String RESTART = "Restart";
    private JFrame mainGame;
    private Board currentBoard;
    @Getter
    private JLabel infoLabel;
    private int currentBoardSize;
    private int level;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.level = 1;
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
        this.infoLabel.setText("Level : " + level + " - Current board size: " + this.currentBoardSize);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.level = 1;
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.updateInfoLabels();
    }

    private void checkRoute() {
        boolean newLevel = this.currentBoard.floodPipes();
        this.mainGame.repaint();
        if (newLevel) {
            this.level++;
            this.mainGame.remove(this.currentBoard);
            this.initializeNewBoard(this.currentBoardSize);
            this.mainGame.add(this.currentBoard);
            this.updateInfoLabels();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            this.currentBoard.repaint();
            return;
        }
        ((Tile) current).setHighlighted(true);
        this.currentBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }
        if (((Tile) current).getState().equals(State.PIPE)) {
            this.currentBoard.unfloodPipes();
            ((Tile) current).setHighlighted(true);
            ((Tile) current).rotate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressedButton = new JButton();
        pressedButton = (JButton) e.getSource();
        if (pressedButton.getText().equals(CHECK_ROUTE)) {
            this.checkRoute();
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
                this.checkRoute();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                System.exit(0);
        }
    }
}
