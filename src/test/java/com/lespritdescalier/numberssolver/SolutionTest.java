package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class SolutionTest {
	private final List<Move> NO_MOVES = Collections.emptyList();

	@Test
	public void rotatingSolutionMovesStartingPointRotatingAroundCenterOfBoard() {
		Position origPos = new Position(2, 0);
		Solution original = new Solution(origPos, NO_MOVES);

		Solution rotatedSolution = original.rotate(5, 5);
		Assertions.assertEquals(new Position(4, 2), rotatedSolution.startPosition);
	}

	@Test
	public void mirroringSolutionDiagonallyReturnsOriginalStartingPointAndCorrectlyMirroredMoves() {
		final Position startPos = new Position(0, 0);
		final Solution original = new Solution(startPos, ImmutableList.of(Move.E, Move.SE, Move.S, Move.SW, Move.W, Move.NW, Move.N, Move.NE));

		Solution mirroredSolution = original.mirrorDiagonally();
		Assertions.assertEquals(startPos, mirroredSolution.startPosition);
		Assertions.assertEquals(ImmutableList.of(Move.S, Move.SE, Move.E, Move.NE, Move.N, Move.NW, Move.W, Move.SW), mirroredSolution.moves);
	}
}
