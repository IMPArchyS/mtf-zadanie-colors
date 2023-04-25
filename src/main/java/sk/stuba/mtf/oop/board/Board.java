package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tile.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private Tile[][] board;
    private ArrayList<Node> dfsPath;

    public Board(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.dfsPath = new ArrayList<Node>();
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        this.setBackground(Color.CYAN);
    }

    private void initializeBoard(int dimension) {
        Node[][] grid = new Node[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Tile();
            }
        }

        int startPos = (int) (Math.random() * dimension - 1);
        int endPos = (int) (Math.random() * dimension - 1);

        this.dfsPath = this.generatePath(grid, grid[0][startPos], grid[dimension - 1][endPos]);

        this.placePipes(startPos, endPos);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.add(this.board[j][i]);
            }
        }
    }

    private void placePipes(int startPos, int endPos) {
        for (int i = 1; i < dfsPath.size() - 1; i++) {
            Node node = dfsPath.get(i);
            Node prev = dfsPath.get(i - 1);
            Node next = dfsPath.get(i + 1);
            int x = node.getX();
            int y = node.getY();

            if (prev.getX() == next.getX()) {
                board[x][y] = new StraightPipe();
            } else if (prev.getY() == next.getY()) {
                board[x][y] = new StraightPipe();
            } else if ((prev.getX() < node.getX() && next.getY() > node.getY()) || (prev.getY() < node.getY() && next.getX() > node.getX())) {
                board[x][y] = new BentPipe();
            } else {
                board[x][y] = new BentPipe();
            }
        }
        board[0][startPos] = new Start();
        board[board.length - 1][endPos] = new End();

        if (dfsPath.get(1).getY() == startPos) {
            board[0][startPos].setHoles(new Direction[]{Direction.NONE, Direction.RIGHT, Direction.NONE, Direction.NONE});
        } else if (dfsPath.get(1).getY() > startPos) {
            board[0][startPos].setHoles(new Direction[]{Direction.NONE, Direction.NONE, Direction.DOWN, Direction.NONE});
        } else {
            board[0][startPos].setHoles(new Direction[]{Direction.UP, Direction.NONE, Direction.NONE, Direction.NONE});
        }
        if (dfsPath.get(dfsPath.size() - 2).getY() == endPos) {
            board[board.length - 1][endPos].setHoles(new Direction[]{Direction.NONE, Direction.RIGHT, Direction.NONE, Direction.NONE});
        } else if (dfsPath.get(dfsPath.size() - 2).getY() > endPos) {
            board[board.length - 1][endPos].setHoles(new Direction[]{Direction.NONE, Direction.NONE, Direction.DOWN, Direction.NONE});
        } else {
            board[board.length - 1][endPos].setHoles(new Direction[]{Direction.UP, Direction.NONE, Direction.NONE, Direction.NONE});
        }
    }

    private ArrayList<Node> generatePath(Node[][] grid, Node start, Node end) {
        ArrayList<Node> path = new ArrayList<>();
        findPath(grid, start, end, path);
        return path;
    }

    private boolean findPath(Node[][] grid, Node current, Node end, ArrayList<Node> path) {
        path.add(current);

        if (current == end) {
            current.setPath(true);
            return true;
        }

        current.setVisited(true);

        ArrayList<Node> candidates = getUnvisitedNeighbors(grid, current);
        while (!candidates.isEmpty()) {
            int randomIndex = (int) Math.floor(Math.random() * candidates.size());
            Node next = candidates.remove(randomIndex);

            if (findPath(grid, next, end, path)) {
                current.setPath(true);
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private ArrayList<Node> getUnvisitedNeighbors(Node[][] grid, Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();

        int[] rowOffsets = {-1, 1, 0, 0};
        int[] colOffsets = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = node.getX() + rowOffsets[i];
            int newCol = node.getY() + colOffsets[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                Node neighbor = grid[newRow][newCol];
                if (!neighbor.isVisited()) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    private Direction getPreviousTileDirection(int prevX, int prevY, int curX, int curY) {
        int xDiff = prevX - curX;
        int yDiff = prevY - curY;
        if (xDiff == 0 && yDiff == 1) {
            if (curY >= 0) {
                return Direction.UP;
            }
        } else if (xDiff == 0 && yDiff == -1) {
            if (curY < this.board.length) {
                return Direction.DOWN;
            }
        } else if (xDiff == 1 && yDiff == 0) {
            if (curX >= 0) {
                return Direction.LEFT;
            }
        } else if (xDiff == -1 && yDiff == 0) {
            if (curX < this.board.length) {
                return Direction.RIGHT;
            }
        }
        return Direction.NONE;
    }

    public boolean floodPipes() {
        this.board[dfsPath.get(0).getX()][dfsPath.get(0).getY()].setFlooded(true);
        Tile currentTile = this.board[dfsPath.get(1).getX()][dfsPath.get(1).getY()];
        Tile endTile = this.board[dfsPath.get(dfsPath.size() - 1).getX()][dfsPath.get(dfsPath.size() - 1).getY()];
        int previousRow = dfsPath.get(0).getX();
        int previousCol = dfsPath.get(0).getY();
        int currentRow = dfsPath.get(1).getX();
        int currentCol = dfsPath.get(1).getY();
        Direction occupiedHole = Direction.NONE;
        while (true) {
            if (currentTile.equals(endTile)) {
                return true;
            }
            Direction dir = this.getPreviousTileDirection(previousRow, previousCol, currentRow, currentCol);

            if (dir.equals(Direction.UP) && currentTile.getHoles()[2].equals(Direction.DOWN)) {
                currentTile.setFlooded(true);
                occupiedHole = Direction.DOWN;
            } else if (dir.equals(Direction.DOWN) && currentTile.getHoles()[0].equals(Direction.UP)) {
                currentTile.setFlooded(true);
                occupiedHole = Direction.UP;
            } else if (dir.equals(Direction.RIGHT) && currentTile.getHoles()[3].equals(Direction.LEFT)) {
                currentTile.setFlooded(true);
                occupiedHole = Direction.LEFT;
            } else if (dir.equals(Direction.LEFT) && currentTile.getHoles()[1].equals(Direction.RIGHT)) {
                currentTile.setFlooded(true);
                occupiedHole = Direction.RIGHT;
            } else {
                break;
            }
            Direction output = Direction.NONE;
            for (Direction hole : currentTile.getHoles()) {
                if (!hole.equals(occupiedHole) && !hole.equals(Direction.NONE)) {
                    output = hole;
                    break;
                }
            }
            previousRow = currentRow;
            previousCol = currentCol;
            currentRow = currentRow + output.getX();
            currentCol = currentCol + output.getY();
            if (currentRow < 0 || currentCol < 0 || currentRow > board.length - 1 || currentCol > board.length - 1) {
                break;
            }
            currentTile = this.board[currentRow][currentCol];
        }
        return false;
    }

    public void unfloodPipes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!(board[i][j] instanceof Tile) || !(board[i][j] instanceof End)) {
                    board[i][j].setFlooded(false);
                }
            }
        }
    }
}
