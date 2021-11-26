public abstract class Ship
{

	int maxHealth;		// holds the ship's maximum health
	int currentHealth;
	int size;		// holds the ship's size (3,4,5)
	boolean isHoriz;	// boolean thats true if the ship is placed horizontally
	boolean isVertical;	// boolean thats true if the ship is placed vertically

	Ship(int maxHealth,int shipOrientation)		// Constructor for any ship
	{
		maxHealth = x;
		currentHealth = x;

		if (shipOrientation == 0)  	// if 0 is passed then orientation is horizontal if 1 is passed orientation is vertical
		{
			isHoriz = true;
			isVertical = false;
		}
		else if (shipOrientation == 1)
		{
			isHoriz = false;
			isVertical = true;
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
