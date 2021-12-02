SPECIAL NOTE: Upon compiling with the javac command the error "Uses Unsafe Checks" is encountered. This tends to happen with larger projects, so we ask to use the provided
" XLint:unchecked " flag when compiling. The program should still work as-intended on all other platforms.

See DISCLAIMER SECTION below for more


===================================
ADVANCED JAVA BATTLESHIP PROJECT:
===================================


	       Ryan Goodrich
	      Marcelo Zapatta
	      Braden Mess

	       Dr. Amirizirtol
        COP 3234 Advanced Java Programming
	       
              Date of Completion: 12/1/2021





===================================
                      GAME RULES
===================================

	Battleship is a game in which two players utilize guessing to figure out where an opponent has placed their ships on their private board. In a physical setting (i.e. not 
on an individual computer), a player would call out a specific location on a labeled 10x10 grid to guess where the opponen's ship may be located: if the player chose a location
that is occupied by an opponent's ship, they mark it on their private board (which we refer to as the player's "radar") in red as a "HIT". If the opponent does not have
a ship at the guessed location, the player marks the location on their private board in white, a registered "MISS". The opponent likewise marks on their ship-board any locations
on their ships which have been previously hit.

	Each player starts out with 5 unique ships: a Destroyer (size 2), a Cruiser (size 3), a Submarine (size 3), a Battleship (size 4), and a Carrier (size 5), which are (typically)
placed prior to staring the game on the player's private board referred to as the player's "Ocean". Each player's "Ocean" is not visible to the opponent, as such would give away their
ships' positions. 

	When a ship is hit in all of it's occupied locations, it is said to be "sunk".

Key Words:

"Ocean" - The player's private board in which the ships physically live. Is not visible to opponent
"Radar" - The player's private board in which registered "HIT" and "MISS" turns are placed. For all intents and purposes, this board does not have to be hidden
                but typically is when playing the physical game
"HIT"  - When a player guesses a position on the opponents board that is occupied by a ship.
"MISS" - When a player guesses a position that is not a registered hit
"SUNK" - When a player guesses/has-guessed all positions occupied by an opponent's ship, the ship is said to be "sunk"


***Win-Condition***:  A player wins the game if they manage to sink all of the opponents ships before their own ships are all sunk. 

======================================================================
	OUR IMPLEMENTATION: RANDOMIZED SHIP LOCATIONS
======================================================================

In order to avoid needing multiple screens and/or windows with large grids to justify 4 playing boards, we have decided to implement the Battleship game
in which the ships are randomly-placed instead of physically placed by the user. This was done for a few reasons

1. It eliminates the need to have 4 visible-boards (players really don't need to know where their ships are located once placed as they cannot physically
   call out "hit" or "miss" to the opposing player on the computer).

2. It is our understanding/philosophy that for a game designed for a computer, players should never have to "leave the computer area"; by this we mean
    there is no coded checks/balances set in place if a player happened to view the opponent place their ships on the board (and simply only revealing
    one board at a time does not alleviate the need for a player to temporarily "remove themselves" from the area so the opponent can place their ships).
    As a result, by placing the ships randomly on the board, the only focus the player's should have would be to guess where the OTHER opponent's
    ships are.

3. By removing the need for 4 individual boards (or separate boards that are used a single time throughout the game), the overall UI can be designed cleaner and reduces
    the number of windows that need to be opened/closed at any given time

======================================================================
		USER INTERFACE INSTRUCTIONS
======================================================================

Though the overall game mechanics are relatively simple, the UI needs some small explanation in order to grasp the full game:

The UI features 2 boards: "Player 1's Ocean" and "Player 2's Ocean" in which the ships are randomly placed and live for the duration of the game (though they are not visible
at first). Players take turns by clicking on THE OPPONENT's board (i.e. Player 1's move will consist of firing at PLAYER 2's board, and vice versa) to fire. Upon clicking one of the
blue buttons, three possibilities occur:

1. The button stores the location of an opponent's ship, and ergo the player has "HIT" the opponent's ship. The button will display "HIT" and will be marked RED
2. The button does not store the location of an opponent's ship. The button turns WHITE and registers as a "MISS"
3. If the button contains the location of a ship and that location is the last surviving piece of the ship, the button and all other locations of the same ship will turn
    BLACK, indicating the ship has been sunk.

