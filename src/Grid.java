/*
 * AUTHOR: Hunter John Larco, John Bieber
 * CLASS: Grid.java
 * PURPOSE: A dynamically sized grid for the PuzzlePiece class
 */
public class Grid {
	
	/*
	 * PURPOSE: Constructor taking the width and height of the grid respectively
	 * RETURNS: <object Grid>
	 */
	public Grid(int width, int height){
		this.grid = new PuzzlePiece[height][width];
	}
	
	/*
	 * PURPOSE: Returns true if a specified location is within the grid, false if not.
	 * RETURNS: boolean
	 */
	public boolean isValid(int x, int y){
		return x > -1 && x < getWidth() && y > -1 && y < getHeight();
	}
	
	/*
	 * PURPOSE: Sets a location in the grid, specified by x and y, setting any provided PuzzlePiece there.
	 * RETURNS: <object PuzzlePiece> The old puzzle piece in that location or null if there was none.
	 */
	public PuzzlePiece setCell(int x, int y, PuzzlePiece piece){
		if(!isValid(x,y)) return null;
		PuzzlePiece tempPiece = getCell(x,y);
		occupied += !isOccupied(x,y)&&piece!=null?1:isOccupied(x,y)&&piece==null?-1:0;
		grid[y][x] = piece;
		return tempPiece;
	}
	
	/*
	 * PURPOSE: Returns the PuzzlePiece at the specified (x, y) location. Null if there is none.
	 * RETURNS: <object PuzzlePiece>
	 */
	public PuzzlePiece getCell(int x, int y){
		return isValid(x,y) ? grid[y][x] : null;
	}
	
	/*
	 * PURPOSE: Getter for the grid height
	 * RETURNS: int
	 */
	public int getHeight(){
		return grid.length;
	}
	
	/*
	 * PURPOSE: Getter for the grid width
	 * RETURNS: int
	 */
	public int getWidth(){
		return grid[0].length;
	}
	
	/*
	 * PURPOSE: Returns true if the specified (x, y) location contains a PuzzlePiece object. False if not,
	 * 			or the location is invalid.
	 * RETURNS: boolean
	 */
	public boolean isOccupied(int x, int y){
		return getCell(x,y) != null;
	}
	
	/*
	 * PURPOSE: Returns true if every grid location contains a PuzzlePiece, false if not.
	 * RETURNS: boolean
	 */
	public boolean isFull(){
		return occupied == getHeight() * getWidth();
	}
	
	/*
	 * PURPOSE: Clears the grid
	 * RETURNS: void
	 */
	public void clear(){
		this.grid = new PuzzlePiece[getHeight()][getWidth()];
		occupied = 0;
	}
	
	/*
	 * PURPOSE: Returns true if no pieces are in the grid, false if not.
	 * RETURNS: boolean
	 */
	public boolean isEmpty(){
		return occupied == 0;
	}
	
	public String toString(){
		String output = "";
		for(int row=-1; row<=getHeight(); row++){
			for(int col=-1; col<=getWidth(); col++){
				output += isValid(col, row) ? grid[row][col]==null?" ":"¥" : "¥";
				output += col!=getWidth()?" ":"";
			}
			output += row!=getHeight()?"\n":"";
		}
		return output;
	}
	
	/*
	 * PURPOSE: Duplicates the piece
	 * RETURNS: <object Grid>
	 */
	public Grid clone(){
		Grid g = new Grid(getWidth(), getHeight());
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[0].length; j++)
				g.grid[i][j] = grid[i][j];
		return g;
	}
	
	// ---------- PRIVATE DATA ---------- \\
	
	private PuzzlePiece[][] grid;
	private int occupied;
	
}
