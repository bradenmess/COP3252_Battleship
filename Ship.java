public abstract class Ship
{

	int maxHealth;		// holds the ship's maximum health
	int currentHealth;
	int size;		// holds the ship's size (3,4,5)
	boolean isHoriz;	// boolean thats true if the ship is placed horizontally
	boolean isVertical;	// boolean thats true if the ship is placed vertically

	Ship(int maxHP,int shipOrientation,int xLocation, int yLocation)		// Constructor for any ship
	{
		maxHealth = maxHP;
		currentHealth = maxHP;

		size = maxHP;

		if (shipOrientation == 0)  	// 0 is passed then orientation is horizontal
		{
			isHoriz = true;
			isVertical = false;

			for (int i=xLocation; i < xLocation + maxHP; i++)		// Put a placeholder for the ship
			{
				OceanBoard[i][yLocation] = '$';
			}

		}
		else if (shipOrientation == 1) // 1 is passed orientation is vertical
		{
			isHoriz = false;
			isVertical = true;

			for (int i=yLocation; i < yLocation + maxHP; i++)		// Put a placeholder for the ship
			{
				OceanBoard[xLocation][i] = '$';
			}
		}


	}


	public void decreaseHealth(Ship s) // functiont used when a ship is hit, removes 1 from the remaining health
	{	
		s.currentHealth--;
	}

	public int getHealth()
	{
		return currentHealth;
	}
	
	public int getSize()
	{
		return size;
	}

	public boolean shipIsVertical()
	{
		return isVertical;
	}

	public boolean shipIsHorizontal()
	{
		return isHoriz;
	}
	


}
