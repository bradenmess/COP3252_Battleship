public abstract class Ship
{

	public int health;		// holds the ship's maximum health
	public int size;		// holds the ship's size (3,4,5)
	public boolean isHoriz;	// boolean thats true if the ship is placed horizontally
	public boolean isVertical;	// boolean thats true if the ship is placed vertically
	public char shipType;
	public boolean isSunk = false;	


	// A function will be created that checks whether the ship is validly-placed. It will utilize the functionality of the start/end locations
	// aforementioned by doing the following:
	// 1. When placing a ship, if any of the components are outside of the bound (i.e. startLocation < 0 or > 10) then a boolean will be returned
	//    as false
	// 2. When the ship is being placed on the board, a loop will be designed so that if the start/end locations (row or column) are the same
	//    then one of two for-loops will be run: either a horizontal or a vertical, depending on the orientation of the ship (determined by the starts/ends


	public abstract void damage();

	public abstract int getHealth();	


}

class Submarine extends Ship
{
        public Submarine()
        {
                health = 3;
                size = 3;
                shipType = 's';
        }

	public void damage()
	{
		health--;
	}
	
	public int getHealth()
	{
		return health;
	}

}

class Destroyer extends Ship
{
        public Destroyer()      // creates a destoryer ship
        {
                health = 4;
                size = 5;
        }

        public void damage()
        {
                health--;
        }

	public int getHealth()
	{
		return health;
	}



}

class Battleship extends Ship
{
        public Battleship()
        {
                health = 4;
                size = 4;
                shipType = 'b';
        }

        public void damage()
        {
                health--;
        }

	public int getHealth()
	{
		return health;
	}


}

class Carrier extends Ship
{
        public Carrier()
        {
                health = 5;
                size = 5;
                shipType =  'C';

        }

        public void damage()
        {
                health--;
        }
	
	public int getHealth()
	{
		return health;
	}



}

class Cruiser extends Ship
{
        public Cruiser()        // Construct a cruiser ship
        {
                health = 3;
                size = 3;
                shipType = 'c';
        }


        public void damage()
        {
                health--;
        }


	public int getHealth()
	{
		return health;
	}


}
