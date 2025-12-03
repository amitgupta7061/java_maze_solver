# Maze Pathfinder

An interactive web-based maze pathfinding visualizer built with Java Spring Boot and Thymeleaf. Watch different pathfinding algorithms explore and solve mazes in real-time.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![License](https://img.shields.io/badge/License-MIT-blue)

## Features

- **Interactive Grid**: Click to create walls, set start and goal positions
- **Multiple Algorithms**: 
  - **BFS (Breadth-First Search)** - Guarantees shortest path
  - **DFS (Depth-First Search)** - Explores depth-first
  - **Dijkstra's Algorithm** - Optimal shortest path finding
- **Real-time Visualization**: Watch the algorithm explore the maze step by step
- **Statistics Panel**: View nodes visited, path length, and execution time
- **Responsive Design**: Works on desktop and tablet devices

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf

### Frontend
- HTML5
- CSS3 (Custom styling with animations)
- Vanilla JavaScript

## Project Structure

```
src/main/java/com/example/mazeSolver/
├── MazeSolverApplication.java
├── controller/
│   └── MazeController.java
├── service/
│   └── MazeService.java
├── algorithm/
│   ├── MazeSolver.java
│   ├── BfsSolver.java
│   ├── DfsSolver.java
│   └── DijkstraSolver.java
├── model/
│   └── Maze.java
└── dto/
    ├── MazeRequest.java
    ├── Position.java
    └── SolveResponse.java

src/main/resources/
├── templates/
│   └── maze.html
├── static/
│   ├── css/
│   │   └── maze.css
│   └── js/
│       └── maze.js
└── application.properties
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/amitgupta7061/java_maze_solver.git
   cd java_maze_solver
   ```

2. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

## Usage

### Controls

| Action | Control |
|--------|---------|
| Toggle Wall | Click on cell |
| Set Start Position | Ctrl + Click |
| Set Goal Position | Shift + Click |
| Draw Walls | Click and drag |

### Steps

1. **Create a maze**: Click on cells to add walls, or drag to draw multiple walls
2. **Set positions**: Use Ctrl+Click for start (green) and Shift+Click for goal (red)
3. **Choose algorithm**: Select BFS, DFS, or Dijkstra from the dropdown
4. **Run**: Click the "Run" button to visualize the pathfinding
5. **Reset**: Clear the visualization while keeping walls, or clear everything

## Algorithm Comparison

| Algorithm | Shortest Path | Time Complexity | Best For |
|-----------|---------------|-----------------|----------|
| BFS | ✅ Yes | O(V + E) | Unweighted graphs |
| DFS | ❌ No | O(V + E) | Memory efficiency |
| Dijkstra | ✅ Yes | O((V + E) log V) | Weighted graphs |

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Main page with maze visualizer |
| POST | `/api/solve` | Solve maze and return path |

### Request Body (POST /api/solve)

```json
{
  "rows": 20,
  "cols": 40,
  "start": { "row": 0, "col": 0 },
  "goal": { "row": 19, "col": 39 },
  "walls": [
    { "row": 5, "col": 10 },
    { "row": 5, "col": 11 }
  ],
  "algorithm": "BFS"
}
```

### Response

```json
{
  "visited": [{ "row": 0, "col": 0 }, ...],
  "path": [{ "row": 0, "col": 0 }, ...],
  "visitedCount": 150,
  "pathLength": 25,
  "executionTimeMs": 5
}
```

## License

This project is licensed under the MIT License.

## Author

**Amit Gupta** - [GitHub](https://github.com/amitgupta7061)
