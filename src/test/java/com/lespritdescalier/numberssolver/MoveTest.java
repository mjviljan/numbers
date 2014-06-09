package com.lespritdescalier.numberssolver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoveTest {

	@Test
	public void moveNDoesNotChangeColumn() {
		assertEquals(0, Move.N.colChange);
	}

	@Test
	public void moveNChangesRowByMinusThree() {
		assertEquals(-3, Move.N.rowChange);
	}

	@Test
	public void moveNEChangesColumnByTwo() {
		assertEquals(2, Move.NE.colChange);
	}

	@Test
	public void moveNEChangesRowByMinusTwo() {
		assertEquals(-2, Move.NE.rowChange);
	}

	@Test
	public void moveEChangesColumnByThree() {
		assertEquals(3, Move.E.colChange);
	}

	@Test
	public void moveEDoesNotChangeRow() {
		assertEquals(0, Move.E.rowChange);
	}

	@Test
	public void moveSEChangesColumnByTwo() {
		assertEquals(2, Move.SE.colChange);
	}

	@Test
	public void moveSEChangesRowByTwo() {
		assertEquals(2, Move.SE.rowChange);
	}

	@Test
	public void moveSDoesNotChangeColumn() {
		assertEquals(0, Move.S.colChange);
	}

	@Test
	public void moveSChangesRowByThree() {
		assertEquals(3, Move.S.rowChange);
	}

	@Test
	public void moveSWChangesColumnByMinusTwo() {
		assertEquals(-2, Move.SW.colChange);
	}

	@Test
	public void moveSWChangesRowByTwo() {
		assertEquals(2, Move.SW.rowChange);
	}

	@Test
	public void moveWChangesColumnByMinusThree() {
		assertEquals(-3, Move.W.colChange);
	}

	@Test
	public void moveWDoesNotChangeRow() {
		assertEquals(0, Move.W.rowChange);
	}

	@Test
	public void moveNWChangesColumnByMinusTwo() {
		assertEquals(-2, Move.NW.colChange);
	}

	@Test
	public void moveNWChangesRowByMinusTwo() {
		assertEquals(-2, Move.NW.rowChange);
	}
}
