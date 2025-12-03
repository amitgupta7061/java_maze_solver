package com.example.mazeSolver;

import org.springframework.boot.SpringApplication;

public class TestMazeSolverApplication {

	public static void main(String[] args) {
		SpringApplication.from(MazeSolverApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
