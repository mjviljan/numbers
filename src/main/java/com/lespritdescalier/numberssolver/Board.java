package com.lespritdescalier.numberssolver;

import org.apache.commons.lang3.StringUtils;

public class Board {
	private static final byte UNOCCUPIED = -1;
	private final int[][] numbers;
	private int numberCount;
	public final int width;
	public final int height;

	public Board(final int size) {
		width = size;
		height = size;
		numbers = new int[height][width];
		numberCount = 0;
		clear();
	}

	public boolean isPositionOutOfBounds(final Position pos) {
		return isColumnPositionOutOfBounds(pos) || isRowPositionOutOfBounds(pos);
	}

	private boolean isColumnPositionOutOfBounds(final Position pos) {
		return pos.col < 0 || pos.col >= width;
	}

	private boolean isRowPositionOutOfBounds(final Position pos) {
		return pos.row < 0 || pos.row >= height;
	}

	public void addNumber(final Position pos, final int number) {
		// Note: for efficiency reasons, there's no check for adding a number to
		// an already occupied position
		numbers[pos.row][pos.col] = number;
		numberCount++;
	}

	public void removeNumber(final Position pos) {
		// Note: for efficiency reasons, there's no check for removing a number
		// from an unoccupied position
		numbers[pos.row][pos.col] = UNOCCUPIED;
		numberCount--;
	}

	public boolean isPositionOccupied(final Position pos) {
		return numbers[pos.row][pos.col] != UNOCCUPIED;
	}

	public boolean isFull() {
		return numberCount == width * height;
	}

	public void clear() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				numbers[row][col] = UNOCCUPIED;
			}
		}

		numberCount = 0;
	}

	private String rowSeparator() {
		StringBuilder sb = new StringBuilder();

		sb.append("+");
		for (int i = 0; i < width; i++) {
			sb.append("---+");
		}

		return sb.toString();
	}

	private String numberRow(final int row) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n|");
		for (int col = 0; col < width; col++) {
			sb.append(numberPosition(col, row));
		}
		sb.append("\n");
		sb.append(rowSeparator());

		return sb.toString();
	}

	private String numberPosition(final int col, final int row) {
		StringBuilder sb = new StringBuilder();

		int number = numbers[row][col];
		if (number == UNOCCUPIED) {
			sb.append("   ");
		} else {
			sb.append(StringUtils.center(Integer.toString(number), 3));
		}

		sb.append("|");
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(rowSeparator());

		for (int row = 0; row < height; row++) {
			sb.append(numberRow(row));
		}

		sb.append("\n\n");
		return sb.toString();
	}
}
