import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;
public class Board{
	private int dim;
	private int toWin;
	public int[][] board;
	public int[] lastMove, stLastMove;
	private static final int[][] directions = new int[][] {{1,0},{1,1},{0,1},
		{-1,1}};
	private static Random random = new Random();
	private static final int MCNum = 10000;
	private static final int maxSpecialOperationElement = 15;
	private static final int availableSpotDist = 1;

	public Board(int dimension, int toWin){
    this.dim = dimension;
    this.toWin = toWin;
    board = new int[dim][dim];
    lastMove = new int[2];
    stLastMove = new int[2];
    stLastMove[0] = stLastMove[1] = lastMove[0] = lastMove[1] = 4;

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
	 private Board(Board origin) {
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
			System.err.println("Coords is not an empty spot");
			return false;
		}

		stLastMove[0] = lastMove[0];
		stLastMove[1] = stLastMove[1];

    board[row][col] = player;

    lastMove[0] = row;
    lastMove[1] = col;
		return true;
	}



	/**
	 * Used for the AI player, the arguments are the player's last move and
	 * the opponent's last move
 	 */
	public ArrayList<int[]> getAvailableSpots(int row1, int col1, int row2, int
    col2) {
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
		int row2Start = (row2 >= 1) ? row2 - 1 : 0;
		int row2End = (row2 + 1 < dim) ? row2 + 1 : dim - 1;
		int col2Start = (col2 >= 1) ? col2 - 1 : 0;
		int col2End = (col2 + 1 < dim) ? col2 + 1 : dim - 1;
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



	public void printBoard() {
		System.out.println("============================");
		System.out.print("   ");
		for (int i = 0; i < dim; i++) {
			System.out.print(i + "  ");
		}
		System.out.println();
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
		System.out.println("============================");
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

    str += "\n-----------------------------------------------\n";
    return str;
  }



  public int[] AIGetMove() {
	  return AIGetMove(stLastMove, lastMove);
  }


	public int[] AIGetMove(int[] lastAIMove, int[] lastHumanMove) {
	  if (lastAIMove[0] == -1 || lastAIMove[1] == -1) {
	  	lastAIMove[0] = lastAIMove[1] = dim / 2;
		}

	  // get moves around AI and human
		ArrayList<int[]> worklist = this.getAvailableSpots(lastAIMove[0],
      lastAIMove[1], lastHumanMove[0], lastHumanMove[1]);

		int bestMoveIndex = 0;

		for (int possibleMove = 0; possibleMove < worklist.size(); possibleMove++) {

		  // get the studied next move coord
			int[] newMove = {(Integer)worklist.get(possibleMove)[0],
							(Integer)worklist.get(possibleMove)[1]};

			int winTime = 0;

			for (int i = 0; i <= MCNum; i++) {
				int[] win = directWin(1);
			  if (win != null) {
			  	return win;
				}
				winTime += tryMove(this, lastAIMove, lastHumanMove, newMove, 1);
			}

			worklist.get(possibleMove)[2] = winTime;

			// update the best move
			if ((Integer)worklist.get(possibleMove)[2] > (Integer)worklist.get
        (bestMoveIndex)[2]) {
				bestMoveIndex = possibleMove;
			}
		}

		int[] newMove = new int[]{(Integer)worklist.get(bestMoveIndex)[0],
						(Integer)worklist.get(bestMoveIndex)[1]};
		return newMove;
	}




	// see the result of a move without actually making a move on the actual board
	private int tryMove(Board origin, int[] secondToLastMove, int[] lastMove,
											int[] thisMove, int player) {
	  // copy the board
		Board newBoard = new Board(origin);
		// make the move
		newBoard.move(player, thisMove[0], thisMove[1]);

		// check if absolute Win
		int[] directWin = directWin(player);
		if (directWin != null) {
		  newBoard.move(1- player, directWin[0], directWin[1]);
		}
		directWin = directWin(1 - player);
		if (directWin != null) {
			newBoard.move(1 - player, directWin[0], directWin[1]);
		}


		if (newBoard.checkWin() == 0) {
			return -1;
		} else if (newBoard.checkWin() == 1) {
			return 1;
		} else {
		  // get around coords
			ArrayList<int[]> worklist = newBoard.getAvailableSpots
							(secondToLastMove[0], secondToLastMove[1], lastMove[0], lastMove[1]);

			if (worklist.size() == 0) {
			  worklist = newBoard.specialOperatoin(thisMove[0], thisMove[1]);
      }

      if (worklist.size() == 0) {
			  return 0;
      }

      random.setSeed(System.currentTimeMillis());
			int choice = random.nextInt(worklist.size());

			int[] newMove = new int[] {(Integer)worklist.get(choice)[0],
							(Integer)worklist.get(choice)[1]};
			return tryMove(newBoard, lastMove, thisMove, newMove, 1 - player);
		}
	}



	private ArrayList<int[]> specialOperatoin(int row, int col) {
	  HashSet<int[]> sets = new HashSet<int[]>();
	  int count = 0;
	  int bound = 1;

	  int[] upperLeft = new int[] {row - bound, col + bound};
	  int[] lowerRight = new int[] {row + bound, col + bound};

	  int[] boundedPt1, boundedPt2;

	  while (count < maxSpecialOperationElement && (ifInBound(upperLeft) ||
      ifInBound(lowerRight))) {
	    boundedPt1 = toInBound(upperLeft);
	    boundedPt2 = toInBound(lowerRight);

	    for (int j = boundedPt1[0]; j <= boundedPt2[1]; j++) {
	      for (int k = boundedPt1[1]; k <= boundedPt2[1]; k++) {
	        // check if on boarder
          if (Math.abs(row - j) == bound && Math.abs(col - k) == bound) {

            // check if empty
            if(board[j][k] == -1) {
              sets.add(new int[] {j,k});
              count++;
            }

          }
        }
      }


	    // increase bound
      bound++;
      upperLeft = new int[] {row - bound, col + bound};
      lowerRight = new int[] {row + bound, col + bound};
    }

    return new ArrayList<>(sets);
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


  private boolean ifInBound(int row, int col) {
	  if (row < 0 || row >= dim) {
	    return false;
    }

    if (col < 0 || col >= dim) {
	    return false;
    }

    return true;
  }



  private boolean ifInBound(int[] arr) {
	  return ifInBound(arr[0], arr[1]);
  }



  private int[] toInBound(int row, int col) {
	  row = (row >= 0 ? row : 0);
	  row = (row < dim ? row : dim - 1);

	  col = (col >= 0 ? col : 0);
	  col = (col < dim ? col : dim - 1);

	  return new int[] {row, col};
  }



  private int[] toInBound(int[] arr) {
	  return toInBound(arr[0], arr[1]);
  }



  private ArrayList<int[]> getAllPLayerCoord(int player) {
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


	private ArrayList<int[]> getCoordNextTo(int row, int col) {
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



	private int[] directWin(int player) {
	  ArrayList<int[]> allPlayerCoord = getAllPLayerCoord(player);
	  HashSet<int[]> allAvailableMove = new HashSet<>();

	  for (int[] coord : allPlayerCoord) {
	  	ArrayList<int[]> temp = getCoordNextTo(coord[0], coord[1]);

	  	for (int[] thisCoord : temp) {
	  		allAvailableMove.add(thisCoord);
			}
		}

		ArrayList<int[]> allMoves = new ArrayList<>(allAvailableMove);

	  Board testBoard;
	  for (int[] thisCoord : allMoves) {
	  	testBoard = new Board(this);

	  	testBoard.move(player, thisCoord[0], thisCoord[1]);
	  	if (testBoard.checkWin() == player) {
	  		return thisCoord;
			}
		}
		return null;
	}
}
