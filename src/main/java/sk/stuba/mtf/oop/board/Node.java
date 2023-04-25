package sk.stuba.fei.uim.oop.board;

import lombok.Data;

@Data
public class Node {
    private boolean visited;
    private boolean path;

    private int x;
    private int y;

    public Node(int x, int y) {
        this.path = false;
        this.visited = false;
        this.x = x;
        this.y = y;
    }
}
