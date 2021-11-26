import java.util.Random;

public class OceanBoard extends Battleship_Game
{

	public static char[][] ocean = new char[10][10] ; 	// creates an ocean board for players to place their ships

	public OceanBoard()		// constructor for the OceanBoard intializes an empty board
	{
		for(int i = 0; i<=9; i++)
		{
			for(int j = 0; j<= 9; j++)
			{
				ocean[i][j] = '~';	// '~' will be used as an empty space (looks like a wave)
			}
		}
	}

	public placeShips()
	{

		 randomNumber = new Random().nextInt(10);

		 sys.println(randomNumber);
		 
		// going to randomly place 5 ships in this function
		// of SIZES (2,3,4,5,6)

		ship twoHP(5,0);
		ship threeHP(5,0);
		ship fourHP(5,0);
		ship fiveHP(5,0);
		ship sixHP(5,0);

	}

	public static void displayOcean()
	{
		for(int i = 0; i<= 9; i++)
		{
			for(int j = 0; j<= 9; j++)
			{
				System.out.println('|' + ocean[i][j]);
				
				if(j == 9)
					System.out.println();

			}
		}
	}

	



}
