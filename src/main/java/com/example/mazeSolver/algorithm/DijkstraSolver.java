package com.example.mazeSolver.algorithm;

import com.example.mazeSolver.dto.Position;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.model.Maze;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DijkstraSolver implements MazeSolver {

    // Direction vectors: up, down, left, right
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    @Override
    public SolveResponse solve(Maze maze, Position start, Position goal) {
        long startTime = System.currentTimeMillis();

        List<Position> visitedOrder = new ArrayList<>();
        List<Position> path = new ArrayList<>();

        int rows = maze.getRows();
        int cols = maze.getCols();

        // Distance array
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        boolean[][] visited = new boolean[rows][cols];
        Map<Position, Position> parentMap = new HashMap<>();

        // Priority queue: [distance, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        dist[start.getRow()][start.getCol()] = 0;
        pq.offer(new int[]{0, start.getRow(), start.getCol()});

        boolean found = false;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int d = current[0];
            int row = current[1];
            int col = current[2];

            if (visited[row][col]) {
                continue;
            }

            visited[row][col] = true;
            visitedOrder.add(new Position(row, col));

            Position currentPos = new Position(row, col);

            if (currentPos.equals(goal)) {
                found = true;
                break;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newRow = row + DR[i];
                int newCol = col + DC[i];

                if (maze.isInside(newRow, newCol) && !maze.isWall(newRow, newCol) && !visited[newRow][newCol]) {
                    // All edges have weight 1 in a simple maze
                    int newDist = d + 1;

                    if (newDist < dist[newRow][newCol]) {
                        dist[newRow][newCol] = newDist;
                        Position neighbor = new Position(newRow, newCol);
                        parentMap.put(neighbor, currentPos);
                        pq.offer(new int[]{newDist, newRow, newCol});
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
