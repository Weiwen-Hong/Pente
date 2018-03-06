public class RIPOfCode {




  /*
  public int lastcheckWin() {
    if (checkHorizontal() != -1) {
      return checkHorizontal();
    } else if (checkVertical() != -1) {
      return checkVertical();
    } else {
      return checkDiagonal();
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
*/
}
