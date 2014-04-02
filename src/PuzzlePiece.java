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
	public static final int EAST = 90;
	public static final int SOUTH = 180;
	public static final int WEST = 270;

	private int north, east, south, west;
	public PuzzlePiece(int north, int east, int south, int west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	public void rotate() {
		int temp = this.north;
		this.north = this.west;
		this.west = this.south;
		this.south = this.east;
		this.east = temp;
	}

	public void rotateTheWayOppositeOfTheOtherRotateMethod() // yolo
	{
		int temp = this.north;
		this.north = this.east;
		this.east = this.south;
		this.south = this.west;
		this.west = temp;
	}

	public int getSide(int direction) {
		if(direction == NORTH)
			return this.north;
		if(direction == EAST)
			return this.east;
		if(direction == SOUTH)
			return this.south;
		if(direction == WEST)
			return this.west;
		return 0;
	}
	public String toString()
	{
		String s = new String();
		s+= "NORTH:" + this.getSide(NORTH) + "\n";
		s+= "EAST:" + this.getSide(EAST) + "\n";
		s+= "SOUTH:" + this.getSide(SOUTH) + "\n";
		s+= "WEST:" + this.getSide(WEST) + "\n";
		return s;
		
	}
}
