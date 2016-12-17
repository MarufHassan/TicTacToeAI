/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
	Seed content; // content of this cell of type Seed.
					// take a value of Seed.EMPTY, Seed.CROSS,
					// or Seed.NOUGHT

	// Constructor to initialize this cell
	public Cell(int row, int col) {
		clear(); // clear the content of this cell
	}

	// Clear the cell content to EMPTY
	public void clear() {
		content = Seed.EMPTY;
	}

	// Paint the cell
	public void paint() {
		switch (content) {
		case CROSS:
			System.out.print(" X ");
			break;
		case ZERO:
			System.out.print(" 0 ");
			break;
		case EMPTY:
			System.out.print("   ");
			break;
		}
	}
}
