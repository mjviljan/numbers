package com.lespritdescalier.numberssolver;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class SolutionTest {
	private final List<Move> NO_MOVES = Collections.emptyList();

	@Test
	public void rotatingSolutionReturnsSolutionRotated90Degrees() {
		Position origPos = new Position(2, 0);
		Solution original = new Solution(origPos, NO_MOVES);

		Solution rotatedSolution = original.rotate(5, 5);
		assertEquals(new Position(4, 2), rotatedSolution.startPosition);
	}
}
