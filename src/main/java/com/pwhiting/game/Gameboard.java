package com.pwhiting.game;

import java.awt.Color;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

/**
 * A game board holding Game Pieces
 * 
 * @author Philip
 *
 * @param <T>
 */
public class Gameboard<T extends GamePiece<T>> implements Iterable<Gameboard.BoardPosition<T>>{

	private final BoardPosition<T>[][] boardLayout;
	private final int boardSizeX;
	private final int boardSizeY;
	
	public Gameboard(int size) {
		this(size,size);
	}
	
	@SuppressWarnings("unchecked")
	public Gameboard(int sizeX, int sizeY) {
		boardLayout = new BoardPosition[sizeX][sizeY];
		boardSizeX = sizeX;
		boardSizeY = sizeY;
		populateEmptyBoard();
	}
	
	private void populateEmptyBoard() {
		
		for (int i = 0; i < boardLayout.length; i++) {
			
			for (int j = 0; j < boardLayout[i].length; ++j) {
				BoardPosition<T> bp = new BoardPosition<T>();
				bp.xPos = i;
				bp.yPos = j;
				boardLayout[i][j] = bp;
			}
			
		}
	}
	
	/**
	 * Unlike set piece, this performs checks and analysis first.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public boolean tryMovePiece(int x1, int y1, int x2, int y2) {
		if (hasPieceAt(x1, y1)) {
			T piece = getPosition(x1, y1).getPiece();
			if (piece.isValidMove(this, getPosition(x1, y1), getPosition(x2, y2))) {
				MoveOutcome outcome = piece.onMovePiece(this, getPosition(x1, x2), getPosition(x2, y2));
				if (outcome.isValid()) {
					setPieceAt(null, x1, y1);
					return setPieceAt(outcome.resultantPiece, x2, y2);
				}
			}
		}
		return false;
	}
	
	public boolean hasPieceAt(int x, int y) {
		
		if (!inRange(x, y)) {
			return false;
		}
		
		return !boardLayout[x][y].isEmpty();
	}
	
	public BoardPosition<T> getPosition(int x, int y) {
		return boardLayout[x][y];
	}
	
	protected boolean inRange(int x, int y) {
		return x >= 0 || x <= boardSizeX - 1  || y >= 0 || y <= boardSizeY - 1;
	}
	
	public boolean setPieceAt(BoardPosition<T> piece, int x, int y) {
		if (inRange(x, y)) {
			BoardPosition<T> bp = piece == null ? new BoardPosition<T>() : piece;
			bp.xPos = x;
			bp.yPos = y;
			boardLayout[x][y] = bp;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the outcome as invalid
	 * 
	 * @return
	 */
	public MoveOutcome generateInvalidOutcome() {
		return new MoveOutcome(false, null);
	}
	
	/**
	 * Set the collision as valid.
	 * Give a null value for both pieces to disappear.
	 * 
	 * @param piece the piece to repace
	 * @return
	 */
	public MoveOutcome generateValidOutcome(boolean shouldReplace, BoardPosition<T> resultantPiece) {
		return new MoveOutcome(shouldReplace, resultantPiece);
	}

	@Override
	public Iterator<BoardPosition<T>> iterator() {
		List<BoardPosition<T>> helper = Lists.newArrayList();
		for (BoardPosition<T>[] bp : boardLayout) {
			Collections.addAll(helper, bp);
		}
		return helper.iterator();
	}
	
	public class MoveOutcome {
		
		private final BoardPosition<T> resultantPiece;
		private final boolean shouldReplace;
		
		public MoveOutcome(boolean shouldReplace, BoardPosition<T> resultantPiece) {
			this.resultantPiece = resultantPiece;
			this.shouldReplace = shouldReplace;
		}
		
		public boolean isValid() {
			return shouldReplace;
		}
		
		public BoardPosition<T> getResultantPiece() {
			return resultantPiece;
		}
		
	}
	
	/**
	 * Represents the piece, along with some meta-data.
	 * 
	 * @author Philip
	 *
	 */
	public static class BoardPosition<T> {
		
		private final T thePiece;
		private final Color theColor;
		private final boolean isEmpty;
		int xPos;
		int yPos;
		
		public BoardPosition() {
			thePiece = null;
			theColor = null;
			isEmpty = true;
		}
		
		public BoardPosition(T piece, Color black) {
			thePiece = piece;
			theColor = black;
			isEmpty = false;
		}
		
		public BoardPosition<T> withColor(Color color) {
			return new BoardPosition<T>(thePiece, color);
		}
		
		public BoardPosition<T> withPiece(T piece) {
			return new BoardPosition<T>(piece, theColor);
		}
		
		/**
		 * The associated piece. May be null.
		 * 
		 * @return
		 */
		public @Nullable T getPiece() {
			return thePiece;
		}
		
		public boolean isEmpty() {
			return isEmpty;
		}
		
		public String toString() {
			return !isEmpty ? "Piece: " + thePiece + ", Color: " + theColor.getRGB() : "Empty Position";
		}
		
	}
	
	public static class Coordinate {
		
		public final int x;
		public final int y;
		
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	
}
