/**
 * Constants: CLUBS_IN = -1 CLUBS_OUT = 1 DIAMONDS_IN = - 2 DIAMONDS_OUT = 2
 * HEARTS_IN = - 3 HEARTS_OUT = 3 SPADES_IN = - 4 SPADES_OUT = 4 Constructor:
 * (int north, int east, int south, int west) // from side constants void
 * rotate() turns clockwise 90deg void
 * rotateTheWayOppositeOfTheOtherRotateMethod() // yolo turns counter clockwise
 * 90deg int getSide(direction constant) mod the constant to wrap values Public
 * Static Data NORTH EAST SOUTH WEST
 */
public class PuzzlePiece {
	public static final int CLUBS_IN = -1;
	public static final int CLUBS_OUT = 1;
	public static final int DIAMONDS_IN = -2;
	public static final int DIAMONDS_OUT = 2;
	public static final int HEARTS_IN = -3;
	public static final int HEARTS_OUT = 3;
	public static final int SPADES_IN = -4;
	public static final int SPADES_OUT = 4;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public PuzzlePiece(int north, int east, int south, int west) {

	}

	public void rotate() {

	}

	public void rotateTheWayOppositeOfTheOtherRotateMethod() // yolo
	{

	}

	public int getSide(int direction) {
		return 0;
	}
}
