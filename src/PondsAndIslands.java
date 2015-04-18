import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*********************************************************************************
 * Name: Drew Kim
 * Block: D
 * Date: 11/9/13
 * Program 6: Ponds and Islands
 * This program pulls a map of X's and .'s from a text file. The X's correspond to
 * land and the .'s correspond to water. When the program "flood fills" the map,
 * it uses recursion to check for "islands" and "ponds". Islands are masses of land
 * that are connected. A block of land is connected to any other block of land that
 * is in any of the 8 spaces around it. Ponds are masses of water that are connected.
 * A block of water is connected to any other block of water that is above, below, 
 * or to the sides of it. The flood fill detects for these and replaces them with 
 * characters corresponding to a single mass.
 ***********************************************************************************/
public class PondsAndIslands 
{
	static final String FILE_NAME = "map.txt";
	static final char WATER = '.';
	static final char LAND = 'X';

	/**
	 * The main calls a set of commands necessary to execute the program.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Scanner console = getFile();
		int num_rows = console.nextInt();
		int num_cols = console.nextInt();
		Cell[][] grid = new Cell[num_rows][num_cols];
		console.nextLine();
		fillArray(console, num_rows, num_cols, grid);
		printMap(grid, num_rows, num_cols);
		floodFill(grid, num_rows, num_cols);
		printID(grid, num_rows, num_cols);
	}
	
	/**
	 * Prints the map from map.txt
	 * @param grid
	 * @param num_rows
	 * @param num_cols
	 */
	public static void printMap(Cell[][] grid, int num_rows, int num_cols)
	{
		for(int row = 0; row < num_rows; row++)
		{
			for(int col = 0; col < num_cols; col++)
			{
				System.out.print(grid[row][col].substance + " ");
			}
			System.out.println();
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Prints the ID of the cell
	 * @param grid
	 * @param num_rows
	 * @param num_cols
	 */
	public static void printID(Cell[][] grid, int num_rows, int num_cols)
	{
		for(int row = 0; row < num_rows; row++)
		{
			for(int col = 0; col < num_cols; col++)
			{
				System.out.print(grid[row][col].id + " ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	/**
	 * Flood fills the map
	 * @param grid
	 * @param num_rows
	 * @param num_cols
	 */
	public static void floodFill(Cell[][] grid, int num_rows, int num_cols)
	{
		char IDForIsland = 'a';
		char IDForPond = '1';
 
		for(int row = 0; row < num_rows; row++)
		{
			for(int col = 0; col < num_cols; col++)
			{
				if(!grid[row][col].tested)
				{
					grid[row][col].tested = true;
					if(grid[row][col].substance == LAND)
					{
						grid[row][col].id = IDForIsland;
						checkForIslands(grid, row, col, IDForIsland, num_rows, num_cols);
						IDForIsland++;
					}
					else if(grid[row][col].substance == WATER)
					{
						grid[row][col].id = IDForPond;
						checkForPonds(grid, row, col, IDForPond, num_rows, num_cols);
						IDForPond++;
					}
				}
			}
		}
	}

	/**
	 * Flood fills the island for the cell tested
	 * @param grid
	 * @param row
	 * @param col
	 * @param IDForIsland
	 * @param num_rows
	 * @param num_cols
	 */
	public static void checkForIslands(Cell[][] grid, int row, int col, char IDForIsland, int num_rows, int num_cols)
	{
		for(int rows = row - 1; rows <= row + 1; rows++)
		{
			for(int cols = col - 1; cols <= col + 1; cols++)
			{
				if(rows != row || cols != col)
				{
					if(rows >= 0 && rows < num_rows && cols >= 0 && cols < num_cols)
					{
						if(grid[rows][cols].substance == LAND)
						{
							if(!grid[rows][cols].tested)
							{
								grid[rows][cols].tested = true;
								grid[rows][cols].id = IDForIsland;
								checkForIslands(grid, rows, cols, IDForIsland, num_rows, num_cols);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Flood fills the pond for the cell tested
	 * @param grid
	 * @param row
	 * @param col
	 * @param IDForPond
	 * @param num_rows
	 * @param num_cols
	 */
	public static void checkForPonds(Cell[][] grid, int row, int col, char IDForPond, int num_rows, int num_cols)
	{
		for(int rows = row - 1; rows <= row + 1; rows++)
		{
			for(int cols = col - 1; cols <= col + 1; cols++)
			{
				if(rows != row || cols != col)
				{
					if(rows >= 0 && rows < num_rows && cols >= 0 && cols < num_cols)
					{
						if(!(rows == row - 1 && cols == col - 1) && !(rows == row + 1 && cols == col + 1) && !(rows == row + 1 && cols == col - 1) && !(rows == row + 1 && cols == col + 1))
						{
							if(grid[rows][cols].substance == WATER)
							{
								if(!grid[rows][cols].tested)
								{
									grid[rows][cols].tested = true;
									grid[rows][cols].id = IDForPond;
									checkForPonds(grid, rows, cols, IDForPond, num_rows, num_cols);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Fills the array with cells and assigns its data
	 * @param console
	 * @param num_rows
	 * @param num_cols
	 * @param grid
	 */
	public static void fillArray(Scanner console, int num_rows, int num_cols, Cell[][] grid)
	{
		for(int row = 0; row < num_rows; row++)
		{
			String str = console.nextLine();
			for(int col = 0; col < num_cols; col++)
			{
				grid[row][col] = new Cell();
				char ch = str.charAt(col);
				if(ch == LAND)
					grid[row][col].substance = LAND;
				else
					grid[row][col].substance = WATER;
				grid[row][col].tested = false;
			}
		}
	}

	/**
	 * Gets the file of commands
	 * @return
	 */
	public static Scanner getFile()
	{
		Scanner console = null;
		try 
		{
			// Open the file.  Note that Eclipse looks for the file in your 
			//	workspace inside your project folder.
			console = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));

		}
		catch (IOException e)
		{	
			// Something went wrong!
			System.out.println("File error - file not found, could not be " +
					"opened or could not be read.");
		}
		System.out.println("Done opening the file!");
		System.out.println();
		return console;
	}

}

/**
 * Cell class. Keeps the
 * @author akim
 *
 */
class Cell
{
	boolean tested;
	char substance;
	char id;
}