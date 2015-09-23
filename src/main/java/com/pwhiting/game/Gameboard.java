package com.pwhiting.game;

public class Gameboard<T extends GamePiece> {

	private final BoardPosition[][] boardLayout;
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
	
	public boolean tryMovePiece(int x1, int y1, int x2, int y2) {
		//if ()
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
			return (T) boardLayout[x][y];
		} else {
			return null;
		}
	}
	
	private boolean inRange(int x, int y) {
		return x >= 0 || x <= boardSizeX - 1  || y >= 0 || y <= boardSizeY - 1;
	}
	
	public void setPieceAt(T piece, int x, int y) {
		if (inRange(x, y)) {
		//	boardLayout[x][y] = piece;
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
	
	public static class MoveOutcome {
		
		private final GamePiece resultantPiece;
		private final boolean shouldReplace;
		
		private MoveOutcome(boolean shouldReplace, GamePiece resultantPiece) {
			this.resultantPiece = resultantPiece;
			this.shouldReplace = shouldReplace;
		}
		
		public boolean isValid() {
			return shouldReplace;
		}
		
		public GamePiece getResultantPiece() {
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
		public static MoveOutcome valid(GamePiece piece) {
			return new MoveOutcome(true, piece);
		}
		
	}
	
	/**
	 * An immutable object representing a location on the game board.
	 * 
	 * @author Philip
	 *
	 */
	public static class BoardPosition {
		
		private final GamePiece thePiece;
		private final Color theColor;
		
		public BoardPosition(GamePiece piece, Color color) {
			thePiece = piece;
			theColor = color;
		}
		
		public BoardPosition withColor(Color color) {
			return new BoardPosition(thePiece, color);
		}
		
		public BoardPosition withPiece(GamePiece piece) {
			return new BoardPosition(piece, theColor);
		}
		
	}
	
	public static enum Color {
		RED, BLACK, WHITE
	}
	
	
	
}
