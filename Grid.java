import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.io.File;
import java.io.IOException;


public class Grid extends Battleship_Game implements ActionListener
{
    JPanel windowContent;
    JLabel titleLabel, player1Label, player2Label;
    JButton player1Buttons[][];
    JButton player2Buttons[][];
    JPanel grid1, grid2, grid1WLabel, grid2WLabel, grids;
    JLabel hitMissIndicator = new JLabel("AWAITING");
    JLabel turnIndicator = new JLabel("FIRST ATTACK");
    JLabel moveStatus = new JLabel("LAST PLAYER MOVE");
    JLabel turnStatus = new JLabel("NEXT MOVE");

    public static int numOfTotalClicks;     // Used to lock one side of the board in salvo mode once the first shot is fired

    String localDir = System.getProperty("java.class.path")+"\\";
    File waterExplosionSound = new File(localDir+"yt5s.com - Water Splash Sound FX 1 (320 kbps) (mp3cut.net).wav");
    File explosionSound = new File(localDir+"mixkit-truck-crash-with-explosion-1616 (mp3cut.net) (1).wav");

    public void AudioPlayer(File fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException   // This creates the "explosion sound" when a ship is hit
    {
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
                    }

                }
            }
        }


    }

    public void checkShipStatus(int playerNum)      // This function will check if the provided ship is sunk (i.e. the ship health is 0 for the player)
    {
        if(playerNum ==1)
        {
            if (Battleship_Game.player1.playerBattleshipHP == 0)
                sunkenShip(1,'b');
            if (Battleship_Game.player1.playerSubmarineHP == 0)
                sunkenShip(1,'s');
            if (Battleship_Game.player1.playerDestroyerHP == 0)
                sunkenShip(1,'d');
            if (Battleship_Game.player1.playerCruiserHP == 0)
                sunkenShip(1,'c');
            if (Battleship_Game.player1.playerCarrierHP == 0)
                sunkenShip(1,'C');
        }
        else
        {
            if (Battleship_Game.player2.playerBattleshipHP == 0)
                sunkenShip(2,'b');
            if (Battleship_Game.player2.playerSubmarineHP == 0)
                sunkenShip(2,'s');
            if (Battleship_Game.player2.playerDestroyerHP == 0)
                sunkenShip(2,'d');
            if (Battleship_Game.player2.playerCruiserHP == 0)
                sunkenShip(2,'c');
            if (Battleship_Game.player2.playerCarrierHP == 0)
                sunkenShip(2,'C');
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

                    if(player2Buttons[i][j].getText().equals(""))       // Set unused buttons on player 2's board to enabled
                        player2Buttons[i][j].setEnabled(true);
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

                    if(player1Buttons[i][j].getText().equals(""))       // Set unused buttons on player 1's board to enabled
                        player1Buttons[i][j].setEnabled(true);
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

            windowContent.remove(turnStatus);
            windowContent.remove(moveStatus);
            windowContent.add(hitMissIndicator);


            if(Battleship_Game.player1.getTotalPlayerHealth() == 0)         // Player 1 lost all their health
            {
                hitMissIndicator.setText("PLAYER 2 HAS WON THE GAME!");
                turnIndicator.setText(" ");
            }
            else // Player 2 lost all their health
            {
                hitMissIndicator.setText("PLAYER 1 HAS WON THE GAME!");
                turnIndicator.setText(" ");
            }

        }
    }

    public void actionPerformed(ActionEvent e)
    {
        numOfTotalClicks++;

        for(int i = 0; i < 10 ; i++)
        {
            for(int j = 0; j < 10 ; j++)
            {
                if(e.getSource() == player1Buttons[i][j])   // If a click is made on player 1's buttons (player 2 fires on player 1)
                {
                    if(numOfTotalClicks == 1)
                        changeButtonFunctionality(2);           // No plays can be made on Player 2's board

                    if(Battleship_Game.player1ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button un-clickable
                    {
                        buttonHit(player1Buttons,i,j);
                        damagePlayerShip(Battleship_Game.player1,player1ShipBoard[i][j]);
                        checkShipStatus(1);
                        hitMissIndicator.setText("PLAYER 2 HAS HIT PLAYER 1!");
                        turnIndicator.setText("IT IS PLAYER 1's TURN");

                        if(salvoMode && Battleship_Game.player2.numRemainingMoves == 1)     // Player 2 has run out of moves upon click
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            changeButtonFunctionality(1);
                        }
                        else if(!salvoMode)
                            changeButtonFunctionality(1);

                        try {AudioPlayer(explosionSound);}
                        catch(Exception ex) {System.out.println("Error Playing Audio");}
                    }
                    else                                                        // Player 2 missed...
                    {

                        buttonMiss(player1Buttons,i,j);
                        hitMissIndicator.setText("PLAYER 2 HAS MISSED!");       // Indicate player 1 missed
                        turnIndicator.setText("IT IS PLAYER 1's TURN");         // Indicate player 2's turn


                        if(salvoMode && Battleship_Game.player2.numRemainingMoves == 1)     // Player 2 has run out of moves upon click
                        {
                            player1.moveNumIdentifier();
                            player1.resetCurrentMoves();
                            changeButtonFunctionality(1);
                        }
                        else if(!salvoMode)
                            changeButtonFunctionality(1);

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
                        buttonHit(player2Buttons,i,j);                                     // Make the button disabled and mark as hit
                        damagePlayerShip(Battleship_Game.player2,player2ShipBoard[i][j]);  // Damage player 2's respective ship
                        checkShipStatus(2);                                      // Check to see if any of player 2's ships are sunk
                        hitMissIndicator.setText("PLAYER 1 HAS HIT PLAYER 2!");           // Indicate player 2 made a hit
                        turnIndicator.setText("IT IS PLAYER 2's TURN");                   // Indicate it's player 1's turn



                        if(salvoMode && Battleship_Game.player1.numRemainingMoves == 1)     // Player 1 has run out of moves upon click
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            changeButtonFunctionality(2);
                        }
                        else if(!salvoMode)
                            changeButtonFunctionality(2);




                        try{AudioPlayer(explosionSound);}
                        catch(Exception ex) {System.out.println(ex); }

                    }
                    else                                                    // Player 1 missed...
                    {
                        buttonMiss(player2Buttons,i,j);
                        hitMissIndicator.setText("PLAYER 1 HAS MISSED!");   // Indicate that player 2 has missed
                        turnIndicator.setText("IT IS PLAYER 2's TURN");     // Indicate it is now Player 1's turn



                        if(salvoMode && Battleship_Game.player1.numRemainingMoves == 1)     // Player 1 has run out of moves upon click
                        {
                            player2.moveNumIdentifier();
                            player2.resetCurrentMoves();
                            changeButtonFunctionality(2);
                        }
                        else if(!salvoMode)
                            changeButtonFunctionality(2);




                        try { AudioPlayer(waterExplosionSound); }
                        catch(Exception ex) {System.out.println("Error Playing Audio"); }

                    }

                    if(salvoMode)                               // If the salvo mode is turn on, reduce player 1's remaining moves
                        player1.depleteRemainingMoves();
                }
            }


        }

        // Check game-winning conditions

        checkWinCondition();
    }

    public Grid()
    {

        windowContent = new JPanel();
        player1Buttons = new JButton[10][10];
        player2Buttons = new JButton[10][10];


        BoxLayout bl = new BoxLayout(windowContent, BoxLayout.Y_AXIS);
        windowContent.setLayout(bl);

        titleLabel = new JLabel();
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font f = new Font("Impact", Font.PLAIN, 60);
        titleLabel.setFont(f);
        titleLabel.setText("Battleship");
        windowContent.add(titleLabel, JLabel.CENTER);

        player1Label = new JLabel("Player 1's Ocean");
        player1Label.setFont(new Font("Impact", 0 , 30));
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Label = new JLabel("Player 2's Ocean");
        player2Label.setFont(new Font("Impact", 0 , 30));
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        grid1 = new JPanel();
        grid2 = new JPanel();
        grid1.setBorder(new EmptyBorder(10, 10, 10, 10));
        grid2.setBorder(new EmptyBorder(10, 10, 10, 10));
        grid1WLabel = new JPanel();
        grid2WLabel = new JPanel();
        grid1WLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        grid2WLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        grids = new JPanel();

        GridLayout gl = new GridLayout(10, 10);
        grid1.setLayout(gl);

        grid2.setLayout(gl);



        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                player1Buttons[i][j] = new JButton();
                player1Buttons[i][j].setPreferredSize(new Dimension(40,40));
                player1Buttons[i][j].setBackground(new Color(150,250,250));
                player1Buttons[i][j].addActionListener(this);
                grid1.add(player1Buttons[i][j]);

                player2Buttons[i][j] = new JButton();
                player2Buttons[i][j].setPreferredSize(new Dimension(40,40));
                player2Buttons[i][j].setBackground(new Color(150,250,250));
                player2Buttons[i][j].addActionListener(this);
                grid2.add(player2Buttons[i][j]);


                // When a button is disabled, make the font more visible

                player1Buttons[i][j].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.BLACK;
                }});
                player2Buttons[i][j].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.BLACK;
                }});
            }
        }


        JLabel destroyer1Label = new JLabel("Destroyer (2)");
        destroyer1Label.setFont(new Font("Serif", Font.PLAIN, 28));
        destroyer1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel destroyer2Label = new JLabel("Destroyer (2)");
        destroyer2Label.setFont(new Font("Serif", Font.PLAIN, 28));
        destroyer2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel submarine1Label = new JLabel("Submarine (3)");
        submarine1Label.setFont(new Font("Serif", Font.PLAIN, 28));
        submarine1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel submarine2Label = new JLabel("Submarine (3)");
        submarine2Label.setFont(new Font("Serif", Font.PLAIN, 28));
        submarine2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cruiser1Label = new JLabel("Cruiser (3)");
        cruiser1Label.setFont(new Font("Serif", Font.PLAIN, 28));
        cruiser1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cruiser2Label = new JLabel("Cruiser (3)");
        cruiser2Label.setFont(new Font("Serif", Font.PLAIN, 28));
        cruiser2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel battleship1Label = new JLabel("Battleship (4)");
        battleship1Label.setFont(new Font("Serif", Font.PLAIN, 28));
        battleship1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel battleship2Label = new JLabel("Battleship (4)");
        battleship2Label.setFont(new Font("Serif", Font.PLAIN, 28));
        battleship2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel carrier1Label = new JLabel("Carrier (5)");
        carrier1Label.setFont(new Font("Serif", Font.PLAIN, 28));
        carrier1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel carrier2Label = new JLabel("Carrier (5)");
        carrier2Label.setFont(new Font("Serif", Font.PLAIN, 28));
        carrier2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Change fonts of Board Messages!

        turnStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        turnStatus.setFont(new Font("Impact",Font.BOLD,40));
        moveStatus.setFont(new Font("Impact",Font.BOLD,40));


        turnIndicator.setFont(new Font("Impact",Font.BOLD,40));
        hitMissIndicator.setFont(new Font("Impact",Font.BOLD,40));
        turnIndicator.setMaximumSize(new Dimension(500,60));
        turnIndicator.setMinimumSize(new Dimension( 500,60));
        hitMissIndicator.setMaximumSize(new Dimension(500,60));
        hitMissIndicator.setMinimumSize(new Dimension(500,60));
        turnIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
        hitMissIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add ship sunk-status indicators
        grid1WLabel.setLayout(new BoxLayout(grid1WLabel, BoxLayout.Y_AXIS));
        grid1WLabel.add(player1Label);
        grid1WLabel.add(grid1);
        grid1WLabel.add(destroyer1Label);
        grid1WLabel.add(submarine1Label);
        grid1WLabel.add(cruiser1Label);
        grid1WLabel.add(battleship1Label);
        grid1WLabel.add(carrier1Label);
        grid1WLabel.add(moveStatus);
        grid1WLabel.add(hitMissIndicator);

        grid2WLabel.setLayout(new BoxLayout(grid2WLabel, BoxLayout.Y_AXIS));
        grid2WLabel.add(player2Label);
        grid2WLabel.add(grid2);
        grid2WLabel.add(destroyer2Label);
        grid2WLabel.add(submarine2Label);
        grid2WLabel.add(cruiser2Label);
        grid2WLabel.add(battleship2Label);
        grid2WLabel.add(carrier2Label);
        grid2WLabel.add(turnStatus);
        grid2WLabel.add(turnIndicator);

        grids.setLayout(new BoxLayout(grids, BoxLayout.X_AXIS));
        grids.add(grid1WLabel);
        grids.add(grid2WLabel);
        windowContent.add(grids);

        JFrame frame = new JFrame("Battleship");
        frame.setMinimumSize(new Dimension(1500,1000));
        frame.setContentPane(windowContent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

    }
}
