package sk.stuba.fei.uim.oop.tile;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.State;

import java.awt.*;

public class StraightPipe extends Tile {
    public StraightPipe() {
        this.state = State.PIPE;
        this.highlighted = false;
        this.holes = new Direction[4];
        this.setRandomRotation();
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
            if (this.holes[0] == Direction.UP) {
                g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

            } else {
                g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
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

    @Override
    public void rotate() {
        switch (this.holes[0]) {
            case UP:
                this.holes = new Direction[]{Direction.NONE, Direction.RIGHT, Direction.NONE, Direction.LEFT};
                break;
            case NONE:
                this.holes = new Direction[]{Direction.UP, Direction.NONE, Direction.DOWN, Direction.NONE};
                break;

            default:
                break;
        }
        this.paintComponent(getGraphics());
    }

    private void setRandomRotation() {
        Double chance = Math.random();
        if (chance > 0.49) {
            this.holes = new Direction[]{Direction.UP, Direction.NONE, Direction.DOWN, Direction.NONE};
        } else {
            this.holes = new Direction[]{Direction.NONE, Direction.RIGHT, Direction.NONE, Direction.LEFT};
        }
    }
}
