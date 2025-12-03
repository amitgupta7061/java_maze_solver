package com.example.mazeSolver.controller;

import com.example.mazeSolver.dto.MazeRequest;
import com.example.mazeSolver.dto.SolveResponse;
import com.example.mazeSolver.service.MazeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MazeController {

    private final MazeService mazeService;

    public MazeController(MazeService mazeService) {
        this.mazeService = mazeService;
    }

    @GetMapping("/")
    public String index() {
        return "maze";
    }

    @PostMapping("/api/solve")
    @ResponseBody
    public ResponseEntity<SolveResponse> solve(@RequestBody MazeRequest request) {
        SolveResponse response = mazeService.solveMaze(request);
        return ResponseEntity.ok(response);
    }
}
