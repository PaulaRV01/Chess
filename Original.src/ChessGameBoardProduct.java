

import java.awt.Color;
import java.io.Serializable;

public class ChessGameBoardProduct implements Serializable {
	private BoardSquare[][] chessCells;

	public BoardSquare[][] getChessCells() {
		return chessCells;
	}

	public void setChessCells(BoardSquare[][] chessCells) {
		this.chessCells = chessCells;
	}

	/**
	* Checks to make sure row and column are valid indices.
	* @param row  the row to check
	* @param col  the column to check
	* @return  boolean true if they are valid, false otherwise
	*/
	public boolean validateCoordinates(int row, int col) {
		return chessCells.length > 0 && chessCells[0].length > 0 && row < chessCells.length
				&& col < chessCells[0].length && row >= 0 && col >= 0;
	}

	/**
	* Gets the BoardSquare at row 'row' and column 'col'.
	* @param row  the row to look at
	* @param col  the column to look at
	* @return  BoardSquare the square found, or null if it does not exist
	*/
	public BoardSquare getCell(int row, int col) {
		if (validateCoordinates(row, col)) {
			return chessCells[row][col];
		}
		return null;
	}

	/**
	* Clears the cell at 'row', 'col'.
	* @param row  the row to look at
	* @param col  the column to look at
	*/
	public void clearCell(int row, int col) {
		if (validateCoordinates(row, col)) {
			chessCells[row][col].clearSquare();
		} else {
			throw new IllegalStateException("Row " + row + " and column" + " " + col
					+ " are invalid, or the board has not been" + "initialized. This square cannot be cleared.");
		}
	}

	/**
	* Clears the colors on the board.
	*/
	public void clearColorsOnBoard() {
		for (int i = 0; i < chessCells.length; i++) {
			for (int j = 0; j < chessCells[0].length; j++) {
				if ((i + j) % 2 == 0) {
					chessCells[i][j].setBackground(Color.WHITE);
				} else {
					chessCells[i][j].setBackground(Color.BLACK);
				}
			}
		}
	}
}