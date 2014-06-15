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
		solver.recordSolutions();
		solver.findSolutionsFromAllPositions();
		List<Solution> allSolutions = solver.getSolutions(); 
		Set<Solution> uniqueSolutions = new HashSet<Solution>(allSolutions);

		assertEquals(allSolutions.size(), uniqueSolutions.size());
	}

	@Test
	public void foundSolutionsIncludeKnownSolution() {
		Position solutionStartPosition = new Position(4, 0);
		List<Move> solutionsMoves = ImmutableList.of(
				Move.S, Move.W, Move.N, Move.SE, Move.SW, Move.E, Move.NW, Move.SW, Move.E, Move.NW, Move.E, Move.NW,
				Move.SW, Move.SE, Move.N, Move.SW, Move.N, Move.E, Move.S, Move.NW, Move.E, Move.SW, Move.NW, Move.E);
		Solution knownSolution = new Solution(solutionStartPosition, solutionsMoves);

		PuzzleSolver solver = new PuzzleSolver(new Board(5, 5));
		solver.recordSolutions();
		solver.findSolutionsFromAllPositions();
		List<Solution> allSolutions = solver.getSolutions(); 
		assertTrue(allSolutions.contains(knownSolution));
	}
}
