package com.example.mazeSolver.service;

import com.example.mazeSolver.algorithm.BfsSolver;
import com.example.mazeSolver.algorithm.DfsSolver;
import com.example.mazeSolver.algorithm.DijkstraSolver;
import com.example.mazeSolver.algorithm.MazeSolver;
import com.example.mazeSolver.dto.MazeRequest;
import com.example.mazeSolver.dto.Position;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.model.Maze;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MazeService {

    private final DfsSolver dfsSolver;
    private final BfsSolver bfsSolver;
    private final DijkstraSolver dijkstraSolver;

    public MazeService(DfsSolver dfsSolver, BfsSolver bfsSolver, DijkstraSolver dijkstraSolver) {
        this.dfsSolver = dfsSolver;
        this.bfsSolver = bfsSolver;
        this.dijkstraSolver = dijkstraSolver;
    }

    public SolveResponse solveMaze(MazeRequest request) {
        // Build the maze from the request
        Maze maze = buildMaze(request);

        // Select the solver based on algorithm
        MazeSolver solver = selectSolver(request.getAlgorithm());

        // Solve and return response
        return solver.solve(maze, request.getStart(), request.getGoal());
    }

    private Maze buildMaze(MazeRequest request) {
        Maze maze = new Maze(request.getRows(), request.getCols());

        List<Position> walls = request.getWalls();
        if (walls != null) {
            for (Position wall : walls) {
                maze.setWall(wall.getRow(), wall.getCol(), true);
            }
        }

        return maze;
    }

    private MazeSolver selectSolver(String algorithm) {
        if (algorithm == null || algorithm.isEmpty()) {
            return bfsSolver;
        }

        return switch (algorithm.toUpperCase()) {
            case "DFS" -> dfsSolver;
            case "BFS" -> bfsSolver;
            case "DIJKSTRA" -> dijkstraSolver;
            default -> bfsSolver;
        };
    }
}
