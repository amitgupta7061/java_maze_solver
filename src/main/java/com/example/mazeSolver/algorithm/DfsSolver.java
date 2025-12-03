package com.example.mazeSolver.algorithm;

import com.example.mazeSolver.dto.Position;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.model.Maze;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DfsSolver implements MazeSolver {

    // Direction vectors: up, down, left, right
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    @Override
    public SolveResponse solve(Maze maze, Position start, Position goal) {
        long startTime = System.currentTimeMillis();

        List<Position> visitedOrder = new ArrayList<>();
        List<Position> path = new ArrayList<>();

        boolean[][] visited = new boolean[maze.getRows()][maze.getCols()];
        Map<Position, Position> parentMap = new HashMap<>();

        boolean found = dfs(maze, start, goal, visited, visitedOrder, parentMap);

        if (found) {
            // Reconstruct path from goal to start using parent map
            Position current = goal;
            while (current != null) {
                path.add(current);
                current = parentMap.get(current);
            }
            Collections.reverse(path);
        }

        long endTime = System.currentTimeMillis();

        return new SolveResponse(
                visitedOrder,
                path,
                visitedOrder.size(),
                path.size(),
                endTime - startTime
        );
    }

    private boolean dfs(Maze maze, Position current, Position goal,
                        boolean[][] visited, List<Position> visitedOrder,
                        Map<Position, Position> parentMap) {

        int row = current.getRow();
        int col = current.getCol();

        // Check boundaries and walls
        if (!maze.isInside(row, col) || maze.isWall(row, col) || visited[row][col]) {
            return false;
        }

        // Mark as visited
        visited[row][col] = true;
        visitedOrder.add(new Position(row, col));

        // Check if we reached the goal
        if (current.equals(goal)) {
            return true;
        }

        // Explore neighbors
        for (int i = 0; i < 4; i++) {
            int newRow = row + DR[i];
            int newCol = col + DC[i];
            Position neighbor = new Position(newRow, newCol);

            if (maze.isInside(newRow, newCol) && !maze.isWall(newRow, newCol) && !visited[newRow][newCol]) {
                parentMap.put(neighbor, current);

                if (dfs(maze, neighbor, goal, visited, visitedOrder, parentMap)) {
                    return true;
                }
            }
        }

        return false;
    }
}
