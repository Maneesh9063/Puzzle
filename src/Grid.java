/*
 * Grid (3x3/ rectangle) 
 * Constructor: (int width, int height)
 * boolean isValid(int x, int y)
 * 		(0,0) is top left
 * PuzzlePiece setCell(int x, int y, PuzzlePiece piece)
 * 		returns old data
 * 		if not valid return null
 * PuzzlePiece getCell(int x, int y)
 * 		return null if not valid
 * void clear()
 * int getHeight()
 * int getWidth()
 * boolean isOccupied(int x, int y)
 * boolean isFull()
 * 		returns true if each location in the grid contains a PuzzlePiece
 * boolean isEmpty()

 */

public class Grid {
	public Grid(int length, int width) {
		
	}
	
	public boolean isValid(int x, int y) {
		return true;
	}
	
	public PuzzlePiece setCell(int x, int y, PuzzlePiece piece) {
		return new PuzzlePiece(0, 0, 0, 0);
	}
	
	public PuzzlePiece getCell(int x, int y) {
		return new PuzzlePiece(0, 0, 0, 0);
	}
	
	public void clear() {
		
	}
	
	public int getHeight() {
		return 0;
	}
	
	public int getWidth() {
		return 0;
	}
	
	public boolean isOccupied(int x, int y) {
		return true;
	}
	
	public boolean isFull() {
		return false;
	}
	
	public boolean isEmpty() {
		return false;
	}
}
