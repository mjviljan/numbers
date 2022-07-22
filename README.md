Number puzzle solver
====================

This puzzle solver finds all valid solutions to a simple number puzzle.

## The puzzle

There is a number puzzle (a game, if you will) where you have to enter all the numbers from 1 to 100 in an empty 10x10 grid. The idea is to fill the grid by starting with number 1, then entering number 2, and so on, incrementing the number by one on each step.

There are strict rules on where numbers can be entered. You can start filling the grid (with "1") from any cell in the grid. After this the following number can only be entered in a cell that is three cells away from the last number in the four main directions (up, right, down, left) or two cells away in any diagonal direction. Also, you have to stay within the grid's borders and cannot enter a number in any cell that already has a number.

A progress of one simple puzzle game is demonstrated in the images below. The first picture shows the situation after the first move. The number "1" is inserted near the middle of the grid, and all of the eight possible directions to advance are available.

![Picture of a grid after the first move](/doc-img/firstMove.png "First move")

The second picture shows the situation after the second move. The chosen direction was northeast, so the number "2" was inserted two cells away from the previous cell diagonally. Now only five of the possible directions are available; the three choices going straight or diagonally up are not available because the move would be out of the grid bounds, and going southwest is an invalid move because that cell is already filled.

![Picture of a grid after two moves](/doc-img/secondMove.png "Second move")

The third picture shows the situation after eight moves. The game could still be continued to six different directions. The moves that got the game to this state are (after inserting the number "1" in a freely chosen cell): NE, S, S, W, W, N, SE.

![Picture of a grid after eight moves](/doc-img/thirdMove.png "After eight moves")

## Searching for solutions

I've written a program earlier that finds a single solution to the puzzle by random search. However, this time I wanted to find all possible solutions (so I could later do a graphical presentation of how many solutions there are from each starting point etc.), so a different approach was needed.

The algorithm is a simple (and stupid) depth-first search, starting separately from each starting point. There is currently very little optimization, and the solver runs rather quickly only up to a 5x5 grid for which it finds all possible solutions in less than a second.

### Optimization ideas

Since many of the solutions are mirror images or rotated versions of another solution, it should be enough to find all solutions in only part of the starting positions and then generate other solutions based on those. Using this approach, it should be enough to e.g. find all solutions starting in one quarter of the grid, and then transform those to find the rest of the solutions.

## License

Copyright © 2014-2022 Mika Viljanen.

Distributed under the Apache License, Version 2.0.
