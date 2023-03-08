import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class ChessGameBoardProductTest2 {

    private ChessGameBoardProduct chessGameBoard;

    @Before
    public void setUp() {
        // Inicializa el tablero de ajedrez
        chessGameBoard = new ChessGameBoardProduct();
        BoardSquare[][] chessCells = new BoardSquare[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessCells[i][j] = new BoardSquare(j, j, null);
            }
        }
        chessGameBoard.setChessCells(chessCells);
    }

    @Test
    public void testGetCell() {
        // Verifica que null se devuelva en caso de que las coordenadas estén fuera del tablero
        assertNull(chessGameBoard.getCell(-1, 0));
        assertNull(chessGameBoard.getCell(0, -1));
        assertNull(chessGameBoard.getCell(8, 0));
        assertNull(chessGameBoard.getCell(0, 8));

        // Verifica que se devuelva la casilla correcta en caso de que las coordenadas estén dentro del tablero
        BoardSquare expectedSquare = chessGameBoard.getChessCells()[0][0];
        assertEquals(expectedSquare, chessGameBoard.getCell(0, 0));
    }
}
