
public class Grid {
	
	public Grid(int width, int height){
		this.grid = new PuzzlePiece[height][width];
	}
	
	public boolean isValid(int x, int y){
		return x > -1 && x < getWidth() && y > -1 && y < getHeight();
	}
	
	public PuzzlePiece setCell(int x, int y, PuzzlePiece piece){
		if(!isValid(x,y)) return null;
		PuzzlePiece tempPiece = getCell(x,y);
		occupied += !isOccupied(x,y)&&piece!=null?1:isOccupied(x,y)&&piece==null?-1:0;
		grid[y][x] = piece;
		return tempPiece;
	}
	
	public PuzzlePiece getCell(int x, int y){
		return isValid(x,y) ? grid[y][x] : null;
	}
	
	public int getHeight(){
		return grid.length;
	}
	
	public int getWidth(){
		return grid[0].length;
	}
	
	public boolean isOccupied(int x, int y){
		if(!isValid(x,y)) return false;
		return grid[y][x] != null;
	}
	
	public boolean isFull(){
		return occupied == getHeight() * getWidth();
	}
	
	public void clear(){
		this.grid = new PuzzlePiece[getHeight()][getWidth()];
		occupied = 0;
	}
	
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
	
	public Grid clone(){
		Grid g = new Grid(getWidth(), getHeight());
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[0].length; j++)
				g.grid[i][j] = grid[i][j];
		return g;
	}
	
	public static void main(String[] args){
		Grid g = new Grid(3,3);
		System.out.println(g.isValid(0, 0));
		System.out.println(g.isValid(-1, 0));
		System.out.println(g.isValid(0, -1));
		System.out.println(g.isValid(2, 2));
		System.out.println(g.isValid(3, 2));
		System.out.println(g.isValid(2, 3));
		// isValid works
		System.out.println(g);
		PuzzlePiece p = new PuzzlePiece(1,2,-1,2);
		System.out.println(g.setCell(1,0,p));
		System.out.println(g);
		System.out.println(g.getCell(1, 0)==p);
		System.out.println(g.setCell(1,0,new PuzzlePiece(1,2,-1,2))==p);
		System.out.println(g.getCell(1, 0)==p);
		System.out.println(g);
		g.setCell(1,0,null);
		System.out.println(g);
		// getCell and setCell work
		System.out.println(g.isEmpty());
		System.out.println(g.setCell(1,0,p));
		System.out.println(g.setCell(1,1,p));
		System.out.println(g.setCell(2,2,p));
		System.out.println(g);
		g.clear();
		System.out.println(g);
		System.out.println(g.isEmpty());
		for(int x=0; x<g.getWidth(); x++)
			for(int y=0; y<g.getHeight(); y++){
				g.setCell(x,y,p);
				System.out.println(g.isFull());
			}
		// clear, isEmpty, getHeight, getWidth, and isFull work
		System.out.println(g.isOccupied(1,2));
		g.setCell(1,2,null);
		System.out.println(g.isOccupied(1,2));
		System.out.println(g.isFull());
		// isOccupied works
		System.out.println(g);
		Grid g2 = g.clone();
		g2.setCell(1,2,p);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(g);
		System.out.println();
		System.out.println(g2);
	}
	
	private PuzzlePiece[][] grid;
	private int occupied;
	
}
