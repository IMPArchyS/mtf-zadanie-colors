package sk.stuba.mtf.oop.tile;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    @Getter @Setter
    private boolean highlighted;

    @Getter @Setter
    private boolean revealed;

    @Getter
    private Color color;
    @Getter
    private String colorName;

    @Getter @Setter
    private State state;
    public Tile() {
        this.highlighted = false;
        this.revealed = false;
        this.state = State.NONE;
        this.setRandomColor();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);
    }

    public Tile(Tile other) {
        this.highlighted = other.highlighted;
        this.revealed = other.revealed;
        this.state = other.state;
        this.color = other.color;
        this.colorName = other.colorName;
        this.setBorder(other.getBorder());
        this.setBackground(other.getBackground());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.revealed) {
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (this.highlighted) {
            g.setColor(Color.BLACK);
            this.highlighted = false;
        } else if (!this.revealed) {
            g.setColor(Color.LIGHT_GRAY);
        }

        ((Graphics2D) g).setStroke(new BasicStroke(15));
        g.drawRect(0, 0, getWidth(), getHeight());
        
        if (this.state.equals(State.WRONG) && this.revealed) {
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawLine(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() - this.getWidth() / 4, this.getHeight() - this.getHeight() / 4);
            g.drawLine(this.getWidth() - this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 4, this.getHeight() - this.getHeight() / 4);
            g.setColor(color);
        } else if (this.state.equals(State.CORRECT) && this.revealed) {
            g.setColor(Color.GREEN);
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawLine(this.getWidth() / 4, this.getHeight() / 2, this.getWidth() / 2, this.getHeight() / 4 * 3);
            g.drawLine(this.getWidth() / 2, this.getHeight() / 4 * 3, this.getWidth() / 4 * 3, this.getHeight() / 4);
            g.setColor(color);
        } else if (this.state.equals(State.WRONG_PLACE) && this.revealed) {
            g.setColor(Color.GRAY);
            g.fillOval( (int) (0 + this.getWidth() * 0.3), (int) (0 + this.getHeight() * 0.3),
                        (int) (this.getWidth() * 0.4), (int) (this.getHeight() * 0.4));
        }
        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }

    public void getTileInfo() {
        System.out.println("\nTILE INFO");
        System.out.println("Color: " + this.getColorName());
    }
    private void setRandomColor() {
        int colorIndex = (int) (Math.random() * 4);

        switch (colorIndex) {
            case 0:
                this.color = Color.BLUE;
                this.colorName = "blue";
                break;
            case 1:
                this.color = Color.ORANGE;
                this.colorName = "orange";
                break;
            case 2:
                this.color = Color.YELLOW;
                this.colorName = "yellow";
                break;
            case 3:
                this.color = Color.MAGENTA;
                this.colorName = "pink";
                break;

            default:
                break;
        }
    }

    public void setTileColor(char c) {
        switch (c) {
            case 'B':
                this.color = Color.BLUE;
                this.colorName = "blue";
                break;
            case 'O':
                this.color = Color.ORANGE;
                this.colorName = "orange";
                break;
            case 'Y':
                this.color = Color.YELLOW;
                this.colorName = "yellow";
                break;
            case 'P':
                this.color = Color.MAGENTA;
                this.colorName = "pink";
                break;

            default:
                break;
        }
    }
}
