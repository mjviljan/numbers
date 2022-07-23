package com.lespritdescalier.numberssolver;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	public final Position startPosition;
	public final List<Move> moves;

	public Solution(final Position startPosition, final List<Move> moves) {
		this.startPosition = startPosition;
		this.moves = ImmutableList.copyOf(moves);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(startPosition);

		for (Move move : moves) {
			sb.append(move);
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(startPosition, moves);
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

		Solution other = (Solution) obj;
		return Objects.equal(startPosition, other.startPosition) && Objects.equal(moves, other.moves);
	}

	public Solution rotate(int boardSize) {
		final Position rotatedStartposition = new Position(boardSize - 1 - startPosition.row, startPosition.col);

		final List<Move> rotatedMoves = new ArrayList<>();
		for (Move move : this.moves) {
			rotatedMoves.add(move.rotate());
		}

		return new Solution(rotatedStartposition, rotatedMoves);
	}

	public Solution mirrorDiagonally() {
		final List<Move> mirroredMoves = new ArrayList<>();

		if (this.startPosition.col == this.startPosition.row && (this.moves.get(0).equals(Move.NW) || this.moves.get(0).equals(Move.SE))) {
			return null;
		}

		for (Move move : this.moves) {
			mirroredMoves.add(move.mirrorDiagonally());
		}

		final Position mirroredPosition = new Position(this.startPosition.row, this.startPosition.col);
		return new Solution(mirroredPosition, mirroredMoves);
	}
}
