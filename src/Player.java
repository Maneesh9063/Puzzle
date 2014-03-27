/*
 * variables
 * 	grid
 * 	pieces in bank
 * void solve()
 * Constructor: (Grid grid, PuzzlePiece[] pieces)
 * Grid getGrid()
 * PuzzlePiece[] get$Bank()
 * boolean canPlace(int x, int y, PuzzlePiece piece)
 *		returns false if invalid location or does not fit
 * 		checks left, top, bottom, right and see if parts align
 * 		returns true if specified location is valid for piece
 * boolean place(int x, int y, PuzzlePiece piece)
 * 		returns true only if the piece was places, false if it couldn’t be
 * boolean remove(int x, int y)
 * 		returns true if a piece was removed
 */
public class Player {
	
	public Player(Grid grid, PuzzlePiece[] pieces) {
		
	}
	
	public void solve() {
		
	}
	
	public Grid getGrid() {
		return new Grid(0, 0);
	}
	
	public PuzzlePiece[] get$Bank() {
		return new PuzzlePiece[9];
	}
	
	public boolean canPlace(int x, int y, PuzzlePiece piece) {
		return true;
	}
	
	public boolean place(int x, int y, PuzzlePiece piece) {
		return true;
	}

	public boolean remove(int x, int y) {
		return true;
	}
}
