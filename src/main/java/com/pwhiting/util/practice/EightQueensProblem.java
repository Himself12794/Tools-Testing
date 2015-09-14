package com.pwhiting.util.practice;

import java.util.Scanner;

public class EightQueensProblem {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	private static ChessBoard readChessboard(Scanner sc) {
		return new ChessBoard();
	}
	
	public static void main(String[] args) {
		
		ChessBoard chessboard = readChessboard(SCANNER);	
				
	}
	
	private static class ChessBoard {
		
		private final boolean[][] board = new boolean[8][8];
		
		public void readRow(String row, int rowNum) throws IllegalArgumentException {
			
			if (rowNum >= 8) {
				throw new IllegalArgumentException("Row must be between 0-7 inclusive");
			} else {
				
				char[] chars = row.toCharArray();
				
				for (int i = 0; i < chars.length; i++) {
					board[rowNum][i] = chars[i] == '*';
				}
				
			}
			
		}
		
		public boolean isValid() {
			
			for (int i = 0; i < board.length; i++) {
				
				for (int j = 0; j < board[i].length; j++) {
					
				}
				
			}
			
			return false;
			
		}
		
	}
	
}