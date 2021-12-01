import java.util.*;

public class HPlayer extends Battleship_Game {
    public int playerNum;
    public int playerCruiserHP;
    public int playerSubmarineHP;
    public int playerCarrierHP;
    public int playerBattleshipHP;
    public int playerDestroyerHP;

    // The following variables and functions are used only in Salvo Mode


    public int numRemainingShips = 5;
    public int numRemainingMoves = 5;


    public boolean canContinue()
    {
        if(numRemainingMoves > 0)
            return true;
        else
            return false;
    }

    public void depleteRemainingMoves()
    {
        numRemainingMoves--;
    }

    public void resetCurrentMoves()     // This function runs to reset the total remaining moves for the player to the number of currently remianing ships
    {
        numRemainingMoves = numRemainingShips;
    }

    public void moveNumIdentifier()       // This function will run after a player completes their turn in "Grid"
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

    public HPlayer(int num)
    {
        playerCruiserHP = 3;
        playerSubmarineHP = 3;
        playerCarrierHP = 5;
        playerBattleshipHP = 4;
        playerDestroyerHP = 2;
        playerNum = num;
    }

    public void setPlayerNum(int num)
    {
        playerNum = num;
    }

    public int getPlayerNum()
    {
        return playerNum;
    }

    public void damage(char shipType)
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


    public int getTotalPlayerHealth()
    {
        int remainingHP = playerCruiserHP + playerSubmarineHP + playerCarrierHP + playerBattleshipHP + playerDestroyerHP;

        return remainingHP;
    }


}
