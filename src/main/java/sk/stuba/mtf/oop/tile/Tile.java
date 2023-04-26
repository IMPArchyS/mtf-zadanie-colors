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
    @Getter
    private int col;
    @Getter
    private int row;
    public Tile(int x, int y) {
        this.col = x;
        this.row = y;
        this.highlighted = false;
        this.revealed = false;
        this.setRandomColor();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);
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
        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }

    public void getTileInfo() {
        System.out.println("\nTILE INFO");
        System.out.println("Color: " + this.getColorName());
        System.out.println("cord X: " + this.col);
        System.out.println("cord Y: " + this.row);
    }
    private void setRandomColor() {
        int colorIndex = (int) (Math.random() * 7);

        switch (colorIndex) {
            case 0:
                this.color = Color.BLUE;
                this.colorName = "blue";
                break;
            case 1:
                this.color = Color.RED;
                this.colorName = "red";
                break;
            case 2:
                this.color = Color.ORANGE;
                this.colorName = "orange";
                break;
            case 3:
                this.color = Color.YELLOW;
                this.colorName = "yellow";
                break;
            case 4:
                this.color = Color.GREEN;
                this.colorName = "green";
                break;
            case 5:
                this.color = Color.MAGENTA;
                this.colorName = "magenta";
                break;
            case 6:
                this.color = Color.PINK;
                this.colorName = "pink";
                break;

            default:
                break;
        }
    }
}
