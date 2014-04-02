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
						PuzzlePiece.DIAMONDS_IN) };
		pieces = new PuzzlePieceImage[puzzlePieces.length];
		for (int i = 0; i < puzzlePieces.length; i++) {
			pieces[i] = new PuzzlePieceImage(
					"images/piece_" + (i + 1) + ".png", new Vector2(), 0,
					puzzlePieces[i]);
		}
		player = new Player(new Grid(3, 3), pieces);
		player.get$Bank();
		player.get$Bank()[0].rotate(); // currently does not display rotations
		player.place(0, 0, player.get$Bank()[0]);
		player.place(1, 1, player.get$Bank()[1]);
		player.place(2, 2, player.get$Bank()[2]);
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
		/**
		 * 
		 */
		private static final long serialVersionUID = -934843960429454280L;
		private Display display;
		private long lastTick = 0;
		private int width = 0;
		private int height = 0;
		private PuzzlePieceImage p;
		private int pieceInnerWidth = 70; // How wide the inner square is

		// TODO: MAKE ALL THESE FINAL
		private int gridMargin = 0;
		private int bankMargin = 150;
		private int bankPadding = 40;// How far the pieces will stay apart in
										// the bank
		private int selectionDistance = 59; // How far away to select the piece

		public boolean needsRelayout = true;

		// +------+ = gridPadding
		// |XXXXXX|
		// |XXXXXX|
		// |XXXXXX|
		// +------+
		// ^
		// | gridMargin
		// v
		// . Origin
		// ^
		// | bankMargin
		// v
		// XX XX XX

		// Fun fact: The images are sized 118 x 118 pixels. but they really only
		// take up 70

		// Fun fact: The images are sized 118 x 118 pixels. but they really only
		// take up 70

		public PuzzleDrawingComponent(Display display) {
			this.display = display;
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					int currentX = e.getX() - width / 2;
					int currentY = e.getY() - height / 2;
					double minDistance = Double.POSITIVE_INFINITY;
					for (int i = 0; i < pieces.length; i++) {
						double distance = Math.max(
								Math.abs(pieces[i].getPosition().getX()
										- currentX),
								Math.abs(pieces[i].getPosition().getY()
										- currentY));
						if (distance < minDistance) {
							minDistance = distance;
							p = pieces[i];
						}
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					p = null;
					needsRelayout=true;
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
					if (p != null)
						p.getPosition().set(e.getX() - width / 2,
								e.getY() - height / 2);
				}

				@Override
				public void mouseMoved(MouseEvent e) {
				}
			});
			// Listen for mouse scrolling
			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL
							&& p != null) {
						p.rotate();
					}
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

			// draw the pieces in the grid (and/or)
			// for testing this it would be soooo helpful to actually have a
			// working grid&//player class
			width = this.getWidth();
			height = this.getHeight();

			for (int i = 0; i < player.getGrid().getHeight(); i++) {
				for (int j = 0; j < player.getGrid().getWidth(); j++) {
					if (player.getGrid().isOccupied(i, j)) {
						if (needsRelayout) {
							relayout((PuzzlePieceImage) player.getGrid()
									.getCell(i, j), i - 1, 2 - j);
						}
					}
				}
			}
			for (int i = 0; i < player.get$Bank().length; i++) {
				if (player.get$Bank()[i] != null) {
					if (needsRelayout) {
						relayout((PuzzlePieceImage) player.get$Bank()[i], i
								- (player.get$Bank().length - 1) / 2.0);
					}
				}
			}
			for (int i = 0; i < pieces.length; i++) {
				drawPiece((PuzzlePieceImage) pieces[i], g);
			}

			needsRelayout = false;

			// draw the pieces in the bank

			// Draw background

			long currentTime = new Date().getTime();
			double timeElapsed = (lastTick == 0 ? 0 : (currentTime - lastTick)) * 0.001;
			lastTick = currentTime;
			this.repaint();
		}

		/**
		 * Draws a given piece into its location in a given graphics context.
		 * 
		 * @param piece
		 * @param g
		 */
		private void drawPiece(PuzzlePieceImage piece, Graphics2D g) {
			if (piece == null) {
				return;
			}
			g.drawImage(piece.getImage(), (int) piece.getPosition().getX()
					- piece.getImage().getWidth() / 2 + width / 2, (int) piece
					.getPosition().getY()
					- piece.getImage().getHeight()
					/ 2
					+ height / 2, null);
		}

		/**
		 * Changes the target position of a piece in a grid - where it needs to
		 * go.
		 * 
		 * @param piece
		 * @param x
		 * @param y
		 */
		private void relayout(PuzzlePieceImage piece, int x, int y) {
			if (piece == null) {
				return;
			}
			piece.getPosition().set(pieceInnerWidth * x,
					-gridMargin - pieceInnerWidth * y);
		}

		/**
		 * Changes the target position of a piece in a bank - where it needs to
		 * go.
		 * 
		 * @param puzzlePieceImage
		 * @param x
		 */
		private void relayout(PuzzlePieceImage piece, double x) {
			if (piece == null) {
				return;
			}
			piece.getPosition().set((pieceInnerWidth + bankPadding) * x,
					bankMargin);
		}
	}
}