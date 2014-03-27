import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
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
	
	public static void main(String[] args) {
		new Display();
	}

	/**
	 * Creates a new Display, opening a window and doing everything.
	 */
	public Display() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocation(10, 10);
		frame.add(new PuzzleDrawingComponent(this));
		frame.setVisible(true);
		
		
	}

}

/**
 * A component to draw the puzzle and its pieces.
 * 
 */
class PuzzleDrawingComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7063163051251953145L;
	private long lastTick = 0;
	Display display;

	public PuzzleDrawingComponent(Display display) {
		this.display = display;
		this.addMouseListener(new MouseListener() {

			/*
			 * Okay, so here's what I'm thinking: When you click: selects the
			 * piece you want to drag. When you're pressing the mouse down:
			 * dragging the piece to where your mouse is When you release: Place
			 * the puzzle piece on the correct spot on the grid or bank, if it's
			 * not on the grid or bank then it will just put it back where it
			 * was
			 * 
			 * We need to know where the grid and bank are, which shouldn't be a
			 * problem. With that- we need to call place(..blah..) or
			 * remove(..blah..) appropriately depending on where the piece is.
			 * That's all I'm doing for now... :)
			 * 
			 * We should also record where on the piece we clicked in the first
			 * place so we can drag on that position.
			 */
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
		// EXAMPLE ON HOW TO DRAW AN IMAGE
		// try {
		// g.drawImage(ImageIO.read(new File("images/piece_1.png")), 0, 0,
		// null);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Draw background

		long currentTime = new Date().getTime();
		double timeElapsed = (lastTick == 0 ? 0 : (currentTime - lastTick)) * 0.001;
		lastTick = currentTime;
	}
}
