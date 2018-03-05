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
		if (checkHorizontal(checkBoard) != -1) {
			return checkHorizontal(checkBoard);
		} else if (checkVertical(checkBoard) != -1) {
			return checkVertical(checkBoard);
		} else if (checkDiagonal(checkBoard) != -1) {
			return checkVertical(checkBoard);
		}
		return -1;
	}

	public int checkHorizontal(Board checkBoard) {

		for (int i = 0; i < checkBoard.dim; i++) {
			for (int j = 0; j <= checkBoard.dim - checkBoard.toWin; j++) {

				switch (checkBoard.toWin) {
					case (3):
						if (checkBoard.board[i][j] == checkBoard.board[i][j + 1] &&
										checkBoard.board[i][j + 1] == checkBoard.board[i][j + 2]) {
							return checkBoard.board[i][j];
						}
						break;
					case (4):
						if (checkBoard.board[i][j] == checkBoard.board[i][j + 1] &&
										checkBoard.board[i][j + 1] == checkBoard.board[i][j + 2] &&
										checkBoard.board[i][j + 2] == checkBoard.board[i][j + 3]) {
							return checkBoard.board[i][j];
						}
						break;
					case (5):
						if (checkBoard.board[i][j] == checkBoard.board[i][j + 1] &&
										checkBoard.board[i][j + 1] == checkBoard.board[i][j + 2] &&
										checkBoard.board[i][j + 2] == checkBoard.board[i][j + 3] &&
										checkBoard.board[i][j + 3] == checkBoard.board[i][j + 4]) {
							return checkBoard.board[i][j];
						}
				}
			}
		}
		return -1;
	}


	public int checkVertical(Board checkBoard) {

		for (int i = 0; i < checkBoard.dim; i++) {
			for (int j = 0; j <= checkBoard.dim - checkBoard.toWin; j++) {

				switch (checkBoard.toWin) {
					case (3):
						if (checkBoard.board[j][i] == checkBoard.board[j + 1][i] &&
										checkBoard.board[j + 1][i] == checkBoard.board[j + 2][i]) {
							return checkBoard.board[j][i];
						}
						break;
					case (4):
						if (checkBoard.board[j][i] == checkBoard.board[j + 1][i] &&
										checkBoard.board[j + 1][i] == checkBoard.board[j + 2][i] &&
										checkBoard.board[j + 2][i] == checkBoard.board[j + 3][i]) {
							return checkBoard.board[i][j];
						}
						break;
					case (5):
						if (checkBoard.board[j][i] == checkBoard.board[j + 1][i] &&
										checkBoard.board[j + 1][i] == checkBoard.board[j + 2][i] &&
										checkBoard.board[j + 2][i] == checkBoard.board[j + 3][i] &&
										checkBoard.board[j + 3][i] == checkBoard.board[j + 4][i]) {
							return checkBoard.board[i][j];
						}
				}
			}
		}
		return -1;
	}


	public int checkDiagonal(Board checkBoard) {

		// check for upperleft to lowerright
		for (int i = 0; i < checkBoard.dim; i++) {
			for (int j = 0; j <= checkBoard.dim - checkBoard.toWin; j++) {

				switch (checkBoard.toWin) {
					case (3):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j + 1] &&
											checkBoard.board[i + 1][j + 1] == checkBoard.board[i + 2][j + 2]) {
								return checkBoard.board[i][j];
							}
						}
						break;
					case (4):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j + 1] &&
											checkBoard.board[i + 1][j + 1] == checkBoard.board[i + 2][j + 2] &&
											checkBoard.board[i + 2][j + 2] == checkBoard.board[i + 3][j + 3]) {
								return checkBoard.board[i][j];
							}
						}
						break;
					case (5):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j + 1] &&
											checkBoard.board[i + 1][j + 1] == checkBoard.board[i + 2][j + 2] &&
											checkBoard.board[i + 2][j + 2] == checkBoard.board[i + 3][j + 3] &&
											checkBoard.board[i + 3][j + 3] == checkBoard.board[i + 4][j + 4]) {
								return checkBoard.board[i][j];
							}
						}
				}
			}
		}

		// check for upperright to lowerleft
		for (int i = 0; i < checkBoard.dim; i++) {
			for (int j = checkBoard.dim - 1; j >= checkBoard.toWin; j--) {

				switch (checkBoard.toWin) {
					case (3):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j - 1] &&
											checkBoard.board[i + 1][j - 1] == checkBoard.board[i + 2][j - 2]) {
								return checkBoard.board[i][j];
							}
						}
						break;
					case (4):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j - 1] &&
											checkBoard.board[i + 1][j - 1] == checkBoard.board[i + 2][j - 2] &&
											checkBoard.board[i + 2][j - 2] == checkBoard.board[i + 3][j - 3]) {
								return checkBoard.board[i][j];
							}
						}
						break;
					case (5):
						if (i <= checkBoard.dim - checkBoard.toWin) {
							if (checkBoard.board[i][j] == checkBoard.board[i + 1][j - 1] &&
											checkBoard.board[i + 1][j - 1] == checkBoard.board[i + 2][j - 2] &&
											checkBoard.board[i + 2][j - 2] == checkBoard.board[i + 3][j - 3] &&
											checkBoard.board[i + 3][j - 3] == checkBoard.board[i + 4][j - 4]) {
								return checkBoard.board[i][j];
							}
						}
				}
			}
		}

		return -1;
	}

}
