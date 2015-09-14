package com.pwhiting.util.practice;

import java.util.Arrays;
import java.util.Scanner;

public class EightQueensProblem {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	private static Chessboard readChessboard(Scanner sc) {
		Chessboard board = new Chessboard();
		
		for (int i = 0; i < 8; i++) {
			String row = sc.next();
			board.readRow(row, i);
		}
		
		return board;
		
	}
	
	public static void main(String[] args) {
		
		Chessboard chessboard = readChessboard(SCANNER);	
		
		System.out.println(chessboard);
		System.out.println("Is valid? : " + chessboard.isValid());
		
	}
	
	private static class Chessboard {
		
		private final boolean[][] board = new boolean[8][8];
		
		public void readRow(String row, int rowNum) throws IllegalArgumentException {
			
			if (rowNum >= 8) {
				throw new IllegalArgumentException("Row must be between 0-7 inclusive");
			} else {
				
				char[] chars = row.toCharArray();
				
				for (int i = 0; i < chars.length; i++) {
					if (i >= board.length) break;
					board[rowNum][i] = chars[i] == '*';
				}
				
			}
			
		}
		
		private boolean checkAxes(int x, int y) {
			
			for (int y2 = 0; y2 < board.length; y2++) {
				boolean value = board[x][y2];
				if (y2 != y) {
					if (value) return false;
				}
			}
			
			for (int x2 = 0; x2 < board.length; x2++) {
				boolean value = board[x2][y];
				if (x2 != x) {
					if (value) return false;
				}
				
			}
			
			return true;
			
		}
		
		private boolean checkNegSlope(int x, int y) {
			
			if (x >= 0 && y >= 0) {
				int xT = x;
				int yT = y;
			
				while (yT > 0 && xT > 0) {
					xT--;
					yT--;
				}
				
				while (xT < board.length && yT < board.length) {
					boolean value = board[xT][yT];
					
					if (xT != x && yT != y) {
						if (value) return false;
					}
					
					xT++;
					yT++;
				}
				
			} else {
				throw new ArrayIndexOutOfBoundsException("Either x=" + x + " is out of bounds, or y=" + y + " is.");
			}
			
			return true;
			
		}
		
		private boolean checkPosSlope(int x, int y) {
			
			if (x < board.length + 1 && y >= 0) {
				int xT = x;
				int yT = y;
				
				while (xT < board.length - 1 && yT > 0) {
					xT++;
					yT--;
				}
				
				while (xT > 0 && yT < board.length - 2) {
					boolean value = board[xT][yT];
					
					if (xT != x && yT != y) {
						if (value) return false;
					}
					
					xT--;
					yT++;
				}
				
			} else {
				throw new ArrayIndexOutOfBoundsException("Either x=" + x + " is out of bounds, or y=" + y + " is.");
			}
			
			return true;
			
		}
		
		public boolean isValid() {
			
			for (int i = 0; i < board.length; i++) {
				
				for (int j = 0; j < board[i].length; j++) {
					
					boolean value = board[i][j];
					
					if (value) {
						boolean valid = true;
						
						valid &= checkAxes(i, j);
						valid &= checkPosSlope(i, j);
						valid &= checkNegSlope(i, j);
						
						if (valid) break;
						else return false;
					}
					
				}
				
			}
			
			return true;
			
		}
		
		public String toString() {
			
			StringBuilder value = new StringBuilder();
			
			for (int x = 0; x < board.length; x++) {
				value.append('|');
				for (int y = 0; y < board[x].length; y++) {
					//value.append("(x=" + x + ",y=" + y + ",v=" + board[x][y]);
					value.append(board[x][y] ? 'Q' : ' ');
					value.append('|');
				}
				value.append("\n");
			}
			
			return value.toString();
			
		}
		
	}
	
}