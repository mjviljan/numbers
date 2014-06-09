package com.lespritdescalier.numberssolver;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

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
}
