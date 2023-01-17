/** Dr. Andrew Steinberg
 *  COP3503 Computer Science 2
 *  Programming Assignment 2 Driver
 *  Fall 2022
 */
 

public class MagicMazeDriver 
{   
    public static void main(String args[]) throws Exception
    {
        MagicMaze maze = new MagicMaze("maze1.txt", 11, 25);
           
        if(maze.solveMagicMaze())
			System.out.println("maze1.txt" + " passed!");
		else
			System.out.println("maze1.txt" + " did not passed!");
    }
}
