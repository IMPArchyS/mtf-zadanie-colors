package sk.stuba.fei.uim.oop.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.State;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    @Getter
    protected State state;
    @Getter
    @Setter
    protected boolean highlighted;
    @Getter
    @Setter
    protected boolean flooded;

    @Getter
    @Setter
    protected Direction[] holes;

    public Tile() {
        this.state = State.EMPTY;
        this.holes = new Direction[]{Direction.NONE, Direction.NONE, Direction.NONE, Direction.NONE};
        this.highlighted = false;
        this.flooded = false;
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.state.equals(State.EMPTY)) {
            if (this.highlighted) {
                g.setColor(Color.RED);
                this.highlighted = false;
            } else {
                g.setColor(Color.WHITE);
            }
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawRect(0, 0, getWidth(), getHeight());
            ((Graphics2D) g).setStroke(new BasicStroke(1));
        }
    }

    public void rotate() {

    }
}
