package com.example.mazeSolver.dto;

import java.util.List;

public class MazeRequest {
    private int rows;
    private int cols;
    private Position start;
    private Position goal;
    private List<Position> walls;
    private String algorithm;

    public MazeRequest() {
    }

    public MazeRequest(int rows, int cols, Position start, Position goal, List<Position> walls, String algorithm) {
        this.rows = rows;
        this.cols = cols;
        this.start = start;
        this.goal = goal;
        this.walls = walls;
        this.algorithm = algorithm;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public Position getGoal() {
        return goal;
    }

    public void setGoal(Position goal) {
        this.goal = goal;
    }

    public List<Position> getWalls() {
        return walls;
    }

    public void setWalls(List<Position> walls) {
        this.walls = walls;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
