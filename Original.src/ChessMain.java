import java.awt.HeadlessException;
import javax.swing.*;
// -------------------------------------------------------------------------
/**
 * Shows the GUI for the Chess game.
 * 
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessMain{
    // ----------------------------------------------------------
    /**
     * Creates the GUI for Chess.
     * 
     * @param args
     *            command line arguments, not used
     */
    public static void main( String[] args ){
        JFrame frame = frame();
		frame.getContentPane().add( new ChessPanel() );
    }

	private static JFrame frame() throws HeadlessException {
		JFrame frame = new JFrame("YetAnotherChessGame 1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}
}
