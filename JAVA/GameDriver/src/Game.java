import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* Hasan Khan
 * Dr. Andrew Steinberg
 * COP3503 Fall 2022
 * Programming Assignment 1
 */




public class Game 
{
	int[][] board;
	int player;
	char[] moves;
	String filename;
	int size;
	File file;
	public Game(int x, String filename) // Constructor Class
	{
		this.board = new int[x][x]; // Initilaize board
		size = board.length;
		this.file = new File(filename);
	}
	public void readMoves(String filename) throws FileNotFoundException //Reading move files 
	{
		Scanner sc = new Scanner(file); //Scanner for count
		Scanner scx = new Scanner(file);;//Scanner for hcar
		int c = 0;
		while(sc.hasNextLine())
		{
			c++;
			sc.next();
		}
		moves = new char[c];
		int i = 0;
		while(scx.hasNextLine())
		{
			moves[i] = scx.next().charAt(0);
			i++;
		}
		sc.close();
		scx.close();
		
	}
	
	public int countMoves(String filename) throws FileNotFoundException //Counting moves in a file using scanner
	{
		Scanner sc = new Scanner(file);
		int c = 0;
		while(sc.hasNextLine())
		{
			c++;
			sc.next();
		}
		sc.close();
		return c;
	}
	public int play(int playerWin) throws FileNotFoundException //play function split into two helper function
	{
		readMoves(filename);
		if(playerWin == 1)
			player = player1Win();
		if(playerWin == 2)
			player = player2Win();
		return player;
	}
	
	
	public int player1Win() throws FileNotFoundException
	{
		int x = 1; //x coord
		int y = 1;// y coord
		int c = countMoves(filename);
		board[x][y] = 1; // at one since player 1 will be moving diagonal as first move
			for(int i = 0; i < c; i++)
			{
				if(board[board.length-1][board.length-1] == 0)
				{
						if(moves[i] == 'r') //Everytime player 2 goes right player 1 will also unless the board length is out of bounds
						{
							if(x+1 == board.length)
							{
								y++;
								board[x][y+2] = 1;
							}
								else
								{
									x = x + 2;
									board[x][y] = 1;
								}
						}
						if(moves[i] == 'b') //Everytime player 2 goes down player 1 will also unless the board length is out of bounds
						{
							y = y + 2;
							board[x][y] = 1;
						}
						if(moves[i] == 'd') //Everytime player 2 goes diagonal player 1 goes diagonal unless out of bounds
						{
							if(x+1 == board.length)
							{
								y = y+2;
								board[x][y] = 1;
							}
							
							else if(y+1 == board.length)
							{
								x = x + 2;
								board[x][y] = 1;
							}
							else
							{
								x = x+2;
								y = y+2;
								board[x][y] = 1;
							}
						}
				}
			}
		return board[board.length-1][board.length-1];
	}
	public int player2Win() throws FileNotFoundException
	{
		int x = 1;
		int y = 0;
		int c = countMoves(filename);
		board[x][y] = 1; // for this one we set x to 1 and y to 0 because player 1 first move is right
		for(int i = 0; i < c; i++)
		{
			if(board[board.length-1][board.length-1] != 2) // while the board hasnt been touched
			{
				if(board[board.length-2][board.length-2] == 1) // Smart Scenario as mentioned in announcements
				{
					board[board.length-1][board.length-1] = 2;
				}
				else if(x + 1 == board.length) // if it meets border only one way to go
				{
					y++;
					board[x][y] = 2;
					if(y+1 != board.length)
					{
						y++;
						board[x][y] = 1;	
					}
				}
				else if(y + 1 == board.length) // if it meets border only one way to go
				{
					x++;
					board[x][y] = 2;
					x++;
					board[x][y] = 1;
				}
				else
				{
					if(moves[i] == 'r')
					{
						x++;
						board[x][y] = 2;
						if(x == 6 && y == 6) // player 1 will only move the same direction if the board is at the end
						{
							x++;
							board[x][y] = 1;
						}
						else
						{
							y++;
							board[x][y] = 1;
						}
					}
					if(moves[i] == 'b')
					{
						y++;
						board[x][y] = 2;
						if(x == 6 && y == 6) // same srategy as rightwards just flipped
						{
							x++;
							board[x][y] = 1;
						}
						else
						{
							x++;
							board[x][y] = 1;
						}
					}
					if(moves[i] == 'd')
					{
						x++;
						y++;
						board[x][y] = 2; //showing p2 move
						if(x == 6 && y == 6) // player one will not take the win and choose to not go diagonal
						{
							x++;
							board[x][y] = 1;
						}
						else if(x + 1 != board.length && y + 1 != board.length) //any other scenario it will go diagonal so long as it isnt the winning move
						{
							y++;
							x++;
							board[x][y] = 1;
						}
					}
				}
			}
		}
		return board[board.length-1][board.length-1]; //return value
	}

}