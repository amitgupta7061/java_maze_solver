package com.example.mazeSolver.algorithm;

import com.example.mazeSolver.dto.Position;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.model.Maze;

public interface MazeSolver {
    SolveResponse solve(Maze maze, Position start, Position goal);
}
