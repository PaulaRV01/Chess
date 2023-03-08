import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ChessGameBoardProductTest {

    private ChessGameBoardProduct chessGameBoardProduct;

    @Before
    public void setUp() {
        chessGameBoardProduct = new ChessGameBoardProduct();
        BoardSquare[][] chessCells = new BoardSquare[8][8];
        chessGameBoardProduct.setChessCells(chessCells);
    }

    @Test
    public void testValidateCoordinates() {
        // Test valid coordinates
        assertTrue(chessGameBoardProduct.validateCoordinates(0, 0));
        assertTrue(chessGameBoardProduct.validateCoordinates(7, 7));

        // Test invalid coordinates
        assertFalse(chessGameBoardProduct.validateCoordinates(-1, 0));
        assertFalse(chessGameBoardProduct.validateCoordinates(0, -1));

    }
}
