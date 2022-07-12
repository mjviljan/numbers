package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzlerSolverTest {

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
		solver.recordSolutions();
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
		solver.recordSolutions();
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Assertions.assertTrue(allSolutions.contains(knownSolution));
	}
}
