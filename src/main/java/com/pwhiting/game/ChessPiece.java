package com.pwhiting.game;

import java.util.List;

import com.google.common.collect.Lists;

@SuppressWarnings("rawtypes")
public enum ChessPiece implements GamePiece {

	KING, ROOK, QUEEN, KNIGHT, PAWN, BISHOP;
	
	public boolean isValidMove(Gameboard board, int x, int y, int x2, int y2) {
		return false;
	}

	@Override
	public List<Gameboard.Coordinate> getValidMovePositions(Gameboard board, int x, int y) {
		return Lists.newArrayList();
	}

	@Override
	public Gameboard.MoveOutcome onMoveToLocation(Gameboard board, int x, int y, GamePiece piece) {
		return Gameboard.MoveOutcome.invalid();
	}
	
}
