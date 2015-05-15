/**********************************************************************
The Surround Game - a game of strategy where the player tries to
place tiles that surround the opponent's tiles.

@author Conner Toney
@version GVSU Winter 2015
 *********************************************************************/

package package1;

public class SurroundGame {

	/** the board game */
	private Cell[][] board;

	/** the current status of the game */
	private GameStatus status;

	/** used to determine whose turn it is */
	private int playerTurn;

	/** the board size */
	private int boardSize;

	/******************************************************************
    Constructor sets up a blank a board, and sets the board size and
    starting player dependent on input
    @param boardSize size of the game board
    @param player the starting player
	 *****************************************************************/
	public SurroundGame(int boardSize, int player) {
		this.boardSize = boardSize;
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;
		playerTurn = player;
		reset();
	}

	/******************************************************************
    Selects the tile based on which player it is, and determines if
    anybody has won yet
    @param row row the tile is in
    @param col column the tile is in
    @return true if tile was selected, false if not
	 *****************************************************************/
	public Boolean select(int row, int col) {

		//makes sure the tile is empty before selection
		if (board[row][col].getCellNumber() == 0) {
			board[row][col] = new Cell(playerTurn);
			isWinner();
			determineSafety();
			nextPlayer();
			return true;
		}		
		else
			return false;
	}

