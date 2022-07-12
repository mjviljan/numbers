package com.lespritdescalier.numberssolver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
	private static final int BOARD_SIZE = 10;
	private static final int VALID_COL = BOARD_SIZE / 2;
	private static final int VALID_ROW = BOARD_SIZE / 2;
	private static final Position VALID_POSITION = new Position(VALID_COL, VALID_ROW);
	private Board board;

	@BeforeEach
	public void setUp() {
		board = new Board(BOARD_SIZE);
	}

	@Test
	public void negativeColumnPositionIsOutOfBounds() {
		Position pos = new Position(-1, VALID_ROW);
		Assertions.assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void columnPositionGreaterThanBoardWidthIsOutOfBounds() {
		Position pos = new Position(board.width, VALID_ROW);
		Assertions.assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionInFirstColumnIsInBounds() {
		Position pos = new Position(0, VALID_ROW);
		Assertions.assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionInLastColumnIsInBounds() {
		Position pos = new Position(board.width - 1, VALID_ROW);
		Assertions.assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void negativeRowPositionIsOutOfBounds() {
		Position pos = new Position(VALID_COL, -1);
		Assertions.assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void rowPositionGreaterThanBoardHeightIsOutOfBounds() {
		Position pos = new Position(VALID_COL, board.height);
		Assertions.assertTrue(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionOnFirstRowIsInBounds() {
		Position pos = new Position(VALID_COL, 0);
		Assertions.assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionOnLastRowIsInBounds() {
		Position pos = new Position(VALID_COL, board.height - 1);
		Assertions.assertFalse(board.isPositionOutOfBounds(pos));
	}

	@Test
	public void positionWillNotBeOccupiedIfNoNumberIsAdded() {
		Assertions.assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void positionOfAddedNumberWillBeOccupied() {
		board.addNumber(VALID_POSITION, 1);
		Assertions.assertTrue(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void positionCanBeEmptied() {
		board.addNumber(VALID_POSITION, 1);
		board.removeNumber(VALID_POSITION);
		Assertions.assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void boardWithEmptyPositionIsNotFull() {
		fillBoard();
		board.removeNumber(VALID_POSITION);
		Assertions.assertFalse(board.isFull());
	}

	@Test
	public void boardWithAllPositionsOccupiedIsFull() {
		fillBoard();
		Assertions.assertTrue(board.isFull());
	}

	@Test
	public void positionsAreNotOccupiedAfterClearingBoard() {
		board.addNumber(VALID_POSITION, 1);
		board.clear();
		Assertions.assertFalse(board.isPositionOccupied(VALID_POSITION));
	}

	@Test
	public void clearedBoardIsNotFull() {
		fillBoard();
		board.clear();
		Assertions.assertFalse(board.isFull());
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
