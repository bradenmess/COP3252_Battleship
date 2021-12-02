// Ryan Goodrich
// Marcelo Zapatta
// Braden Mess
// Dr. Amirizirtol
// COP 3234 Advanced Java Programming
// Date of Completion: 12/1/2021
// BATTLESHIP PROJECT


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class Grid extends Battleship_Game implements ActionListener
{
    JFrame frame = new JFrame("Battleship" + ( salvoMode ? ": SALVO" : ""));
    JPanel windowContent;
    JLabel titleLabel, player1Label, player2Label;
    JButton player1Buttons[][];
    JButton player2Buttons[][];
    JPanel grid1, grid2, grid1WLabel, grid2WLabel, grids;
    JLabel hitMissIndicator = new JLabel("AWAITING");
    JLabel turnIndicator = new JLabel("FIRST ATTACK");
    JLabel moveStatus = new JLabel("LAST PLAYER MOVE");
    JLabel turnStatus = new JLabel("NEXT MOVE");
    JButton newGameStandard = new JButton("Play Again: STANDARD");
    JButton newGameSalvo = new JButton("Play Again: SALVO");

    JLabel destroyer1Label = new JLabel("Destroyer (2)");
    JLabel destroyer2Label = new JLabel("Destroyer (2)");
    JLabel submarine1Label = new JLabel("Submarine (3)");
    JLabel submarine2Label = new JLabel("Submarine (3)");
    JLabel cruiser1Label = new JLabel("Cruiser (3)");
    JLabel cruiser2Label = new JLabel("Cruiser (3)");
    JLabel battleship1Label = new JLabel("Battleship (4)");
    JLabel battleship2Label = new JLabel("Battleship (4)");
    JLabel carrier1Label = new JLabel("Carrier (5)");
    JLabel carrier2Label = new JLabel("Carrier (5)");
    JPanel shipIndicators1 = new JPanel();
    JPanel shipIndicators2 = new JPanel();

    // Here is where all of the colors that are used extensively are placed

    Color oceanColor = new Color(50,250,250);
    Color buttonFGColor = new Color(70,70,70);

    public static int numOfTotalClicks;     // Used to lock one side of the board in salvo mode once the first shot is fired


    String localDir = System.getProperty("java.class.path")+"/";
    File waterExplosionSound = new File(localDir+ "WaterExplosionSound.wav");
    File explosionSound = new File(localDir+ "ExplosionSound.wav");


    public void AudioPlayer(File fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException   // This creates the "explosion sound" when a ship is hit
    {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileName);
            Clip myClip = AudioSystem.getClip();
            myClip.open(audioStream);
            myClip.loop(0);
            myClip.addLineListener(new LineListener() {
                public void update(LineEvent myLineEvent) {
                    if (myLineEvent.getType() == LineEvent.Type.STOP)
                        myClip.close();
                }
            });
        }
        catch(UnsupportedAudioFileException ex)
        {
            System.out.println("The Audio File Encountered an Error: " + ex);
        }
    }


    public void sunkenShip(int playerNum, char shipType)    // this function will run ONLY if a ship is determined to be sunk by the proceeding method
    {
        if (playerNum == 1)
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (Battleship_Game.player1ShipBoard[i][j] == shipType)
                    {
                        player1Buttons[i][j].setBackground(Color.BLACK);    // If the given ship is hit, make the buttons black
                        player1Buttons[i][j].setText("");
                    }
                }
            }
        }
        else                // Do the same for player 2
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (Battleship_Game.player2ShipBoard[i][j] == shipType)
                    {
                        player2Buttons[i][j].setBackground(Color.BLACK);
                        player2Buttons[i][j].setText("");
                    }

                }
            }
        }


    }

    public void checkShipStatus(int playerNum) throws UnsupportedOperationException      // This function will check if the provided ship is sunk (i.e. the ship health is 0 for the player)
    {
        Font sunkFont = new Font("Impact",Font.BOLD,40);

        //Map a = sunkFont.getAttributes();
        //a.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        //sunkFont = new Font(a);

        switch(playerNum)
        {
            case 1:
                if (Battleship_Game.player1.playerBattleshipHP == 0)
                {
                    sunkenShip(1, 'b');
                    battleship1Label.setForeground(Color.RED);
                    battleship1Label.setFont(sunkFont);
                }

                if (Battleship_Game.player1.playerSubmarineHP == 0)
                {
                    submarine1Label.setFont(sunkFont);
                    submarine1Label.setForeground(Color.RED);
                    sunkenShip(1, 's');
                }

                if (Battleship_Game.player1.playerDestroyerHP == 0)
                {
                    destroyer1Label.setForeground(Color.RED);
                    destroyer1Label.setFont(sunkFont);
                    sunkenShip(1, 'd');
                }

                if (Battleship_Game.player1.playerCruiserHP == 0)
                {
                    cruiser1Label.setForeground(Color.RED);
                    cruiser1Label.setFont(sunkFont);
                    sunkenShip(1, 'c');
                }

                if (Battleship_Game.player1.playerCarrierHP == 0)
                {
                    carrier1Label.setForeground(Color.RED);
                    carrier1Label.setFont(sunkFont);
                    sunkenShip(1, 'C');
                }
                break;
            case 2:
                if (Battleship_Game.player2.playerBattleshipHP == 0)
                {
                    sunkenShip(2, 'b');
                    battleship2Label.setForeground(Color.RED);
                    battleship2Label.setFont(sunkFont);
                }
                if (Battleship_Game.player2.playerSubmarineHP == 0)
                {
                    submarine2Label.setForeground(Color.RED);
                    submarine2Label.setFont(sunkFont);
                    sunkenShip(2, 's');
                }
                if (Battleship_Game.player2.playerDestroyerHP == 0)
                {
                    destroyer2Label.setForeground(Color.RED);
                    destroyer2Label.setFont(sunkFont);
                    sunkenShip(2, 'd');
                }
                if (Battleship_Game.player2.playerCruiserHP == 0)
                {
                    cruiser2Label.setForeground(Color.RED);
                    cruiser2Label.setFont(sunkFont);
                    sunkenShip(2, 'c');
                }
                if (Battleship_Game.player2.playerCarrierHP == 0)
                {
                    carrier2Label.setForeground(Color.RED);
                    carrier2Label.setFont(sunkFont);
                    sunkenShip(2, 'C');
                }
                break;
        }

    }

    public void changeButtonFunctionality(int playerNum)    // Used to remove a player's ability to click on one side of the board. Used to force turns
    {
        if(playerNum == 1)  // Proceed with normal button removal
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    player1Buttons[i][j].setEnabled(false);             // Set buttons on player 1's board to disabled

                    if(!player1Buttons[i][j].getText().equals("MISS") && !player1Buttons[i][j].getText().equals("HIT") && !player1Buttons[i][j].getText().equals(""))
                        player1Buttons[i][j].setBackground(Color.DARK_GRAY);

                    if(player1Buttons[i][j].getText().equals(""))
                        player1Buttons[i][j].setBackground(Color.BLACK);

                    player2Label.setForeground(Color.RED);
                    player1Label.setForeground(Color.BLACK);
                    if(!player2Buttons[i][j].getText().equals("HIT") && !player2Buttons[i][j].getText().equals("MISS") && !player2Buttons[i][j].getText().equals(""))       // Set unused buttons on player 1's board to enabled
                    {
                        player2Buttons[i][j].setEnabled(true);
                        player2Buttons[i][j].setBackground(oceanColor);
                    }
                    else if(player2Buttons[i][j].getText().equals("HIT"))
                        player2Buttons[i][j].setBackground(Color.RED);
                    else if(player2Buttons[i][j].getText().equals("MISS"))
                        player2Buttons[i][j].setBackground(Color.WHITE);
                    else if(player2Buttons[i][j].getText().equals(""))
                        player2Buttons[i][j].setBackground(Color.BLACK);
                }
            }
        }
        else if(playerNum == 2)      // Proceed with normal button removal
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    player2Buttons[i][j].setEnabled(false);             // Set buttons on player 2's board to disabled
                    if(!player2Buttons[i][j].getText().equals("MISS") && !player2Buttons[i][j].getText().equals("HIT") && !player2Buttons[i][j].getText().equals("HIT"))
                        player2Buttons[i][j].setBackground(Color.DARK_GRAY);

                    if(player2Buttons[i][j].getText().equals(""))
                        player2Buttons[i][j].setBackground(Color.BLACK);

                    player1Label.setForeground(Color.RED);
                    player2Label.setForeground(Color.BLACK);

                    if(!player1Buttons[i][j].getText().equals("HIT") && !player1Buttons[i][j].getText().equals("MISS") && !player1Buttons[i][j].getText().equals("") )       // Set unused buttons on player 1's board to enabled
                    {
                        player1Buttons[i][j].setEnabled(true);
                        player1Buttons[i][j].setBackground(oceanColor);
                    }
                    else if(player1Buttons[i][j].getText().equals("HIT"))
                        player1Buttons[i][j].setBackground(Color.RED);
                    else if(player1Buttons[i][j].getText().equals("MISS"))
                        player1Buttons[i][j].setBackground(Color.WHITE);
                    else if(player1Buttons[i][j].getText().equals(""))
                        player1Buttons[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    public void damagePlayerShip(HPlayer player, char shipType)    // Damage player's specific ship given a player and shipType as argument
    {
        switch(shipType)
        {
            case 'c':
                player.damage('c');
                break;
            case 'C':
                player.damage('C');
                break;
            case 'd':
                player.damage('d');
                break;
            case 'b':
                player.damage('b');
                break;
            case 's':
                player.damage('s');
                break;
        }
    }

    public void buttonHit(JButton[][] button, int i, int j)     // Marks as "hit" and makes button unusable
    {
        button[i][j].setText("HIT");
        button[i][j].setEnabled(false);
        button[i][j].setBackground(Color.RED);
    }

    public void buttonMiss(JButton[][] button, int i, int j)    // Marks as "miss" and makes button unusable
    {
        button[i][j].setText("MISS");
        button[i][j].setEnabled(false);
        button[i][j].setBackground(Color.WHITE);
    }

    public void checkWinCondition()
    {
        if(Battleship_Game.player1.getTotalPlayerHealth() == 0 || Battleship_Game.player2.getTotalPlayerHealth() == 0)
        {

            for(int i = 0; i< 10; i++)
            {
                for(int j = 0 ; j < 10; j++)
                {
                    player1Buttons[i][j].setEnabled(false);
                    player2Buttons[i][j].setEnabled(false);
                }
            }



            if(Battleship_Game.player1.getTotalPlayerHealth() == 0)         // Player 1 lost all their health
            {
                hitMissIndicator.setForeground(Color.RED);
                hitMissIndicator.setText("PLAYER 2 HAS WON THE GAME!");
                turnIndicator.setText(" PLAY NEW GAME BELOW ");
                turnStatus.setText(" ");
                moveStatus.setText(" ");
            }
            else // Player 2 lost all their health
            {
                hitMissIndicator.setForeground(Color.RED);
                hitMissIndicator.setText("PLAYER 1 HAS WON THE GAME!");
                turnIndicator.setText(" PLAY NEW GAME BELOW ");
                turnStatus.setText(" ");
                moveStatus.setText(" ");
            }


            newGameStandard.setMinimumSize(new Dimension(300,400));
            newGameStandard.setFont(new Font("SansSerif",Font.PLAIN,40));
            newGameStandard.setToolTipText("Play a new round of Battleship with 'standard' rules");
            newGameStandard.addActionListener(this);
            newGameStandard.setAlignmentX(turnIndicator.getAlignmentX());
            newGameStandard.setAlignmentY(JFrame.BOTTOM_ALIGNMENT);
            windowContent.add(newGameStandard);

            newGameSalvo.setMinimumSize(new Dimension(300,400));
            newGameSalvo.setFont(new Font("SansSerif",Font.PLAIN,40));
            newGameSalvo.setToolTipText("Play a new round of Battleship with 'SALVO' rules");
            newGameSalvo.addActionListener(this);
            newGameSalvo.setAlignmentY(JFrame.BOTTOM_ALIGNMENT);
            newGameSalvo.setAlignmentX(turnIndicator.getAlignmentX());
            windowContent.add(newGameSalvo);


        }
    }

    public void actionPerformed(ActionEvent e)
    {
        numOfTotalClicks++;

        if(e.getSource() == newGameStandard || e.getSource() == newGameSalvo)
        {
            // Reset Turn Indicator States
            turnIndicator.setText(" FIRST ATTACK ");
            turnStatus.setText(" WAITING FOR ");
            moveStatus.setText(" PLEASE MAKE ");
            hitMissIndicator.setText(" A MOVE ");
            hitMissIndicator.setForeground(Color.BLACK);
            Font defaultFont = new Font("SansSerif",Font.PLAIN,20);

            // Re-establish all buttons and reset all boards in Battleship_Game()

            for(int i =0; i<=9; i++)			// Where player 1 ships will live
            {
                for(int j = 0; j<= 9; j++)
                {
                    player1ShipBoard[i][j] = '~';
                    player1Radar[i][j] = '~';
                    player2ShipBoard[i][j] = '~';
                    player2Radar[i][j] = '~';

                    // Format and add player 1 buttons
                    player1Buttons[i][j].setEnabled(true);
                    player1Buttons[i][j].setBackground(oceanColor);
                    player1Buttons[i][j].setForeground(buttonFGColor);
                    player1Buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    player1Buttons[i][j].setPreferredSize(new Dimension(5,5));
                    player1Buttons[i][j].setBounds(10,10,10,10);
                    player1Buttons[i][j].setText( (char)(i+65) + Integer.toString(j+1));
                    player1Buttons[i][j].setFont(defaultFont);
                    grid1.add(player1Buttons[i][j]);

                    // Format and add player 2 buttons
                    player2Buttons[i][j].setEnabled(true);
                    player2Buttons[i][j].setBackground(oceanColor);
                    player2Buttons[i][j].setForeground(buttonFGColor);
                    player2Buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    player2Buttons[i][j].setPreferredSize(new Dimension(5,5));
                    player2Buttons[i][j].setBounds(10,10,10,10);
                    player2Buttons[i][j].setText( (char)(i+65) + Integer.toString(j+1));
                    player2Buttons[i][j].setFont(defaultFont);
                    grid2.add(player2Buttons[i][j]);
                }
            }

            Font remainingShips = new Font("Impact",Font.PLAIN,40);

            // Re-add ship labels with appropriate font
            cruiser1Label.setFont(remainingShips); destroyer1Label.setFont(remainingShips); carrier1Label.setFont(remainingShips);
            battleship1Label.setFont(remainingShips); submarine1Label.setFont(remainingShips); cruiser2Label.setFont(remainingShips);
            destroyer2Label.setFont(remainingShips); carrier2Label.setFont(remainingShips); battleship2Label.setFont(remainingShips);
            submarine2Label.setFont(remainingShips);

            cruiser1Label.setForeground(Color.BLACK); destroyer1Label.setForeground(Color.BLACK); carrier1Label.setForeground(Color.BLACK);
            battleship1Label.setForeground(Color.BLACK); submarine1Label.setForeground(Color.BLACK); cruiser2Label.setForeground(Color.BLACK);
            destroyer2Label.setForeground(Color.BLACK); carrier2Label.setForeground(Color.BLACK); battleship2Label.setForeground(Color.BLACK);
            submarine2Label.setForeground(Color.BLACK);

            // Establish new positions for the ships on the boards
            placePlayerShips(1);
            placePlayerShips(2);


            // Reinitialize all ship HP to full
            Battleship_Game.player1.playerCruiserHP = 3;
            Battleship_Game.player1.playerSubmarineHP = 3;
            Battleship_Game.player1.playerCarrierHP = 5;
            Battleship_Game.player1.playerBattleshipHP = 4;
            Battleship_Game.player1.playerDestroyerHP = 2;
            Battleship_Game.player2.playerCruiserHP = 3;
            Battleship_Game.player2.playerSubmarineHP = 3;
            Battleship_Game.player2.playerCarrierHP = 5;
            Battleship_Game.player2.playerBattleshipHP = 4;
            Battleship_Game.player2.playerDestroyerHP = 2;

            // Reinitialize all player's remaining ships and moves explicitly
            Battleship_Game.player1.numRemainingMoves = 5;
            Battleship_Game.player1.numRemainingShips = 5;
            Battleship_Game.player2.numRemainingShips = 5;
            Battleship_Game.player2.numRemainingMoves = 5;

            // Re-seed all player's remaining ships and moves if salvo mode is activated
            if(e.getSource() == newGameSalvo)
            {
                player1.moveNumIdentifier();
                player2.moveNumIdentifier();
                player1.resetCurrentMoves();
                player2.resetCurrentMoves();
                salvoMode = true;

            }
            else                    // Set the salvo mode to false
                salvoMode = false;


            // Remove the buttons
            windowContent.remove(newGameSalvo);
            windowContent.remove(newGameStandard);

            // Reset the total number of detected clicks
            numOfTotalClicks = 0;

        }


        for(int i = 0; i < 10 ; i++)
        {
            for(int j = 0; j < 10 ; j++)
            {
                if(e.getSource() == player1Buttons[i][j] )   // If a click is made on player 1's buttons (player 2 fires on player 1)
                {
                    if(numOfTotalClicks == 1)
                        changeButtonFunctionality(2);           // No plays can be made on Player 2's board

                    if(Battleship_Game.player1ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button un-clickable
                    {

                        // If the player's turn is over (standard) or is out of moves (salvo), indicate the next player's turn
                        if(salvoMode && player2.numRemainingMoves > 1)
                            turnIndicator.setText("PLAYER   2   REMAINING   MOVES:   " + (player2.numRemainingMoves-1) );
                        else
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            turnIndicator.setText("IT   IS   PLAYER   1's   TURN" + (salvoMode ? ":   REMAINING   MOVES:   " + (player1.numRemainingShips) : ""));
                        }

                        // Set buttons to given color and remove ability to fire on board 2
                        if(salvoMode && Battleship_Game.player2.numRemainingMoves == 1)     // Player 2 has run out of moves upon click
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            changeButtonFunctionality(1);
                            player1Buttons[i][j].setBackground(Color.RED);
                        }
                        else if(!salvoMode)
                        {
                            changeButtonFunctionality(1);

                            player1Buttons[i][j].setBackground(Color.RED);
                        }

                        // Set the button as hit, damage the ship, check for sunken ships, and indicate to screen
                        buttonHit(player1Buttons,i,j);
                        damagePlayerShip(Battleship_Game.player1,player1ShipBoard[i][j]);
                        checkShipStatus(1);
                        String numberLocator = Integer.toString(j+1);
                        hitMissIndicator.setText("PLAYER   2   HIT   PLAYER   1   AT:   " + (char)(i+65) + numberLocator);

                        // Attempt to play explosion sound to user
                        try {AudioPlayer(explosionSound);}
                        catch(Exception ex) {System.out.println("Error Playing Audio");}

                    }
                    else                                                        // Player 2 missed...
                    {

                        buttonMiss(player1Buttons,i,j);
                        hitMissIndicator.setText("PLAYER   2   HAS   MISSED!");       // Indicate player 1 missed


                        // If the player's turn is over (standard) or is out of moves (salvo), indicate the next player's turn
                        if(salvoMode && player2.numRemainingMoves >1)
                            turnIndicator.setText("PLAYER   2   REMAINING   MOVES:   " + (player2.numRemainingMoves-1) );
                        else
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            turnIndicator.setText("IT   IS   PLAYER   1's   TURN" + (salvoMode ? ":   REMAINING   MOVES:   " + (player1.numRemainingShips) : ""));
                        }
                                     // Indicate player 2's turn


                        // Set buttons to given color and remove ability to fire on board 2
                        if(salvoMode && Battleship_Game.player2.numRemainingMoves == 1)     // Player 2 has run out of moves upon click
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            changeButtonFunctionality(1);
                            player1Buttons[i][j].setBackground(Color.WHITE);
                        }
                        else if(!salvoMode)
                        {
                            changeButtonFunctionality(1);
                            player1Buttons[i][j].setBackground(Color.WHITE);
                        }

                        // Attempt to play explosion sound to user
                        try {AudioPlayer(waterExplosionSound);}
                        catch(Exception ex) {System.out.println("Error Playing Audio");}

                    }

                    if(salvoMode)                                           // If the salvo mode is turn on, reduce player 2's remianing moves
                        Battleship_Game.player2.depleteRemainingMoves();


                }
                else if(e.getSource() == player2Buttons[i][j])                  // If a click is made on player 2's buttons (i.e. player 1 fires on player 2)
                {
                    if(numOfTotalClicks == 1)
                        changeButtonFunctionality(1);           // No plays can be made on Player 1's board

                    if(Battleship_Game.player2ShipBoard[i][j] != '~')           // The player has made a hit, ergo indicate this and make the button un-clickable
                    {

                        // If the player's turn is over (standard) or is out of moves (salvo), indicate the next player's turn
                        if(salvoMode && player1.numRemainingMoves >1)
                            turnIndicator.setText("PLAYER   1   REMAINING   MOVES:   " + (player1.numRemainingMoves-1) );
                        else
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            turnIndicator.setText("IT   IS   PLAYER   2's   TURN" + (salvoMode ? ":   REMAINING   MOVES:   " + (player2.numRemainingShips) : ""));                   // Indicate its player 1's turn
                        }

                        // Set buttons to given color and remove ability to fire on board 2
                        if(salvoMode && Battleship_Game.player1.numRemainingMoves == 1)     // Player 1 has run out of moves upon click
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            changeButtonFunctionality(2);
                            player2Buttons[i][j].setBackground(Color.RED);
                        }
                        else if(!salvoMode)
                        {
                            changeButtonFunctionality(2);
                            player2Buttons[i][j].setBackground(Color.RED);
                        }

                        // Set the button as hit, damage the ship, check for sunken ships, and indicate to screen
                        buttonHit(player2Buttons,i,j);                                     // Make the button disabled and mark as hit
                        damagePlayerShip(Battleship_Game.player2,player2ShipBoard[i][j]);  // Damage player 2's respective ship
                        checkShipStatus(2);                                      // Check to see if any of player 2's ships are sunk
                        String numberLocator = Integer.toString(j+1);
                        hitMissIndicator.setText("PLAYER   1   HIT   PLAYER   2   AT:   " + (char)(i+65) + numberLocator);           // Indicate player 2 made a hit


                        // Attempt to play explosion sound to user
                        try{AudioPlayer(explosionSound);}
                        catch(Exception ex) {System.out.println(ex); }

                    }
                    else                                                    // Player 1 missed...
                    {
                        buttonMiss(player2Buttons,i,j);
                        hitMissIndicator.setText("PLAYER   1   HAS   MISSED!");         // Indicate that player 2 has missed

                        // If the player's turn is over (standard) or is out of moves (salvo), indicate the next player's turn
                        if(salvoMode && player1.numRemainingMoves >1)
                            turnIndicator.setText("PLAYER   1   REMAINING   MOVES:   " + (player1.numRemainingMoves-1) );
                        else
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            turnIndicator.setText("IT   IS   PLAYER   2's   TURN" + (salvoMode ? ":   REMAINING   MOVES:   " + (player2.numRemainingShips) : ""));                   // Indicate it's player 1's turn
                        }


                        // (Same principles as previous if statement)
                        if(salvoMode && Battleship_Game.player1.numRemainingMoves == 1)     // Player 1 has run out of moves upon click
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            changeButtonFunctionality(2);
                            player2Buttons[i][j].setBackground(Color.WHITE);
                        }
                        else if(!salvoMode)
                        {
                            player2Buttons[i][j].setBackground(Color.WHITE);
                            changeButtonFunctionality(2);

                        }

                        // Attempt to play explosion sound to user
                        try { AudioPlayer(waterExplosionSound); }
                        catch(Exception ex) {System.out.println("Error Playing Audio"); }

                    }

                    if(salvoMode)                                           // If the salvo mode is turn on, reduce player 1's remaining moves
                        Battleship_Game.player1.depleteRemainingMoves();
                }
            }


        }

        // Check game-winning conditions

        checkWinCondition();
    }


    // Displays the game to the screen
    public Grid(boolean modeSet)
    {
        windowContent = new JPanel();

        // Determine if the game logic will follow the SALVO extra game mode
        if(modeSet == true)
            Battleship_Game.salvoMode = true;

        player1Buttons = new JButton[10][10];
        player2Buttons = new JButton[10][10];

        // Generate the title label and general content layout
        BoxLayout bl = new BoxLayout(windowContent, BoxLayout.Y_AXIS);
        windowContent.setLayout(bl);
        titleLabel = new JLabel();
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font f = new Font("Impact", Font.PLAIN, 60);
        titleLabel.setFont(f);
        titleLabel.setText("Battleship" + ( salvoMode ? ": SALVO" : ""));
        windowContent.add(titleLabel, JLabel.CENTER);

        // Generate the player labels to indicate boards
        player1Label = new JLabel("Player 1's Ocean");
        player1Label.setFont(new Font("Impact", 0 , 30));
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Label = new JLabel("Player 2's Ocean");
        player2Label.setFont(new Font("Impact", 0 , 30));
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Generate the buttons to the panel
        Dimension buttonSize = new Dimension(10,10);
        for(int i =0 ;i<10;i++)
        {
            for(int j = 0; j< 10; j++)
            {
                player1Buttons[i][j] = new JButton();
                player1Buttons[i][j].setSize(buttonSize);
                player2Buttons[i][j] = new JButton();
                player2Buttons[i][j].setSize(buttonSize);
            }
        }

        // Generate a location for the buttons to occupy with provided format
        grid1 = new JPanel();
        grid2 = new JPanel();
        grid1.setBorder(new EmptyBorder(1, 1, 1, 1));
        grid2.setBorder(new EmptyBorder(1, 1, 1, 1));
        grid1WLabel = new JPanel();
        grid2WLabel = new JPanel();
        grid1WLabel.setBorder(new EmptyBorder(1, 1, 1, 1));
        grid2WLabel.setBorder(new EmptyBorder(1, 1, 1, 1));
        grids = new JPanel();
        GridLayout gl = new GridLayout(10,10);
        grid1.setLayout(gl);
        grid2.setLayout(gl);

        Font defaultFont = new Font("SansSerif",Font.PLAIN,20);

        // Format the buttons placed in the player's boards
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                // Format and add player 1 buttons
                player1Buttons[i][j].setBackground(oceanColor);
                player1Buttons[i][j].setForeground(buttonFGColor);
                player1Buttons[i][j].addActionListener(this);
                player1Buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                player1Buttons[i][j].setPreferredSize(new Dimension(5,5));
                player1Buttons[i][j].setBounds(10,10,10,10);
                player1Buttons[i][j].setText( (char)(i+65) + Integer.toString(j+1));
                player1Buttons[i][j].setFont(defaultFont);
                grid1.add(player1Buttons[i][j]);

                // Format and add player 2 buttons
                player2Buttons[i][j].setBackground(oceanColor);
                player2Buttons[i][j].setForeground(buttonFGColor);
                player2Buttons[i][j].addActionListener(this);
                player2Buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                player2Buttons[i][j].setPreferredSize(new Dimension(5,5));
                player2Buttons[i][j].setBounds(10,10,10,10);
                player2Buttons[i][j].setText( (char)(i+65) + Integer.toString(j+1));
                player2Buttons[i][j].setFont(defaultFont);
                grid2.add(player2Buttons[i][j]);


                // When a button is disabled, make the font more visible
                player1Buttons[i][j].setUI(new MetalButtonUI()
                {
                protected Color getDisabledTextColor() {
                    return new Color(71, 71, 71);
                }});
                player2Buttons[i][j].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor()
                {

                    return new Color(71, 71, 71);
                }});
            }
        }



        // Create ship indicator layout
        GridLayout newLayout = new GridLayout(2,2);
        newLayout.setHgap(0);
        newLayout.setVgap(0);
        shipIndicators1.setLayout(newLayout);
        shipIndicators2.setLayout(newLayout);
        shipIndicators1.setMaximumSize(new Dimension(800,30));
        shipIndicators2.setMaximumSize(new Dimension(800,30));

        // Set the alignment of the ship labels in the panels to the center
        destroyer1Label.setHorizontalAlignment(JLabel.CENTER);
        carrier1Label.setHorizontalAlignment(JLabel.CENTER);
        battleship1Label.setHorizontalAlignment(JLabel.CENTER);
        cruiser1Label.setHorizontalAlignment(JLabel.CENTER);
        submarine1Label.setHorizontalAlignment(JLabel.CENTER);
        destroyer2Label.setHorizontalAlignment(JLabel.CENTER);
        carrier2Label.setHorizontalAlignment(JLabel.CENTER);
        battleship2Label.setHorizontalAlignment(JLabel.CENTER);
        cruiser2Label.setHorizontalAlignment(JLabel.CENTER);
        submarine2Label.setHorizontalAlignment(JLabel.CENTER);


        // Add the ship indicators to respective side
        shipIndicators1.setOpaque(false);
        shipIndicators2.setOpaque(false);
        shipIndicators1.add(destroyer1Label);
        shipIndicators2.add(destroyer2Label);
        shipIndicators1.add(cruiser1Label);
        shipIndicators2.add(cruiser2Label);
        shipIndicators1.add(submarine1Label);
        shipIndicators2.add(submarine2Label);
        shipIndicators2.add(battleship2Label);
        shipIndicators1.add(battleship1Label);


        // Set Ship Status Indicator Alignments in the Grid
        Font remainingShips = new Font("Impact",Font.PLAIN,40);
        destroyer1Label.setFont(remainingShips);
        destroyer1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        destroyer2Label.setFont(remainingShips);
        destroyer2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        submarine1Label.setFont(remainingShips);
        submarine1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        submarine2Label.setFont(remainingShips);
        submarine2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        cruiser1Label.setFont(remainingShips);
        cruiser1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        cruiser2Label.setFont(remainingShips);
        cruiser2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        battleship1Label.setFont(remainingShips);
        battleship1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        battleship2Label.setFont(remainingShips);
        battleship2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        carrier1Label.setFont(remainingShips);
        carrier1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        carrier2Label.setFont(remainingShips);
        carrier2Label.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Change fonts of Board Messages

        turnStatus.setFont(new Font("SansSerif",Font.BOLD,30));
        moveStatus.setFont(new Font("SansSerif",Font.BOLD,30));
        turnIndicator.setFont(new Font("SansSerif",Font.BOLD,30));
        hitMissIndicator.setAlignmentX(grid1.getAlignmentX());
        turnIndicator.setAlignmentX(grid1.getAlignmentX());
        moveStatus.setAlignmentX(grid1.getAlignmentX());
        turnStatus.setAlignmentX(grid1.getAlignmentX());
        hitMissIndicator.setFont(new Font("SansSerif",Font.BOLD,30));
        moveStatus.setForeground(Color.RED);
        turnStatus.setForeground(Color.RED);



        // Add the player indicator, board, shipIndicators and the carrier indicator to left side
        grid1WLabel.setLayout(new BoxLayout(grid1WLabel, BoxLayout.Y_AXIS));
        grid1WLabel.add(player1Label);
        grid1WLabel.add(grid1);
        grid1WLabel.add(shipIndicators1);
        grid1WLabel.add(carrier1Label);


        // Add the player indicator, board, shipIndicators and the carrier indicator to right side
        grid2WLabel.setLayout(new BoxLayout(grid2WLabel, BoxLayout.Y_AXIS));
        grid2WLabel.add(player2Label);
        grid2WLabel.add(grid2);
        grid2WLabel.add(shipIndicators2);
        grid2WLabel.add(carrier2Label);

        // Add grids to the grid layout
        grids.setLayout(new BoxLayout(grids, BoxLayout.X_AXIS));
        grids.add(grid1WLabel);
        grids.add(grid2WLabel);

        // Add the grids/boards/ship status indicators and move/turn indicators to the pane
        windowContent.add(grids);
        windowContent.add(moveStatus);
        windowContent.add(hitMissIndicator);
        windowContent.add(turnStatus);
        windowContent.add(turnIndicator);

        // Set all items to have same background color EXCEPT title and move statuses PANELS
        Color backgroundC = new Color(220,220,220);
        grid1.setBackground(backgroundC);
        grid2.setBackground(backgroundC);
        grid1WLabel.setBackground(backgroundC);
        grid2WLabel.setBackground(backgroundC);
        grids.setBackground(backgroundC);
        player1Label.setBackground(backgroundC);
        player2Label.setBackground(backgroundC);
        titleLabel.setBackground(backgroundC);
        shipIndicators1.setBackground(backgroundC);
        shipIndicators2.setBackground(backgroundC);
        cruiser1Label.setBackground(backgroundC);
        cruiser2Label.setBackground(backgroundC);
        destroyer1Label.setBackground(backgroundC);
        destroyer2Label.setBackground(backgroundC);
        carrier1Label.setBackground(backgroundC);
        carrier2Label.setBackground(backgroundC);
        battleship1Label.setBackground(backgroundC);
        battleship2Label.setBackground(backgroundC);
        submarine1Label.setBackground(backgroundC);
        submarine2Label.setBackground(backgroundC);
        turnIndicator.setBackground(backgroundC);
        hitMissIndicator.setBackground(backgroundC);
        moveStatus.setBackground(backgroundC);
        turnStatus.setBackground(backgroundC);


        // Design the JFrame to contain the windowContent Panel
        frame.setMinimumSize(new Dimension(1500,700));
        frame.setContentPane(windowContent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

    }
}
