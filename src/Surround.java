/**********************************************************************
Graphical representation of the SurroundGame in a JFrame.

@author Conner Toney
@version GVSU Winter 2015
 *********************************************************************/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Surround {

	/** the main frame used to set up the GUI */
	private JFrame frame;

	/** menubar used to contain menu items */
	private JMenuBar menuBar;

	/** menu used to contain menu items */
	private JMenu menu;

	/** used to create a new game */
	private JMenuItem newGameItem;

	/** used to exit the game */
	private JMenuItem exitGameItem;

	/** the SurroundPanel object used for the GUI */
	private SurroundPanel panel;

	/** used to listen to button selections */
	private ButtonListener bListener;

	/** string containing input board size */
	private String strBoardSize;

	/** int value of input board size */
	private int intBoardSize;

	/** int value of input starting player */
	private int inpPlayer;

	/** string containing input starting player */
	private String strPlayer;

	/** int value of starting player */
	private int intPlayer;

	/******************************************************************
    Creates the entire GUI used to play the Surround Game
	 *****************************************************************/
	public void createGUI() {

		//declares the frame and menu
		frame = new JFrame("The Surround Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bListener = new ButtonListener();
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		newGameItem = new JMenuItem("New game...");
		newGameItem.addActionListener(bListener);
		exitGameItem = new JMenuItem("Exit game...");
		exitGameItem.addActionListener(bListener);
		menuBar.add(menu);
		menu.add(newGameItem);
		menu.add(exitGameItem);
		frame.setJMenuBar(menuBar);

		//pop up asking for board size
		strBoardSize = JOptionPane.showInputDialog("Board size: ");

		//a new pane is popped up if the input isn't a 0-9 digit,
		//asking for input again
		if (!strBoardSize.matches("[0-9]+")) {
			do {
				int askSizeAgain = JOptionPane.showConfirmDialog(null,
						"Invalid input. Try again?",
						"Invalid input.",
						JOptionPane.YES_NO_OPTION);
				if (askSizeAgain == JOptionPane.YES_OPTION)
					strBoardSize = JOptionPane.showInputDialog(""
							+ "Board size: ");
				if (askSizeAgain == JOptionPane.NO_OPTION)
					System.exit(0);
			} while (!strBoardSize.matches("[0-9]+"));
		}

		//parses the input string and turns it into a integer
		intBoardSize = Integer.parseInt(strBoardSize);

		//a pane is popped up that asks for a board size,
		//a new pane pops up if the size is <3 or >20
		do {
			if (intBoardSize < 3 || intBoardSize > 20) {
				int selection = JOptionPane.showConfirmDialog(null,
						"Numbers must be between 3 and 20. Try again?",
						"Invalid input.",
						JOptionPane.YES_NO_OPTION);

				//if yes, asks for input
				if (selection == JOptionPane.YES_OPTION) {
					strBoardSize = JOptionPane.showInputDialog("" + 
							"Board size: ");
					intBoardSize = Integer.parseInt(strBoardSize);
				}

				//if no, exits program
				if (selection == JOptionPane.NO_OPTION)
					System.exit(0);
			}
		} while (intBoardSize < 3 || intBoardSize > 20);

		//asks for the starting player -- if the player enters anything
		//besides '1' or '2', a new pane is popped up that lets
		//the user know that Player 1 will start by default
		try {
			strPlayer = JOptionPane.showInputDialog("Who starts?"
					+ " 1 or 2: ");
			intPlayer = Integer.parseInt(strPlayer);
			if (intPlayer == 1)
				inpPlayer = 1;
			else if (intPlayer == 2)
				inpPlayer = 2;
			else {
				JOptionPane.showMessageDialog(null, 
						"Incorrect input. Player 1 will start.",
						"Incorrect input!",
						JOptionPane.ERROR_MESSAGE);
				inpPlayer = 1;
			}
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Incorrect input. Player 1 will start.",
					"Incorrect input!",
					JOptionPane.ERROR_MESSAGE);
			inpPlayer = 1;
		}

		//creates a panel based on the user input in the panes
		panel = new SurroundPanel(intBoardSize, inpPlayer);
		frame.getContentPane().add(panel);
		
		//frame size depends on the board size
		if (intBoardSize == 3)
			frame.setSize(200, 215);
		if (intBoardSize == 4)
			frame.setSize(250, 250);
		if (intBoardSize > 4)
			frame.setSize((intBoardSize*35) + 25, 
					(intBoardSize*35) + 100);
		frame.setVisible(true);
	}
	
	/******************************************************************
    MAIN METHOD - Used to set up and play the entire game
	 *****************************************************************/
	public static void main(String[] args) {
		Surround gameFrame = new Surround();
		gameFrame.createGUI();
	}

	/******************************************************************
    ActionListener used to listen to button selections 
	 *****************************************************************/
	private class ButtonListener implements ActionListener {
		
		/**************************************************************
	    Determines which course of actions to proceed with dependent
	    on the button selected
	    @param e the action event
		 *************************************************************/
		public void actionPerformed(ActionEvent e) {
			
			//if 'New game' is selected, the current frame is disposed
			//and a new game is created with the correct labels
			if (e.getSource() == newGameItem) {
				frame.dispose();
				createGUI();
				panel.setPlayer1WinTotalLbl(SurroundPanel
						.getPlayer1WinTotal());
				panel.setPlayer2WinTotalLbl(SurroundPanel
						.getPlayer1WinTotal());
			}
			
			//exits if 'Exit game' is selected
			if (e.getSource() == exitGameItem) {
				System.exit(0);
			}
		}
	}
}
