package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzlerSolverTest {

	@Test
	public void solverFindsCorrectNumberOfSolutions() {
		final PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutions();
		Assertions.assertEquals(12400, solver.getSolutions().size());
	}

	@Test
	public void solutionsAreSearchedFromOptimalStartingPointsOnly() {
		final PuzzleSolver solver4x4 = new PuzzleSolver(new Board(4));
		final List<Position> solutionStartingPoints4x4 = solver4x4.getUniqueSolutionStartingPoints();
		Assertions.assertEquals(3, solutionStartingPoints4x4.size());
		Assertions.assertTrue(solutionStartingPoints4x4.contains(new Position(0,0)));
		Assertions.assertTrue(solutionStartingPoints4x4.contains(new Position(1,0)));
		Assertions.assertTrue(solutionStartingPoints4x4.contains(new Position(1,1)));

		final PuzzleSolver solver5x5 = new PuzzleSolver(new Board(5));
		final List<Position> solutionStartingPoints5x5 = solver5x5.getUniqueSolutionStartingPoints();
		Assertions.assertEquals(6, solutionStartingPoints5x5.size());
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(0,0)));
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(1,0)));
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(1,1)));
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(2,0)));
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(2,1)));
		Assertions.assertTrue(solutionStartingPoints5x5.contains(new Position(2,2)));

		final PuzzleSolver solver6x6 = new PuzzleSolver(new Board(6));
		final List<Position> solutionStartingPoints6x6 = solver6x6.getUniqueSolutionStartingPoints();
		Assertions.assertEquals(6, solutionStartingPoints6x6.size());
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(0,0)));
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(1,0)));
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(1,1)));
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(2,0)));
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(2,1)));
		Assertions.assertTrue(solutionStartingPoints6x6.contains(new Position(2,2)));

		final PuzzleSolver solver10x10 = new PuzzleSolver(new Board(10));
		final List<Position> solutionStartingPoints10x10 = solver10x10.getUniqueSolutionStartingPoints();
		Assertions.assertEquals(15, solutionStartingPoints10x10.size());
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(0,0)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(1,0)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(1,1)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(2,0)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(2,1)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(2,2)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(3,0)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(3,1)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(3,2)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(3,3)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(4,0)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(4,1)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(4,2)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(4,3)));
		Assertions.assertTrue(solutionStartingPoints10x10.contains(new Position(4,4)));
	}

	@Test
	public void allSolutionsToPuzzleAreUnique() {
		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Set<Solution> uniqueSolutions = new HashSet<>(allSolutions);

		Assertions.assertEquals(allSolutions.size(), uniqueSolutions.size());
	}

	@Test
	public void foundSolutionsIncludeKnownSolution() {
		Position solutionStartPosition = new Position(1, 1);
		List<Move> solutionsMoves = ImmutableList.of(
				Move.E, Move.SW, Move.NW, Move.E, Move.SW, Move.E, Move.N, Move.W, Move.SE, Move.SW, Move.E, Move.NW,
				Move.NW, Move.E, Move.S, Move.W, Move.NE, Move.S, Move.NW, Move.NE, Move.SE, Move.W, Move.SE, Move.W);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}

	@Test
	public void foundSolutionsIncludeMirroredSolution() {
		// original known solution is from (2,0) so mirrored solution should be from (0,2)
		Position solutionStartPosition = new Position(0,2);
		// From "↙→↖↓→↑↙↖↓↗↓↗←↗↓↖↓→↑↙↖↓→↑" to "↗↓↖→↓←↗↖→↙→↙↑↙→↖→↓←↗↖→↓←"
		List<Move> solutionsMoves = ImmutableList.of(Move.NE, Move.S, Move.NW, Move.E, Move.S, Move.W, Move.NE, Move.NW, Move.E, Move.SW,
				Move.E, Move.SW, Move.N, Move.SW, Move.E, Move.NW, Move.E, Move.S, Move.W, Move.NE, Move.NW, Move.E, Move.S, Move.W);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}

	@Test
	public void foundSolutionsIncludeRotatedSolutionOf90Degrees() {
		// original known solution is from (1,1) so mirrored solution should be from (3,1)
		Position solutionStartPosition = new Position(3,1);
		// From "→↙↖↓→↑↙→↑←↘↙→↖↖↓→↑↙→↖↙↘↑" to "↓↖↗←↓→↖↓→↑↙↖↓↗↗←↓→↖↓↗↖↙→"
		List<Move> solutionsMoves = ImmutableList.of(Move.S, Move.NW, Move.NE, Move.W, Move.S, Move.E, Move.NW, Move.S, Move.E, Move.N,
				Move.SW, Move.NW, Move.S, Move.NE, Move.NE, Move.W, Move.S, Move.E, Move.NW, Move.S, Move.NE, Move.NW, Move.SW, Move.E);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}

	@Test
	public void foundSolutionsIncludeRotatedSolutionOf180Degrees() {
		// original known solution is from (1,1) so mirrored solution should be from (3,3)
		Position solutionStartPosition = new Position(3,3);
		// From "→↙↖↓→↑↙→↑←↘↙→↖↖↓→↑↙→↖↙↘↑" to "←↗↘↑←↓↗←↓→↖↗←↘↘↑←↓↗←↘↗↖↓"
		List<Move> solutionsMoves = ImmutableList.of(Move.W, Move.NE, Move.SE, Move.N, Move.W, Move.S, Move.NE, Move.W, Move.S, Move.E,
				Move.NW, Move.NE, Move.W, Move.SE, Move.SE, Move.N, Move.W, Move.S, Move.NE, Move.W, Move.SE, Move.NE, Move.NW, Move.S);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}

	@Test
	public void foundSolutionsIncludeRotatedSolutionOf270Degrees() {
		// original known solution is from (1,1) so mirrored solution should be from (1,3)
		Position solutionStartPosition = new Position(1, 3);
		// From "→↙↖↓→↑↙→↑←↘↙→↖↖↓→↑↙→↖↙↘↑" to "↑↘↙→↑←↘↑←↓↗↘↑↙↙→↑←↘↑↙↘↗←"
		List<Move> solutionsMoves = ImmutableList.of(Move.N, Move.SE, Move.SW, Move.E, Move.N, Move.W, Move.SE, Move.N, Move.W, Move.S,
				Move.NE, Move.SE, Move.N, Move.SW, Move.SW, Move.E, Move.N, Move.W, Move.SE, Move.N, Move.SW, Move.SE, Move.NE, Move.W);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}
}
