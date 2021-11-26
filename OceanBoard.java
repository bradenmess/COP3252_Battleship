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


// going to randomly place 5 ships in this function
// of SIZES (2,3,4,5,6)

// CONSTRUCTOR FOR SHIP Ship(int maxHP,int shipOrientation,int xLocation, int yLocation)



// BUILDING 2HP SHIP

	xCoordinate = new Random().nextInt(10);
	yCoordinate = new Random().nextInt(10);
	orientation = new Random().nextInt(1);

	while (!checkShip(2,orientation,xCoordinate,yCoordinate))
		{
			xCoordinate = new Random().nextInt(10);
			yCoordinate = new Random().nextInt(10);
			orientation = new Random().nextInt(1);
		}
			// THIS CONSTRUCTOR PLACES SHIP IN OCEANBOARD
			Ship twoHP(2,orientation,xCoordinate,yCoordinate);


// BUILD 3HP SHIP

xCoordinate = new Random().nextInt(10);
yCoordinate = new Random().nextInt(10);
orientation = new Random().nextInt(1);

	while (!checkShip(3,orientation,xCoordinate,yCoordinate))
		{
			xCoordinate = new Random().nextInt(10);
			yCoordinate = new Random().nextInt(10);
			orientation = new Random().nextInt(1);
		}
			// THIS CONSTRUCTOR PLACES SHIP IN OCEANBOARD
			Ship threeHP(3,orientation,xCoordinate,yCoordinate);		


// BUILD 4HP SHIP

xCoordinate = new Random().nextInt(10);
yCoordinate = new Random().nextInt(10);
orientation = new Random().nextInt(1);

	while (!checkShip(4,orientation,xCoordinate,yCoordinate))
		{
			xCoordinate = new Random().nextInt(10);
			yCoordinate = new Random().nextInt(10);
			orientation = new Random().nextInt(1);
		}
		
			Ship fourHP(4,orientation,xCoordinate,yCoordinate);	

	}


	public boolean checkShip(int size,int shipOrientation,int xLocation, int yLocation)
	{

		if ( ocean[s.xCoordinate][s.yCoordinate] == '$')
				return false;


		if (shipOrientation == 0) // HORIZONTAL CHECK
		{
			xCoordinate += size;

			if (xCoordinate > 9)		    // MAKE SURE SHIP IS IN BOUNDS
				return false;

			for (int i = xCoordinate; i >= 0; i--)	
			{
				if ( ocean[i][yCoordinate] == '$')	// CHECK IF SHIP IS ALR IN THE LOCATION
					return false;
			}
		}
		else if (shipOrientation == 1)	// VERTICAL CHECK
		{
			yCoordinate += size;

			if (yCoordinate > 9)			// MAKE SURE SHIP IS IN BOUNDS
				return false;
			
			for (int i = yCoordinate; i >= 0; i--)	
			{
				if ( ocean[xCoordinate][i] == '$')	// CHECK IF SHIP IS ALR IN THE LOCATION
					return false;
			}
		}

		return false;

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
