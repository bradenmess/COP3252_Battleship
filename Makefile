all: Battleship_Game.java Cruiser.java Destroyer.java Grid.java HPlayer.java OceanBoard.java Player.java Ship.java
	javac Battleship_Game.java Cruiser.java Destroyer.java Grid.java HPlayer.java OceanBoard.java Player.java Ship.java
	java Battleship_Game.java

clean:
	rm *.class