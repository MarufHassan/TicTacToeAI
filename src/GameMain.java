import java.io.IOException;
import java.util.Scanner;

/**
 * The main class for the Tic-Tac-Toe It acts as the overall controller of the
 * game.
 */
public class GameMain {
	private Board board;
	private GameState currentState;
	private Seed currentPlayer;
	
	private Minimax minmax;

	private static Scanner in = new Scanner(System.in); // input Scanner

	// Constructor to setup the game
	public GameMain() throws IOException {
		board = new Board(); // allocate game-board
		do {
			init();
			board.paint(); // ask the board to paint itself

			run();
			System.out.println("\n\n--------------------------------------------------\n\n");
			boolean validInput = false;
			char choice;
			do {
				System.out.println("Want to play again? y/n");
				choice = in.next().charAt(0);
				if (choice == 'y' || choice == 'n') {
					validInput = true;
				} else {
					System.out.println("Invalid input. Please try again.");
				}
			} while(!validInput);
			if (choice == 'n')	break;
		} while (true);
	}

	private void run() {
		do {
			if (currentPlayer == Seed.CROSS) 
				movePlayer(Seed.CROSS);
			else 
				moveAI(Seed.ZERO);
			
			board.paint(); // ask the board to paint itself
			updateGame(currentPlayer); // update currentState
			
			// Print message if game-over
			if (currentState == GameState.CROSS_WON) {
				System.out.println("Player won! Bye!");
			} else if (currentState == GameState.ZERO_WON) {
				System.out.println("AI won! Bye!");
			} else if (currentState == GameState.DRAW) {
				System.out.println("It's Draw! Bye!");
			}
			
			// Switch player
			currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.ZERO
					: Seed.CROSS;
		} while (currentState == GameState.PLAYING);
	}

	// initialize the game board contents and current states
	private void init() {
		board.init(); // clear the board contents
		currentPlayer = Seed.CROSS;
		currentState = GameState.PLAYING; // ready to play
		minmax = new Minimax(board, Seed.ZERO);
	}

	/**
	 * The player with "theSeed" makes one move, with input validation. Update
	 * Cell's content.
	 */
	private void movePlayer(Seed theSeed) {
		boolean validInput = false; // for validating input
		do {
			System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
			
			int row = in.nextInt() - 1;
			int col = in.nextInt() - 1;
			
			if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
					&& board.cells[row][col].content == Seed.EMPTY) {
				board.cells[row][col].content = theSeed;
				validInput = true; // input okay, exit loop
			} else {
				System.out.println("This move at (" + (row + 1) + ","
						+ (col + 1) + ") is not valid. Try again...");
			}
			
		} while (!validInput); // repeat until input is valid
	}

	private void moveAI(Seed theSeed) {
		System.out.println("\nWaiting for the AI's responses... ");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		MovePosition move = minmax.findBestMove();
		int row = move.row + 1;
		int col = move.col + 1;
		board.cells[move.row][move.col].content = theSeed;
		
		System.out.printf("AI choose row %d  and col %d\n", row, col);
	}

	// Update the currentState after the player with "theSeed" has moved
	private void updateGame(Seed theSeed) {
		if (board.hasWon(theSeed)) { // check for win
			currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON
					: GameState.ZERO_WON;
		} else if (board.isTie()) { // check for draw
			currentState = GameState.DRAW;
		}
	}

	public static void main(String[] args) throws IOException {
        new GameMain();
    }
}
