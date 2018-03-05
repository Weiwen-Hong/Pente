import java.util.*;

public class Pente {
	public static void main(String[] args) {
		Board board = new Board(6,3);
		while (board.checkWin() == -1) {
			board.printBoard();
			int[] coords;
			System.out.println("Player O's turn: (type coords separated by comma)");
			coords = getCoords();
			while (!board.move(0, coords[0], coords[1])) {
				System.out.println("Player O's turn: (type an non-empty coords again)");
				coords = getCoords();
			}


			board.printBoard();
			if (board.checkWin() == -1) {
				System.out.println("Player X's turn: (type coords separated by comma)");
				coords = getCoords();
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

}
