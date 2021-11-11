public abstract class Ship
{

	int health;		// holds the ship's maximum health
	int size;		// holds the ship's size (3,4,5)
	boolean isHoriz;	// boolean thats true if the ship is placed horizontally
	boolean isVertical;	// boolean thats true if the ship is placed vertically


	public void decreaseHealth(Ship s) // functiont used when a ship is hit, removes 1 from the remaining health
	{	
		s.health--;
	}

	public int getHealth()
	{
		return health;
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
