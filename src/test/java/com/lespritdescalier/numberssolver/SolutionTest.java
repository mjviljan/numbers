package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class SolutionTest {

	@Test
	public void rotatingSolutionMovesStartingPointRotatingAroundCenterOfBoard() {
		Position origPos = new Position(2, 0);
		List<Move> origMoves = ImmutableList.of(
				Move.NE, Move.S, Move.NW, Move.E, Move.S,
				Move.W, Move.NE, Move.NW, Move.E, Move.SW,
				Move.E, Move.SW, Move.N, Move.SW, Move.E,
				Move.NW, Move.E, Move.S, Move.W, Move.NE,
				Move.NW, Move.E, Move.S, Move.W);
		Solution original = new Solution(origPos, origMoves);

		Solution rotatedSolution = original.rotate(5);
		List<Move> rotatedMoves = ImmutableList.of(
				Move.SE, Move.W, Move.NE, Move.S, Move.W,
				Move.N, Move.SE, Move.NE, Move.S, Move.NW,
				Move.S, Move.NW, Move.E, Move.NW, Move.S,
				Move.NE, Move.S, Move.W, Move.N, Move.SE,
				Move.NE, Move.S, Move.W, Move.N);

		Assertions.assertEquals(new Position(4, 2), rotatedSolution.startPosition);
		Assertions.assertEquals(rotatedMoves, rotatedSolution.moves);
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
