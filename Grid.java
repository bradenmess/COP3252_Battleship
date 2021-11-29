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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Grid extends Battleship_Game implements ActionListener
{
    JPanel windowContent;
    JLabel titleLabel, player1Label, player2Label;
    JButton player1Buttons[][];
    JButton player2Buttons[][];
    JPanel grid1, grid2, grid1WLabel, grid2WLabel, grids;

    public void enablePlayerTurn(int playerNum)
    {
        if(playerNum == 1)  // The 1st players turn, so make player 2's board uneditable
        {
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    if(player2Buttons[i][j].getText().equals(""))
                        player2Buttons[i][j].setEnabled(true);
                }
            }
            for(int i = 0; i< 10; i++)          // Remove the ability to fire on own board
            {
                for(int j = 0; j < 10; j++)
                {
                    player1Buttons[i][j].setEnabled(false);
                }
            }
        }
        else        // The 2nd players turn, so make player 1's board uneditable
        {
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    if(player1Buttons[i][j].getText().equals(""))
                        player1Buttons[i][j].setEnabled(true);
                }
            }
            for(int i = 0; i< 10; i++)          // Remove the ability to fire on own board
            {
                for(int j = 0; j < 10; j++)
                {
                    player1Buttons[i][j].setEnabled(false);
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
                    if(player1ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button unclickable
                    {
                        player1Buttons[i][j].setText("HIT");
                        player1Buttons[i][j].setEnabled(false);
                        player1Buttons[i][j].setBackground(Color.GREEN);

                        if(player1ShipBoard[i][j] == 'c')
                        {

                        }
                        if(player1ShipBoard[i][j] == 'C')
                        {

                        }
                        if(player1ShipBoard[i][j] == 'd')
                        {

                        }
                        if(player1ShipBoard[i][j] == 's')
                        {

                        }
                        if(player1ShipBoard[i][j] == 'b')
                        {

                        }


                    }
                    else
                    {
                        player1Buttons[i][j].setText("MISS");
                        player1Buttons[i][j].setBackground(Color.RED);
                        player1Buttons[i][j].setEnabled(false);

                    }
                }
                else if(e.getSource() == player2Buttons[i][j])  // If a click is made on player 2's buttons (i.e. player 1 fires on player 2)
                {

                    if(player2ShipBoard[i][j] != '~')       // The player has made a hit, ergo indicate this and make the button unclickable
                    {
                        player2Buttons[i][j].setText("HIT");
                        player2Buttons[i][j].setEnabled(false);
                        player2Buttons[i][j].setBackground(Color.GREEN);
                        grid1.repaint();
                        grid2.repaint();
                    }
                    else
                    {
                        player2Buttons[i][j].setText("MISS");
                        player2Buttons[i][j].setBackground(Color.RED);
                        player2Buttons[i][j].setEnabled(false);
                    }
                }
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
                player2Buttons[i][j].setBackground(Color.RED);
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