

/**
 * @see ChessGamePiece#WHITE
 */
public class White extends PieceColor {
	public int getColorOfPiece() {
		return ChessGamePiece.WHITE;
	}

	public boolean isEnemy(ChessGamePiece enemyPiece, ChessGamePiece chessGamePiece) {
		if (chessGamePiece.getColorOfPiece() == ChessGamePiece.BLACK) {
			return true;
		} else {
			return false;
		}
	}
}