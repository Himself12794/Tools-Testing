package com.pwhiting.game;

public class Gameboard<T extends GamePiece> {

	private final BoardPosition<T>[][] boardLayout;
	private final int boardSizeX;
	private final int boardSizeY;
	
	public Gameboard(int size) {
		this(size,size);
	}
	
	public Gameboard(int sizeX, int sizeY) {
		boardLayout = new BoardPosition[sizeX][sizeY];
		boardSizeX = sizeX;
		boardSizeY = sizeY;
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
			T piece = getPieceAt(x1, y1);
			if (piece.isValidMove(this, x1, y1, x2, y2)) {
				MoveOutcome<T> outcome = piece.onMoveToLocation(this, x2, y2, getPieceAt(x2, y2));
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
		
		return boardLayout[x][y] != null;
	}
	
	public T getPieceAt(int x, int y) {
		if (hasPieceAt(x, y)) {
			return boardLayout[x][y].getPiece();
		} else {
			return null;
		}
	}
	
	private boolean inRange(int x, int y) {
		return x >= 0 || x <= boardSizeX - 1  || y >= 0 || y <= boardSizeY - 1;
	}
	
	public boolean setPieceAt(BoardPosition<T> piece, int x, int y) {
		if (inRange(x, y)) {
			boardLayout[x][y] = piece;
			return true;
		}
		return false;
	}
	
	public static class Coordinate {
		
		public final int x;
		public final int y;
		
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static class MoveOutcome<T> {
		
		private final BoardPosition<T> resultantPiece;
		private final boolean shouldReplace;
		
		private MoveOutcome(boolean shouldReplace, BoardPosition<T> resultantPiece) {
			this.resultantPiece = resultantPiece;
			this.shouldReplace = shouldReplace;
		}
		
		public boolean isValid() {
			return shouldReplace;
		}
		
		public BoardPosition<T> getResultantPiece() {
			return resultantPiece;
		}
		
		/**
		 * Sets the outcome as invalid
		 * 
		 * @return
		 */
		public static MoveOutcome invalid() {
			return new MoveOutcome(false, null);
		}
		
		/**
		 * Set the collision as valid.
		 * Give a null value for both pieces to disappear.
		 * 
		 * @param piece the piece to repace
		 * @return
		 */
		public static <T extends GamePiece> MoveOutcome valid(BoardPosition<T> piece) {
			return new MoveOutcome(true, piece);
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
		
		public BoardPosition(T piece, Color color) {
			thePiece = piece;
			theColor = color;
		}
		
		public BoardPosition withColor(Color color) {
			return new BoardPosition(thePiece, color);
		}
		
		public BoardPosition withPiece(T piece) {
			return new BoardPosition(piece, theColor);
		}
		
		public T getPiece() {
			return thePiece;
		}
		
	}
	
	public static enum Color {
		RED, BLACK, WHITE
	}
	
	
	
}
