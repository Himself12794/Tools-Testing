package com.pwhiting.game;

import java.util.List;

import com.google.common.collect.Lists;
import com.pwhiting.game.Gameboard.BoardPosition;
import com.pwhiting.game.Gameboard.Coordinate;

public enum ChessPiece implements GamePiece<ChessPiece> {

	KING, ROOK, QUEEN, KNIGHT, PAWN, BISHOP;
	
	@Override
	public boolean isValidMove(Gameboard<ChessPiece> board, BoardPosition<ChessPiece> posA, BoardPosition<ChessPiece> posB) {
		return false;
	}

	@Override
	public List<Coordinate> getValidMovePositions(Gameboard<ChessPiece> board, int x, int y) {
		return Lists.newArrayList();
	}

	@Override
	public Gameboard<ChessPiece>.MoveOutcome onMovePiece(Gameboard<ChessPiece> board, BoardPosition<ChessPiece> pieceA, BoardPosition<ChessPiece> pieceB) {
		return board.generateInvalidOutcome();
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
}
