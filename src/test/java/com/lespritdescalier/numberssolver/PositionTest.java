package com.lespritdescalier.numberssolver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {

	@Test
	public void applyingMoveToPositionReturnsTargetPosition() {
		Move move = Move.N;
		Position origPos = new Position(5, 5);
		Position newPos = origPos.applyMove(move);
		Assertions.assertEquals(origPos.col + move.colChange, newPos.col);
		Assertions.assertEquals(origPos.row + move.rowChange, newPos.row);
	}
}
