/**********************************************************************
The Cell object acts as a tile in the board game.  A Cell is defined
by the player's number that selected it.

@author Conner Toney
@version GVSU Winter 2015
 *********************************************************************/

package package1;

public class Cell {

	/** the player's number used to define a cell */
	private int playerNumber;
	
	/** the current safety level of the cell */
	private int safetyLevel;
	
	/******************************************************************
    Constructor used to create cell with specified player number
	 *****************************************************************/
	public Cell(int playerNumber) {
		this.playerNumber = playerNumber;
		safetyLevel = 1;
	}
	
	/******************************************************************
    Gets the cell's player number
    @return this cell's player number
	 *****************************************************************/
	public int getCellNumber() {
		return playerNumber;
	}
	
	/******************************************************************
    Sets the safety level of this cell
    @param safetyLevel input safety level for cell
	 *****************************************************************/
	public void setSafety(int safetyLevel) {
		this.safetyLevel = safetyLevel;
	}
	
	/******************************************************************
    Gets the safety level of this cell
    @return the safety level
	 *****************************************************************/
	public int getSafety() {
		return safetyLevel;
	}
}
