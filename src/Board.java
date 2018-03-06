import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;
public class Board{
	public int dim;
	private int toWin;
	public int[][] board;
	public int[] lastMove, stLastMove;
	private static final int[][] directions = new int[][] {{1,0},{1,1},{0,1},
		{-1,1}};
	private static final int availableSpotDist = 1;

	public Board(int dimension, int toWin){
    this.dim = dimension;
    this.toWin = toWin;
    board = new int[dim][dim];
    lastMove = new int[2];
    stLastMove = new int[2];
    stLastMove[0] = stLastMove[1] = lastMove[0] = lastMove[1] = -1;

    System.out.println("Initialized");
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        board[i][j] = -1;
      }
    }
	}

	/**
	 * Copy constructor
	 * @param origin
	 */
	 public Board(Board origin) {
		this.dim=origin.dim;
		this.toWin=origin.toWin;
		// make a deep copy of the board
		int[][] newBoard = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				newBoard[i][j] = origin.board[i][j];
			}
		}

		lastMove = new int[] {origin.lastMove[0], origin.lastMove[1]};
		stLastMove = new int[] {origin.stLastMove[0], origin.stLastMove[1]};
		board = newBoard;
	}

	public boolean move(int player, int row, int col) {
		if (row > dim || col > dim) {
			System.err.println("Coords not valid, give a new coords");
			return false;
		}

		if (board[row][col] != -1) {
			//System.err.println("Coords is not an empty spot");
			return false;
		}

		stLastMove[0] = lastMove[0];
		stLastMove[1] = lastMove[1];

    board[row][col] = player;

    lastMove[0] = row;
    lastMove[1] = col;
    if (stLastMove[0] == -1) {
    	stLastMove[0] = lastMove[0];
			stLastMove[1] = lastMove[1];
		}
		return true;
	}



	/**
	 * Used for the AI player, the arguments are the player's last move and
	 * the opponent's last move
 	 */
	public ArrayList<int[]> getAvailableSpots(int row2, int col2, int row1, int
    col1) {
	  HashSet<int[]> available = new HashSet<>();

		// calculate and get available spot around first coordinates
    int row1Start = toInBound(row1 - availableSpotDist, col1 -
      availableSpotDist)[0];
    int col1Start = toInBound(row1 - availableSpotDist, col1 -
      availableSpotDist)[1];
    int row1End = toInBound(row1 + availableSpotDist, col1 +
      availableSpotDist)[0];
    int col1End = toInBound(row1 + availableSpotDist, col1 +
      availableSpotDist)[1];


		for (int i = row1Start; i <= row1End; i++) {
			for (int j = col1Start; j <= col1End; j++) {
				if (board[i][j] == -1) {
					int[] coords = new int[] {i, j, 0};
					available.add(coords);
				}
			}
		}

    // calculate and get available spot around first coordinates
		int row2Start = (row2 >= 2) ? row2 - 2 : 0;
		int row2End = (row2 + 2 < dim) ? row2 + 2 : dim - 1;
		int col2Start = (col2 >= 2) ? col2 - 2 : 0;
		int col2End = (col2 + 2 < dim) ? col2 + 2 : dim - 1;
		for (int i = row2Start; i <= row2End; i++) {
			for (int j = col2Start; j <= col2End; j++) {
				if (board[i][j] == -1) {
				  int[] coords = new int[] {i, j, 0};
					available.add(coords);
				}
			}
		}

		return new ArrayList<int[]>(available);
	}




	@Override
  public String toString() {
	  String str = "  | ";
    // print head number
    for (int i = 0; i < dim; i++) {
      str += i + " | ";
    }

    // print body
    for (int row = 0; row < dim; row++)
    {
      str += "\n---------------------------------------------\n";
      str += row + " |";

      for (int column = 0; column < dim; column++)
      {
        if (board[row][column] == 0) {
          str += " O ";
        } else if (board[row][column] == 1) {
          str += " X ";
        } else {
          str += "   ";
        }
        str += "|";
      }
    }

    str += "\n---------------------------------------------\n";
    return str;
  }



	public int checkWin() {
		// no move
		if (lastMove[0] == -1 || lastMove[1] == -1) {
			return -1;
		}

		// set directions
		int currentX, currentY;
		int xIncre, yIncre;
		int count;
		int thePlayer = board[lastMove[0]][lastMove[1]];
		if (thePlayer == -1) {
		  return -1;
    }

		for (int[] increDir : directions) {
			xIncre = increDir[0];
			yIncre = increDir[1];

			currentX = lastMove[0] + xIncre;
			currentY = lastMove[1] + yIncre;
			count = 1;

			// check all in positive
      while (ifInBound(currentX, currentY) && board[currentX][currentY] == thePlayer) {
        currentX += xIncre;
        currentY += yIncre;
        count++;

        if (count >=  toWin) {
          return thePlayer;
        }
      }

      xIncre = -xIncre;
      yIncre = -yIncre;

      currentX = lastMove[0] + xIncre;
      currentY = lastMove[1] + yIncre;

      // check all in negative
      while (ifInBound(currentX, currentY) && board[currentX][currentY] == thePlayer) {
        currentX += xIncre;
        currentY += yIncre;
        count++;
      }

      if (count >=  toWin) {
        return thePlayer;
      }
		}

	  return -1;
  }


  public boolean ifInBound(int row, int col) {
	  if (row < 0 || row >= dim) {
	    return false;
    }

    if (col < 0 || col >= dim) {
	    return false;
    }

    return true;
  }



  public boolean ifInBound(int[] arr) {
	  return ifInBound(arr[0], arr[1]);
  }



  public int[] toInBound(int row, int col) {
	  row = (row >= 0 ? row : 0);
	  row = (row < dim ? row : dim - 1);

	  col = (col >= 0 ? col : 0);
	  col = (col < dim ? col : dim - 1);

	  return new int[] {row, col};
  }



  public int[] toInBound(int[] arr) {
	  return toInBound(arr[0], arr[1]);
  }



  public ArrayList<int[]> getAllPLayerCoord(int player) {
		ArrayList<int[]> list = new ArrayList<>();

		for (int j = 0; j < dim; j++) {
			for (int k = 0; k < dim; k++) {
				if (board[j][k] == player) {
					list.add(new int[] {j,k});
				}
			}
		}

		return list;
	}


	public ArrayList<int[]> getCoordNextTo(int row, int col) {
		HashSet<int[]> available = new HashSet<>();

		// calculate and get available spot around first coordinates
		int row1Start = toInBound(row - availableSpotDist, col -
			availableSpotDist)[0];
		int col1Start = toInBound(row - availableSpotDist, col -
			availableSpotDist)[1];
		int row1End = toInBound(row + availableSpotDist, col +
			availableSpotDist)[0];
		int col1End = toInBound(row + availableSpotDist, col +
			availableSpotDist)[1];


		for (int i = row1Start; i <= row1End; i++) {
			for (int j = col1Start; j <= col1End; j++) {
				if (board[i][j] == -1) {
					int[] coords = new int[] {i, j, 0};
					available.add(coords);
				}
			}
		}

		return new ArrayList<int[]>(available);
	}


}
