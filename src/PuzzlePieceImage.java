/**
 * Associates a Puzzle Piece and an Image
 * 
 * Written March 27, 2014
 * 
 * Ted Bieber, Willy Wu, Rohan Kadambi
 */

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PuzzlePieceImage extends PuzzlePiece {
	// Constructor takes the image path (example: "images/piece_1.png"), takes
	// in a Vector2 position,
	// a visualRotation (0 - 359, 360 = 0), and the PuzzlePiece it is associated
	// with
	public PuzzlePieceImage(String imagePath, Vector2 position, int rotation,
			PuzzlePiece piece) {
		super(piece.getSide(PuzzlePiece.NORTH),
				piece.getSide(PuzzlePiece.EAST), piece
						.getSide(PuzzlePiece.SOUTH), piece
						.getSide(PuzzlePiece.WEST));
		try {
			originalImage = ImageIO.read(new File(imagePath));
			image = new BufferedImage(originalImage.getWidth(),
					originalImage.getHeight(), originalImage.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.position = position;
		this.visualRotation = rotation % 360;
		this.updateVisualRotation();
	}

	public void updateVisualRotation() {
		AffineTransform tx = new AffineTransform();
		int w = image.getWidth();
		int h = image.getHeight();
		double angle = Math.PI * this.getVisualRotation() / 180;
		tx.rotate(angle, w / 2, h / 2);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		image = op.filter(originalImage, image);

	}

	// returns the visualRotation of the image (not necessarily the piece)
	public double getVisualRotation() {
		return visualRotation;
	}

	// sets the visualRotation of the image (NOT NECESSARILY THE PIECE)
	public void setVisualRotation(double visualRotation) {
		this.visualRotation = visualRotation;
	}

	public Vector2 getPosition() {
		return position;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Vector2 getTarget() {
		return target;
	}

	public void setTarget(Vector2 target) {
		this.target = target;
	}

	/**
	 * @return the transition
	 */
	public double getTransition() {
		return transition;
	}

	/**
	 * @param transition
	 *            the transition to set
	 */
	public void setTransition(double transition) {
		this.transition = transition;
	}

	/**
	 * @return the source
	 */
	public Vector2 getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Vector2 source) {
		this.source = source;
	}

	private double visualRotation;
	private Vector2 position;
	private BufferedImage image;
	private BufferedImage originalImage;
	private Vector2 source = new Vector2();
	private Vector2 target = new Vector2();
	private double transition = 0;

}
