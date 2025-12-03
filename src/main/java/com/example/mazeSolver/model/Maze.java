package com.example.mazeSolver.model;

public class Maze {
    private final int rows;
    private final int cols;
    private final boolean[][] walls;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.walls = new boolean[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean[][] getWalls() {
        return walls;
    }

    public void setWall(int row, int col, boolean isWall) {
        if (isInside(row, col)) {
            walls[row][col] = isWall;
        }
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isWall(int row, int col) {
        if (!isInside(row, col)) {
            return true;
        }
        return walls[row][col];
    }
}
