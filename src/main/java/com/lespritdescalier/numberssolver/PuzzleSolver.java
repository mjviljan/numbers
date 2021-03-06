package com.lespritdescalier.numberssolver;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;


/**
 * An algorithm for finding all solutions for a number puzzle of given board
 * size. The algorithm is a depth-first search with somewhat optimized search
 * space (attempting to find only unique solutions that are duplicated by
 * rotating to generate all of the possible solutions).
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
		moves = new LinkedList<>();
		solutions = new LinkedList<>();
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

	private void findNextMove(final Position startPosition, final int currentNumber, final Position currentPos, final Move[] movesToAttempt) {
		logSearchProgress();

		for (Move moveToAttempt : movesToAttempt) {
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
				} else {
					findNextMove(startPosition, currentNumber + 1, newPosCandidate, Move.values());
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

		Move[] movesToAttempt;
		if (start.col == start.row) {
			movesToAttempt = new Move[]{Move.N, Move.NE, Move.E, Move.SE};
		} else {
			movesToAttempt = Move.values();
		}
		findNextMove(start, startingNumber + 1, start, movesToAttempt);
	}

	protected List<Position> getUniqueSolutionStartingPoints() {
		final List<Position> startingPoints = new LinkedList<>();

		int maxCol = (board.width - 1) / 2;
		int maxRow = (board.height - 1) / 2;

		for (int row = 0; row <= maxRow; row++) {
			for (int col = row; col <= maxCol; col++) {
				startingPoints.add(new Position(col, row));
			}
		}

		return startingPoints;
	}

	/**
	 * Find all solutions for the board. The search is started separately from
	 * each position on the board and solutions are ordered by starting
	 * position.
	 */
	public void findSolutionsFromUniquePositions() {
		final List<Position> startingPoints = getUniqueSolutionStartingPoints();
		for (Position position : startingPoints) {
			searchAllSolutionsFromStartingPoint(position);
		}
	}

	/**
	 * Find all possible solutions for the board and report the number of found
	 * solutions and the time (in milliseconds) it took to find them.
	 */
	public void reportSolutions() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		findSolutionsFromUniquePositions();
		stopWatch.stop();
		long duration = stopWatch.getTime();

		logger.info(String.format("Found a total of %d solutions in %d milliseconds (%dx%d)", solutionCount, duration, board.width, board.height));
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
		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.reportSolutions();
	}
}
