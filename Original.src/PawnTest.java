import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PawnTest {

	@Test
	public void testCalculatePossibleMoves() {
	    ChessGameBoard board = new ChessGameBoard();
	    int row = 1;
	    int col = 1;
	    int color = ChessGamePiece.WHITE;
	    Pawn pawn = new Pawn(board, row, col, color);
	    ArrayList<String> possibleMoves = pawn.calculatePossibleMoves(board);
	    assertEquals(2, possibleMoves.size());
	    
	}

}
