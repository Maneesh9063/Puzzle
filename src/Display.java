import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
//		player.get$Bank();
//		player.get$Bank()[0].rotate(); // currently does not display rotations
//		player.place(0, 0, player.get$Bank()[0]);
//		player.place(1, 1, player.get$Bank()[1]);
//		player.place(2, 2, player.get$Bank()[2]);
		JFrame frame = new JFrame("Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocation(10, 10);
		frame.add(new PuzzleDrawingComponent(this));
		frame.add(panel(), BorderLayout.SOUTH);
		frame.setVisible(true);

	}
	
	// Creates a panel with buttons on it
	private JPanel panel() {
		JPanel panel = new JPanel();
		JButton solve = new JButton("Solve");
		class A implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				player.solve();
			}
		}
		ActionListener a = new A();
		solve.addActionListener(a);
		solve.setPreferredSize(new Dimension(400, 100));
		// I did this part for fun
		solve.setIcon(new Icon(){

			public int getIconHeight() {
				return 100;
			}

			public int getIconWidth() {
				return 400;
			}

			public void paintIcon(Component arg0, Graphics arg1, int arg2,int arg3) {
				Graphics2D g = (Graphics2D) arg1;
				BufferedImage image;
				try {
					image = ImageIO.read(new File("images/solveButton.png"));
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}});

		panel.add(solve);
		return panel;
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
				int originalX;
				int originalY;
				boolean startedOnBank = false;
				int bankX;
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
					
					originalX = (int) Math.round(p.getPosition().getX() / 70) + 1;
					originalY = (int) Math.round(p.getPosition().getY() / 70) + 2;
					
					if(originalY == 4) { // if the piece you  picked up is in the bank, then onBank is true, otherwise it's false :(
						startedOnBank = true;
					} else {
						startedOnBank = false;
					}
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					int releaseX = (int) Math.round(p.getPosition().getX() / 70) + 1;
					int releaseY = (int) Math.round(p.getPosition().getY() / 70) + 2;
					bankX = releaseX + 5; // if you're plopping it in the bank you'll need to know where to put it :) - this provides an index of where you're placing it
					boolean onGrid; // keeps track of whether or not you're dropping onto the grid, as well as simplifying the impending series of if statements
					
					if(releaseY >= 0 && releaseY < player.getGrid().getHeight() && releaseX >= 0 && releaseX < player.getGrid().getWidth()) onGrid = true;
					else onGrid = false;
					
					// if the piece you had picked up was in the bank, you can place it where you dropped it
					if(startedOnBank) {
						if(player.canPlace(releaseX, releaseY, p)) {
							player.place(releaseX, releaseY, p);
						}
					} else { // otherwise, if you try to put it in the grid and it's in the grid already, you have to remove it from the grid, and replace it where you want
						if(onGrid) {
							if(player.canPlace(releaseX, releaseY, p)) {
								player.remove(originalX, originalY);
								player.place(releaseX, releaseY, p);
							}
						} else if(releaseY == 4) { // if you're putting it in the bank then IT WILL DO IT
							player.remove(originalX, originalY);
						}
					}
					
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
						if(e.getPreciseWheelRotation() == -1) {
							p.rotate();
							changed = p;
						} else if(e.getPreciseWheelRotation() == 1) {
							p.rotateTheWayOppositeOfTheOtherRotateMethod();
							changed = p;
						}
						
					}
				}
			});
		}
		private PuzzlePiece changed = null; // to be used l8r, keeps track of the puzzle piece you just rotated
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
						if(changed!= null) {
							if(player.getGrid().getCell(i, j).equals(changed)) {
								player.remove(i, j);
								if(player.canPlace(i, j, changed)) {
									player.place(i, j, changed);
								}
							}
						}
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