package com.lespritdescalier.numberssolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class PuzzlerSolverTest {

	@Test
	public void allSolutionsToPuzzleAreUnique() {
		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.recordSolutions();
		solver.findSolutionsFromUniquePositions();
		List<Solution> allSolutions = solver.getSolutions();
		Set<Solution> uniqueSolutions = new HashSet<>(allSolutions);

		assertEquals(allSolutions.size(), uniqueSolutions.size());
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
		assertTrue(allSolutions.contains(knownSolution));
	}
}
