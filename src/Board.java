import java.util.*;
public class Board{
	private int dim;
	private int toWin;
	public int[][] board;

	public Board(int dimension, int toWin){

		this.dim=dimension;
		this.toWin=toWin;
		board = new int[dim][dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				board[i][j] = -1;
			}
		}

	}

	public boolean move(int player, int row, int col) {
		if (row > dim || col > dim) {
			System.err.println("Coords not valid, give a new coords");
			return false;
		}

		if (board[row][col] != -1) {
			System.err.println("Coords is not an empty spot");
			return false;
		}

		board[row][col] = player;
		return true;
	}

	/**
	 * Used for the AI player, the arguments are the player's last move and
	 * the opponent's last move
 	 */
	public ArrayList getAvailableSpots(int row1, int col1, int row2, int col2) {
		ArrayList<ArrayList<Integer>> available = new ArrayList<>();
		int row1Start = (row1 >= 2) ? row1 - 2 : 0;
		int row1End = (row1 + 2 < dim) ? row1 + 2 : dim;
		int col1Start = (col1 >= 2) ? col1 - 2 : 0;
		int col1End = (col1 + 2 < dim) ? col1 + 2 : dim;
		for (int i = row1Start; i <= row1End; i++) {
			for (int j = col1Start; j <= col1End; j++) {
				if (board[i][j] == -1) {
					ArrayList<Integer> coords = new ArrayList<>();
					coords.add(i);
					coords.add(j);
					available.add(coords);
				}
			}
		}

		int row2Start = (row2 >= 2) ? row2 - 2 : 0;
		int row2End = (row2 + 2 < dim) ? row2 + 2 : dim;
		int col2Start = (col2 >= 2) ? col2 - 2 : 0;
		int col2End = (col2 + 2 < dim) ? col2 + 2 : dim;
		for (int i = row2Start; i <= row2End; i++) {
			for (int j = col2Start; j <= col2End; j++) {
				if (board[i][j] == -1) {
					ArrayList<Integer> coords = new ArrayList<>();
					coords.add(i);
					coords.add(j);
					available.add(coords);
				}
			}
		}

		return available;
	}

	public void printBoard() {
		System.out.println("======================");
		System.out.println("   0  1  2  3  4  5 ");
		for (int i = 0; i < dim; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < dim; j++) {
				if (board[i][j] == 0) {
					System.out.print(" O ");
				} else if (board[i][j] == 1) {
					System.out.print(" X ");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("======================");
	}

	public int checkWin() {
		if (checkHorizontal() != -1) {
			return checkHorizontal();
		} else if (checkVertical() != -1) {
			return checkVertical();
		} else {
			return checkVertical();
		}
	}

	private int checkHorizontal() {

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j <= dim - toWin; j++) {

				switch (toWin) {
					case (3):
						if (board[i][j] != -1 &&
										board[i][j] == board[i][j + 1] &&
										board[i][j + 1] == board[i][j + 2]) {
							return board[i][j];
						}
						break;
					case (4):
						if (board[i][j] != -1 &&
										board[i][j] == board[i][j + 1] &&
										board[i][j + 1] == board[i][j + 2] &&
										board[i][j + 2] == board[i][j + 3]) {
							return board[i][j];
						}
						break;
					case (5):
						if (board[i][j] != -1 &&
										board[i][j] == board[i][j + 1] &&
										board[i][j + 1] == board[i][j + 2] &&
										board[i][j + 2] == board[i][j + 3] &&
										board[i][j + 3] == board[i][j + 4]) {
							return board[i][j];
						}
				}
			}
		}
		return -1;
	}


	private int checkVertical() {

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j <= dim - toWin; j++) {

				switch (toWin) {
					case (3):
						if (board[i][j] != -1 &&
										board[j][i] == board[j + 1][i] &&
										board[j + 1][i] == board[j + 2][i]) {
							return board[j][i];
						}
						break;
					case (4):
						if (board[i][j] != -1 &&
										board[j][i] == board[j + 1][i] &&
										board[j + 1][i] == board[j + 2][i] &&
										board[j + 2][i] == board[j + 3][i]) {
							return board[i][j];
						}
						break;
					case (5):
						if (board[i][j] != -1 &&
										board[j][i] == board[j + 1][i] &&
										board[j + 1][i] == board[j + 2][i] &&
										board[j + 2][i] == board[j + 3][i] &&
										board[j + 3][i] == board[j + 4][i]) {
							return board[i][j];
						}
				}
			}
		}
		return -1;
	}


	private int checkDiagonal() {

		// check for upperleft to lowerright
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j <= dim - toWin; j++) {

				switch (toWin) {
					case (3):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j + 1] &&
											board[i + 1][j + 1] == board[i + 2][j + 2]) {
								return board[i][j];
							}
						}
						break;
					case (4):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j + 1] &&
											board[i + 1][j + 1] == board[i + 2][j + 2] &&
											board[i + 2][j + 2] == board[i + 3][j + 3]) {
								return board[i][j];
							}
						}
						break;
					case (5):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j + 1] &&
											board[i + 1][j + 1] == board[i + 2][j + 2] &&
											board[i + 2][j + 2] == board[i + 3][j + 3] &&
											board[i + 3][j + 3] == board[i + 4][j + 4]) {
								return board[i][j];
							}
						}
				}
			}
		}

		// check for upperright to lowerleft
		for (int i = 0; i < dim; i++) {
			for (int j = dim - 1; j >= toWin; j--) {

				switch (toWin) {
					case (3):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j - 1] &&
											board[i + 1][j - 1] == board[i + 2][j - 2]) {
								return board[i][j];
							}
						}
						break;
					case (4):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j - 1] &&
											board[i + 1][j - 1] == board[i + 2][j - 2] &&
											board[i + 2][j - 2] == board[i + 3][j - 3]) {
								return board[i][j];
							}
						}
						break;
					case (5):
						if (i <= dim - toWin) {
							if (board[i][j] != -1 &&
											board[i][j] == board[i + 1][j - 1] &&
											board[i + 1][j - 1] == board[i + 2][j - 2] &&
											board[i + 2][j - 2] == board[i + 3][j - 3] &&
											board[i + 3][j - 3] == board[i + 4][j - 4]) {
								return board[i][j];
							}
						}
				}
			}
		}

		return -1;
	}

}
