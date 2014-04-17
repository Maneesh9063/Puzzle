import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private boolean needsRelayout = true;
	private long lastTick = 0;
	// Constructor for the display- all you have to do to make this run is construct it
	public Display() {
		// create puzzle pieces
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
		// player.get$Bank();
		// player.get$Bank()[0].rotate(); // currently does not display
		// rotations
		// player.place(0, 0, player.get$Bank()[0]);
		// player.place(1, 1, player.get$Bank()[1]);
		// player.place(2, 2, player.get$Bank()[2]);
		JFrame frame = new JFrame("Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setLocation(10, 10);
//		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(600, 800));
		frame.add(new PuzzleDrawingComponent(this));
		frame.add(panel(), BorderLayout.SOUTH);
		frame.setVisible(true);
		BufferedImage icon;
		try {
			icon = ImageIO.read(new File("images/piece_1.png"));
			frame.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Creates a panel with buttons on it
	private JPanel panel() {
		final JPanel panel = new JPanel();
		JButton solve = new JButton("Solve");
		class A implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(!player.getGrid().isFull()) {
					player.solve();
					needsRelayout = true;
					lastTick = 0;
					pressedSolveButton = true;
					if(player.getGrid().isFull()) JOptionPane.showMessageDialog( new JFrame(), "You've solved it with the button, good job.");
				}
				
								
			}
		}
		ActionListener a = new A();
		solve.addActionListener(a);
		solve.setPreferredSize(new Dimension(200, 100));
		// I did this part for fun
		solve.setIcon(new Icon() {
			public int getIconHeight() {
				return 100;
			}

			public int getIconWidth() {
				return 400;
			}

			public void paintIcon(Component arg0, Graphics arg1, int arg2,
					int arg3) {
				Graphics2D g = (Graphics2D) arg1;
				BufferedImage image;
				try {
					image = ImageIO.read(new File("images/solveButton.png"));
					
					AffineTransform tx = new AffineTransform();
					tx.scale(0.5, 1);
					AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
					image = op.filter(image,null);
					
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton clear = new JButton("Clear");
		class ClearListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(!player.getGrid().isEmpty()) {
					player.clear();
					player.randomize();
					needsRelayout = true;
					pressedSolveButton = false;
				}
			}
		}
		ActionListener clearListener = new ClearListener();
		clear.addActionListener(clearListener);
		clear.setPreferredSize(new Dimension(200, 100));
		// I did this part for fun
		clear.setIcon(new Icon() {
			public int getIconHeight() {
				return 100;
			}

			public int getIconWidth() {
				return 400;
			}

			public void paintIcon(Component arg0, Graphics arg1, int arg2,
					int arg3) {
				Graphics2D g = (Graphics2D) arg1;
				BufferedImage image;
				try {
					image = ImageIO.read(new File("images/clear.png"));
					
					AffineTransform tx = new AffineTransform();
					tx.scale(0.5, 1);
					AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
					image = op.filter(image,null);
					
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(clear);
		panel.add(solve);
		return panel;
	}

	private boolean pressedSolveButton = false;

	/**
	 * A component to draw the puzzle and its pieces.
	 * 
	 */
	class PuzzleDrawingComponent extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = -934843960429454280L;
		private int width = 0;
		private int height = 0;
		private Vector2 tempV2 = new Vector2();
		private PuzzlePieceImage selectedPiece;
		private int pieceInnerWidth = 70; // How wide the inner square is
		// How long a piece's prong is
		private int pieceMargin = (118 - pieceInnerWidth) / 2 + 5;
		private Vector2 mousePosition = new Vector2();
		private Vector2 deltaPos = new Vector2();
		private boolean selectedToGrid = true;
		// Use 1-9 for board locations (easy way out!)
		private int possibleBoardLocations = 0;
		private int selectedOnGrid = -1;

		private final int bankRadius = 170;// How large the bank ring will be
		private final int bankPadding = 70;// How far pieces will be from the
											// ring center
		private final int selectionDistance = 59; // How far away to select the
													// piece
		private final int indicatorWidth = 10;
		private final double transitionDuration = 2;

		// the puzzle piece you just rotated

		// Fun fact: The images are sized 118 x 118 pixels. but they really only
		// take up 70

		// Fun fact: The images are sized 118 x 118 pixels. but they really only
		// take up 70

		public PuzzleDrawingComponent(Display display) {
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					mousePosition.set(e.getX() - width / 2, e.getY() - height
							/ 2);

					double minDistance = Double.POSITIVE_INFINITY;
					for (int i = 0; i < pieces.length; i++) {
						double distance = Math.max(
								Math.abs(pieces[i].getPosition().getX()
										- mousePosition.getX()),
								Math.abs(pieces[i].getPosition().getY()
										- mousePosition.getY()));
						if (distance < minDistance) {
							minDistance = distance;
							selectedPiece = pieces[i];
						}
					}
					if (minDistance > selectionDistance) {
						selectedPiece = null;
					} else {
						selectedPiece.setTransition(1);
						selectedPiece.getTarget().copy(
								selectedPiece.getPosition());
						selectedOnGrid = -1;
						// Compute possible board locations
						for (int i = 0; i < 9; i++) {
							if (player.getGrid().getCell(i % 3, i / 3) == selectedPiece) {
								selectedOnGrid = i;
								break;
							}
						}
						if (selectedOnGrid >= 0) {
							player.remove(selectedOnGrid % 3,
									selectedOnGrid / 3);
						}
						for (int i = 0; i < 9; i++) {
							if (player.canPlace(i % 3, i / 3, selectedPiece)) {
								possibleBoardLocations |= 1 << i;
							}
						}
						if (selectedOnGrid >= 0) {
							player.place(selectedOnGrid % 3,
									selectedOnGrid / 3, selectedPiece);
						}
						selectedToGrid = selectedOnGrid >= 0;
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// keeps track of whether or not you're
					// dropping onto the grid, as well as
					// simplifying the impending series of if
					// statements
					if (selectedPiece != null) {
						if (selectedOnGrid >= 0) {
							// Remove first (if necessary)
							player.remove(selectedOnGrid % 3,
									selectedOnGrid / 3);
						}
						if (selectedToGrid && possibleBoardLocations > 0) {
							int nextBoardLocation = -1;
							double minDistance = Double.POSITIVE_INFINITY;
							for (int i = 0; i < 9; i++) {
								if ((possibleBoardLocations & (1 << i)) != 0) {
									relayout(tempV2, i);
									double distance = tempV2
											.distanceTo(selectedPiece
													.getPosition());
									if (distance < minDistance) {
										minDistance = distance;
										nextBoardLocation = i;
										//
									}
								}
							}
							if (nextBoardLocation >= 0) {
								player.place(nextBoardLocation % 3,
										nextBoardLocation / 3, selectedPiece);
							}
						}
						
						if(player.getGrid().isFull() && !pressedSolveButton) 
							JOptionPane.showMessageDialog(new JFrame(), "Wow, you wasted your time");

						selectedPiece = null;
						// Clear possible board locations
						possibleBoardLocations = 0;
						needsRelayout = true;
					}
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
					if (selectedPiece != null) {
						deltaPos.set(e.getX() - width / 2,
								e.getY() - height / 2).subtract(mousePosition);
						mousePosition.add(deltaPos);
						selectedPiece.setTransition(1);
						selectedPiece.getTarget().add(deltaPos);
						selectedToGrid = selectedPiece.getPosition().length() < bankRadius;
					}
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
							&& selectedPiece != null) {
						if (e.getPreciseWheelRotation() == -1) {
							selectedPiece.rotate();
						} else if (e.getPreciseWheelRotation() == 1) {
							selectedPiece
									.rotateTheWayOppositeOfTheOtherRotateMethod();
						}
					}
				}
			});
		}

		private final Color[] colorArray = { Color.WHITE, Color.BLACK,
				Color.RED, Color.GREEN, Color.BLUE };
		private double color = 0;

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

			// Time!
			long currentTime = new Date().getTime();
			double timeElapsed = (lastTick == 0 ? 0 : (currentTime - lastTick)) * 0.001;
			lastTick = currentTime;

			// draw the pieces in the grid (and/or)
			// for testing this it would be soooo helpful to actually have a
			// working grid&//player class
			width = this.getWidth();
			height = this.getHeight();
			if (player.getGrid().isFull()) {
				
			} else {
				g.setColor(Color.WHITE);

			}
			// g.fillRect(0, 0, width, height);

			// Draw a Box around the grid. This is alllll hardcoded but I think
			// its needed in some form
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.BLUE);
			if (selectedPiece != null) {
				if (selectedToGrid != (selectedOnGrid >= 0)) {
					if (selectedToGrid) {
						g.drawRect(width / 2 - pieceInnerWidth * 3 / 2
								- pieceMargin, height / 2 - pieceInnerWidth * 3
								/ 2 - pieceMargin, pieceInnerWidth * 3
								+ pieceMargin * 2, pieceInnerWidth * 3
								+ pieceMargin * 2);
					} else {
						g.drawArc(width / 2 - bankRadius, height / 2
								- bankRadius, bankRadius * 2, bankRadius * 2,
								0, 360);
					}
				}
			}
			for (int i = 0; i < 9; i++) {
				if (selectedPiece == null
						|| (possibleBoardLocations & (1 << i)) != 0) {
					relayout(tempV2, i);
					g.drawArc((int) tempV2.getX() + width / 2 - indicatorWidth
							/ 2, (int) tempV2.getY() + height / 2
							- indicatorWidth / 2, indicatorWidth,
							indicatorWidth, 0, 360);
				}
			}
			for (int i = 0; i < player.getGrid().getHeight(); i++) {
				for (int j = 0; j < player.getGrid().getWidth(); j++) {
					if (player.getGrid().isOccupied(i, j)) {
						if (needsRelayout) {
							PuzzlePieceImage piece = (PuzzlePieceImage) player
									.getGrid().getCell(i, j);
							piece.setTransition(0);
							piece.getSource().copy(piece.getPosition());
							relayout(piece.getTarget(), i + 3 * j);
						}
					}
				}
			}
			for (int i = 0; i < player.get$Bank().length; i++) {
				if (player.get$Bank()[i] != null) {
					if (needsRelayout) {
						PuzzlePieceImage piece = (PuzzlePieceImage) player
								.get$Bank()[i];
						piece.setTransition(0);
						piece.getSource().copy(piece.getPosition());
						relayout(piece.getTarget(), i, player.get$Bank().length);
					}
				}
			}
			needsRelayout = false;
			// Animation
			for (int i = 0; i < pieces.length; i++) {
				PuzzlePieceImage piece = (PuzzlePieceImage) pieces[i];
				if (piece.getTransition() < 1) {
					piece.setTransition(Math.min(piece.getTransition()
							+ timeElapsed / transitionDuration, 1));
					double scaled = 1.0 - Math.pow(0.2,
							10 * piece.getTransition());
					piece.getPosition().copy(piece.getSource())
							.lerp(piece.getTarget(), scaled);
				} else {
					piece.getPosition().copy(piece.getTarget());
				}
				// Make the angle change properly
				if (Math.abs((piece.getVisualRotation() - 360)
						- piece.getRotation()) <= 180) {
					piece.setVisualRotation(piece.getVisualRotation() - 360);
				} else if (Math.abs((piece.getVisualRotation() + 360)
						- piece.getRotation()) < 180) {
					piece.setVisualRotation(piece.getVisualRotation() + 360);
				}
				piece.setVisualRotation(piece.getVisualRotation() * 0.97 + 0.03
						* piece.getRotation());
				piece.updateVisualRotation();
				// draw the pieces in the bank
				drawPiece(piece, g);
			}
			if(celebrationCounter >= 150 && player.getGrid().isFull() && pressedSolveButton == false) {
				for(int i = 0; i < pieces.length; i++) {
					pieces[i].setVisualRotation(new Random().nextInt(360));
				}
				celebrationCounter = 0;
			}
			
			celebrationCounter++;
			this.repaint();
		}
		private int celebrationCounter = 0;
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
		 * Changes a given vector to a given (g:1-9) relative position in the
		 * grid.
		 * 
		 * @param vector
		 * @param g
		 */
		private void relayout(Vector2 vector, int g) {
			if (vector == null) {
				return;
			}
			vector.set(pieceInnerWidth * (g % 3 - 1), pieceInnerWidth
					* (g / 3 - 1));
		}

		/**
		 * Changes a given vector to a given (b/length) relative position in the
		 * bank
		 * 
		 * @param vector
		 * @param b
		 * @param length
		 */
		private void relayout(Vector2 vector, int b, int length) {
			if (vector == null) {
				return;
			}
			vector.set(0, -bankRadius - bankPadding).rotate(
					-Math.PI * 2 * ((double) b / length));
		}
	}
	public static void main(String[] args) {
		new Display();
	}
}