# N-puzzle solver
Program made for an university project for the Data Structure and Algorithms course given by professor Fabio Vandin a.y 2021/2022.
The project was designed as a competition between students, to design the fastest algorithm to solve a generalized <a href="https://en.wikipedia.org/wiki/15_Puzzle" target="_blank" rel="noreferrer">15-Puzzle</a> with the minimum number of moves. 

Among all students, this program was awarded as the fastest (the professor tested all programs on the same machine with the 8-puzzle version, though).

## Overview
The algorithm is based on the <a href="https://en.wikipedia.org/wiki/A*_search_algorithm" target="_blank" rel="noreferrer">A* search</a>, 


## Usage 
**Java** is required to run the program.
The program takes as input a txt file formatted as follows:
```txt
4
5 1 3 10 15 12 7 9 2 4 6 8 13 0 11 14
```
- The first line is the size of the problem.
- The second line is the displacement of the tiles, where from left to right we insert the element starting from the upper left corner; 0 is used as empty cell.

After compilation, run :
```shell
java Solver data/$.txt
```

## Idea
### Structure
The board class contains:
- A square matrix to contain the current configuration of the puzzle
- Coordinates of the empty cell
- Path and heuristic cost
- The current and father configuration strings are saved

### Logic
A priority queue keeps the frontier of the unexplored board sorted by evaluation function, smaller values indicates higher priority.
Then a hash map tracks down the already visited configuration, in order to not fall again in the same loop of moves. Therefore, we pick
the first element in the frontier which has never been visited.

### The Heuristics
When we use the A* algorithm, we have a evaluation function $f(x)$ which is obtained as the sum of the heuristic function and a cost function:
$$f(x)=g(x)+h(x)$$
- $g(x)$ is the path cost to reach a configuration $x$ (the number of steps)
- $h(x)$ is an estimate of the missing steps required to reach the final position.
    I've used the manhattan heuristic combined with the linear conflict heuristic

#### Manhattan Heuristic
The manhattan heuristic is obtained by adding for each cell the distance to it's final position:
$$h(c)=|c.x-\text{goal}(c).x|+|c.y-\text{goal}(c).y|$$

#### Linear Conflict
The linear conflict consists in the fact that if two cells in the same line (or column) must eventually swap places, then one of them must change line (or column) in order to let the other one through, thus if a conflict happens we can consider 2 more moves in the heuristics.
