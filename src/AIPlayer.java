import java.util.*;

public class AIPlayer extends Player{
  private static Random random = new Random();
  private static final int MCNum = 1000;
  private static ArrayList<int[]> worklist;
  private static final boolean ENABLE = false;
  public int[][] board;
  public int[] lastMove, stLastMove;
  private Board gameBoard;
  private int dim;
  private static final int maxSpecialOperationElement = 15;
  
  public AIPlayer(Board board) {
    gameBoard = board;
    this.board = gameBoard.board;
    lastMove = gameBoard.lastMove;
    stLastMove = gameBoard.stLastMove;
    dim = board.dim;
  }
  
  @Override
  int[] getMove() {
    return new int[0];
  }
  
  @Override
  int getSigil() {
    return 1;
  }
  
  public int[] AIGetMove() {
    return AIGetMove(stLastMove, lastMove);
  }
  
  
  public int[] AIGetMove(int[] lastAIMove, int[] lastHumanMove) {
    if (lastAIMove[0] == -1 || lastAIMove[1] == -1) {
      lastAIMove[0] = lastAIMove[1] = dim / 2;
    }
    
    // get moves around AI and human
    worklist = gameBoard.getAvailableSpots(lastHumanMove[0],
      lastHumanMove[1], lastAIMove[0], lastAIMove[1]);
    
    // add necessary defense to the list
    int[] defense = directWin(gameBoard, 0);
    if (defense != null) {
      return defense;
    }
    
    int[] win = directWin(gameBoard, 1);
    if (win != null) {
      return win;
    }
    
    if (ENABLE) {
      int[] almostDirectWin = almostDirectWin(gameBoard, 1);
      if (almostDirectWin != null) {
        return almostDirectWin;
      }
    }
    
    int bestMoveIndex = 0;
    
    for (int possibleMove = 0; possibleMove < worklist.size(); possibleMove++) {
      
      // get the studied next move coord
      int[] newMove = {(Integer)worklist.get(possibleMove)[0],
        (Integer)worklist.get(possibleMove)[1]};
      
      int winTime = 0;
      
      for (int i = 0; i <= MCNum; i++) {
        winTime += tryMove(new Board(gameBoard), lastAIMove, lastHumanMove, newMove,
          1, 1);
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
  private int tryMove(Board newBoard, int[] secondToLastMove, int[] lastMove,
                      int[] thisMove, int player, int loopDepth) {
    // make the move
    newBoard.move(player, thisMove[0], thisMove[1]);
    
    // check if absolute Win, add weight corresponding to depth
    int[] directWin = directWin(newBoard, player);
    if (directWin != null) {
      if (player == 1) {
        if (loopDepth < 5) {
          // ai will win
          return 1 * (6 - loopDepth);
        } else {
          return 1;
        }
      } else if (loopDepth < 6) {
				/*if (loopDepth < 3) {
					worklist.add(directWin);
				}*/
        return -1 * (7 - loopDepth);
      } else {
        return -1;
      }
    }
    
    directWin = directWin(newBoard, 1 - player);
    if (directWin != null) {
      if (player == 1) {
        if (loopDepth < 6) {
          // human will win
          return -1 * (7 - loopDepth);
        } else {
          return -1;
        }
      } else if (loopDepth < 5) {
        // ai will win
        return 1 * (6 - loopDepth);
      } else {
        return 1;
      }
    }
    
    
    if (newBoard.checkWin() == 0) {
      return -1;
    } else if (newBoard.checkWin() == 1) {
      return 1;
    } else {
      // get around coords
      ArrayList<int[]> worklist = newBoard.getAvailableSpots
        (lastMove[0], lastMove[1],
          secondToLastMove[0], secondToLastMove[1]);
      
      if (worklist.size() == 0) {
        worklist = specialOperatoin(newBoard, thisMove[0], thisMove[1]);
      }
      
      if (worklist.size() == 0) {
        return 0;
      }
      
      random.setSeed(System.currentTimeMillis());
      int choice = random.nextInt(worklist.size());
      
      int[] newMove = new int[] {(Integer)worklist.get(choice)[0],
        (Integer)worklist.get(choice)[1]};
      return tryMove(newBoard, lastMove, thisMove, newMove, 1 - player, loopDepth + 1);
    }
  }
  
  
  
  
  public int[] almostDirectWin(Board board, int player) {
    ArrayList<int[]> allPlayerCoord = board.getAllPLayerCoord(1 - player);
    HashSet<int[]> allAvailableMove = new HashSet<>();
    
    for (int[] coord : allPlayerCoord) {
      ArrayList<int[]> temp = board.getCoordNextTo(coord[0], coord[1]);
      allAvailableMove.addAll(temp);
    }
    
    ArrayList<int[]> allMoves = new ArrayList<>(allAvailableMove);
    
    
    Board testBoard;
    int[] thisGame = null;
    
    for (int[] thisCoord : allMoves) {
      // find the coord that if the ai fails to occupy, will result in a absolute loss
      testBoard = new Board(board);
      
      testBoard.move(1 - player, thisCoord[0], thisCoord[1]);
      ArrayList<int[]> allMovesOriginal = testBoard.getAllPLayerCoord(1 - player);
      HashSet<int[]> allMoves2 = new HashSet<>(allMovesOriginal);
      
      boolean defenseUseless = true;
      
      for (int[] defenseCoord : allMoves2) {
        // a good thisCoord should have defenseUseless true throughout the for loop
        testBoard.move(player, defenseCoord[0], defenseCoord[1]);
        thisGame = directWin(testBoard, 1 - player);
        if (thisGame == null) {
          defenseUseless = false;
        }
        if (thisGame != null) {
          //defenseUseless = false;
        }
      }
      
      if (defenseUseless) {
        return thisCoord;
      }
    }
    return null;
  }
  
  
  
  public ArrayList<int[]> specialOperatoin(Board board, int row, int col) {
    HashSet<int[]> sets = new HashSet<int[]>();
    int count = 0;
    int bound = 1;
    
    int[] upperLeft = new int[] {row - bound, col + bound};
    int[] lowerRight = new int[] {row + bound, col + bound};
    
    int[] boundedPt1, boundedPt2;
    
    while (count < maxSpecialOperationElement && (board.ifInBound(upperLeft) ||
      board.ifInBound(lowerRight))) {
      boundedPt1 = board.toInBound(upperLeft);
      boundedPt2 = board.toInBound(lowerRight);
      
      for (int j = boundedPt1[0]; j <= boundedPt2[1]; j++) {
        for (int k = boundedPt1[1]; k <= boundedPt2[1]; k++) {
          // check if on boarder
          if (Math.abs(row - j) == bound && Math.abs(col - k) == bound) {
            
            // check if empty
            if(board.board[j][k] == -1) {
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
  
  public int[] directWin(Board board, int player) {
    ArrayList<int[]> allPlayerCoord = board.getAllPLayerCoord(player);
    HashSet<int[]> allAvailableMove = new HashSet<>();
    
    for (int[] coord : allPlayerCoord) {
      ArrayList<int[]> temp = board.getCoordNextTo(coord[0], coord[1]);
      allAvailableMove.addAll(temp);
    }
    
    ArrayList<int[]> allMoves = new ArrayList<>(allAvailableMove);
    
    Board testBoard;
    for (int[] thisCoord : allMoves) {
      testBoard = new Board(board);
      
      testBoard.move(player, thisCoord[0], thisCoord[1]);
      if (testBoard.checkWin() == player) {
        return thisCoord;
      }
    }
    return null;
  }
  
  
}
