import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Display.java
 * 
 * Written Mar 25, 2014
 * 
 * @author Ted Beiber, William Wu, and Rohan Kadambi
 * 
 */
public class Display {
	protected Player player;
	protected PuzzlePieceImage[] pieces;
	
	public Display() {
		PuzzlePiece[] puzzlePieces = {
				new PuzzlePiece(PuzzlePiece.CLUBS_OUT, PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.SPADES_IN,
						PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT, PuzzlePiece.SPADES_OUT,
						PuzzlePiece.SPADES_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.CLUBS_IN,
						PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT, PuzzlePiece.SPADES_OUT,
						PuzzlePiece.HEARTS_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.DIAMONDS_IN,
						PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.HEARTS_IN,
						PuzzlePiece.DIAMONDS_IN),
				new PuzzlePiece(PuzzlePiece.CLUBS_OUT, PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.SPADES_IN, PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.DIAMONDS_OUT,
						PuzzlePiece.CLUBS_OUT, PuzzlePiece.CLUBS_IN,
						PuzzlePiece.DIAMONDS_IN)};
		pieces = new PuzzlePieceImage[puzzlePieces.length];
		for(int i = 0; i < puzzlePieces.length; i++) {
			pieces[i] = new PuzzlePieceImage("images/piece_" + (i+1) + ".png", new Vector2(), 0, puzzlePieces[i]);
		}
		player = new Player(new Grid(3, 3), pieces);
		player.place(0, 0, pieces[0]);
		player.place(1, 1, pieces[1]);
		player.place(2, 2, pieces[2]);
		JFrame frame = new JFrame("Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocation(10, 10);
		frame.add(new PuzzleDrawingComponent(this));
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new Display();
	}


/**
 * A component to draw the puzzle and its pieces.
 * 
 */
class PuzzleDrawingComponent extends JComponent {
	private Display display;
	private long lastTick = 0;
	
	// Fun fact: The images are sized 118 x 118 pixels. but they really only take up 70
	
	public PuzzleDrawingComponent(Display display) {
		this.display = display;
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Select piece
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Start dragging it
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Put it down
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
		// Listen for mouse scrolling
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
			}
		});
	}

	/**
	 * @param graphics
	 *            the graphics context
	 * 
	 */
	public void paintComponent(Graphics graphics) {
		// Extract Graphics2D
		Graphics2D g = (Graphics2D) graphics;
		// adds some anti-Aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		// draw the pieces in the grid                                             (and/or)
		// for testing this it would be soooo helpful to actually have a working grid&//player class
		int imagespacing = 70;
		int counter = 0;
		for(int i = 0; i < player.getGrid().getHeight(); i++) {
			for(int j = 0; j < player.getGrid().getWidth(); j++) {
				if(player.getGrid().isOccupied(j, i))
				g.drawImage(pieces[counter].getImage(), this.getWidth() / 2 + j*imagespacing - (int)(imagespacing*1.5), 
						this.getHeight() / 2 + i*imagespacing - 100 - (int)(imagespacing*1.5), null);
				counter++;
			}
		}
		for(int i = 0; i < player.get$Bank().length; i++) {
			g.drawImage(((PuzzlePieceImage) player.get$Bank()[i]).getImage(), i*imagespacing, this.getHeight() / 2 + 218, null);
		}
		
		// draw the pieces in the bank
		
		
		// Draw background

		long currentTime = new Date().getTime();
		double timeElapsed = (lastTick == 0 ? 0 : (currentTime - lastTick)) * 0.001;
		lastTick = currentTime;
	}
}
}