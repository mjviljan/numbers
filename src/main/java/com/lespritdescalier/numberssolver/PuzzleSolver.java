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
	private final boolean SHOW_SOLUTION_BOARD = false;
	private boolean recordSolutions = false;

	/**
	 * A puzzle board used to store the state of the progressing search.
	 */
	private final Board board;

	/**
	 * The moves that the current depth-first search has made so far.
	 */
	private final List<Move> moves;

	/**
	 * Founds solutions. Only recorded if the <code>recordSolutions</code> flag
	 * is set.
	 */
	private final List<Solution> solutions;

	private long solutionCount;

	public PuzzleSolver(final Board board) {
		this.board = board;
		moves = new LinkedList<Move>();
		solutions = new LinkedList<Solution>();
		solutionCount = 0;
	}

	private void logSearchProgress() {
		if (logger.isTraceEnabled()) {
			logger.trace("\n" + board.toString());
		}
	}

	private void logFoundSolution(Solution solution) {
		if (logger.isDebugEnabled()) {
			String toLog = "Solution found:";
			if (SHOW_SOLUTION_BOARD) {
				toLog += "\n" + board;
			} else {
				toLog += solution;
			}
			logger.debug(toLog);
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

	private void findNextMove(final Position startPosition, final int currentNumber, final Position currentPos) {
		logSearchProgress();

		for (Move moveToAttempt : Move.values()) {
			Position newPosCandidate = currentPos.applyMove(moveToAttempt);

			if (isPossibleMove(newPosCandidate)) {
				moves.add(moveToAttempt);
				board.addNumber(newPosCandidate, currentNumber);

				if (board.isFull()) {
					solutionCount++;
					Solution foundSolution = new Solution(startPosition, moves);
					logFoundSolution(foundSolution);
					if (recordSolutions) {
						solutions.add(foundSolution);
					}
					clearLastMove(newPosCandidate);
				}
				else {
					findNextMove(startPosition, currentNumber + 1, newPosCandidate);
				}
			}
		}

		clearLastMove(currentPos);
	}

	private void searchAllSolutionsFromStartingPoint(final Position start) {
		logger.info("Starting from " + start);
		board.clear();
		moves.clear();
		int startingNumber = 1;
		board.addNumber(start, startingNumber);

		findNextMove(start, startingNumber + 1, start);
	}

	private void findSolutionsFromAllColumnsOfRow(final int row) {
		for (int col = 0; col < board.width; col++) {
			searchAllSolutionsFromStartingPoint(new Position(col, row));
		}
	}

	/**
	 * Find all solutions for the board. The search is started separately from
	 * each position on the board and solutions are ordered by starting
	 * position.
	 * 
	 * @return all found solutions for the board
	 */
	public void findSolutionsFromAllPositions() {
		for (int row = 0; row < board.height; row++) {
			findSolutionsFromAllColumnsOfRow(row);
		}
	}

	/**
	 * Find all possible solutions for the board and report the number of found
	 * solutions and the time (in milliseconds) it took to find them.
	 */
	public void reportSolutions() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		findSolutionsFromAllPositions();
		long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);

		logger.info(String.format("Found a total of %d solutions in %d milliseconds", solutionCount, duration));
	}

	public void recordSolutions() {
		recordSolutions = true;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public static void main(String[] args) {
		// the real board's size is 10x10 but currently the algorithm is fast
		// enough up to a 5x5 board only
		PuzzleSolver solver = new PuzzleSolver(new Board(5, 5));
		solver.reportSolutions();
	}
}
