package com.lespritdescalier.numberssolver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveTest {

	@Test
	public void moveNDoesNotChangeColumn() {
		Assertions.assertEquals(0, Move.N.colChange);
	}

	@Test
	public void moveNChangesRowByMinusThree() {
		Assertions.assertEquals(-3, Move.N.rowChange);
	}

	@Test
	public void moveNEChangesColumnByTwo() {
		Assertions.assertEquals(2, Move.NE.colChange);
	}

	@Test
	public void moveNEChangesRowByMinusTwo() {
		Assertions.assertEquals(-2, Move.NE.rowChange);
	}

	@Test
	public void moveEChangesColumnByThree() {
		Assertions.assertEquals(3, Move.E.colChange);
	}

	@Test
	public void moveEDoesNotChangeRow() {
		Assertions.assertEquals(0, Move.E.rowChange);
	}

	@Test
	public void moveSEChangesColumnByTwo() {
		Assertions.assertEquals(2, Move.SE.colChange);
	}

	@Test
	public void moveSEChangesRowByTwo() {
		Assertions.assertEquals(2, Move.SE.rowChange);
	}

	@Test
	public void moveSDoesNotChangeColumn() {
		Assertions.assertEquals(0, Move.S.colChange);
	}

	@Test
	public void moveSChangesRowByThree() {
		Assertions.assertEquals(3, Move.S.rowChange);
	}

	@Test
	public void moveSWChangesColumnByMinusTwo() {
		Assertions.assertEquals(-2, Move.SW.colChange);
	}

	@Test
	public void moveSWChangesRowByTwo() {
		Assertions.assertEquals(2, Move.SW.rowChange);
	}

	@Test
	public void moveWChangesColumnByMinusThree() {
		Assertions.assertEquals(-3, Move.W.colChange);
	}

	@Test
	public void moveWDoesNotChangeRow() {
		Assertions.assertEquals(0, Move.W.rowChange);
	}

	@Test
	public void moveNWChangesColumnByMinusTwo() {
		Assertions.assertEquals(-2, Move.NW.colChange);
	}

	@Test
	public void moveNWChangesRowByMinusTwo() {
		Assertions.assertEquals(-2, Move.NW.rowChange);
	}

	@Test
	public void mirroringStraightMoveReturnsMoveMirroredByTheDiagonalAxis() {
		Assertions.assertEquals(Move.W, Move.N.mirrorDiagonally());
	}

	@Test
	public void mirroringDiagonalMoveReturnsMoveMirroredByTheDiagonalAxis() {
		Assertions.assertEquals(Move.SW, Move.NE.mirrorDiagonally());
	}

	@Test
	public void mirroringMoveOnMirroringAxisReturnsOriginalMove() {
		Assertions.assertEquals(Move.SE, Move.SE.mirrorDiagonally());
	}

	@Test
	public void rotatingStraightMoveReturnsMoveRotatedBy90Degrees() {
		Assertions.assertEquals(Move.E, Move.N.rotate());
	}

	@Test
	public void rotatingDiagonalMoveReturnsMoveRotatedBy90Degrees() {
		Assertions.assertEquals(Move.NW, Move.SW.rotate());
	}
}
