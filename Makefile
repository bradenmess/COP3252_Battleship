all: Battleship_Game.java HPlayer.java Grid.java
	javac Battleship_Game.java HPlayer.java Grid.java
	java Battleship_Game.java

clean:
	rm *.class