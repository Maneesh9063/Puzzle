import java.util.ArrayList;


public class Player {

	public Player(Grid grid, PuzzlePiece[] pieces){
		this.grid = grid;
		for(PuzzlePiece p : pieces)
			bank.add(p);
	}

	public boolean solve(){
		// ensure that the board is cleared before solving
		for(int x=0; x<grid.getWidth(); x++)
			for(int y=0; y<grid.getHeight(); y++)
				remove(x,y);
		// if this returns false then the puzzle is not solvable
		return pSolve(0);
	}
	private boolean pSolve(int index){
		if(grid.isFull()) return true;
		int y = index/grid.getWidth();
		int x = index-index/grid.getWidth()*grid.getWidth();
		for(int i=bank.size()-1; i>-1; i--){
			for(int j=0; j<3; j++){
				if(place(x,y,bank.get(i))){
					if(pSolve(index+1)) return true;
					remove(x,y);
				}
				bank.get(i).rotate();
			}
		}
		return false;
	}

	public Grid getGrid(){
		return grid;
	}

	public PuzzlePiece[] get$Bank(){
		PuzzlePiece[] out = new PuzzlePiece[bank.size()];
		for(int i=0; i<out.length; i++) out[i] = bank.get(i);
		return out;
	}

	public boolean canPlace(int x, int y, PuzzlePiece piece){
		if(!grid.isValid(x, y) || grid.getCell(x, y) != null) return false;
		if(grid.getCell(x, y-1) != null && grid.getCell(x, y-1).getSide(PuzzlePiece.SOUTH)+piece.getSide(PuzzlePiece.NORTH)!=0) return false;
		if(grid.getCell(x, y+1) != null && grid.getCell(x, y+1).getSide(PuzzlePiece.NORTH)+piece.getSide(PuzzlePiece.SOUTH)!=0) return false;
		if(grid.getCell(x-1, y) != null && grid.getCell(x-1, y).getSide(PuzzlePiece.EAST)+piece.getSide(PuzzlePiece.WEST)!=0) return false;
		if(grid.getCell(x+1, y) != null && grid.getCell(x+1, y).getSide(PuzzlePiece.WEST)+piece.getSide(PuzzlePiece.EAST)!=0) return false;
		return true;
	}

	public boolean place(int x, int y, PuzzlePiece piece){
		if(canPlace(x,y,piece)){
			bank.remove(piece);
			grid.setCell(x,y,piece);
			return true;
		}
		return false;
	}

	public boolean remove(int x, int y){
		return grid.getCell(x, y) != null && grid.isValid(x, y) ? bank.add(grid.setCell(x,y,null)) : false;
	}
	
	public String toString(){
		return grid.toString();
	}

	public static void main(String[] args){
		Grid grid = new Grid(3,3);
		PuzzlePiece piece1 = new PuzzlePiece(PuzzlePiece.CLUBS_OUT,PuzzlePiece.HEARTS_OUT,PuzzlePiece.DIAMONDS_IN,PuzzlePiece.CLUBS_IN);
		PuzzlePiece piece2 = new PuzzlePiece(PuzzlePiece.SPADES_OUT,PuzzlePiece.DIAMONDS_OUT,PuzzlePiece.SPADES_IN,PuzzlePiece.HEARTS_IN);
		PuzzlePiece piece3 = new PuzzlePiece(PuzzlePiece.HEARTS_OUT,PuzzlePiece.SPADES_OUT,PuzzlePiece.SPADES_IN,PuzzlePiece.CLUBS_IN);
		PuzzlePiece piece4 = new PuzzlePiece(PuzzlePiece.HEARTS_OUT,PuzzlePiece.DIAMONDS_OUT,PuzzlePiece.CLUBS_IN,PuzzlePiece.CLUBS_IN);
		PuzzlePiece piece5 = new PuzzlePiece(PuzzlePiece.SPADES_OUT,PuzzlePiece.SPADES_OUT,PuzzlePiece.HEARTS_IN,PuzzlePiece.CLUBS_IN);
		PuzzlePiece piece6 = new PuzzlePiece(PuzzlePiece.HEARTS_OUT,PuzzlePiece.DIAMONDS_OUT,PuzzlePiece.DIAMONDS_IN,PuzzlePiece.HEARTS_IN);
		PuzzlePiece piece7 = new PuzzlePiece(PuzzlePiece.SPADES_OUT,PuzzlePiece.DIAMONDS_OUT,PuzzlePiece.HEARTS_IN,PuzzlePiece.DIAMONDS_IN);
		PuzzlePiece piece8 = new PuzzlePiece(PuzzlePiece.CLUBS_OUT,PuzzlePiece.HEARTS_OUT,PuzzlePiece.SPADES_IN,PuzzlePiece.HEARTS_IN);
		PuzzlePiece piece9 = new PuzzlePiece(PuzzlePiece.CLUBS_OUT,PuzzlePiece.CLUBS_IN,PuzzlePiece.DIAMONDS_IN,PuzzlePiece.DIAMONDS_OUT);
		// These pieces are specialized to be the ones Mr. Marshall gave us.
		PuzzlePiece[] bank2 = {piece1,piece2,piece3,piece4,piece5,piece6,piece7,piece8,piece9};
		Player player = new Player(grid,bank2);

		System.out.println(player.solve());
		
		System.out.println(player);
	}

	private Grid grid;
	private ArrayList<PuzzlePiece> bank = new ArrayList<PuzzlePiece>();

}
