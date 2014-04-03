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
		grid = new PuzzlePiece[length][width];
	}
	
	public boolean isValid(int x, int y) {
		if(x >= getWidth() || x < 0) return false;
		if(y >= getHeight() || y < 0) return false;
		if(isOccupied(x, y)) return false;
		return true;
	}
	
	public PuzzlePiece setCell(int x, int y, PuzzlePiece piece) {
		grid[y][x] = piece;
		return new PuzzlePiece(0, 0, 0, 0);
	}
	
	public PuzzlePiece getCell(int x, int y) {
		return grid[y][x];
	}
	
	public void clear() {
		grid = new PuzzlePiece[getHeight()][getWidth()];
	}
	
	public int getHeight() {
		return grid.length;
	}
	
	public int getWidth() {
		return grid[0].length;
	}
	
	public boolean isOccupied(int x, int y) {
		if(x >= getWidth() || x < 0) return false;
		if(y >= getHeight() || y < 0) return false;
		return (getCell(x,y) != null);
	}
	
	public boolean isFull() {
		for(int i =0;i < grid.length; i++)
		{
			for(int j = 0; j< grid[i].length; j++)
				if(grid[i][j] == null)
					return false;
		}
		return true;
	}
	
	public boolean isEmpty() {
		for(int i =0;i < grid.length; i++)
		{
			for(int j = 0; j< grid[i].length; j++)
				if(!(grid[i][j] == null))
					return false;
		}
		return true;
	}
	private PuzzlePiece grid[][];
}
