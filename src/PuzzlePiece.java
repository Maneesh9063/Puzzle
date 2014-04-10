
public class PuzzlePiece {

	private int roDeg = 0;
	private int[] directions = new int[4];
	
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

	public PuzzlePiece(int north, int east, int south, int west){// clubs, diamonds, etc.
		directions[0] = north;
		directions[1] = east;
		directions[2] = south;
		directions[3] = west;
	}
	public void rotate(){
		roDeg = (roDeg+90)%360;//turkey wrap
	}
	
	public int getRotation(){
		return roDeg;
	}

	public void rotateTheWayOppositeOfTheOtherRotateMethod(){// yolo
		for(int i=0; i<3; i++) rotate();
	}

	public int getSide(int direction){
		return directions[(direction+360-roDeg)%360/90];
	}
	
	public String toString(){ // I modified this so that pieces are easier to look at
		char[] letters = {' ','C','D','H','S'};
		String output = "";
		output += getSide(NORTH)>0?"    " + letters[getSide(NORTH)] + "  ":"       ";
		output += getSide(NORTH)>0?"\n  ¥ ¥ ¥  \n":"\n  ¥ " + letters[getSide(NORTH)*-1] + " ¥  \n";
		output += getSide(WEST)>0? letters[getSide(WEST)] + " ¥ ¥ ":"  " + letters[getSide(WEST)*-1] + " ¥ ";
		output += getSide(EAST)>0?"¥ " + letters[getSide(EAST)]: letters[getSide(EAST)*-1] + "  ";
		output += getSide(SOUTH)>0?"\n  ¥ ¥ ¥  \n":"\n  ¥ " + letters[getSide(SOUTH)*-1] + " ¥  \n";
		output += getSide(SOUTH)>0?"    " + letters[getSide(SOUTH)] + "  ":"       ";
		return output;
	}
	
	public static void main(String[] args){
		PuzzlePiece piece = new PuzzlePiece(CLUBS_IN, DIAMONDS_OUT, HEARTS_OUT, SPADES_IN);
		System.out.println(piece);
		System.out.println();
		piece.rotate();
		System.out.println(piece);
		System.out.println();
		piece.rotateTheWayOppositeOfTheOtherRotateMethod();
		System.out.println(piece);
		System.out.println();
	}
	
}
