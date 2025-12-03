package com.example.mazeSolver.algorithm;

import com.example.mazeSolver.dto.Position;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.model.Maze;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BfsSolver implements MazeSolver {

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

        Queue<Position> queue = new LinkedList<>();
        queue.offer(start);
        visited[start.getRow()][start.getCol()] = true;
        visitedOrder.add(new Position(start.getRow(), start.getCol()));

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Position current = queue.poll();

            if (current.equals(goal)) {
                found = true;
                break;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + DR[i];
                int newCol = current.getCol() + DC[i];

                if (maze.isInside(newRow, newCol) && !maze.isWall(newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    Position neighbor = new Position(newRow, newCol);
                    visitedOrder.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.offer(neighbor);

                    if (neighbor.equals(goal)) {
                        found = true;
                        break;
                    }
                }
            }
        }

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
}
