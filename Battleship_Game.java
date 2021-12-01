import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Battleship_Game
{

    public static char[][] player1ShipBoard = new char[10][10];         // Player 1 board where the ships will live
    public static char[][] player1Radar = new char[10][10];             // Player 1 board where the player will fire
    public static char[][] player2ShipBoard = new char[10][10];         // Player 2 board where the ships will live
    public static char[][] player2Radar = new char[10][10];             // Player 2 board where the player will fire
    public static HPlayer player1 = new HPlayer(1);                // Global player 1
    public static HPlayer player2 = new HPlayer(2);                // Global player 2
    public static int totalP1Moves = 0;                                 // Placeholders for number of player moves. Only utilized in Terminal-Only sessions
    public static int totalP2Moves = 0;                                 // (i.e. for testing purposes)
    public static int totalMoves = totalP1Moves + totalP2Moves;


    // the following variables and functions are only used in salvo mode

    public static boolean salvoMode = false;

    public void setModeStatus(boolean mode)
    {
        salvoMode = mode;
    }

    public boolean getModeStatus()
    {
        return salvoMode;
    }


    public Battleship_Game() // Construct the battleship game by initializing empty boards
    {

        for(int i =0; i<=9; i++)			// Where player 1 ships will live
        {
            for(int j = 0; j<= 9; j++)
            {
                player1ShipBoard[i][j] = '~';
            }
        }

        for(int i =0; i<=9; i++)			// Player 1's hit/miss board
        {
            for(int j = 0; j<= 9; j++)
            {
                player1Radar[i][j] = '~';
            }
        }
        for(int i =0; i<=9; i++)			// Where player 2 ships will live
        {
            for(int j = 0; j<= 9; j++)
            {
                player2ShipBoard[i][j] = '~';
            }
        }
        for(int i =0; i<=9; i++)			// Player 2's hit/miss board
        {
            for(int j = 0; j<= 9; j++)
            {
                player2Radar[i][j] = '~';
            }
        }

	placePlayerShips(1);
	placePlayerShips(2);	

    }


    // I plan on having the board pieces be as follows:
    // 'c' for cruiser
    // 's' for submarine
    // 'b' for battleship
    // 'd' for destroyer
    // 'C' (capitalized) for Carriers
    // 'M' for "Missed" moves
    // 'H' for "Hit" moves
    // a board space is considered occupied if the board space is NOT the characer '~'
    // when a certain ship type is hit (i.e. changing a 'C' character to an 'H' character, it will remove one health from the Carrier health.
    // and once the carrier is down to zero health, we will implement the board spaces (whether be buttons or panels) to be colored black
    // For ship orientation, the left-most or top-most portion of the ship will be considered the ships 'starting location'


    public boolean isValid(int row, int column, boolean isVertical, int size, int playerNum)
    {
        if(size == 2)
        {
            if(isVertical)
            {
                if( row>= 9)		// The ship's placement would be [row][column] = [9][col] + [10][col] ... which lies outside bounds
                    return false;
            }
            else
            {
                if( column >= 9)
                    return false;	// The ship's placement would be [row]column] = [row][9] + [row][10] ... which lies outside bounds
            }
        }
        else if(size == 3)
        {
            if(isVertical)
            {
                if( row>= 8)            // The ship's placement would be [row][column] = [8][col] + [9][col] + [10][col] ... which lies outside bounds
                    return false;
            }
            else
            {
                if( column >= 8)
                    return false;   // The ship's placement would be [row]column] = [row][9] + [row][10] ... which lies outside bounds
            }

        }
        else if(size == 4)
        {
            if(isVertical)
            {
                if( row>= 7)            // The ship's placement would be outside bounds
                    return false;
            }
            else
            {
                if( column >= 7)
                    return false;   // The ship's placement would be outside bounds
            }

        }
        else if(size == 5)
        {
            if(isVertical)
            {
                if( row>= 6)            // The ship's placement would be outside bounds
                    return false;
            }
            else
            {
                if( column >= 6)
                    return false;   // The ship's placement would be outside bounds
            }


        }
        else if(size != 2 && size != 3 && size != 4 && size != 5)			// If an incorrect size is placed inside, then obviously not valid
            return false;

        // If none of the above is false, then so far the ship has chosen a location which would allow it to fit on the board. Thus check if the board is empty...

        int numOpenSpaces = 0;

        if(isVertical)					// If the ship is placed vertically, check positions from [row][column] -> [row+size-1][column]
        {
            for(int i = row; i< row+size; i++)
            {
                if(playerNum == 1 && player1ShipBoard[i][column] == '~')
                    numOpenSpaces++;
                if(playerNum == 2 && player2ShipBoard[i][column] == '~')
                    numOpenSpaces++;
            }

        }
        else                            // Do the same for [row][col] -> [row][col+size-1]
        {
            for(int i = column; i< column+size; i++)
            {
                if(playerNum == 1 && player1ShipBoard[row][i] == '~')
                    numOpenSpaces++;
                if(playerNum == 2 && player2ShipBoard[row][i] == '~')
                    numOpenSpaces++;
            }


        }

        if(numOpenSpaces == size)           // If there is an equal number of open spaces to the size of the ship, then space is avaailble
            return true;
        else
            return false;


    }


    public void placeShip(int startRow, int startCol, boolean isVert, int size, int playerNum, char shipType)
    {
        if(isValid(startRow,startCol,isVert,size,playerNum))        // If the ship is to be placed in a valid location
        {
            if( playerNum == 1 && isVert)                           // If the ship is vertical, work from start location down
            {
                for(int i = startRow; i< startRow+size; i++)
                {
                    player1ShipBoard[i][startCol] = shipType;
                }
            }
            else if (playerNum == 1 && !isVert)                     // Otherwise work left to right
            {
                for(int i = startCol; i< startCol+size; i++)
                {
                    player1ShipBoard[startRow][i] = shipType;
                }

            }
            else if (playerNum == 2 && isVert)                  // Same mechanics for player 2...
            {
                for(int i = startRow; i< startRow+size; i++)
                {
                    player2ShipBoard[i][startCol] = shipType;
                }

            }
            else if(playerNum == 2 && !isVert)
            {
                for(int i = startCol; i< startCol+size; i++)
                {
                    player2ShipBoard[startRow][i] = shipType;
                }

            }
            else
                System.out.println("An error has occured\n");
        }
        else
            System.out.println("That area either lies out of bounds or is already occupied!\n");

    }


    public static void display_Board(char[][] board)
    {
        System.out.println();
        System.out.println("     0   1   2   3   4   5   6   7   8   9  \n");

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


    public boolean isGameWon(HPlayer playerInstance1, HPlayer playerInstance2)
    {

        if(playerInstance1.getTotalPlayerHealth() == 0 || playerInstance2.getTotalPlayerHealth() == 0)
            return true;
        else
            return false;

    }


    public void placePlayerShips(int playerInstanceNum)
    {
        // Placing Destroyer

        Random rnd = new Random();
        boolean randIsVertical = false;
        int randNum = rnd.nextInt(2);
        if(randNum == 0)
            randIsVertical = true;
        int randRow = rnd.nextInt(9);
        int randCol = rnd.nextInt(9);

        if(isValid(randRow,randCol,randIsVertical,2,playerInstanceNum))
        {
            placeShip(randRow,randCol,randIsVertical,2,playerInstanceNum, 'd');
        }
        else
        {
            while(true)
            {

                randNum = rnd.nextInt(2);

                if(randNum == 0)
                    randIsVertical = true;

                randRow = rnd.nextInt(9);
                randCol = rnd.nextInt(9);

                if(isValid(randRow,randCol,randIsVertical,2,playerInstanceNum))
                {
                    placeShip(randRow,randCol,randIsVertical,2,playerInstanceNum,'d');
                    break;
                }

            }
        }

        // Placing Additional Submarine

        randNum = rnd.nextInt(2);
        if(randNum == 0)
            randIsVertical = true;
        randRow = rnd.nextInt(9);
        randCol = rnd.nextInt(9);

        if(isValid(randRow,randCol,randIsVertical,3,playerInstanceNum))
        {
            placeShip(randRow,randCol,randIsVertical,3,playerInstanceNum, 's');
        }
        else
        {
            while(true)
            {

                randNum = rnd.nextInt(2);

                if(randNum == 0)
                    randIsVertical = true;

                randRow = rnd.nextInt(9);
                randCol = rnd.nextInt(9);

                if(isValid(randRow,randCol,randIsVertical,3,playerInstanceNum))
                {
                    placeShip(randRow,randCol,randIsVertical,3,playerInstanceNum,'s');
                    break;
                }

            }
        }


        // Placing additional Cruiser


        randNum = rnd.nextInt(2);
        if(randNum == 0)
            randIsVertical = true;
        randRow = rnd.nextInt(9);
        randCol = rnd.nextInt(9);

        if(isValid(randRow,randCol,randIsVertical,3,playerInstanceNum))
        {
            placeShip(randRow,randCol,randIsVertical,3,playerInstanceNum, 'c');
        }
        else
        {
            while(true)
            {

                randNum = rnd.nextInt(2);

                if(randNum == 0)
                    randIsVertical = true;

                randRow = rnd.nextInt(9);
                randCol = rnd.nextInt(9);

                if(isValid(randRow,randCol,randIsVertical,3,playerInstanceNum))
                {
                    placeShip(randRow,randCol,randIsVertical,3,playerInstanceNum,'c');
                    break;
                }

            }
        }


        // Placing additional Battleship

        randNum = rnd.nextInt(2);
        if(randNum == 0)
            randIsVertical = true;
        randRow = rnd.nextInt(9);
        randCol = rnd.nextInt(9);

        if(isValid(randRow,randCol,randIsVertical,4,playerInstanceNum))
        {
            placeShip(randRow,randCol,randIsVertical,4,playerInstanceNum, 'b');
        }
        else
        {
            while(true)
            {

                randNum = rnd.nextInt(2);

                if(randNum == 0)
                    randIsVertical = true;

                randRow = rnd.nextInt(9);
                randCol = rnd.nextInt(9);

                if(isValid(randRow,randCol,randIsVertical,4,playerInstanceNum))
                {
                    placeShip(randRow,randCol,randIsVertical,4,playerInstanceNum,'b');
                    break;
                }

            }
        }

        // Placing carrier

        randNum = rnd.nextInt(2);
        if(randNum == 0)
            randIsVertical = true;
        randRow = rnd.nextInt(9);
        randCol = rnd.nextInt(9);

        if(isValid(randRow,randCol,randIsVertical,5,playerInstanceNum))
        {
            placeShip(randRow,randCol,randIsVertical,5,playerInstanceNum, 'C');
        }
        else
        {
            while(true)
            {

                randNum = rnd.nextInt(2);

                if(randNum == 0)
                    randIsVertical = true;

                randRow = rnd.nextInt(9);
                randCol = rnd.nextInt(9);

                if(isValid(randRow,randCol,randIsVertical,5,playerInstanceNum))
                {
                    placeShip(randRow,randCol,randIsVertical,5,playerInstanceNum,'C');
                    break;
                }

            }
        }


        return;
    }



    public static void main(String[] args)
    {
        // StartMenu newMenu = new StartMenu();
	    Battleship_Game game = new Battleship_Game();
        Grid j = new Grid();

        // display_Board(player1ShipBoard);            // This function displays the player 1 ship layout onto the terminal. Can be removed or commented out
        // display_Board(player2ShipBoard);            // This function displays the player 2 ship layout onto the terminal.
        /*
            NOTE: The following lines of code were utilized to check the functionality of the ship-placing functions and general
            array mechanics behind the game. The commented-out sections can ergo be ignored. If the following lines of code are
            not commented out, the game will still work as intended. The lines below can be utilized to likewise play the battleship
            game using the Terminal only (but like the TicTacToe game is cumbersome).

            - Ryan Goodrich


        Scanner s = new Scanner(System.in);

        while(!game.isGameWon(player1,player2))
        {
            System.out.println("Player 1 moves...");


            while(true)
            {

                boolean loopBoolean = false;
                int rowFire = 0;
                int colFire = 0;

                System.out.println("Please enter the row you wish to fire upon: ");

                rowFire = s.nextInt();

                while(rowFire > 9 || rowFire < 0 )
                {
                    System.out.println("Row is out of bounds. Enter a valid row: ");
                    rowFire = s.nextInt();

                }

                System.out.println("Please enter the column you wish to fire upon: ");

                colFire = s.nextInt();

                while(colFire > 9 || colFire < 0 )
                {
                    System.out.println("Row is out of bounds. Enter a valid row: ");
                    colFire = s.nextInt();

                }

                if(player1Radar[rowFire][colFire] != '~')
                {
                    System.out.println("You have already fired on that position, please select a new position...");
                    continue;
                }

                if(player2ShipBoard[rowFire][colFire] != '~')
                {
                    System.out.println("PLAYER 1 HAS HIT A TARGET");
                    player1Radar[rowFire][colFire] = 'H';

                    if(player2ShipBoard[rowFire][colFire] == 'c')
                        player2.damage('c');
                    if(player2ShipBoard[rowFire][colFire] == 'd')
                        player2.damage('d');
                    if(player2ShipBoard[rowFire][colFire] == 's')
                        player2.damage('s');
                    if(player2ShipBoard[rowFire][colFire] == 'b')
                        player2.damage('b');
                    if(player2ShipBoard[rowFire][colFire] == 'C')
                        player2.damage('C');

                    break;
                }
                else
                {
                    System.out.println("PLAYER 1 HAS MISSED!");
                    player1Radar[rowFire][colFire] = 'M';
                    break;
                }

            }

            display_Board(player1Radar);

            if(game.isGameWon(player1,player2))
                break;

            System.out.println("Player 2 moves...");


            while(true)
            {

                boolean loopBoolean = false;
                int rowFire = 0;
                int colFire = 0;

                System.out.println("Please enter the row you wish to fire upon: ");

                rowFire = s.nextInt();

                while(rowFire > 9 || rowFire < 0 )
                {
                    System.out.println("Row is out of bounds. Enter a valid row: ");
                    rowFire = s.nextInt();

                }

                System.out.println("Please enter the column you wish to fire upon: ");

                colFire = s.nextInt();

                while(colFire > 9 || colFire < 0 )
                {
                    System.out.println("Row is out of bounds. Enter a valid row: ");
                    colFire = s.nextInt();

                }

                if(player2Radar[rowFire][colFire] != '~')
                {
                    System.out.println("You have already fired on that position, please select a new position...");
                    continue;
                }
                if(player1ShipBoard[rowFire][colFire] != '~')
                {
                    System.out.println("PLAYER 2 HAS HIT A TARGET");
                    player2Radar[rowFire][colFire] = 'H';

                    if(player1ShipBoard[rowFire][colFire] == 'c')
                        player1.damage('c');
                    if(player1ShipBoard[rowFire][colFire] == 'd')
                        player1.damage('d');
                    if(player1ShipBoard[rowFire][colFire] == 's')
                        player1.damage('s');
                    if(player1ShipBoard[rowFire][colFire] == 'b')
                        player1.damage('b');
                    if(player1ShipBoard[rowFire][colFire] == 'C')
                        player1.damage('C');


                    break;
                }
                else
                {
                    System.out.println("PLAYER 2 HAS MISSED!");
                    player2Radar[rowFire][colFire] = 'M';
                    break;
                }


            }

            display_Board(player2Radar);

            System.out.println("The remaining health of player 1 is " + player1.getTotalPlayerHealth());
            System.out.println("The remaining health of player 2 is " + player2.getTotalPlayerHealth());


        }

        if(game.isGameWon(player1,player2))
        {
            if(player1.getTotalPlayerHealth() == 0)
                System.out.println("PLAYER 2 HAS WON THE GAME");
            else
                System.out.println("PLAYER 1 HAS WON THE GAME");
        }


         */

    }	// END main class

}

