package com.pwhiting.game;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface GamePiece {
	
	/**
	 * Called when a piece is moved to a position.
	 * 
	 * @param piece
	 */
	Gameboard.MoveOutcome onMoveToLocation(Gameboard board, int x, int y, GamePiece piece);

	boolean isValidMove(Gameboard board, int x1, int y1, int x2, int y2);
	
	/**
	 * Return a list of possible move positions for this piece
	 * 
	 * @param board the board to use
	 * @param x current x of this piece
	 * @param y current y of this piece
	 * @return
	 */
	List<Gameboard.Coordinate> getValidMovePositions(Gameboard board, int x, int y);
	
}
