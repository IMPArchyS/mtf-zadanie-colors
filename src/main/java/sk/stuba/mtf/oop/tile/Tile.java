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
    @Setter
    private boolean correctGuess;
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
        this.correctGuess = false;
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
        
        if (!this.correctGuess && this.revealed) {
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawLine(0,this.getHeight(),this.getWidth(),0);
            g.drawLine(this.getWidth(),this.getHeight(),0,0);
            g.setColor(color);
        } else if (this.correctGuess && this.revealed) {
            g.setColor(Color.GREEN);
            ((Graphics2D) g).setStroke(new BasicStroke(8));
            g.drawLine(this.getWidth() / 4, this.getHeight() / 2, this.getWidth() / 2, this.getHeight() / 4 * 3);
            g.drawLine(this.getWidth() / 2, this.getHeight() / 4 * 3, this.getWidth() / 4 * 3, this.getHeight() / 4);
            g.setColor(color);
        }
        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }

    public void getTileInfo() {
        System.out.println("\nTILE INFO");
        System.out.println("Color: " + this.getColorName());
        System.out.println("cord X: " + this.col);
        System.out.println("cord Y: " + this.row);
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

    public void setTileColor(String c) {
        switch (c) {
            case "blue":
                this.color = Color.BLUE;
                this.colorName = "blue";
                break;
            case "orange":
                this.color = Color.ORANGE;
                this.colorName = "orange";
                break;
            case "yellow":
                this.color = Color.YELLOW;
                this.colorName = "yellow";
                break;
            case "pink":
                this.color = Color.MAGENTA;
                this.colorName = "pink";
                break;

            default:
                break;
        }
    }
}
