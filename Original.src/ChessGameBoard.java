import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
// -------------------------------------------------------------------------
/**
 * The panel that represents the Chess game board. Contains a few methods that
 * allow other classes to access the physical board.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessGameBoard extends JPanel{
    private ChessGameBoardProduct chessGameBoardProduct = new ChessGameBoardProduct();
	private BoardListener   listener;
    // ----------------------------------------------------------
    /**
     * Returns the entire board.
     *
     * @return BoardSquare[][] the chess board
     */
    public BoardSquare[][] getCells(){
        return chessGameBoardProduct.getChessCells();
    }
    // ----------------------------------------------------------
    /**
     * Gets the BoardSquare at row 'row' and column 'col'.
     * @param row the row to look at
     * @param col the column to look at
     * @return BoardSquare the square found, or null if it does not exist
     */
    public BoardSquare getCell( int row, int col ){
        return chessGameBoardProduct.getCell(row, col);
    }
    // ----------------------------------------------------------
    /**
     * Clears the cell at 'row', 'col'.
     * @param row the row to look at
     * @param col the column to look at
     */
    public void clearCell(int row, int col){
        chessGameBoardProduct.clearCell(row, col);
    }
    // ----------------------------------------------------------
    /**
     * Gets all the white game pieces on the board.
     *
     * @return ArrayList<GamePiece> the pieces
     */
    public ArrayList<ChessGamePiece> getAllWhitePieces(){
        ArrayList<ChessGamePiece> whitePieces = new ArrayList<ChessGamePiece>();
        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ){
                if ( chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare() != null
                    && chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare().getColorOfPiece() ==
                        ChessGamePiece.WHITE ){
                    whitePieces.add( chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare() );
                }
            }
        }
        return whitePieces;
    }
    // ----------------------------------------------------------
    /**
     * Gets all the black pieces on the board
     *
     * @return ArrayList<GamePiece> the pieces
     */
    public ArrayList<ChessGamePiece> getAllBlackPieces(){
        ArrayList<ChessGamePiece> blackPieces = new ArrayList<ChessGamePiece>();
        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ){
                if ( chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare() != null
                    && chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare().getColorOfPiece() ==
                        ChessGamePiece.BLACK ){
                    blackPieces.add( chessGameBoardProduct.getChessCells()[i][j].getPieceOnSquare() );
                }
            }
        }
        return blackPieces;
    }
    // ----------------------------------------------------------
    /**
     * Create a new ChessGameBoard object.
     */
    public ChessGameBoard(){
        this.setLayout( new GridLayout( 8, 8, 1, 1 ) );
        listener = new BoardListener();
        chessGameBoardProduct.setChessCells(new BoardSquare[8][8]);
        initializeBoard();
    }
    // ----------------------------------------------------------
    /**
     * Clears the board of all items, including any pieces left over in the
     * graveyard, and all old game logs.
     * @param addAfterReset if true, the board will add the BoardSquares
     * back to the board, if false it will simply reset everything and leave
     * the board blank.
     */
    public void resetBoard ( boolean addAfterReset ){
        chessGameBoardProduct.setChessCells(new BoardSquare[8][8]);
        this.removeAll();
        if ( getParent() instanceof ChessPanel ){
            ( (ChessPanel)getParent() ).getGraveyard( 1 ).clearGraveyard();
            ( (ChessPanel)getParent() ).getGraveyard( 2 ).clearGraveyard();
            ( (ChessPanel)getParent() ).getGameLog().clearLog();
        }
        for ( int i = 0; i < chessGameBoardProduct.getChessCells().length; i++ ){
            for ( int j = 0; j < chessGameBoardProduct.getChessCells()[0].length; j++ ){
                chessGameBoardProduct.getChessCells()[i][j] = new BoardSquare( i, j, null );
                if ( ( i + j ) % 2 == 0 ){
                    chessGameBoardProduct.getChessCells()[i][j].setBackground( Color.WHITE );
                }
                else
                {
                    chessGameBoardProduct.getChessCells()[i][j].setBackground( Color.BLACK );
                }
                if ( addAfterReset ){
                    chessGameBoardProduct.getChessCells()[i][j].addMouseListener( listener );
                    this.add( chessGameBoardProduct.getChessCells()[i][j] );
                }
            }
        }
        repaint();
        //revalidate();
        // only the combination of these two calls work...*shrug*
    }
    /**
     * (Re)initializes this ChessGameBoard to its default layout with all 32
     * pieces added.
     */
    public void initializeBoard(){
        resetBoard( false );
        for ( int i = 0; i < chessGameBoardProduct.getChessCells().length; i++ ){
            for ( int j = 0; j < chessGameBoardProduct.getChessCells()[0].length; j++ ){
                ChessGamePiece pieceToAdd = pieceToAdd(i, j);
				chessGameBoardProduct.getChessCells()[i][j] = new BoardSquare( i, j, pieceToAdd );
                if ( ( i + j ) % 2 == 0 ){
                    chessGameBoardProduct.getChessCells()[i][j].setBackground( Color.WHITE );
                }
                else
                {
                    chessGameBoardProduct.getChessCells()[i][j].setBackground( Color.BLACK );
                }
                chessGameBoardProduct.getChessCells()[i][j].addMouseListener( listener );
                this.add( chessGameBoardProduct.getChessCells()[i][j] );
            }
        }
    }
	private ChessGamePiece pieceToAdd(int i, int j) {
		ChessGamePiece pieceToAdd;
		if (i == 1) {
			pieceToAdd = new Pawn(this, i, j, ChessGamePiece.BLACK);
		} else if (i == 6) {
			pieceToAdd = new Pawn(this, i, j, ChessGamePiece.WHITE);
		} else if (i == 0 || i == 7) {
			int colNum = i == 0 ? ChessGamePiece.BLACK : ChessGamePiece.WHITE;
			if (j == 0 || j == 7) {
				pieceToAdd = new Rook(this, i, j, colNum);
			} else if (j == 1 || j == 6) {
				pieceToAdd = new Knight(this, i, j, colNum);
			} else if (j == 2 || j == 5) {
				pieceToAdd = new Bishop(this, i, j, colNum);
			} else if (j == 3) {
				pieceToAdd = new King(this, i, j, colNum);
			} else {
				pieceToAdd = new Queen(this, i, j, colNum);
			}
		} else {
			pieceToAdd = null;
		}
		return pieceToAdd;
	}
    // ----------------------------------------------------------
    /**
     * Clears the colors on the board.
     */
    public void clearColorsOnBoard(){
        chessGameBoardProduct.clearColorsOnBoard();
    }
    /**
     * Listens for clicks on BoardSquares.
     *
     * @author Ben Katz (bakatz)
     * @author Danielle Bushrow (dbushrow)
     * @author Myles David (davidmm2)
     * @version 2010.11.16
     */
    private class BoardListener
        implements MouseListener
    {
        /**
         * Do an action when the left mouse button is clicked.
         *
         * @param e
         *            the event from the listener
         */
        public void mouseClicked( MouseEvent e ){
            if ( e.getButton() == MouseEvent.BUTTON1 &&
                getParent() instanceof ChessPanel ){
                ( (ChessPanel)getParent() ).getGameEngine()
                    .determineActionFromSquareClick( e );
            }
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseEntered( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseExited( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mousePressed( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseReleased( MouseEvent e ){ /* not used */
        }
    }
}
