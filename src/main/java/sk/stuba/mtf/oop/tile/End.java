package sk.stuba.fei.uim.oop.tile;

import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.State;

import javax.swing.*;
import java.awt.*;

public class End extends Tile {
    public End() {
        this.state = State.END;
        this.holes = new Direction[]{Direction.UP, Direction.NONE, Direction.NONE, Direction.NONE};
        this.highlighted = false;
        this.flooded = false;
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.state.equals(State.END)) {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(12));
            if (this.holes[0] == Direction.UP) {
                g.drawLine(getWidth() / 3, getHeight() * 3 / 4, getWidth() * 2 / 3, getHeight() * 3 / 4);
                g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
            } else if (this.holes[2] == Direction.DOWN) {
                g.drawLine(getWidth() / 3, getHeight() / 4, getWidth() * 2 / 3, getHeight() / 4);
                g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
            } else {
                g.drawLine(getWidth() * 3 / 4, getHeight() / 4, getWidth() * 3 / 4, getHeight() * 3 / 4);
                g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
            }
            if (this.highlighted) {
                g.setColor(Color.RED);
                this.highlighted = false;
            } else {
                g.setColor(Color.MAGENTA);
            }
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawRect(0, 0, getWidth(), getHeight());
            ((Graphics2D) g).setStroke(new BasicStroke(1));
        }
    }
}
