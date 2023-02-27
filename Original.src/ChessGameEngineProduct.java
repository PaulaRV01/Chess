

import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.util.ArrayList;

public class ChessGameEngineProduct {
	private int currentPlayer;
	private ChessGameBoard board;
	private King king1;
	private King king2;

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ChessGameBoard getBoard() {
		return board;
	}

	public void setBoard(ChessGameBoard board) {
		this.board = board;
	}

	public void setKing1(King king1) {
		this.king1 = king1;
	}

	public void setKing2(King king2) {
		this.king2 = king2;
	}

	/**
	* Determines if the game should continue (i.e. game is in check or is 'normal'). If it should not, the user is asked to play again (see above method).
	*/
	public void checkGameConditions(ChessGameEngine chessGameEngine) {
		int origPlayer = currentPlayer;
		for (int i = 0; i < 2; i++) {
			int gameLostRetVal = determineGameLost();
			if (gameLostRetVal < 0) {
				askUserToPlayAgain("Game over - STALEMATE. You should both go" + " cry in a corner!", chessGameEngine);
				return;
			} else if (gameLostRetVal > 0) {
				askUserToPlayAgain("Game over - CHECKMATE. " + "Player " + gameLostRetVal + " loses and should go"
						+ " cry in a corner!", chessGameEngine);
				return;
			} else if (isKingInCheck(true)) {
				JOptionPane.showMessageDialog(board.getParent(), "Be careful player " + currentPlayer + ", "
						+ "your king is in check! Your next move must get " + "him out of check or you're screwed.",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
			currentPlayer = currentPlayer == 1 ? 2 : 1;
		}
		currentPlayer = origPlayer;
		nextTurn();
	}

	/**
	* Switches the turn to be the next player's turn.
	*/
	public void nextTurn() {
		currentPlayer = (currentPlayer == 1) ? 2 : 1;
		((ChessPanel) board.getParent()).getGameLog().addToLog("It is now Player " + currentPlayer + "'s turn.");
	}

	/**
	* Determines if the requested player has legal moves.
	* @param playerNum the player to check
	* @return  boolean true if the player does have legal moves, false otherwise
	*/
	public boolean playerHasLegalMoves(int playerNum) {
		ArrayList<ChessGamePiece> pieces;
		if (playerNum == 1) {
			pieces = board.getAllWhitePieces();
		} else if (playerNum == 2) {
			pieces = board.getAllBlackPieces();
		} else {
			return false;
		}
		for (ChessGamePiece currPiece : pieces) {
			if (currPiece.hasLegalMoves(board)) {
				return true;
			}
		}
		return false;
	}

	/**
	* Determines if the game is lost. Returns 1 or 2 for the losing player, -1 for stalemate, or 0 for a still valid game.
	* @return  int 1 or 2 for the losing play, -1 for stalemate, or 0 for a still valid game.
	*/
	public int determineGameLost() {
		if (king1.isChecked(board) && !playerHasLegalMoves(1)) {
			return 1;
		}
		if (king2.isChecked(board) && !playerHasLegalMoves(2)) {
			return 2;
		}
		if ((!king1.isChecked(board) && !playerHasLegalMoves(1)) || (!king2.isChecked(board) && !playerHasLegalMoves(2))
				|| (board.getAllWhitePieces().size() == 1 && board.getAllBlackPieces().size() == 1)) {
			return -1;
		}
		return 0;
	}

	/**
	* Determines if the requested King is in check.
	* @param checkCurrent if true, will check if the current king is in check if false, will check if the other player's king is in check.
	* @return  true if the king is in check, false otherwise
	*/
	public boolean isKingInCheck(boolean checkCurrent) {
		if (checkCurrent) {
			if (currentPlayer == 1) {
				return king1.isChecked(board);
			}
			return king2.isChecked(board);
		} else {
			if (currentPlayer == 2) {
				return king1.isChecked(board);
			}
			return king2.isChecked(board);
		}
	}

	/**
	* Asks the user if they want to play again - if they don't, the game exits.
	* @param endGameStr the string to display to the user (i.e. stalemate, checkmate, etc)
	*/
	public void askUserToPlayAgain(String endGameStr, ChessGameEngine chessGameEngine) {
		board(endGameStr);
		int resp = JOptionPane.showConfirmDialog(board.getParent(), endGameStr + " Do you want to play again?");
		if (resp == JOptionPane.YES_OPTION) {
			reset(chessGameEngine);
		} else {
		}
	}

	private void board(String endGameStr) throws HeadlessException {
		int resp = JOptionPane.showConfirmDialog(board.getParent(), endGameStr + " Do you want to play again?");
		if (resp == JOptionPane.YES_OPTION) {
		} else {
			board.resetBoard(false);
		}
	}

	/**
	* Resets the game to its original state.
	*/
	public void reset(ChessGameEngine chessGameEngine) {
		chessGameEngine.setFirstClick(true);
		currentPlayer = 1;
		((ChessPanel) board.getParent()).getGraveyard(1).clearGraveyard();
		((ChessPanel) board.getParent()).getGraveyard(2).clearGraveyard();
		((ChessPanel) board.getParent()).getGameBoard().initializeBoard();
		((ChessPanel) board.getParent()).revalidate();
		this.king1 = (King) board.getCell(7, 3).getPieceOnSquare();
		this.king2 = (King) board.getCell(0, 3).getPieceOnSquare();
		((ChessPanel) board.getParent()).getGameLog().clearLog();
		((ChessPanel) board.getParent()).getGameLog().addToLog("A new chess "
				+ "game has been started. Player 1 (white) will play " + "against Player 2 (black). BEGIN!");
	}
}