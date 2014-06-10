package com.lespritdescalier.numberssolver;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

/**
 * The algorithm for finding all solutions for a number puzzle of given board
 * size. The algorithm is a simple depth-first search with no
 * optimizations.
 * 
 * @author Mika Viljanen (https://github.com/mjviljan)
 */
public class PuzzleSolver {
	private final Logger logger = Logger.getLogger(PuzzleSolver.class);

	/**
	 * A puzzle board used to store the state of the progressing search.
	 */
	private final Board board;

	/**
	 * The moves that the current depth-first search has made so far.
	 */
	private final List<Move> moves;

	public PuzzleSolver(final Board board) {
		this.board = board;
		moves = new LinkedList<Move>();
	}

	private void logSearchProgress() {
		if (logger.isTraceEnabled()) {
			logger.trace("\n" + board.toString());
		}
	}

	private void logFoundSolution() {
		if (logger.isDebugEnabled()) {
			logger.debug("Solution found:\n" + board);
		}
	}

	private boolean isPossibleMove(final Position newPosCandidate) {
		if (board.isPositionOutOfBounds(newPosCandidate)) {
			return false;
		}

		if (board.isPositionOccupied(newPosCandidate)) {
			return false;
		}

		return true;
	}

	private void clearLastMove(final Position pos) {
		board.removeNumber(pos);
		if (!moves.isEmpty()) {
			moves.remove(moves.size() - 1);
		}
	}

	private List<Solution> findNextMove(final Position startPosition, final int currentNumber, final Position currentPos) {
		List<Solution> solutions = new LinkedList<Solution>();
		logSearchProgress();

		for (Move moveToAttempt : Move.values()) {
			Position newPosCandidate = currentPos.applyMove(moveToAttempt);

			if (isPossibleMove(newPosCandidate)) {
				moves.add(moveToAttempt);
				board.addNumber(newPosCandidate, currentNumber);

				if (board.isFull()) {
					logFoundSolution();
					solutions.add(new Solution(startPosition, moves));
					clearLastMove(newPosCandidate);
				}
				else {
					solutions.addAll(findNextMove(startPosition, currentNumber + 1, newPosCandidate));
				}
			}
		}

		clearLastMove(currentPos);

		return solutions;
	}

	private List<Solution> searchAllSolutionsFromStartingPoint(final Position start) {
		logger.info("Starting from " + start);
		board.clear();
		moves.clear();
		int startingNumber = 1;
		board.addNumber(start, startingNumber);

		return findNextMove(start, startingNumber + 1, start);
	}

	private List<Solution> findSolutionsFromAllColumnsOfRow(final int row) {
		List<Solution> solutions = new LinkedList<Solution>();

		for (int col = 0; col < board.width; col++) {
			solutions.addAll(searchAllSolutionsFromStartingPoint(new Position(col, row)));
		}

		return solutions;
	}

	/**
	 * Find all solutions for the board. The search is started separately from
	 * each position on the board and solutions are ordered by starting
	 * position.
	 * 
	 * @return all found solutions for the board
	 */
	public List<Solution> findSolutionsFromAllPositions() {
		List<Solution> solutions = new LinkedList<Solution>();

		for (int row = 0; row < board.height; row++) {
			solutions.addAll(findSolutionsFromAllColumnsOfRow(row));
		}

		return solutions;
	}

	/**
	 * Find all possible solutions for the board and report the number of found
	 * solutions and the time (in milliseconds) it took to find them.
	 */
	public void reportSolutions() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		List<Solution> solutions = findSolutionsFromAllPositions();
		long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);

		logger.info(String.format("Found a total of %d solutions in %d milliseconds", solutions.size(), duration));
	}

	public static void main(String[] args) {
		// the real board's size is 10x10 but currently the algorithm is fast
		// enough up to a 5x5 board only
		PuzzleSolver solver = new PuzzleSolver(new Board(5, 5));
		solver.reportSolutions();
	}
}
