/**********************************************************************
Graphical representation of the SurroundGame in JPanels.

@author Conner Toney
@version GVSU Winter 2015
 *********************************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SurroundPanel extends JPanel {

	/** default serial ID */
	private static final long serialVersionUID = 1L;

	/** a 2D array of buttons used to represent the game board */
	private JButton[][] board;

	/** a 2D array of non-graphical cells used to represent the board */
	private Cell[][] iBoard;

	/** the SurroundGame object used in this class */
	private SurroundGame game;

	/** the board size */
	private int BDSIZE;

	/** used to listen to button selections */
	private ButtonListener bListener;

	/** panel that will contain the game board */
	private JPanel topPanel;

	/** panel that will contain the score board */
	private JPanel bottomPanel;

	/** panel that will contain the top and bottom panels */
	private JPanel mainPanel;

	/** label indicating Player 1 wins */
	private JLabel player1Lbl;

	/** label indicating Player 2 wins */
	private JLabel player2Lbl;

	/** label that contains the Player 1 win amount */
	private JLabel p1WinTotalLbl;

	/** label that contains the Player 2 win amount */
	private JLabel p2WinTotalLbl;

	/** total Player 1 win total */
	private static int player1WinTotal = 0;

	/** total Player 2 win total */
	private static int player2WinTotal = 0;

	/******************************************************************
    Constructor that sets up the panel and all objects on the panel
    @param boardsize size of game board
    @param player starting player turn
	 *****************************************************************/
	public SurroundPanel(int boardsize, int player) {
		BDSIZE = boardsize;
		game = new SurroundGame(BDSIZE, player);
		bListener = new ButtonListener();
		mainPanel = new JPanel();

		//sets up the panel so that the gameboard and scoreboard
		//appear in a vertical column
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.add(mainPanel);
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);

		player1Lbl = new JLabel("Player 1 wins: ");
		player2Lbl = new JLabel("Player 2 wins: ");
		p1WinTotalLbl = new JLabel("0");
		p2WinTotalLbl = new JLabel("0");

		board = new JButton[BDSIZE][BDSIZE];
		iBoard = new Cell[BDSIZE][BDSIZE];

		topPanel.setLayout(new GridLayout(BDSIZE, BDSIZE));

		//creates a 2D array of buttons dependent on the BDSIZE, which
		//will function as the visual game board
		for (int row = 0; row < BDSIZE; row++)
			for (int col = 0; col < BDSIZE; col++) {
				board[row][col] = new JButton();
				board[row][col].addActionListener(bListener);
				board[row][col].setMargin(new Insets(0, 0, 0, 0));
				board[row][col].setPreferredSize(new Dimension(35, 35));
				board[row][col].setBackground(Color.CYAN);
				topPanel.add(board[row][col]);
			}

		//sets up the scoreboard panel
		bottomPanel.setLayout(new GridLayout(2, 2));
		bottomPanel.add(player1Lbl);
		bottomPanel.add(player2Lbl);
		bottomPanel.add(p1WinTotalLbl);
		bottomPanel.add(p2WinTotalLbl);

	}

	/******************************************************************
    Gets the win total for Player 1
    @return statWins1 player 1 win total
	 *****************************************************************/
	public static int getPlayer1WinTotal() {
		return player1WinTotal;
	}

	/******************************************************************
    Gets the win total for Player 2
    @return statWins2 player 2 win total
	 *****************************************************************/
	public static int getPlayer2WinTotal() {
		return player2WinTotal;
	}

	/******************************************************************
    ActionListener used to listen to button selection and then determine
    correct actions to proceed with
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		/**************************************************************
	    Determines which action was performed and which course of
	    action to then proceed with
	    @param e the action event
		 *************************************************************/
		public void actionPerformed(ActionEvent e) {

			//iterates through the board, and selects the correct
			//tile based on user selection
			for (int r = 0; r < BDSIZE; r++)
				for (int c = 0; c < BDSIZE; c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			//updates display
			displayBoard();
			displaySafety();

			//determines if anyone has won yet
			//if so, a pane pops up declaring winner and adds a win
			//to the player's win total
			int winner = game.isWinner();
			if (winner == 1) {
				JOptionPane.showMessageDialog(null, "Player 1 wins");
				p1WinTotalLbl.setText(""
						+ (Integer.parseInt(p1WinTotalLbl.getText()) 
								+ 1));
				player1WinTotal = Integer.parseInt(p1WinTotalLbl
						.getText());
			}
			if (winner == 2) {
				JOptionPane.showMessageDialog(null, "Player 2 wins");
				p2WinTotalLbl.setText(""
						+ (Integer.parseInt(p2WinTotalLbl.getText())
								+ 1));
				player2WinTotal = Integer.parseInt(p2WinTotalLbl
						.getText());
			}
			if (winner == 0) {
				JOptionPane.showMessageDialog(null, "CATS!");
			}
		}
	}

	/******************************************************************
    Sets the label for Player 1 Win totals
    @param win1 win amount
	 *****************************************************************/
	public void setPlayer1WinTotalLbl(int win1) {
		p1WinTotalLbl.setText("" + player1WinTotal);
	}

	/******************************************************************
    Sets the label for Player 2 Win totals
    @param win2 win amount
	 *****************************************************************/
	public void setPlayer2WinTotalLbl(int win2) {
		p2WinTotalLbl.setText("" + player2WinTotal);
	}

	/******************************************************************
    Updates the display for the game board's GUI
	 *****************************************************************/
	private void displayBoard() {

		//receives the game board information
		iBoard = game.getBoard();

		//updates the tile's text based on which player has
		//selecte the tile
		for (int row = 0; row < BDSIZE; row++)
			for (int col = 0; col < BDSIZE; col++) {
				if (iBoard[row][col].getCellNumber() == 1)
					board[row][col].setText("1");
				if (iBoard[row][col].getCellNumber() == 2)
					board[row][col].setText("2");
				if (iBoard[row][col].getCellNumber() == 0)
					board[row][col].setText("");
			}
	}

	/******************************************************************
    Updates the display to incorporate the safety level of each tile.
    Yellow represents a caution that the tile has at least 1 opponent
    tile nearby. Red represents a warning that the tile is about to be
    surrounded by an opponent tile.
	 *****************************************************************/
	public void displaySafety() {

		//iterates through the iBoard, setting the tile to the correct
		//color based on its safety level
		for (int row = 0; row < BDSIZE; row++)
			for (int col = 0; col < BDSIZE; col++) {
				if (iBoard[row][col].getSafety() == 2)
					board[row][col].setBackground(Color.YELLOW);
				if (iBoard[row][col].getSafety() == 3)
					board[row][col].setBackground(Color.RED);
			}
	}
}
