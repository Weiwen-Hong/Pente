import java.util.*;

/**
 * THINGS TO DO:
 *
 * CheckEmpty() in Board.java
 * TryMove return 0 when there is no empty spot
 * When available spot list is empty (nearby spots all occupied), add random
 * empty spots to the available list
 * Record secondToLastMove and lastMove in Pente.java
 * Change checkWin to only checking nearby possible formation of a line (to O(1))
 */
public class Pente {
	public static void main(String[] args) {

		Board board = new Board(10, 5);

		//twoPlayersMode(board);
		playerAIMode(board);

	}

	private static void twoPlayersMode(Board board) {
		while (board.checkWin() == -1) {

		  System.out.println(board);
			int[] coords;
			System.out.println("Player O's turn: (type coords separated by comma)");
			coords = getCoords();
			// in case invalid coords is entered
			while (!board.move(0, coords[0], coords[1])) {
				System.out.println("Player O's turn: (type an non-empty coords again)");
				coords = getCoords();
			}

			System.out.println(board);
			if (board.checkWin() == -1) {
				System.out.println("Player X's turn: (type coords separated by comma)");
				coords = getCoords();
				// in case invalid coords is entered
				while (!board.move(1, coords[0], coords[1])) {
					System.out.println("Player X's turn: (type an non-empty coords again)");
					coords = getCoords();
				}
			} else {
				System.out.println("Player O wins!");
				System.exit(0);
			}
		}
		System.out.println("Player X wins!");
		System.exit(0);
	}


	private static int[] getCoords() {
		Scanner scan = new Scanner(System.in);
		String coords = scan.nextLine();
		String[] toBeInt = coords.split(",");
		int[] returned = new int[]{Integer.parseInt(toBeInt[0]), Integer.parseInt(toBeInt[1])};
		return returned;
	}

	private static void playerAIMode(Board board) {
		int[] AIMove;
		while (board.checkWin() == -1) {

		  System.out.println(board);
			int[] coords;
			System.out.println("Player O's turn: (type coords separated by comma)");
			coords = getCoords();
			// in case invalid coords is entered
			while (!board.move(0, coords[0], coords[1])) {
				System.out.println("Player O's turn: (type an non-empty coords again)");
				coords = getCoords();
			}

			System.out.println(board);
			if (board.checkWin() != -1) {
				System.out.println("Human won!");
				System.exit(0);
			}

			System.out.println("AI Player X's turn: ...");
			AIMove = board.AIGetMove();
			board.move(1, AIMove[0], AIMove[1]);
		}
		System.out.println(board);
		System.out.println("AI won!");
		System.exit(0);
	}

}
