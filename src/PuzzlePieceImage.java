import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Associates a Puzzle Piece and an Image
 * 
 * Written March 27, 2014
 * 
 * Ted Bieber, Willy Wu, Rohan Kadambi
 */
public class PuzzlePieceImage {
	
	public PuzzlePieceImage(String imagePath, Vector2 vector, int rotation) {
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.vector = vector;
		this.rotation = rotation;
	}
	
	
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public Vector2 getVector() {
		return vector;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	private int rotation;
	private Vector2 vector;
	private BufferedImage image;
	
}
