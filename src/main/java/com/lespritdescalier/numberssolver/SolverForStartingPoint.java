package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Callable;

public class SolverForStartingPoint implements Callable<List<Solution>> {
	private final Logger logger = LogManager.getLogger(SolverForStartingPoint.class);
	private final boolean SHOW_SOLUTION_BOARD = false;

	/**
	 * A puzzle board used to store the state of the progressing search.
	 */
	private final Board board;

	private final Position startPosition;

	/**
	 * The moves that the current depth-first search has made so far.
	 */
	private final List<Move> moves;

	/**
	 * Found solutions.
	 */
	private final List<Solution> solutions;

	private final Set<Move> neededFirstMovesFromAxisPoint = ImmutableSet.of(Move.NW, Move.N, Move.NE, Move.E, Move.SE);
	private final Move[][] possibleMovesByPoint;

	public SolverForStartingPoint(final Position startPosition, final int boardSize, final Move[][] possibleMovesByPoint) {
		this.startPosition = startPosition;
		this.board = new Board(boardSize);
		this.possibleMovesByPoint = possibleMovesByPoint;
		moves = new LinkedList<>();
		solutions = new LinkedList<>();
	}

	private void logSearchProgress() {
		if (logger.isTraceEnabled()) {
			logger.trace("\n{}", board.toString());
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
		return !board.isPositionOccupied(newPosCandidate);
	}

	private void clearLastMove(final Position pos) {
		board.removeNumber(pos);
		if (!moves.isEmpty()) {
			moves.remove(moves.size() - 1);
		}
	}

	private void findNextMove(final int currentNumber, final Position currentPos, final Move[] movesToAttempt) {
		logSearchProgress();

		for (Move moveToAttempt : movesToAttempt) {
			Position newPosCandidate = currentPos.applyMove(moveToAttempt);

			if (isPossibleMove(newPosCandidate)) {
				moves.add(moveToAttempt);
				board.addNumber(newPosCandidate, currentNumber);

				if (board.isFull()) {
					Solution foundSolution = new Solution(startPosition, moves);
					solutions.add(foundSolution);
					if (logger.isDebugEnabled()) {
						logFoundSolution(foundSolution);
					}
					clearLastMove(newPosCandidate);
				} else {
					findNextMove(currentNumber + 1, newPosCandidate, possibleMovesByPoint[getCellIndex(newPosCandidate.row, newPosCandidate.col)]);
				}
			}
		}

		clearLastMove(currentPos);
	}

	public List<Solution> searchSolutions() {
		logger.info("Starting from {}", startPosition);
		board.clear();
		moves.clear();
		int startingNumber = 1;
		board.addNumber(startPosition, startingNumber);

		Move[] movesToAttempt;
		if (startPosition.col == startPosition.row) {
			movesToAttempt = Arrays.stream(Move.values())
					.filter(neededFirstMovesFromAxisPoint::contains)
					.filter(ImmutableSet.copyOf(possibleMovesByPoint[getCellIndex(startPosition.row, startPosition.col)])::contains)
					.toArray(Move[]::new);
		} else {
			movesToAttempt = possibleMovesByPoint[getCellIndex(startPosition.row, startPosition.col)];
		}
		findNextMove(startingNumber + 1, startPosition, movesToAttempt);

		return solutions;
	}

	private int getCellIndex(final int row, final int col) {
		return row * board.width + col;
	}

	@Override
	public List<Solution> call() {
		return this.searchSolutions();
	}
}
