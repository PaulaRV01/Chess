import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
// -------------------------------------------------------------------------
/**
 * This is the backend behind the Chess game. Handles the turn-based aspects of
 * the game, click events, and determines win/lose conditions.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessGameEngine{
    private ChessGameEngineProduct chessGameEngineProduct = new ChessGameEngineProduct();
	private ChessGamePiece currentPiece;
    private boolean        firstClick;
    // ----------------------------------------------------------
    /**
     * Create a new ChessGameEngine object. Accepts a fully-created
     * ChessGameBoard. (i.e. all components rendered)
     *
     * @param board
     *            the reference ChessGameBoard
     */
    public ChessGameEngine( ChessGameBoard board ){
        firstClick = true;
        chessGameEngineProduct.setCurrentPlayer(1);
        chessGameEngineProduct.setBoard(board);
        chessGameEngineProduct.setKing1((King) board.getCell(7, 3).getPieceOnSquare());
        chessGameEngineProduct.setKing2((King) board.getCell(0, 3).getPieceOnSquare());
        ( (ChessPanel)board.getParent() ).getGameLog().clearLog();
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
            "A new chess "
                + "game has been started. Player 1 (white) will play "
                + "against Player 2 (black). BEGIN!" );
    }
    // ----------------------------------------------------------
    /**
     * Resets the game to its original state.
     */
    public void reset(){
        chessGameEngineProduct.reset(this);
    }
    // ----------------------------------------------------------
    /**
     * Gets the current player. Used for determining the turn.
     *
     * @return int the current player (1 or 2)
     */
    public int getCurrentPlayer(){
        return chessGameEngineProduct.getCurrentPlayer();
    }
    /**
     * Determines if the requested player has legal moves.
     *
     * @param playerNum
     *            the player to check
     * @return boolean true if the player does have legal moves, false otherwise
     */
    public boolean playerHasLegalMoves( int playerNum ){
        return chessGameEngineProduct.playerHasLegalMoves(playerNum);
    }
    /**
     * Checks if the last-clicked piece is a valid piece (i.e. if it is
     * the correct color and if the user actually clicked ON a piece.)
     * @return boolean true if the piece is valid, false otherwise
     */
    private boolean selectedPieceIsValid(){
        if ( currentPiece == null ) // user tried to select an empty square
        {
            return false;
        }
        if ( chessGameEngineProduct.getCurrentPlayer() == 2 ) // black player
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.BLACK ){
                return true;
            }
            return false;
        }
        else
        // white player
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.WHITE ){
                return true;
            }
            return false;
        }
    }
    /**
     * Determines if the requested King is in check.
     *
     * @param checkCurrent
     *            if true, will check if the current king is in check if false,
     *            will check if the other player's king is in check.
     * @return true if the king is in check, false otherwise
     */
    public boolean isKingInCheck( boolean checkCurrent ){
        return chessGameEngineProduct.isKingInCheck(checkCurrent);
    }
    /**
     * Determines if the game is lost. Returns 1 or 2 for the losing player, -1
     * for stalemate, or 0 for a still valid game.
     *
     * @return int 1 or 2 for the losing play, -1 for stalemate, or 0 for a
     *         still valid game.
     */
    public int determineGameLost(){
        
return chessGameEngineProduct.determineGameLost();
    }
    // ----------------------------------------------------------
    /**
     * Given a MouseEvent from a user clicking on a square, the appropriate
     * action is determined. Actions include: moving a piece, showing the possi
     * ble moves of a piece, or ending the game after checking game conditions.
     *
     * @param e
     *            the mouse event from the listener
     */
    public void determineActionFromSquareClick( MouseEvent e ){
        BoardSquare squareClicked = (BoardSquare)e.getSource();
        ChessGamePiece pieceOnSquare = squareClicked.getPieceOnSquare();
        chessGameEngineProduct.getBoard().clearColorsOnBoard();
        if ( firstClick ){
            currentPiece = squareClicked.getPieceOnSquare();
            if ( selectedPieceIsValid() ){
                currentPiece.showLegalMoves( chessGameEngineProduct.getBoard() );
                squareClicked.setBackground( Color.GREEN );
                firstClick = false;
            }
            else
            {
                if ( currentPiece != null ){
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "You tried to pick up the other player's piece! "
                            + "Get some glasses and pick a valid square.",
                        "Illegal move",
                        JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "You tried to pick up an empty square! "
                            + "Get some glasses and pick a valid square.",
                        "Illegal move",
                        JOptionPane.ERROR_MESSAGE );
                }
            }
        }
        else
        {
            if ( pieceOnSquare == null ||
                !pieceOnSquare.equals( currentPiece ) ) // moving
            {
                boolean moveSuccessful =
                    currentPiece.move(
                        chessGameEngineProduct.getBoard(),
                        squareClicked.getRow(),
                        squareClicked.getColumn() );
                if ( moveSuccessful ){
                    chessGameEngineProduct.checkGameConditions(this);
                }
                else
                {
                    int row = squareClicked.getRow();
                    int col = squareClicked.getColumn();
                    JOptionPane.showMessageDialog(
                        squareClicked,
                        "The move to row " + ( row + 1 ) + " and column "
                            + ( col + 1 )
                            + " is either not valid or not legal "
                            + "for this piece. Choose another move location, "
                            + "and try using your brain this time!",
                        "Invalid move",
                        JOptionPane.ERROR_MESSAGE );
                }
                firstClick = true;
            }
            else
            // user is just unselecting the current piece
            {
                firstClick = true;
            }
        }
    }
	public void setFirstClick(boolean firstClick) {
		this.firstClick = firstClick;
	}
}
