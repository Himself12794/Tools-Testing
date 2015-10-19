package com.pwhiting.game;

import java.util.List;

import com.pwhiting.game.Gameboard.BoardPosition;

public interface GamePiece<T extends GamePiece<?>> {
	
	/**
	 * Called when a piece is moved to a position. If returned as valid, position automagically 
	 * updated with resultant piece. Unless invalid, any changes done to location will be
	 * overwritten.
	 * 
	 * @param piece
	 */
	Gameboard<T>.MoveOutcome onMovePiece(Gameboard<T> board, BoardPosition<T> pieceA, BoardPosition<T> pieceB);

	boolean isValidMove(Gameboard<T> board, BoardPosition<T> posA, BoardPosition<T> posB);
	
	/**
	 * Return a list of possible move positions for this piece
	 * 
	 * @param board the board to use
	 * @param x current x of this piece
	 * @param y current y of this piece
	 * @return
	 */
	List<Gameboard.Coordinate> getValidMovePositions(Gameboard<T> board, int x, int y);
	
}
