/**
 * Associates a Puzzle Piece and an Image
 * 
 * Written March 27, 2014
 * 
 * Ted Bieber, Willy Wu, Rohan Kadambi
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PuzzlePieceImage extends PuzzlePiece {
	// Constructor takes the image path (example: "images/piece_1.png"), takes in a Vector2 position, 
	// a rotation (0 - 359, 360 = 0), and the PuzzlePiece it is associated with
	public PuzzlePieceImage(String imagePath, Vector2 position, int rotation, PuzzlePiece piece) {
		super(piece.getSide(PuzzlePiece.NORTH), piece.getSide(PuzzlePiece.EAST), piece.getSide(PuzzlePiece.SOUTH), piece.getSide(PuzzlePiece.WEST));
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.position = position;
		this.rotation = rotation%360;
	}
	
	
	// returns the rotation of the image (not necessarily the piece)
	public int getRotation() {
		return rotation;
	}
	// sets the rotation of the image (NOT NECESSARILY THE PIECE)
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	
	private int rotation;
	private Vector2 position;
	private BufferedImage image;
	
}
