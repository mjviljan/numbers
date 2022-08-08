package com.lespritdescalier.numberssolver;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * An algorithm for finding all solutions for a number puzzle of given board
 * size. The algorithm is a depth-first search with somewhat optimized search
 * space (attempting to find only unique solutions that are duplicated by
 * mirroring and rotating to generate all the possible solutions).
 *
 * @author <a href="https://github.com/mjviljan">Mika Viljanen</a>
 */
public class PuzzleSolver {
	private final Logger logger = LogManager.getLogger(PuzzleSolver.class);
	private final boolean SHOW_SOLUTION_BOARD = false;

	/**
	 * A puzzle board used to store the state of the progressing search.
	 */
	private final Board board;

	/**
	 * The moves that the current depth-first search has made so far.
	 */
	private final List<Move> moves;

	/**
	 * Found solutions.
	 */
	private final List<Solution> solutions;

	private final HashMap<Position, Integer> solutionsByStartingPoint = new HashMap<>();

	private final Set<Move> neededFirstMovesFromAxisPoint = ImmutableSet.of(Move.NW, Move.N, Move.NE, Move.E, Move.SE);
	private final Move[][] possibleMovesByPoint;

	public PuzzleSolver(final Board board) {
		this.board = board;
		possibleMovesByPoint = new Move[board.width * board.height][];
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

	private void findNextMove(final Position startPosition, final int currentNumber, final Position currentPos, final Move[] movesToAttempt) {
		logSearchProgress();

		for (Move moveToAttempt : movesToAttempt) {
			Position newPosCandidate = currentPos.applyMove(moveToAttempt);

			if (isPossibleMove(newPosCandidate)) {
				moves.add(moveToAttempt);
				board.addNumber(newPosCandidate, currentNumber);

				if (board.isFull()) {
					int oldCount = solutionsByStartingPoint.get(startPosition);
					solutionsByStartingPoint.put(startPosition, oldCount + 1);

					Solution foundSolution = new Solution(startPosition, moves);
					solutions.add(foundSolution);
					if (logger.isDebugEnabled()) {
						logFoundSolution(foundSolution);
					}
					clearLastMove(newPosCandidate);
				} else {
					findNextMove(startPosition, currentNumber + 1, newPosCandidate, possibleMovesByPoint[getCellIndex(newPosCandidate.row, newPosCandidate.col)]);
				}
			}
		}

		clearLastMove(currentPos);
	}

	private void searchAllSolutionsFromStartingPoint(final Position start) {
		logger.info("Starting from {}", start);
		board.clear();
		moves.clear();
		int startingNumber = 1;
		board.addNumber(start, startingNumber);

		solutionsByStartingPoint.put(start, 0);

		Move[] movesToAttempt;
		if (start.col == start.row) {
			movesToAttempt = Arrays.stream(Move.values())
					.filter(neededFirstMovesFromAxisPoint::contains)
					.filter(ImmutableSet.copyOf(possibleMovesByPoint[getCellIndex(start.row, start.col)])::contains)
					.toArray(Move[]::new);
		} else {
			movesToAttempt = possibleMovesByPoint[getCellIndex(start.row, start.col)];
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

		mirrorUniqueSolutions();
		rotateSolutions();
	}

	private void mirrorUniqueSolutions() {
		final List<Solution> mirroredSolutions = new LinkedList<>();

		for (Solution original : solutions) {
			Solution mirrored = original.mirrorDiagonally();

			if (mirrored != null) {
				mirroredSolutions.add(mirrored);

				Position startPosition = mirrored.startPosition;
				Integer oldCount = solutionsByStartingPoint.get(startPosition);
				solutionsByStartingPoint.put(startPosition, oldCount != null ? oldCount + 1 : 1);
			}
		}

		solutions.addAll(mirroredSolutions);
	}

	private Solution rotateSolutionBy90Degrees(Solution original) {
		Solution rotated90Deg = original.rotate(board.width);

		Position startPosition = rotated90Deg.startPosition;
		Integer oldCount = solutionsByStartingPoint.get(startPosition);
		solutionsByStartingPoint.put(startPosition, oldCount != null ? oldCount + 1 : 1);

		return rotated90Deg;
	}

	private void rotateSolutions() {
		final List<Solution> rotatedSolutions = new LinkedList<>();

		boolean isBoardSizeOdd = board.height % 2 != 0;
		int axisRow = board.height / 2;
		int axisCol = board.width / 2;

		for (Solution original : solutions) {
			// If the board is odd in size (e.g. 5x5), skip the solutions on the middle
			// row to avoid duplicates when rotating the solutions.
			// (Otherwise solution on the middle row would be rotated to the upper
			// middle column which already has its solutions.)
			if (isBoardSizeOdd && original.startPosition.row == axisRow) {
				continue;
			}

			Solution rotated90Deg = rotateSolutionBy90Degrees(original);
			rotatedSolutions.add(rotated90Deg);

			Solution rotated180Deg = rotateSolutionBy90Degrees(rotated90Deg);
			rotatedSolutions.add(rotated180Deg);

			// on odd-sized board, skip the middle column also
			// on the last rotation to avoid duplicates
			if (isBoardSizeOdd && original.startPosition.col == axisCol) {
				continue;
			}

			Solution rotated270Deg = rotateSolutionBy90Degrees(rotated180Deg);
			rotatedSolutions.add(rotated270Deg);
		}

		solutions.addAll(rotatedSolutions);
	}

	private void logSolutionsByStartingPoint() {
		StringBuilder sb = new StringBuilder();

		for (int row = 0; row < board.height; row++) {
			sb.append("\n");
			for (int col = 0; col < board.height; col++) {
				Integer sols = solutionsByStartingPoint.get(new Position(col, row));
				sb.append(sols != null ? sols : "0").append("\t");
			}
		}

		logger.info("Solutions by starting point: {}", sb.toString());
	}

	private void precalculateMovesForPoints() {
		for (int row = 0; row < board.height; row++) {
			for (int col = 0; col < board.height; col++) {
				Position pos = new Position(col, row);
				List<Move> possibleMoves = new ArrayList<>();
				for (Move moveCandidate : Move.values()) {
					Position posCandidate = pos.applyMove(moveCandidate);
					if (!board.isPositionOutOfBounds(posCandidate)) {
						possibleMoves.add(moveCandidate);
					}
				}
				possibleMovesByPoint[getCellIndex(row, col)] = possibleMoves.toArray(new Move[possibleMoves.size()]);
			}
		}
	}

	private int getCellIndex(final int row, final int col) {
		return row * board.width + col;
	}

	/**
	 * Find all possible solutions for the board and report the number of found
	 * solutions and the time (in milliseconds) it took to find them.
	 */
	public void findSolutions() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		precalculateMovesForPoints();
		findSolutionsFromUniquePositions();
		stopWatch.stop();
		long duration = stopWatch.getTime();

		logger.info("Found a total of {} solutions in {} milliseconds ({}x{})", solutions.size(), duration, board.width, board.height);

		logSolutionsByStartingPoint();
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public static void main(String[] args) {
		// the real board's size is 10x10 but currently the algorithm is fast
		// enough up to a 5x5 board only
		PuzzleSolver solver = new PuzzleSolver(new Board(5));
		solver.findSolutions();
	}
}
