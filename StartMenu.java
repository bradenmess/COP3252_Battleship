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
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;


public class StartMenu extends JFrame implements ActionListener
{
    JPanel startMenu = new JPanel();
    JLabel titleLabel = new JLabel();
    JButton standardModeButton = new JButton();
    JButton salvoModeButton = new JButton();
    JButton[][] startButtons = new JButton[2][1];
    JFrame startFrame = new JFrame("Battleship");

    String localDir = System.getProperty("java.class.path")+"/";
    ImageIcon img = new ImageIcon(localDir+ "TopographicBackground.jpg");

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == standardModeButton)
        {
            Grid standardGrid = new Grid(false);
            startFrame.dispose();

        }
        else if(e.getSource() == salvoModeButton)
        {
            Grid salvoGrid = new Grid(true);
            startFrame.dispose();
        }
    }


    public StartMenu()
    {
        JLabel background = new JLabel("",img,JLabel.CENTER);

        // Set the JFrame to contain all buttons/panels

        startFrame.setBackground(new Color(50,250,250));
        titleLabel.setText("BATTLESHIP");
        titleLabel.setFont(new Font("Impact",Font.BOLD,70));
        titleLabel.setBackground(Color.WHITE);

        // Establish the button's with their tooltips and sizes
        standardModeButton.setPreferredSize(new Dimension(400,100));
        salvoModeButton.setPreferredSize(new Dimension(400,100));
        salvoModeButton.setToolTipText("Play Battleship: SALVO - Players get to take extra turns depending on their number \n of remaining ships");
        standardModeButton.setToolTipText("Play Battleship: STANDARD - Players take turns firing on opponent's board");

        // Establish and design the JPanel which will contain the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,5,5));
        buttonPanel.setOpaque(false);


        // Instantiate the buttons and place them on the JPanel
        startButtons[0][0] = standardModeButton;
        startButtons[1][0] = salvoModeButton;
        Font buttonFont = new Font("SansSerif",Font.BOLD,30);
        startButtons[0][0].setText("Play Standard Mode");
        startButtons[1][0].setText("Play \"SALVO\" Mode");
        startButtons[0][0].setFont(buttonFont);
        startButtons[1][0].setFont(buttonFont);
        startButtons[0][0].addActionListener(this);
        startButtons[1][0].addActionListener(this);
        buttonPanel.add(startButtons[0][0]);
        buttonPanel.add(startButtons[1][0]);

        // Add the button panel to the JFrame
        startFrame.add(titleLabel);
        startFrame.add(buttonPanel);

        GridBagLayout l = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        background.setLayout(l);
        c.gridx = 0;
        c.gridy = 0;
        background.add(titleLabel,c);
        c.gridy = 1;
        background.add(buttonPanel,c);

        // Set frame default starting size and add the components
        startFrame.setMinimumSize(new Dimension(500,500));
        startFrame.setResizable(false);
        startMenu.setBackground(Color.BLUE);
        startFrame.setContentPane(background);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.gridy = 2;
        startFrame.pack();
        startFrame.setVisible(true);
    }


}
