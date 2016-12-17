public class Minimax {
	private static final int WIN_SCORE = +10;
	private static final int LOOSE_SCORE = -10;

	private Board board;
	private Seed player, opponent;

	public Minimax(Board board, Seed player) {
		this.board = board;
		this.player = player;
		opponent = (player == Seed.CROSS) ? Seed.ZERO : Seed.CROSS;
	}

	private int evaluate() {
		if (board.hasWon(player))
			return WIN_SCORE;
		else if (board.hasWon(opponent))
			return LOOSE_SCORE;
		else
			return 0;
	}

	// This is the minimax function. It considers all
	// the possible ways the game can go and returns
	// the value of the board
	private int minimax(boolean isMax) {
		int score = evaluate();

		if (score == WIN_SCORE)
			return score;
		if (score == LOOSE_SCORE)
			return score;
		if (board.isTie())
			return 0;
		
		if (isMax) {
			int best = -1000;
			for (int row = 0; row < Board.ROWS; row++) {
				for (int col = 0; col < Board.COLS; col++) {
					if (board.isCellEmpty(row, col)) {
						board.cells[row][col].content = this.player;
						best = max(best, minimax(!isMax));
						board.cells[row][col].content = Seed.EMPTY;
					}
				}
			}
			return best;
		} else {
			int best = 1000;
			for (int row = 0; row < Board.ROWS; row++) {
				for (int col = 0; col < Board.COLS; col++) {
					if (board.isCellEmpty(row, col)) {
						board.cells[row][col].content = opponent;
						best = min(best, minimax(!isMax));
						board.cells[row][col].content = Seed.EMPTY;
					}
				}
			}
			return best;
		}
	}

	// return the best possible move for the player
	public MovePosition findBestMove() {
		int bestVal = -1000;
		MovePosition bestMove = new MovePosition();
		bestMove.row = -1;
		bestMove.col = -1;

		for (int row = 0; row < Board.ROWS; row++) {
			for (int col = 0; col < Board.COLS; col++) {
				if (board.isCellEmpty(row, col)) {
					board.cells[row][col].content = player;
					int moveVal = minimax(false);
					board.cells[row][col].content = Seed.EMPTY;
					
					if (moveVal > bestVal) {
						bestMove.row = row;
						bestMove.col = col;
						bestVal = moveVal;
					}
				}
			}
		}
		return bestMove;
	}

	private int max(int number1, int number2) {
		return number1 > number2 ? number1 : number2;
	}

	private int min(int number1, int number2) {
		return number1 < number2 ? number1 : number2;
	}
}