Upon completing a turn, two JLabels toward the bottom of the screen will display information regarding the recent move (i.e. "PLAYER 1 MISSED!") and will likewise indicate
the current turn's status (i.e. "IT'S PLAYER 2's MOVE"). These will change throughout the duraction of the game

Upon sinking a ship, the appropriate ship-name will be struck through underneath the opponent's board and the font will change to RED.

NOTE: At any given time, the buttons on one of the two boards will be entirely disabled; for example, if it is Player 1's turn, player 1' board will have all of its buttons disabled.
This is to avoid having an instance where a player would fire on their own board.

Upon a player winning the game, the JLabels at the bottom will update to display who the winner is. Additionally, 2 buttons will appear at the bottom in case the players want to play again,
one for each of the two provided game modes.

Additionally, prior to playing the game, a start menu will pop-up which will include two buttons: one for the "standard" gamemode and one for the "salvo" gamemode (see below).

======================================================================
			GAME MODES
======================================================================

Our implementation of battleship features 2 different modes designed by Hasbro (the inventors of the game), selectable prior to playing on the Start Menu:

Standard: Player's each take turns firing at the opponent's board one at a time. The opponent will take their turn next regardless if a "hit" or "miss" was registered.

SALVO Mode: During a player's turn, the player can fire on the opponent's board for as many times as they have ships remaining (i.e. not sunk). This means, for example,
if player 1 has 5 ships remaning on their board, they can fire on player 2's board 5 times. Each time a player's ship is destroyed, they lose one of their abilities to fire on the opponents board.


======================================================================
			EXTRA FEATURES
======================================================================

Below we have identified our "special features" for the game:

- Start menu
- SALVO Gamemode
- Sounds Upon button Clcik and Backgrounds


======================================================================
			DISCLAIMERS***
======================================================================

***DISCLAIMER: At the time of submission, the sounds and backgrounds/icons features have not been commented out. Upon testing with linprog, errors occured
where the audio/images would not load, even with the relative AND absolute pathnames provided to work in the current user directory, and a pop-up on the terminal
indicating exceeding the total amount of mmap() data supplied to the user was encountered. While programming, we used XMing 
to forward X11 streams to our local machines to run the UI (as linprog does not have a designated interface) to check that it works using Java SDK 11 on the appropriate environment, 
but we are unsure if the images/sounds would not diplay/play because of XMing. During our video we demonstrated that the sounds and backgrounds/icons worked 
as-intended on our own local machines using XCode and IntelliJ without any alterations

To implement sound and/or images, we ask for the following:

- Run the program on an IDE that is not linprog (i.e. IntelliJ or XCode)
- In the "Grid" Class, seed the locations of the files absolute pathname on your local computer (i.e. C:\\ ... ). The code is designed to implement the files based on your current working directory
  but the pathname may appear different using windows C:\\ pathnames versus UNIX's ./ relative pathname

With the above instructions, the sounds/images SHOULD work on your local machine as well. 

If the sounds and images do not load, all other features of the game should work as-intended/stated above.


ADDITIONAL DISCLAIMER:
- Upon sinking a ship, the ship label under the board will be struck through. When compiling on linprog, even with the XLint:unchecked flag, an unavaoidable warning popped up. This feature at the time
   of submission will be commented out. If you plan on testing this feature, please use a different IDE at this time (though the game will function normally without it). Otherwise the labels will simply
   turn red instead.


======================================================================
			CLASS STRUCTURE BREAKDOWN
======================================================================

NOTE: Any notable function marked with an asterisk * was utilized in terminal-play only (i.e. checking functionality) and can be ignored

Battleship_Game: Provides basic functionality of the boards utilized in the UI. Where the ships' locations are stored and where information about
"hit" and "miss" calls are made. 

	Noteable functions:
	isValid()		// Checks is a ship's placement would be valid. Used in placeShip and placePlayerShips()
	placeShip()	// Physically places the ship onto the board of the supplied player
	*displayBoard()	// Displays the board to the terminal					
	*isGameWon()	// Determines if the game is won to the terminal			 
	placePlayerShips()	// Randomly places all of the player's ships onto their respective boards	
	main()		// Loads the start menu and holds the instantiation for the Battleship_Game object

