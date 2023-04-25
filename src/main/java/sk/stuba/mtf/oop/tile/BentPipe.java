package sk.stuba.fei.uim.oop.tile;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.State;

import java.awt.*;

public class BentPipe extends Tile {
    public BentPipe() {
        this.state = State.PIPE;
        this.holes = new Direction[4];
        this.setRandomRotation();
        this.highlighted = false;
        this.flooded = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.state.equals(State.PIPE)) {
            if (this.flooded) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.DARK_GRAY);
            }

            ((Graphics2D) g).setStroke(new BasicStroke(8));

            if (this.holes[0].equals(Direction.UP) && this.holes[1].equals(Direction.RIGHT)) {
                g.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
                g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight() / 2);
            } else if (this.holes[1].equals(Direction.RIGHT) && this.holes[2].equals(Direction.DOWN)) {
                g.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
                g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
            } else if (this.holes[2].equals(Direction.DOWN) && this.holes[3].equals(Direction.LEFT)) {
                g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
                g.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
            } else {
                g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight() / 2);
                g.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
            }

            if (this.highlighted) {
                g.setColor(Color.RED);
                g.drawRect(0, 0, getWidth(), getHeight());
                this.highlighted = false;
            } else {
                g.setColor(Color.WHITE);
            }
            ((Graphics2D) g).setStroke(new BasicStroke(1));
        }
    }

    public void rotate() {
        if (this.holes[0].equals(Direction.UP) && this.holes[1].equals(Direction.RIGHT)) {
            this.holes = new Direction[]{Direction.NONE, Direction.RIGHT, Direction.DOWN, Direction.NONE};
        } else if (this.holes[1].equals(Direction.RIGHT) && this.holes[2].equals(Direction.DOWN)) {
            this.holes = new Direction[]{Direction.NONE, Direction.NONE, Direction.DOWN, Direction.LEFT};
        } else if (this.holes[2].equals(Direction.DOWN) && this.holes[3].equals(Direction.LEFT)) {
            this.holes = new Direction[]{Direction.UP, Direction.NONE, Direction.NONE, Direction.LEFT};
        } else {
            this.holes = new Direction[]{Direction.UP, Direction.RIGHT, Direction.NONE, Direction.NONE};
        }
        this.paintComponent(getGraphics());
    }

    private void setRandomRotation() {
        Double chance = Math.random();
        if (chance < 0.24) {
            this.holes = new Direction[]{Direction.NONE, Direction.RIGHT, Direction.DOWN, Direction.NONE};
        } else if (chance < 0.49 && chance >= 0.24) {
            this.holes = new Direction[]{Direction.NONE, Direction.NONE, Direction.DOWN, Direction.LEFT};
        } else if (chance >= 0.49 && chance < 0.74) {
            this.holes = new Direction[]{Direction.UP, Direction.NONE, Direction.NONE, Direction.LEFT};
        } else {
            this.holes = new Direction[]{Direction.UP, Direction.RIGHT, Direction.NONE, Direction.NONE};
        }
    }
}
