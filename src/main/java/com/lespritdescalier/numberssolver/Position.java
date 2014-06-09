package com.lespritdescalier.numberssolver;

import com.google.common.base.Objects;

public class Position {
	public final int col;
	public final int row;

	public Position(final int col, final int row) {
		this.col = col;
		this.row = row;
	}

	public Position applyMove(final Move n) {
		return new Position(col + n.colChange, row + n.rowChange);
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", col, row);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(col, row);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Position other = (Position) obj;
		if (col != other.col || row != other.row) {
			return false;
		}

		return true;
	}
}