StartMenu: Displays the start menu prior to playing the actual game

	StartMenu()	// Displays the start menu with the title and gamemode buttons
	actionPerformed()	// Listens for gamemode buttons clicks. Set's salvo boolean value in Battleship_Game()

HPlayer:	Holds all of the information about the player's player number, ship health (as opposed to ship objects), and current remaining moves (SALVO mode only)

	canContinue()		// Determines if the player can continue to play in SALVO mode 
	depleteRemainingMoves()	// In SALVO mode, reduces the total number of moves a player can make on their turn
	resetCurrentMoves()		// Resets the players current remaining moves to the number of ships they have left
	moveNumIdentifier()		// Identifies how many of one's ships have been destroyed
	damage()			// Damages a player's specific ship when supplied with a shipType character argument (reduces player[shipname]HP variable by 1)
	
Grid:	Where all the magic occurs: implements how the game is demonstrated to the user and where player moves are made

	sunkenShip()		// When a player's ship is sunk, makes all buttons black and disables the button. Marks red (and strikes out SEE DISCLAIMER SECTION) the approriate ship under board
	checkShipStatus()		// Determines if a player's ship has been recently sunk, and runks sunkenShip() if it is
	changeButtonFunctionality()	// Removes the ability to play on one side of the board on a player's turn. Alternates active/deactivated side based on supplied player
	damagePlayerShip()		// Checks the ships type when hit and appropriately damages the player's specific ship
	buttonHit()		// When a player registers a hit, makes the button red and disables it
	buttonMiss()		// When a player registers a miss, makes the button white and disables it
	checkWinCondition()	// Checks the total health of the players each move. If totalPlayerHP is 0, the game is won
	Grid()			// Displays the boards, ships' sunken status, turns, and newGame features (upon game win)
		
	actionPerformed()		// This function is very robust and uses all (except Grid) aforementioned functions in the Grid class. In summary:
				// 1. Checks which side of the board was clicked, and registers as a hit/miss. In SALVO mode, likewise determines if a player can continue
				// 2. Alters the buttons to register a hit/miss and appropriately damages the player/ship
				// 3. Checks the game winning status (if applicable)
				// 4. Removes previous sides' functionality appropriately (immediately if in standard mode, when turns are depleted in SALVO mode)
				// 5. Plays unique sound upon registering a hit/miss (SEE EXTRA FEATURES)
				
				// 6. If game is won, displays the new game button (which will reset the game and the board to the desired gamemode)




======================================================================
			WORKLOAD BREAKDOWN
======================================================================

Objective				Name:
----------------------------------------------------------------------------------------------------------

GENERAL LOGIC

- Random Ship Placement Mechanic:	Ryan Goodrich, Marcelo Zapata;  
----------------------------------------------------------------------------------------------------------
- Ship Placement Validity:		Ryan Goodrich, Marcelo Zapata ; 
----------------------------------------------------------------------------------------------------------
- Game-Win Status Testing:		Ryan Goodrich, Marcelo Zapata ; 
----------------------------------------------------------------------------------------------------------
- Button ActionListening:		Ryan Goodrich ; 	
----------------------------------------------------------------------------------------------------------
- Class-wide Connections: 		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Pre-UI Terminal Testing:		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Class Constructors (Non-UI):		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Class Variable Layout:		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Audio/Image Functionality: 		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Code-Wide Comments:		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- EXTRA FEATURE Start Menu:		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- EXTRA FEATURE - SALVO Gamemode: 	Ryan Goodrich ; 


USER INTERFACE:

- Start Menu (Layout)		Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
- Grid Layout Management	
----------------------------------------------------------------------------------------------------------
- Background and Audio		Ryan Goodrich ; 



OTHER:

ReadmeFile			Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
PowerPoint Slides			Ryan Goodrich ; 
----------------------------------------------------------------------------------------------------------
Video				Marcelo Zapata;



