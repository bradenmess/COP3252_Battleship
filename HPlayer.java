import java.util.*;

public class HPlayer extends Battleship_Game
{
    public int playerNum;
    public int playerCruiserHP;
    public int playerSubmarineHP;
    public int playerCarrierHP;
    public int playerBattleshipHP;
    public int playerDestroyerHP;


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
