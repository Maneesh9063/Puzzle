import java.util.ArrayList;

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
 * 		returns true only if the piece was places, false if it couldn't be
 * boolean remove(int x, int y)
 * 		returns true if a piece was removed
 */
public class Player {

	public Player(Grid grid, PuzzlePiece[] pieces) {
		this.grid = grid;
		this.pieces = pieces;
		for (int i = 0; i < pieces.length; i++)
			bank.add(pieces[i]);
	}
	public boolean solve(){
		for(int i = 0; i<grid.getHeight(); i++)
			for(int j = 0; j< grid.getWidth(); j++)
				if(grid.getCell(j,i) != null)
					remove(j,i);
		return solveh();
		
	}
	public boolean solveh() {
		if (grid.isFull())
			return true;
		for (int i = bank.size() - 1; i > -1; i--) {
			for (int j = 0; j < 4; j++) {
				bank.get(i).rotate();
				if (place(nextSpot()[0], nextSpot()[1], bank.get(i))) {
					solveh();
					if (grid.isFull())
						return true;
					bank.add(i, grid.getCell(lastPlaced()[0], lastPlaced()[1]));
					grid.setCell(lastPlaced()[0], lastPlaced()[1], null);
				}
			}

		}
		return false;

	}

	public int[] nextSpot() {
		for (int i = 0; i < grid.getHeight(); i++)
			for (int j = 0; j < grid.getWidth(); j++)
				if (grid.getCell(i, j) == null)
					return new int[] { i, j };
		return new int[] { 0, 0 };
	}

	public int[] lastPlaced() {
		for (int i = grid.getHeight() - 1; i > -1; i--)
			for (int j = grid.getWidth() - 1; j > -1; j--)
				if (grid.getCell(i, j) != null)
					return new int[] { i, j };
		return new int[] { grid.getHeight() - 1, grid.getWidth() - 1 };
	}

	public Grid getGrid() {
		return this.grid;
	}

	public PuzzlePiece[] get$Bank() {
		PuzzlePiece[] temp = new PuzzlePiece[bank.size()];
		for (int i = 0; i < bank.size(); i++)
			temp[i] = bank.get(i);
		return temp;
	}

	public boolean canPlace(int x, int y, PuzzlePiece piece) {
		if (grid.isValid(x, y)) {
			// check north
			boolean north;
			if (adjacentSide(x, y, PuzzlePiece.NORTH) == 0)
				north = true;
			else
				north = piece.getSide(PuzzlePiece.NORTH)
						+ adjacentSide(x, y, PuzzlePiece.NORTH) == 0;
			// check east
			boolean east;
			if (adjacentSide(x, y, PuzzlePiece.EAST) == 0)
				east = true;
			else
				east = piece.getSide(PuzzlePiece.EAST)
						+ adjacentSide(x, y, PuzzlePiece.EAST) == 0;
			// check west
			boolean south;
			if (adjacentSide(x, y, PuzzlePiece.SOUTH) == 0)
				south = true;
			else
				south = piece.getSide(PuzzlePiece.SOUTH)
						+ adjacentSide(x, y, PuzzlePiece.SOUTH) == 0;
			// check south
			boolean west;
			if (adjacentSide(x, y, PuzzlePiece.WEST) == 0)
				west = true;
			else
				west = piece.getSide(PuzzlePiece.WEST)
						+ adjacentSide(x, y, PuzzlePiece.WEST) == 0;
			return north && east && south && west;
		}
		return false;
	}

	public boolean place(int x, int y, PuzzlePiece piece) {
		if (grid.isValid(x, y) && canPlace(x, y, piece)) {
			grid.setCell(x, y, piece);
			bank.remove(piece);
			return true;
		}
		return false;
	}

	public boolean remove(int x, int y) {
		bank.add(grid.getCell(x, y));
		grid.setCell(x, y, null);
		return true;
	}

	public int adjacentSide(int x, int y, int direction) {
		if (direction == PuzzlePiece.NORTH) {
			if (grid.isOccupied(x, y - 1))
				return grid.getCell(x, y - 1).getSide(PuzzlePiece.SOUTH);
			return 0;
		}
		if (direction == PuzzlePiece.EAST) {
			if (grid.isOccupied(x + 1, y))
				return grid.getCell(x + 1, y).getSide(PuzzlePiece.WEST);
			return 0;
		}
		if (direction == PuzzlePiece.SOUTH) {
			if (grid.isOccupied(x, y + 1))
				return grid.getCell(x, y + 1).getSide(PuzzlePiece.NORTH);
			return 0;
		}
		if (direction == PuzzlePiece.WEST) {
			if (grid.isOccupied(x - 1, y))
				return grid.getCell(x - 1, y).getSide(PuzzlePiece.EAST);
			return 0;
		}
		return 0;

	}

	private Grid grid;
	private PuzzlePiece[] pieces;
	private ArrayList<PuzzlePiece> bank = new ArrayList<PuzzlePiece>();

	public static void main(String[] args) {
		PuzzlePiece[] puzzlePieces = {
				new PuzzlePiece(PuzzlePiece.CLUBS_OUT, PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.SPADES_IN,
						PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT, PuzzlePiece.SPADES_OUT,
						PuzzlePiece.SPADES_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.CLUBS_IN,
						PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT, PuzzlePiece.SPADES_OUT,
						PuzzlePiece.HEARTS_IN, PuzzlePiece.CLUBS_IN),
				new PuzzlePiece(PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.DIAMONDS_IN,
						PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.SPADES_OUT,
						PuzzlePiece.DIAMONDS_OUT, PuzzlePiece.HEARTS_IN,
						PuzzlePiece.DIAMONDS_IN),
				new PuzzlePiece(PuzzlePiece.CLUBS_OUT, PuzzlePiece.HEARTS_OUT,
						PuzzlePiece.SPADES_IN, PuzzlePiece.HEARTS_IN),
				new PuzzlePiece(PuzzlePiece.DIAMONDS_OUT,
						PuzzlePiece.CLUBS_OUT, PuzzlePiece.CLUBS_IN,
						PuzzlePiece.DIAMONDS_IN) };

		Player p = new Player(new Grid(3, 3), puzzlePieces);
		System.out.println(p.solve());
		// for(int i =0; i<p.getGrid().getHeight(); i++)
		// 	for(int j = 0; j<p.getGrid().getWidth(); j++)
		// 		System.out.println(p.getGrid().getCell(j, i));
		// p.get$Bank()[0].rotateTheWayOppositeOfTheOtherRotateMethod();
		// System.out.println(p.place(0, 1, p.get$Bank()[0]));
		// System.out.println(p.place(0, 0, p.get$Bank()[0]));
		// System.out.println(p.place(-1, -1, p.get$Bank()[0]));
		// System.out.println(p.place(1, 0, p.get$Bank()[0]));
		// int[] a = p.nextSpot();
		// int[] b = p.lastPlaced();
		// for(int i = 0; i<2; i ++)
		// System.out.println(a[i]);
		// for(int i = 0; i<2 ;i++)
		// System.out.println(b[i]);

	}
}
