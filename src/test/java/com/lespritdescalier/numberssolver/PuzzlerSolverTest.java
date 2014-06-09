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
		PuzzleSolver solver = new PuzzleSolver(new Board(5, 5));
		List<Solution> allSolutions = solver.findSolutionsFromAllPositions();
		Set<Solution> uniqueSolutions = new HashSet<Solution>(allSolutions);

		assertEquals(allSolutions.size(), uniqueSolutions.size());
	}

	@Test
	public void foundSolutionsIncludeKnownSolution() {
		Position solutionStartPosition = new Position(1, 1);
		List<Move> solutionsMoves = ImmutableList.of(
				Move.E, Move.S, Move.W, Move.NE, Move.W, Move.NE, Move.SE, Move.SW, Move.N, Move.W, Move.NE, Move.S,
				Move.W, Move.N, Move.SE, Move.SW, Move.N, Move.E, Move.SW, Move.N, Move.E, Move.S, Move.NW, Move.S);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5, 5));
		List<Solution> allSolutions = solver.findSolutionsFromAllPositions();
		assertTrue(allSolutions.contains(knownSolution));
	}
}
