import java.util.*;

public class Pente {


	public class Board{
		private int dim;
		private int toWin;
		private int[][] board;

		public Board(int dimension, int toWin){

			board = new int[dim][];

			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					board[i][j] = -1;
				}
			}

			this.dim=dim;
			this.toWin=toWin;
		}

		public int checkWin(Board checkBoard) {
			return checkHorizontal(checkBoard) || checkVertical(checkBoard) ||
				checkDiagonal(checkBoard);
		}

		public int checkHorizontal(Board checkBoard) {

			for (int i = 0; i < checkBoard.dim; i++) {
				for (int j = 0; j < checkBoard.dim - checkBoard.toWin; j++) {

					switch(checkBoard.toWin) {
						case(3):
							return checkBoard.board[i][j] == checkBoard.board[i][j+1]
					}
				}
			}
		}

	}



}
