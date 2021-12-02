// Ryan Goodrich
// Marcelo Zapatta
// Braden Mess
// Dr. Amirizirtol
// COP 3234 Advanced Java Programming
// Date of Completion: 12/1/2021
// BATTLESHIP PROJECT

import java.util.*;

public class HPlayer extends Battleship_Game {
    public int playerNum;
    public int playerCruiserHP;
    public int playerSubmarineHP;
    public int playerCarrierHP;
    public int playerBattleshipHP;
    public int playerDestroyerHP;

    // The following variables are used only in Salvo Mode

    public int numRemainingShips = 5;
    public int numRemainingMoves = 5;

    public void depleteRemainingMoves() // Removes a player move in SALVO mode
    {
        --numRemainingMoves;
    }

    public void resetCurrentMoves()     // This function runs to reset the total remaining moves for the player to the number of currently remaining ships
    {
        numRemainingMoves = numRemainingShips;
    }

    public void moveNumIdentifier()       // This function will run after a player completes their turn in "Grid". Identifies number of remaining moves in SALVO
    {
        // Start with total number of ships

        int currentRemainingShips = 5;

        if(playerCruiserHP == 0)
            currentRemainingShips--;
        if(playerSubmarineHP == 0)
            currentRemainingShips--;
        if(playerCarrierHP == 0)
            currentRemainingShips--;
        if(playerBattleshipHP == 0)
            currentRemainingShips--;
        if(playerDestroyerHP == 0)
            currentRemainingShips--;

        if(currentRemainingShips != numRemainingShips)
        {
            numRemainingShips = currentRemainingShips;
            numRemainingMoves = currentRemainingShips;
        }

    }


    // Methods and constructors

    public HPlayer(int num)                 // Constructs an HPlayer with full health
    {
        playerCruiserHP = 3;
        playerSubmarineHP = 3;
        playerCarrierHP = 5;
        playerBattleshipHP = 4;
        playerDestroyerHP = 2;
        playerNum = num;
    }

    public void setPlayerNum(int num)       // Sets the player number (only used in testing)
    {
        playerNum = num;
    }

    public int getPlayerNum()               // Returns the player number (only used in testing)
    {
        return playerNum;
    }

    public void damage(char shipType)       // Damages the appropriate ship when placed as an argument
    {
        if(shipType == 'd')
            playerDestroyerHP--;
        if(shipType == 'c')
            playerCruiserHP--;
        if(shipType == 'C')
            playerCarrierHP--;
        if(shipType == 'b')
            playerBattleshipHP--;
        if(shipType == 's')
            playerSubmarineHP--;
    }


    public int getTotalPlayerHealth()       // Used to return to the program the total remaining player health
    {
        int remainingHP = playerCruiserHP + playerSubmarineHP + playerCarrierHP + playerBattleshipHP + playerDestroyerHP;

        return remainingHP;
    }


}
