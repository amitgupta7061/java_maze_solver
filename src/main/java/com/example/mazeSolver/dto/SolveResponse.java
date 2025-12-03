package com.example.mazeSolver.dto;

import java.util.List;

public class SolveResponse {
    private List<Position> visited;
    private List<Position> path;
    private int visitedCount;
    private int pathLength;
    private long executionTimeMs;

    public SolveResponse() {
    }

    public SolveResponse(List<Position> visited, List<Position> path, int visitedCount, int pathLength, long executionTimeMs) {
        this.visited = visited;
        this.path = path;
        this.visitedCount = visitedCount;
        this.pathLength = pathLength;
        this.executionTimeMs = executionTimeMs;
    }

    public List<Position> getVisited() {
        return visited;
    }

    public void setVisited(List<Position> visited) {
        this.visited = visited;
    }

    public List<Position> getPath() {
        return path;
    }

    public void setPath(List<Position> path) {
        this.path = path;
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}
