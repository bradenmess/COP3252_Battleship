public class Battleship_Game
{
	public static char[][] player1Ocean;		// player 1's board (where ships can be placed)
	public static char[][] player1Radar;		// player 1's radar (where they will fire at player 2)
	public static char[][] player2Ocean;		// player 2's board (where ships can be placed)
	public static char[][] player2Radar;		// player 2's radar (where they will fire at player 1)


	public Battleship_Game() // battleship game constructor, constructs the game NOT the ship
	{
		player1Ocean = new char[10][10];
		player1Radar = new char[10][10];
		player2Ocean = new char[10][10];
		player2Radar = new char[10][10];

		for(int i = 0; i<= 9; i++)
		{
			for(int j = 0; j<= 9; j++)
			{
				player1Ocean[i][j] = '~';
			}
		}	


	        for(int i = 0; i<= 9; i++)
                {
                        for(int j = 0; j<= 9; j++)
                        {
                                player1Radar[i][j] = '~';
                        }
                }

                for(int i = 0; i<= 9; i++)
                {
                        for(int j = 0; j<= 9; j++)
                        {
                                player2Ocean[i][j] = '~';
                        }
                }

                for(int i = 0; i<= 9; i++)
                {
                        for(int j = 0; j<= 9; j++)
                        {
                                player2Radar[i][j] = '~';
                        }
                }

	
	}


	public static void display_Board(char[][] board)
	{
		System.out.println();
                System.out.println("     A   B   C   D   E   F   G   H   I   J  \n");

		for(int i = 0; i<= 9; i++)
		{
			System.out.print(i + " ");
			for(int j = 0; j<= 9; j++)
			{
				if(j == 0)
					System.out.print(" ");

				System.out.print("| " + board[i][j] + " ");

				if(j == 9)
				{
					System.out.print("|\n");
					System.out.println("   -----------------------------------------");
				}
			}
		}
		
		System.out.println();
	}


	public static void main(String[] args)
	{
		OceanBoard gameBoard = new OceanBoard();

		gameBoard.placeShips();

		display_Board(gameBoard.player1Ocean);

		

	}	// main class





}
