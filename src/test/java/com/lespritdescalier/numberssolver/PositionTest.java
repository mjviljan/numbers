package com.lespritdescalier.numberssolver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {

	@Test
	public void applyingMoveToPositionReturnsTargetPosition() {
		Move move = Move.N;
		Position origPos = new Position(5, 5);
		Position newPos = origPos.applyMove(move);
		assertEquals(origPos.col + move.colChange, newPos.col);
		assertEquals(origPos.row + move.rowChange, newPos.row);
	}
}
