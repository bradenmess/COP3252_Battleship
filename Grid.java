import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class Grid extends Battleship_Game implements ActionListener
{
    JPanel windowContent;
    JLabel titleLabel, player1Label, player2Label;
    JButton player1Buttons[][];
    JButton player2Buttons[][];
    JPanel grid1, grid2, grid1WLabel, grid2WLabel, grids;

    JLabel hitMissIndicator = new JLabel("AWAITING");
    JLabel turnIndicator = new JLabel("FIRST ATTACK");
    // ImageIcon fireImage = new ImageIcon("");
    // ImageIcon explosionImage = new ImageIcon("./giphy.gif");

    public void AudioPlayer(String Pathname) throws UnsupportedAudioFileException, IOException, LineUnavailableException   // This creates the "explosion sound" when a ship is hit
    {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(Pathname));
        Clip myClip = AudioSystem.getClip();
        myClip.open(audioStream);
        myClip.loop(0);
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

    public void removeButtonFunctionality(int playerNum)    // Used to remove a player's ability to click on one side of the board. Used to force turns
    {                                                       // IMPORTANT NOTE: This function likewise determines which player goes first AFTER the first player fires!
        if(playerNum == 1)
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    player1Buttons[i][j].setEnabled(false);
                }
            }
        }
        else
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    player2Buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    public void addButtonFunctionality(int playerNum)       // Used to add the ability to click buttons on one side of the board
    {
        if(playerNum == 1)
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    if(player1Buttons[i][j].getText().equals(""))       // The space is not already hit/miss
                        player1Buttons[i][j].setEnabled(true);
                }
            }
        }
        else
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
                    if(player2Buttons[i][j].getText().equals(""))       // The space is not already hit/miss
                        player2Buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {



        for(int i = 0; i < 10 ; i++)
        {
            for(int j = 0; j < 10 ; j++)
            {
                if(e.getSource() == player1Buttons[i][j])   // If a click is made on player 1's buttons (player 2 fires on player 1)
                {
                    if(Battleship_Game.player1ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button un-clickable
                    {
                        player1Buttons[i][j].setText("HIT");
                        player1Buttons[i][j].setEnabled(false);
                        player1Buttons[i][j].setBackground(Color.GREEN);

                        if(Battleship_Game.player1ShipBoard[i][j] == 'c')   // Decrease the health of player 1's cruiser
                        {
                            Battleship_Game.player1.damage('c');
                        }
                        if(Battleship_Game.player1ShipBoard[i][j] == 'C')   // Decrease the health of player 1's Carrier
                        {
                            Battleship_Game.player1.damage('C');
                        }
                        if(Battleship_Game.player1ShipBoard[i][j] == 'd')   // Decrease the health of player 1's destroyer
                        {
                            Battleship_Game.player1.damage('d');
                        }
                        if(Battleship_Game.player1ShipBoard[i][j] == 's')   // Decrease the health of player 1's submarine
                        {
                            Battleship_Game.player1.damage('s');
                        }
                        if(Battleship_Game.player1ShipBoard[i][j] == 'b')   // Decrease the health of player 1's battleship
                        {
                            Battleship_Game.player1.damage('b');
                        }
                        System.out.println("Player 1 total health is: " + Battleship_Game.player1.getTotalPlayerHealth());  // Display to the terminal the remaining player 1 health

                        checkShipStatus(1);
                        removeButtonFunctionality(1);   // Given player 1 button was pressed, remove button click for next turn
                        addButtonFunctionality(2);

                        hitMissIndicator.setText("PLAYER 2 HAS HIT PLAYER 1!");
                        try
                        {
                            AudioPlayer("./mixkit-truck-crash-with-explosion-1616 (mp3cut.net) (1).wav");
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error Playing Audio");
                        }
                        turnIndicator.setText("IT IS PLAYER 1's TURN");

                    }
                    else        // Player 2 missed...
                    {
                        player1Buttons[i][j].setText("MISS");               // Mark as "Miss" and make button un-clickable
                        player1Buttons[i][j].setBackground(Color.RED);
                        player1Buttons[i][j].setEnabled(false);

                        removeButtonFunctionality(1);   // Given player 1 button was pressed, remove button click for next turn
                        addButtonFunctionality(2);
                        hitMissIndicator.setText("PLAYER 2 HAS MISSED!");       // Indicate player 1 missed
                        turnIndicator.setText("IT IS PLAYER 1's TURN");         // Indicate player 2's turn

                        try                     // Play the explosion sound
                        {
                            AudioPlayer("./yt5s.com - Water Splash Sound FX 1 (320 kbps) (mp3cut.net).wav");
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error Playing Audio");
                        }

                        // System.out.println("Player 1 total health is: " + Battleship_Game.player1.getTotalPlayerHealth());  // Display to the terminal the remaining player 1 health
                        // The above line of code is for error checking. Purpose: Display player 1's total health after firing


                    }
                }
                else if(e.getSource() == player2Buttons[i][j])  // If a click is made on player 2's buttons (i.e. player 1 fires on player 2)
                {

                    if(Battleship_Game.player2ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button un-clickable
                    {
                        player2Buttons[i][j].setText("HIT");                // Mark as hit and make button un-clickable
                        player2Buttons[i][j].setEnabled(false);
                        player2Buttons[i][j].setBackground(Color.GREEN);

                        if(Battleship_Game.player2ShipBoard[i][j] == 'c')   // Decrease the health of player 2's cruiser
                        {
                            Battleship_Game.player2.damage('c');
                        }
                        if(Battleship_Game.player2ShipBoard[i][j] == 'C')   // Decrease the health of player 2's Carrier
                        {
                            Battleship_Game.player2.damage('C');
                        }
                        if(Battleship_Game.player2ShipBoard[i][j] == 'd')   // Decrease the health of player 2's destroyer
                        {
                            Battleship_Game.player2.damage('d');
                        }
                        if(Battleship_Game.player2ShipBoard[i][j] == 's')   // Decrease the health of player 2's submarine
                        {
                            Battleship_Game.player2.damage('s');
                        }
                        if(Battleship_Game.player2ShipBoard[i][j] == 'b')   // Decrease the health of player 2's battleship
                        {
                            Battleship_Game.player2.damage('b');
                        }

                       //  System.out.println("Player 2 total health is: " + Battleship_Game.player2.getTotalPlayerHealth());
                        checkShipStatus(2);
                        removeButtonFunctionality(2);   // Given player 2 button was pressed, remove button click for next turn
                        addButtonFunctionality(1);

                        hitMissIndicator.setText("PLAYER 1 HAS HIT PLAYER 2!");     // Indicate player 2 made a hit
                        turnIndicator.setText("IT IS PLAYER 2's TURN");             // Indicate it's player 1's turn

                        try                     // Play the explosion sound
                        {
                            AudioPlayer("./mixkit-truck-crash-with-explosion-1616 (mp3cut.net) (1).wav");
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error Playing Audio");
                        }


                    }
                    else        // Player 1 missed...
                    {
                        player2Buttons[i][j].setText("MISS");                                   // Indicate player 2 missed
                        player2Buttons[i][j].setBackground(Color.RED);
                        player2Buttons[i][j].setEnabled(false);

                        removeButtonFunctionality(2);   // Given player 2 button was pressed, remove button click for next turn
                        addButtonFunctionality(1);
                        hitMissIndicator.setText("PLAYER 1 HAS MISSED!");   // Indicate that player 2 has missed
                        turnIndicator.setText("IT IS PLAYER 2's TURN");     // Indicate it is now Player 1's turn

                        try                     // Play the explosion sound
                        {
                            AudioPlayer("./yt5s.com - Water Splash Sound FX 1 (320 kbps) (mp3cut.net).wav");
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error Playing Audio");
                        }

                        // System.out.println("Player 2 total health is: " + Battleship_Game.player2.getTotalPlayerHealth());
                        // The above line of code is for error checking. Purpose: Display player 2's total health after firing


                    }
                }
            }


        }

        // The following lines of code were used for error-checking and can be ignored
        // Purpose: printing to terminal when a ship has been sunk for verification

        // Check sunken status of ships. Only display upon first time...
        /*
        if(Battleship_Game.player1.playerCruiserHP == 0)
        {
            System.out.println("Player 1's cruiser has been destroyed!");
        }
        if(Battleship_Game.player1.playerDestroyerHP == 0)
        {
            System.out.println("Player 1's destroyer has been destroyed!");
        }
        if(Battleship_Game.player1.playerBattleshipHP == 0)
        {
            System.out.println("Player 1's battleship has been destroyed!");
        }
        if(Battleship_Game.player1.playerSubmarineHP == 0)
        {
            System.out.println("Player 1's submarine has been destroyed!");
        }
        if(Battleship_Game.player1.playerCarrierHP == 0)
        {
            System.out.println("Player 1's carrier has been destroyed!");
        }

        if(Battleship_Game.player2.playerCruiserHP == 0)
        {
            System.out.println("Player 2's cruiser has been destroyed!");
        }
        if(Battleship_Game.player2.playerDestroyerHP == 0)
        {
            System.out.println("Player 2's destroyer has been destroyed!");
        }
        if(Battleship_Game.player2.playerBattleshipHP == 0)
        {
            System.out.println("Player 2's battleship has been destroyed!");
        }
        if(Battleship_Game.player2.playerSubmarineHP == 0)
        {
            System.out.println("Player 2's submarine has been destroyed!");
        }
        if(Battleship_Game.player2.playerCarrierHP == 0)
        {
            System.out.println("Player 2's carrier has been destroyed!");
        }
        */



        // Check game-winning conditions

        if(Battleship_Game.player1.getTotalPlayerHealth() == 0 || Battleship_Game.player2.getTotalPlayerHealth() == 0)
        {

            for(int i = 0 ; i< 10; i++)
            {
                for(int j = 0; j< 10; j++)
                {
                    // Remove ability to click all buttons in game

                    player1Buttons[i][j].setEnabled(false);
                    player2Buttons[i][j].setEnabled(false);
                }
            }


            if(Battleship_Game.player1.getTotalPlayerHealth() == 0)         // Player 1 lost all their health
            {
                // System.out.println("PLAYER 2 HAS WON!");
                hitMissIndicator.setText("PLAYER 2 HAS WON THE GAME!");
                turnIndicator.setText(" ");
            }
            else // Player 2 lost all their health
            {
                // System.out.println("PLAYER 1 HAS WON!");
                hitMissIndicator.setText("PLAYER 1 HAS WON THE GAME!");
                turnIndicator.setText(" ");
            }

        }
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
                player1Buttons[i][j].setBackground(Color.BLUE);
                player1Buttons[i][j].addActionListener(this);
                grid1.add(player1Buttons[i][j]);
            }
        }


        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                player2Buttons[i][j] = new JButton();
                player2Buttons[i][j].setPreferredSize(new Dimension(40,40));
                player2Buttons[i][j].setBackground(Color.BLUE);
                player2Buttons[i][j].addActionListener(this);
                grid2.add(player2Buttons[i][j]);
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

        grid1WLabel.setLayout(new BoxLayout(grid1WLabel, BoxLayout.Y_AXIS));
        grid1WLabel.add(player1Label);
        grid1WLabel.add(grid1);

        grid1WLabel.add(destroyer1Label);
        grid1WLabel.add(submarine1Label);
        grid1WLabel.add(cruiser1Label);
        grid1WLabel.add(battleship1Label);
        grid1WLabel.add(carrier1Label);

        grid2WLabel.setLayout(new BoxLayout(grid2WLabel, BoxLayout.Y_AXIS));
        grid2WLabel.add(player2Label);
        grid2WLabel.add(grid2);

        grid2WLabel.add(destroyer2Label);
        grid2WLabel.add(submarine2Label);
        grid2WLabel.add(cruiser2Label);
        grid2WLabel.add(battleship2Label);
        grid2WLabel.add(carrier2Label);

        grids.setLayout(new BoxLayout(grids, BoxLayout.X_AXIS));
        grids.add(grid1WLabel);
        grids.add(grid2WLabel);
        windowContent.add(grids);


        hitMissIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
        hitMissIndicator.setFont(new Font("Impact", 0 , 30));
        windowContent.add(hitMissIndicator);

        turnIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
        turnIndicator.setFont(new Font("Impact", 0 , 30));
        windowContent.add(turnIndicator);


        JFrame frame = new JFrame("Battleship");
        frame.setMinimumSize(new Dimension(1500,1000));
        frame.setContentPane(windowContent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }
}
