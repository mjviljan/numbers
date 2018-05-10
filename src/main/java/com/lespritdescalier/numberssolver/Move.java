package com.lespritdescalier.numberssolver;

enum Move {
	N("\u2191", PositionChanges.NO_CHANGE, -PositionChanges.STRAIGHT_MOVE_LENGTH),
	NE("\u2197", PositionChanges.DIAGONAL_MOVE_LENGTH, -PositionChanges.DIAGONAL_MOVE_LENGTH),
	E("\u2192", PositionChanges.STRAIGHT_MOVE_LENGTH, PositionChanges.NO_CHANGE),
	SE("\u2198", PositionChanges.DIAGONAL_MOVE_LENGTH, PositionChanges.DIAGONAL_MOVE_LENGTH),
	S("\u2193", PositionChanges.NO_CHANGE, PositionChanges.STRAIGHT_MOVE_LENGTH),
	SW("\u2199", -PositionChanges.DIAGONAL_MOVE_LENGTH, PositionChanges.DIAGONAL_MOVE_LENGTH),
	W("\u2190", -PositionChanges.STRAIGHT_MOVE_LENGTH, PositionChanges.NO_CHANGE),
	NW("\u2196", -PositionChanges.DIAGONAL_MOVE_LENGTH, -PositionChanges.DIAGONAL_MOVE_LENGTH);

	public final int colChange;
	public final int rowChange;
	private final String stringPresentation;

	Move(final String stringPresentation, final int colChange, final int rowChange) {
		this.stringPresentation = stringPresentation;
		this.colChange = colChange;
		this.rowChange = rowChange;
	}

	@Override
	public String toString() {
		return stringPresentation;
	}

	/**
	 * Returns the diagonally mirrored counterpart of the move. The diagonal axis used
	 * as the mirror line is the NW-SE axis.
	 *
	 * @return the diagonally mirrored counterpart move of the original
	 */
	public Move mirrorDiagonally() {
		switch (this) {
			case N:
				return W;
			case NE:
				return SW;
			case E:
				return S;
			case S:
				return E;
			case SW:
				return NE;
			case W:
				return N;
			default:
				return this;
		}
	}

	class PositionChanges {
		public static final int STRAIGHT_MOVE_LENGTH = 3;
		public static final int DIAGONAL_MOVE_LENGTH = 2;
		public static final int NO_CHANGE = 0;
	}
}
