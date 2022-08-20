package com.lespritdescalier.numberssolver;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


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

	private final int boardSize;
	private final int boardWidth;
	private final int boardHeight;

	private final List<Solution> solutions;

	private final HashMap<Position, Integer> solutionsByStartingPoint = new HashMap<>();

	private final Move[][] possibleMovesByPoint;

	public PuzzleSolver(final int boardSize) {
		this.boardSize = boardSize;
		this.boardWidth = boardSize;
		this.boardHeight = boardSize;
		possibleMovesByPoint = new Move[boardWidth * boardHeight][];
		solutions = new LinkedList<>();
	}

	protected List<Position> getUniqueSolutionStartingPoints() {
		final List<Position> startingPoints = new LinkedList<>();

		int maxCol = (boardWidth - 1) / 2;
		int maxRow = (boardHeight - 1) / 2;

		for (int row = 0; row <= maxRow; row++) {
			for (int col = row; col <= maxCol; col++) {
				startingPoints.add(new Position(col, row));
			}
		}

		return startingPoints;
	}

	/**
	 * Find all solutions for the board. The search is started separately from
	 * each given position on the board and solutions are ordered by starting
	 * position.
	 */
	public void findSolutionsFromPositions(List<Position> startingPoints) {
		for (Position position : startingPoints) {
			SolverForStartingPoint positionSolver = new SolverForStartingPoint(position, boardSize, possibleMovesByPoint);
			List<Solution> solutionsFromPosition = positionSolver.searchSolutions();
			solutions.addAll(solutionsFromPosition);
			solutionsByStartingPoint.put(position, solutionsFromPosition.size());
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
		Solution rotated90Deg = original.rotate(boardWidth);

		Position startPosition = rotated90Deg.startPosition;
		Integer oldCount = solutionsByStartingPoint.get(startPosition);
		solutionsByStartingPoint.put(startPosition, oldCount != null ? oldCount + 1 : 1);

		return rotated90Deg;
	}

	private void rotateSolutions() {
		final List<Solution> rotatedSolutions = new LinkedList<>();

		boolean isBoardSizeOdd = boardHeight % 2 != 0;
		int axisRow = boardHeight / 2;
		int axisCol = boardWidth / 2;

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

		for (int row = 0; row < boardHeight; row++) {
			sb.append("\n");
			for (int col = 0; col < boardWidth; col++) {
				Integer sols = solutionsByStartingPoint.get(new Position(col, row));
				sb.append(sols != null ? sols : "0").append("\t");
			}
		}

		logger.info("Solutions by starting point: {}", sb.toString());
	}

	private void precalculateMovesForPoints() {
		final Board board = new Board(boardSize);
		for (int row = 0; row < boardHeight; row++) {
			for (int col = 0; col < boardWidth; col++) {
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
		return row * boardWidth + col;
	}

	/**
	 * Find all possible solutions for the board and report the number of found
	 * solutions and the time (in milliseconds) it took to find them.
	 */
	public void findSolutions() {
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		precalculateMovesForPoints();
		final List<Position> uniqueStartingPoints = getUniqueSolutionStartingPoints();
		findSolutionsFromPositions(uniqueStartingPoints);
		stopWatch.stop();
		long duration = stopWatch.getTime();

		logger.info("Found a total of {} solutions in {} milliseconds ({}x{})", solutions.size(), duration, boardWidth, boardHeight);
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public static void main(String[] args) {
		// the real board's size is 10x10 but currently the algorithm is fast
		// enough up to a 5x5 board only
		final PuzzleSolver solver = new PuzzleSolver(5);
		solver.findSolutions();
		solver.logSolutionsByStartingPoint();
	}
}
