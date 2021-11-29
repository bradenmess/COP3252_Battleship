import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Grid implements ActionListener
{
    JPanel windowContent;
    JLabel titleLabel, player1Label, player2Label;
    JButton player1Buttons[][];
    JButton player2Buttons[][];
    JPanel grid1, grid2, grid1WLabel, grid2WLabel, grids;


    public void removeButtonFunctionality(int playerNum)
    {
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

    public void addButtonFunctionality(int playerNum)
    {
        if(playerNum == 1)
        {
            for(int i = 0; i< 10 ; i++)
            {
                for(int j = 0; j< 10 ; j++)
                {
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
                if(e.getSource() == player1Buttons[i][j])   // If a click is made on player 1's buttons (i.e player 2 fires on player 1)
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

                        removeButtonFunctionality(1);   // Given player 1 button was pressed, remove button click for next turn
                        addButtonFunctionality(2);

                    }
                    else        // Player 2 missed...
                    {
                        player1Buttons[i][j].setText("MISS");
                        player1Buttons[i][j].setBackground(Color.RED);
                        player1Buttons[i][j].setEnabled(false);

                        System.out.println("Player 1 total health is: " + Battleship_Game.player1.getTotalPlayerHealth());  // Display to the terminal the remaining player 1 health
                        removeButtonFunctionality(1);   // Given player 1 button was pressed, remove button click for next turn
                        addButtonFunctionality(2);
                    }
                }
                else if(e.getSource() == player2Buttons[i][j])  // If a click is made on player 2's buttons (i.e. player 1 fires on player 2)
                {

                    if(Battleship_Game.player2ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button unclickable
                    {
                        player2Buttons[i][j].setText("HIT");
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

                        System.out.println("Player 2 total health is: " + Battleship_Game.player2.getTotalPlayerHealth());
                        removeButtonFunctionality(2);   // Given player 2 button was pressed, remove button click for next turn
                        addButtonFunctionality(1);
                    }
                    else        // Player 1 missed...
                    {
                        player2Buttons[i][j].setText("MISS");
                        player2Buttons[i][j].setBackground(Color.RED);
                        player2Buttons[i][j].setEnabled(false);

                        System.out.println("Player 2 total health is: " + Battleship_Game.player2.getTotalPlayerHealth());
                        removeButtonFunctionality(2);   // Given player 2 button was pressed, remove button click for next turn
                        addButtonFunctionality(1);
                    }
                }
            }


        }

        // Check sunken status of ships. Only display upon first time...

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
                System.out.println("PLAYER 2 HAS WON!");
            else                                            // Player 2 lost all their health
                System.out.println("PLAYER 1 HAS WON!");
        }
    }


    public Grid()
    {
        windowContent = new JPanel();
        player1Buttons = new JButton[10][10];
        player2Buttons = new JButton[10][10];
        JSeparator separator = new JSeparator();

        BoxLayout bl = new BoxLayout(windowContent, BoxLayout.Y_AXIS);
        windowContent.setLayout(bl);

        titleLabel = new JLabel();
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("Battleship");
        windowContent.add(titleLabel, JLabel.CENTER);

        player1Label = new JLabel("Player 1's Board");
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Label = new JLabel("Player 2's Board");
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        grid1 = new JPanel();
        grid2 = new JPanel();
        grid1WLabel = new JPanel();
        grid2WLabel = new JPanel();
        grids = new JPanel();

        GridLayout gl = new GridLayout(10, 10);
        grid1.setLayout(gl);
        grid2.setLayout(gl);

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                player1Buttons[i][j] = new JButton();
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
                player2Buttons[i][j].setBackground(Color.BLUE);
                player2Buttons[i][j].addActionListener(this);
                grid2.add(player2Buttons[i][j]);
            }
        }

        grid1WLabel.setLayout(new BoxLayout(grid1WLabel, BoxLayout.Y_AXIS));
        grid1WLabel.add(player1Label);
        grid1WLabel.add(grid1);

        grid2WLabel.setLayout(new BoxLayout(grid2WLabel, BoxLayout.Y_AXIS));
        grid2WLabel.add(player2Label);
        grid2WLabel.add(grid2);

        grids.setLayout(new BoxLayout(grids, BoxLayout.X_AXIS));
        grids.add(grid1WLabel);
        grids.add(grid2WLabel);
        windowContent.add(grids);

        JFrame frame = new JFrame("Battleship");
        frame.setContentPane(windowContent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}