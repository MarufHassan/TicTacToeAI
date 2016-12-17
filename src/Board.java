public class Board {
	// Named-constants for the dimensions
	public static final int ROWS = 3;
	public static final int COLS = 3;

	Cell[][] cells; // a board composes of ROWS-by-COLS Cell instances

	// Constructor to initialize the game board
	public Board() {
		cells = new Cell[ROWS][COLS]; // allocate the array
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	public Board(Cell[][] cells) {
		this.cells = cells;
	}

	// initialize (or re-initialize) the contents of the game board
	public void init() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cells[row][col].clear(); // clear the cell content
			}
		}
	}

	public boolean isCellEmpty(int row, int col) {
		return cells[row][col].content == Seed.EMPTY;
	}

	// Return true if it is a tie (i.e., no more EMPTY cell)
	public boolean isTie() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (cells[row][col].content == Seed.EMPTY) {
					return false; // an empty seed found, not a draw, exit
				}
			}
		}
		return true; // no empty cell, it's a tie
	}

	public boolean isMovesLeft() {
		return !isTie();
	}

	// Return true if the player with "theSeed" has won
	public boolean hasWon(Seed theSeed) {
		// Checking for Rows for X or O victory.
		for (int row = 0; row < ROWS; row++) {
			if (cells[row][0].content == theSeed
					&& cells[row][0].content == cells[row][1].content
					&& cells[row][1].content == cells[row][2].content) {
				return true;
			}
		}

		// Checking for Columns for X or O victory.
		for (int col = 0; col < ROWS; col++) {
			if (cells[0][col].content == theSeed
					&& cells[0][col].content == cells[1][col].content
					&& cells[1][col].content == cells[2][col].content) {
				return true;
			}
		}

		// Checking for Diagonals for X or O victory.
		if (cells[0][0].content == theSeed
				&& cells[0][0].content == cells[1][1].content
				&& cells[1][1].content == cells[2][2].content) {
			return true;
		}

		// Checking for Diagonals for X or O victory.
		if (cells[0][2].content == theSeed
				&& cells[0][2].content == cells[1][1].content
				&& cells[1][1].content == cells[2][0].content) {
			return true;
		}

		return false;
	}

	// paint the board itself
	public void paint() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cells[row][col].paint();
				if (col < COLS - 1)
					System.out.print("|");
			}
			System.out.println();
			if (row < ROWS - 1) {
				System.out.println("-----------");
			}
		}
	}
}
