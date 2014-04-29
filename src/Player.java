import java.util.ArrayList;

/*
 * PURPOSE: A class that creates a Grid object and applies standard puzzle rules,
 * 			manipulates the data to simulate a puzzle game on the Grid instance.
 * AUTHOR: Hunter John Larco, John Bieber
 * CLASS: Player.java
 */
public class Player {

	/*
	 * PURPOSE: Constructor receiving the grid to play on and the pieces one may use.
	 * RETURNS: <object Player>
	 */
	public Player(Grid grid, PuzzlePiece[] pieces){
		this.grid = grid;
		for(PuzzlePiece p : pieces)
			bank.add(p);
	}
	
	/*
	 * PURPOSE: Clears the board
	 * RETURNS: void
	 */
	public void clear(){
		for(int x=0; x<grid.getWidth(); x++)
			for(int y=0; y<grid.getHeight(); y++)
				remove(x,y);
	}
	
	/*
	 * PURPOSE: Randomizes the rotations of the puzzle pieces
	 * RETURNS: void
	 */
	public void randomize(){
		for(PuzzlePiece p : bank)
			for(int i=0; i<(int)(Math.random()*4); i++)
					p.rotate();
	}

	/*
	 * PURPOSE: Solves the puzzle and moves the pieces into their correct locations.
	 * 			Recursively branches using backtracking until a correct solution is found.
	 * RETURNS: boolean
	 */
	public boolean solve(){
		// ensure that the board is cleared before solving
		clear();
		// if this returns false then the puzzle is not solvable
		return pSolve(0);
	}
	private boolean pSolve(int index){
		if(grid.isFull()) return true;
		int y = index/grid.getWidth();
		int x = index-index/grid.getWidth()*grid.getWidth();
		for(int i=bank.size()-1; i>-1; i--){
			for(int j=0; j<4; j++){
				if(place(x,y,bank.get(i))){
					if(pSolve(index+1)) return true;
					remove(x,y);
				}
				bank.get(i).rotate();
			}
		}
		return false;
	}

	/*
	 * PURPOSE: Returns the raw Grid instance that the pieces are organized and held in.
	 * RETURNS: <object Grid>
	 */
	public Grid getGrid(){
		return grid;
	}

	/*
	 * PURPOSE: Returns all the pieces currently not fitted on the board
	 * RETURNS: PuzzlePiece[]
	 */
	public PuzzlePiece[] get$Bank(){
		PuzzlePiece[] out = new PuzzlePiece[bank.size()];
		for(int i=0; i<out.length; i++) out[i] = bank.get(i);
		return out;
	}

	/*
	 * PURPOSE: Returns whether if the provided PuzzlePiece fill fit at the given location (x, y)
	 * 			based on the surrounding pieces and location validity.
	 * RETURNS: boolean
	 */
	public boolean canPlace(int x, int y, PuzzlePiece piece){
		if(!grid.isValid(x, y) || grid.getCell(x, y) != null) return false;
		if(grid.getCell(x, y-1) != null && grid.getCell(x, y-1).getSide(PuzzlePiece.SOUTH)+piece.getSide(PuzzlePiece.NORTH)!=0) return false;
		if(grid.getCell(x, y+1) != null && grid.getCell(x, y+1).getSide(PuzzlePiece.NORTH)+piece.getSide(PuzzlePiece.SOUTH)!=0) return false;
		if(grid.getCell(x-1, y) != null && grid.getCell(x-1, y).getSide(PuzzlePiece.EAST)+piece.getSide(PuzzlePiece.WEST)!=0) return false;
		if(grid.getCell(x+1, y) != null && grid.getCell(x+1, y).getSide(PuzzlePiece.WEST)+piece.getSide(PuzzlePiece.EAST)!=0) return false;
		return true;
	}

	/*
	 * PURPOSE: Places a given piece at the given location (x, y)
	 * RETURNS: boolean ~ whether the piece was actually placed, it will not be if it does not fit.
	 */
	public boolean place(int x, int y, PuzzlePiece piece){
		if(canPlace(x,y,piece)){
			bank.remove(piece);
			grid.setCell(x,y,piece);
			return true;
		}
		return false;
	}

	/*
	 * PURPOSE: Removes a piece from the board at the given location (x, y)
	 * RETURNS: boolean ~ whether or not a piece could be removed
	 */
	public boolean remove(int x, int y){
		return grid.getCell(x, y) != null && grid.isValid(x, y) ? bank.add(grid.setCell(x,y,null)) : false;
	}
	
	public String toString(){
		return grid.toString();
	}
	
	// ---------- PRIVATE DATA ---------- \\

	private Grid grid;
	private ArrayList<PuzzlePiece> bank = new ArrayList<PuzzlePiece>();

}
