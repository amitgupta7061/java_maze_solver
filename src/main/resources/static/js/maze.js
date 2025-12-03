// AI Maze Solver - Frontend JavaScript

document.addEventListener('DOMContentLoaded', () => {
    // Default configuration
    const DEFAULT_ROWS = 20;
    const DEFAULT_COLS = 40;
    const ANIMATION_DELAY_VISITED = 15;
    const ANIMATION_DELAY_PATH = 40;

    // State
    let rows = DEFAULT_ROWS;
    let cols = DEFAULT_COLS;
    let startPos = { row: 0, col: 0 };
    let goalPos = { row: DEFAULT_ROWS - 1, col: DEFAULT_COLS - 1 };
    let walls = new Set();
    let isRunning = false;

    // DOM Elements
    const gridContainer = document.getElementById('gridContainer');
    const algorithmSelect = document.getElementById('algorithm');
    const rowsInput = document.getElementById('rows');
    const colsInput = document.getElementById('cols');
    const generateBtn = document.getElementById('generateBtn');
    const runBtn = document.getElementById('runBtn');
    const resetBtn = document.getElementById('resetBtn');
    const clearWallsBtn = document.getElementById('clearWallsBtn');
    const visitedCountEl = document.getElementById('visitedCount');
    const pathLengthEl = document.getElementById('pathLength');
    const executionTimeEl = document.getElementById('executionTime');
    const statusEl = document.getElementById('status');

    // Initialize
    init();

    function init() {
        generateGrid();
        setupEventListeners();
    }

    function setupEventListeners() {
        generateBtn.addEventListener('click', () => {
            rows = parseInt(rowsInput.value) || DEFAULT_ROWS;
            cols = parseInt(colsInput.value) || DEFAULT_COLS;
            
            // Validate bounds
            rows = Math.max(5, Math.min(50, rows));
            cols = Math.max(5, Math.min(80, cols));
            
            rowsInput.value = rows;
            colsInput.value = cols;
            
            // Reset positions for new grid
            startPos = { row: 0, col: 0 };
            goalPos = { row: rows - 1, col: cols - 1 };
            walls.clear();
            
            generateGrid();
            resetStats();
        });

        runBtn.addEventListener('click', runSolver);
        resetBtn.addEventListener('click', resetVisualization);
        clearWallsBtn.addEventListener('click', clearWalls);
    }

    function generateGrid() {
        gridContainer.innerHTML = '';
        
        const grid = document.createElement('div');
        grid.className = 'grid';
        grid.style.gridTemplateColumns = `repeat(${cols}, 22px)`;
        grid.style.gridTemplateRows = `repeat(${rows}, 22px)`;

        for (let r = 0; r < rows; r++) {
            for (let c = 0; c < cols; c++) {
                const cell = document.createElement('div');
                cell.className = 'cell';
                cell.dataset.row = r;
                cell.dataset.col = c;

                // Set initial start and goal
                if (r === startPos.row && c === startPos.col) {
                    cell.classList.add('start');
                } else if (r === goalPos.row && c === goalPos.col) {
                    cell.classList.add('goal');
                }

                // Handle cell clicks
                cell.addEventListener('click', handleCellClick);
                
                // Handle drag to draw walls
                cell.addEventListener('mouseenter', handleCellDrag);

                grid.appendChild(cell);
            }
        }

        gridContainer.appendChild(grid);

        // Track mouse state for drag drawing
        grid.addEventListener('mousedown', () => { grid.dataset.mouseDown = 'true'; });
        document.addEventListener('mouseup', () => { grid.dataset.mouseDown = 'false'; });
    }

    function handleCellClick(event) {
        if (isRunning) return;

        const cell = event.target;
        const row = parseInt(cell.dataset.row);
        const col = parseInt(cell.dataset.col);
        const key = `${row},${col}`;

        if (event.ctrlKey || event.metaKey) {
            // Set start position
            setStart(row, col);
        } else if (event.shiftKey) {
            // Set goal position
            setGoal(row, col);
        } else {
            // Toggle wall
            toggleWall(row, col);
        }
    }

    function handleCellDrag(event) {
        if (isRunning) return;
        
        const grid = event.target.closest('.grid');
        if (grid && grid.dataset.mouseDown === 'true' && !event.ctrlKey && !event.shiftKey) {
            const row = parseInt(event.target.dataset.row);
            const col = parseInt(event.target.dataset.col);
            
            // Don't allow walls on start or goal
            if ((row === startPos.row && col === startPos.col) ||
                (row === goalPos.row && col === goalPos.col)) {
                return;
            }
            
            const key = `${row},${col}`;
            if (!walls.has(key)) {
                walls.add(key);
                event.target.classList.add('wall');
            }
        }
    }

    function setStart(row, col) {
        // Remove old start
        const oldStart = document.querySelector('.cell.start');
        if (oldStart) {
            oldStart.classList.remove('start');
        }

        // Set new start
        startPos = { row, col };
        const cell = getCell(row, col);
        cell.classList.remove('wall', 'goal');
        cell.classList.add('start');
        
        // Remove from walls if present
        walls.delete(`${row},${col}`);
        
        // If goal was here, move it
        if (goalPos.row === row && goalPos.col === col) {
            // Find a new position for goal
            goalPos = { row: rows - 1, col: cols - 1 };
            if (goalPos.row === row && goalPos.col === col) {
                goalPos = { row: 0, col: cols - 1 };
            }
            const newGoalCell = getCell(goalPos.row, goalPos.col);
            newGoalCell.classList.add('goal');
        }
    }

    function setGoal(row, col) {
        // Remove old goal
        const oldGoal = document.querySelector('.cell.goal');
        if (oldGoal) {
            oldGoal.classList.remove('goal');
        }

        // Set new goal
        goalPos = { row, col };
        const cell = getCell(row, col);
        cell.classList.remove('wall', 'start');
        cell.classList.add('goal');
        
        // Remove from walls if present
        walls.delete(`${row},${col}`);
        
        // If start was here, move it
        if (startPos.row === row && startPos.col === col) {
            startPos = { row: 0, col: 0 };
            if (startPos.row === row && startPos.col === col) {
                startPos = { row: rows - 1, col: 0 };
            }
            const newStartCell = getCell(startPos.row, startPos.col);
            newStartCell.classList.add('start');
        }
    }

    function toggleWall(row, col) {
        // Can't place wall on start or goal
        if ((row === startPos.row && col === startPos.col) ||
            (row === goalPos.row && col === goalPos.col)) {
            return;
        }

        const key = `${row},${col}`;
        const cell = getCell(row, col);

        if (walls.has(key)) {
            walls.delete(key);
            cell.classList.remove('wall');
        } else {
            walls.add(key);
            cell.classList.add('wall');
        }
    }

    function getCell(row, col) {
        return document.querySelector(`.cell[data-row="${row}"][data-col="${col}"]`);
    }

    function resetVisualization() {
        if (isRunning) return;

        // Remove visited and path classes, keep walls, start, and goal
        document.querySelectorAll('.cell.visited, .cell.path').forEach(cell => {
            const row = parseInt(cell.dataset.row);
            const col = parseInt(cell.dataset.col);
            
            cell.classList.remove('visited', 'path');
            
            // Re-apply start/goal if needed
            if (row === startPos.row && col === startPos.col) {
                cell.classList.add('start');
            } else if (row === goalPos.row && col === goalPos.col) {
                cell.classList.add('goal');
            }
        });

        resetStats();
        updateStatus('Ready', 'ready');
    }

    function clearWalls() {
        if (isRunning) return;

        walls.clear();
        document.querySelectorAll('.cell.wall').forEach(cell => {
            cell.classList.remove('wall');
        });
        
        resetVisualization();
    }

    function resetStats() {
        visitedCountEl.textContent = '0';
        pathLengthEl.textContent = '0';
        executionTimeEl.textContent = '0 ms';
    }

    function updateStatus(text, type) {
        statusEl.textContent = text;
        statusEl.className = 'stat-value';
        statusEl.classList.add(`status-${type}`);
    }

    async function runSolver() {
        if (isRunning) return;

        isRunning = true;
        setButtonsEnabled(false);
        
        // Clear previous visualization
        document.querySelectorAll('.cell.visited, .cell.path').forEach(cell => {
            cell.classList.remove('visited', 'path');
        });

        updateStatus('Running...', 'running');

        // Build request
        const wallsList = Array.from(walls).map(key => {
            const [row, col] = key.split(',').map(Number);
            return { row, col };
        });

        const request = {
            rows: rows,
            cols: cols,
            start: startPos,
            goal: goalPos,
            walls: wallsList,
            algorithm: algorithmSelect.value
        };

        try {
            const response = await fetch('/api/solve', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            
            // Update stats
            visitedCountEl.textContent = result.visitedCount;
            pathLengthEl.textContent = result.pathLength;
            executionTimeEl.textContent = `${result.executionTimeMs} ms`;

            // Animate the result
            await animateVisited(result.visited);
            await animatePath(result.path);

            if (result.path && result.path.length > 0) {
                updateStatus('Path Found!', 'complete');
            } else {
                updateStatus('No Path Found', 'no-path');
            }

        } catch (error) {
            console.error('Error solving maze:', error);
            updateStatus('Error', 'no-path');
        } finally {
            isRunning = false;
            setButtonsEnabled(true);
        }
    }

    function setButtonsEnabled(enabled) {
        runBtn.disabled = !enabled;
        resetBtn.disabled = !enabled;
        clearWallsBtn.disabled = !enabled;
        generateBtn.disabled = !enabled;
    }

    async function animateVisited(visited) {
        if (!visited || visited.length === 0) return;

        for (let i = 0; i < visited.length; i++) {
            const pos = visited[i];
            const cell = getCell(pos.row, pos.col);
            
            if (cell && !cell.classList.contains('start') && !cell.classList.contains('goal')) {
                cell.classList.add('visited');
            }
            
            // Add delay for animation effect
            if (i % 3 === 0) {
                await sleep(ANIMATION_DELAY_VISITED);
            }
        }
    }

    async function animatePath(path) {
        if (!path || path.length === 0) return;

        // Small delay before showing path
        await sleep(200);

        for (let i = 0; i < path.length; i++) {
            const pos = path[i];
            const cell = getCell(pos.row, pos.col);
            
            if (cell) {
                cell.classList.remove('visited');
                cell.classList.add('path');
                
                // Keep start and goal classes
                if (pos.row === startPos.row && pos.col === startPos.col) {
                    cell.classList.add('start');
                } else if (pos.row === goalPos.row && pos.col === goalPos.col) {
                    cell.classList.add('goal');
                }
            }
            
            await sleep(ANIMATION_DELAY_PATH);
        }
    }

    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
});