	/******************************************************************
    Resets the game board to all blank tiles, status set to IN_PROGRESS
	 *****************************************************************/
	public void reset() {
		status = GameStatus.IN_PROGRESS;

		//iterates through 2D array, creating a new Cell object
		//set to '0' (aka blank) in each element
		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new Cell(0);
			}
	}

	/******************************************************************
    Determines if anyone has won the game
    @return 1 if player1 has won, 2 if player2 has won, 0 if there
    was a draw, -1 if nobody has won yet
	 *****************************************************************/
	public int isWinner() {

		//used to keep track of empty tiles
		int count = 0;

		//iterates through the entire 2D array, and determines
		//if a player has won based on 9 distinct game positions
		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {

				//focuses on the top left corner
				if (row == 0 && col == 0) {
					if (board[0][0].getCellNumber() == 1
							&& board[0][1].getCellNumber() == 2
							&& board[1][0].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[0][0].getCellNumber() == 2
							&& board[0][1].getCellNumber() == 1
							&& board[1][0].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on top right corner
				if (row == 0 && col == boardSize-1) {
					if (board[0][boardSize-1].getCellNumber() == 1
							&& board[0][boardSize-2]
									.getCellNumber() == 2
									&& board[1][boardSize-1]
											.getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[0][boardSize-1].getCellNumber() == 2
							&& board[0][boardSize-2]
									.getCellNumber() == 1
									&& board[1][boardSize-1]
											.getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on bottom left corner
				if (row == boardSize-1 && col == 0) {
					if (board[boardSize-1][0].getCellNumber() == 1
							&& board[boardSize-2][0]
									.getCellNumber() == 2
									&& board[boardSize-1][1]
											.getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[boardSize-1][0].getCellNumber() == 2
							&& board[boardSize-2][0]
									.getCellNumber() == 1
									&& board[boardSize-1][1]
											.getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on bottom right corner
				if (row == boardSize-1 && col == boardSize-1) {
					if (board[boardSize-1][boardSize-1]
							.getCellNumber() == 1
							&& board[boardSize-2][boardSize-1]
									.getCellNumber() == 2
									&& board[boardSize-1][boardSize-2]
											.getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[boardSize-1][boardSize-1]
							.getCellNumber() == 2
							&& board[boardSize-2][boardSize-1]
									.getCellNumber() == 1
									&& board[boardSize-1][boardSize-2]
											.getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on the 1st row, of non-corner tiles
				if (row == 0 && (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1
							&& board[row][col-1].getCellNumber() == 2
							&& board[row][col+1].getCellNumber() == 2
							&& board[row+1][col].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[row][col].getCellNumber() == 2
							&& board[row][col-1].getCellNumber() == 1
							&& board[row][col+1].getCellNumber() == 1
							&& board[row+1][col].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on the 1st column of non-corner tiles
				if (col == 0 && (row > 0 && row < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1
							&& board[row-1][col].getCellNumber() == 2
							&& board[row+1][col].getCellNumber() == 2
							&& board[row][col+1].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[row][col].getCellNumber() == 2
							&& board[row-1][col].getCellNumber() == 1
							&& board[row+1][col].getCellNumber() == 1
							&& board[row][col+1].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on the last row of non-corner tiles
				if (col == boardSize-1 
						&& (row > 0 && row < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1
							&& board[row-1][col].getCellNumber() == 2
							&& board[row+1][col].getCellNumber() == 2
							&& board[row][col-1].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[row][col].getCellNumber() == 2
							&& board[row-1][col].getCellNumber() == 1
							&& board[row+1][col].getCellNumber() == 1
							&& board[row][col-1].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on the last row of non-corner tiles
				if (row == boardSize-1 
						&& (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1
							&& board[row][col-1].getCellNumber() == 2
							&& board[row][col+1].getCellNumber() == 2
							&& board[row-1][col].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[row][col].getCellNumber() == 2
							&& board[row][col-1].getCellNumber() == 1
							&& board[row][col+1].getCellNumber() == 1
							&& board[row-1][col].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//focuses on the middle tiles
				if ((row > 0 && row < boardSize-1)
						&& (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1
							&& board[row+1][col].getCellNumber() == 2
							&& board[row-1][col].getCellNumber() == 2
							&& board[row][col+1].getCellNumber() == 2
							&& board[row][col-1].getCellNumber() == 2) {
						status = GameStatus.PLAYER2_WON;
						return 2;
					}
					if (board[row][col].getCellNumber() == 2
							&& board[row+1][col].getCellNumber() == 1
							&& board[row-1][col].getCellNumber() == 1
							&& board[row][col+1].getCellNumber() == 1
							&& board[row][col-1].getCellNumber() == 1) {
						status = GameStatus.PLAYER1_WON;
						return 1;
					}
				}

				//if tile is '0' (aka blank), adds to count
				if (board[row][col].getCellNumber() == 0)
					count++;
			}

		//if there are 0 blank tiles left, the game is CATS (aka draw)
		if (count == 0) {
			status = GameStatus.CATS;
			return 0;
		}
		return -1;
	}

	/******************************************************************
    Determines how safe a tile is, based on its surroundings
	 *****************************************************************/
	public void determineSafety() {

		//iterates through the entire 2D array, and determines
		//how safe a selection was
		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {
				int count = 0;

				//top left safety level
				if (row == 0 && col == 0) {
					if (board[0][0].getCellNumber() == 1) {
						if (board[0][1].getCellNumber() == 2)
							count++;
						if (board[1][0].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[0][1].getCellNumber() == 1)
							count++;
						if (board[1][0].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[0][0].setSafety(3);
				}

				//top right safety level
				if (row == 0 && col == boardSize-1) {
					if (board[0][boardSize-1].getCellNumber() == 1) {
						if (board[0][boardSize-2].getCellNumber() == 2)
							count++;
						if (board[1][boardSize-1].getCellNumber() == 2)
							count++;
					}
					if (board[0][boardSize-1].getCellNumber() == 2) {
						if (board[0][boardSize-2].getCellNumber() == 1)
							count++;
						if (board[1][boardSize-1].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[0][boardSize-1].setSafety(3);
				}

				//bottom left safety level
				if (row == boardSize-1 && col == 0) {
					if (board[boardSize-1][0].getCellNumber() == 1) {
						if (board[boardSize-2][0].getCellNumber() == 2)
							count++;
						if (board[boardSize-1][1].getCellNumber() == 2)
							count++;
					}
					if (board[boardSize-1][0].getCellNumber() == 2) {
						if (board[boardSize-2][0].getCellNumber() == 1)
							count++;
						if (board[boardSize-1][1].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[boardSize-1][0].setSafety(3);
				}


				//bottom right safety level
				if (row == boardSize-1 && col == boardSize-1) {
					if (board[boardSize-1][boardSize-1]
							.getCellNumber() == 1) {
						if (board[boardSize-2][boardSize-1]
								.getCellNumber() == 2)
							count++;
						if (board[boardSize-1][boardSize-2]
								.getCellNumber() == 2)
							count++;
					}
					if (board[boardSize-1][boardSize-1]
							.getCellNumber() == 2) {
						if (board[boardSize-2][boardSize-1]
								.getCellNumber() == 1)
							count++;
						if (board[boardSize-1][boardSize-2]
								.getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[boardSize-1][boardSize-1].setSafety(3);
				}

				//top row safety level
				if (row == 0 && (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1) {
						if (board[row][col-1].getCellNumber() == 2)
							count++;
						if (board[row][col+1].getCellNumber() == 2)
							count++;
						if (board[row+1][col].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[row][col-1].getCellNumber() == 1)
							count++;
						if (board[row][col+1].getCellNumber() == 1)
							count++;
						if (board[row+1][col].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[row][col].setSafety(2);
					if (count == 2)
						board[row][col].setSafety(3);
				}

				//bottom row safety level
				if (row == boardSize-1 
						&& (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1) {
						if (board[row][col-1].getCellNumber() == 2)
							count++;
						if (board[row][col+1].getCellNumber() == 2)
							count++;
						if (board[row-1][col].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[row][col-1].getCellNumber() == 1)
							count++;
						if (board[row][col+1].getCellNumber() == 1)
							count++;
						if (board[row-1][col].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[row][col].setSafety(2);
					if (count == 2)
						board[row][col].setSafety(3);
				}

				//left column safety level
				if (col == 0 && (row > 0 && row < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1) {
						if (board[row-1][col].getCellNumber() == 2)
							count++;
						if (board[row+1][col].getCellNumber() == 2)
							count++;
						if (board[row][col+1].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[row-1][col].getCellNumber() == 1)
							count++;
						if (board[row+1][col].getCellNumber() == 1)
							count++;
						if (board[row][col+1].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[row][col].setSafety(2);
					if (count == 2)
						board[row][col].setSafety(3);
				}

				//right column safety level
				if (col == boardSize-1 
						&& (row > 0 && row < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1) {
						if (board[row-1][col].getCellNumber() == 2)
							count++;
						if (board[row+1][col].getCellNumber() == 2)
							count++;
						if (board[row][col-1].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[row-1][col].getCellNumber() == 1)
							count++;
						if (board[row+1][col].getCellNumber() == 1)
							count++;
						if (board[row][col-1].getCellNumber() == 1)
							count++;
					}
					if (count == 1)
						board[row][col].setSafety(2);
					if (count == 2)
						board[row][col].setSafety(3);
				}

				//middle section safety level
				if ((row > 0 && row < boardSize-1) 
						&& (col > 0 && col < boardSize-1)) {
					if (board[row][col].getCellNumber() == 1) {
						if (board[row+1][col].getCellNumber() == 2)
							count++;
						if (board[row-1][col].getCellNumber() == 2)
							count++;
						if (board[row][col+1].getCellNumber() == 2)
							count++;
						if (board[row][col-1].getCellNumber() == 2)
							count++;
					}
					if (board[row][col].getCellNumber() == 2) {
						if (board[row+1][col].getCellNumber() == 1)
							count++;
						if (board[row-1][col].getCellNumber() == 1)
							count++;
						if (board[row][col+1].getCellNumber() == 1)
							count++;
						if (board[row][col-1].getCellNumber() == 1)
							count++;
					}
					if (count == 1 || count == 2)
						board[row][col].setSafety(2);
					if (count == 3)
						board[row][col].setSafety(3);
				}
			}
	}

	/******************************************************************
    Changes turns to the next player
	 *****************************************************************/
	public void nextPlayer() {
		if (playerTurn == 1)
			playerTurn = 2;
		else
			playerTurn = 1;
	}

	/******************************************************************
    Gets the current status of the game
    @return the current status of the game
	 *****************************************************************/
	public GameStatus getStatus() {
		return status;
	}

	/******************************************************************
    Gets the game board
    @return the entire game 'board' object
	 *****************************************************************/
	public Cell[][] getBoard() {
		return board;
	}
}
