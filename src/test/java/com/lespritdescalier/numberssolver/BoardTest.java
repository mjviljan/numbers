package com.lespritdescalier.numberssolver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private static final int BOARD_SIZE = 10;
	private static final int VALID_COL = BOARD_SIZE / 2;
	private static final int VALID_ROW = BOARD_SIZE / 2;
	private static final Position VALID_POSITION = new Position(VALID_COL, VALID_ROW);
	private Board board;

	@Before
	public void setUp() {
		board = new Board(BOARD_SIZE);
	}

	@Test
	public void negativeColumnPositionIsOutOfBounds() {
		Position pos = new Position(-1, VALID_ROW);
		assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void columnPositionGreaterThanBoardWidthIsOutOfBounds() {
		Position pos = new Position(board.width, VALID_ROW);
		assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionInFirstColumnIsInBounds() {
		Position pos = new Position(0, VALID_ROW);
		assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionInLastColumnIsInBounds() {
		Position pos = new Position(board.width - 1, VALID_ROW);
		assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void negativeRowPositionIsOutOfBounds() {
		Position pos = new Position(VALID_COL, -1);
		assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void rowPositionGreaterThanBoardHeightIsOutOfBounds() {
		Position pos = new Position(VALID_COL, board.height);
		assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionOnFirstRowIsInBounds() {
		Position pos = new Position(VALID_COL, 0);
		assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionOnLastRowIsInBounds() {
		Position pos = new Position(VALID_COL, board.height - 1);
		assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionWillNotBeOccupiedIfNoNumberIsAdded() {
		assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void positionOfAddedNumberWillBeOccupied() {
		board.addNumber(VALID_POSITION, 1);
		assertTrue(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void positionCanBeEmptied() {
		board.addNumber(VALID_POSITION, 1);
		board.removeNumber(VALID_POSITION);
		assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void boardWithEmptyPositionIsNotFull() {
		fillBoard();
		board.removeNumber(VALID_POSITION);
		assertFalse(board.isFull());
	}

	@Test
	public void boardWithAllPositionsOccupiedIsFull() {
		fillBoard();
		assertTrue(board.isFull());
	}

	@Test
	public void positionsAreNotOccupiedAfterClearingBoard() {
		board.addNumber(VALID_POSITION, 1);
		board.clear();
		assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void clearedBoardIsNotFull() {
		fillBoard();
		board.clear();
		assertFalse(board.isFull());
	}

	private void fillBoard() {
		for (int row = 0; row < board.height; row++) {
			for (int col = 0; col < board.width; col++) {
				Position pos = new Position(col, row);
				board.addNumber(pos, 1);
			}
		}
	}
}
