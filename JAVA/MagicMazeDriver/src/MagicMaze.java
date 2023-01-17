/* Ilham Mukati
   Dr. Steinberg
   COP3503 Fall 2022
   Programming Assignment 1
*/

import java.io.*;
import java.util.*;


public class MagicMaze {
	
	public char[][] mazee;
	public boolean[][] seen;
	public HashMap<Character, ArrayList<Integer>> transport = new HashMap<Character, ArrayList<Integer>>();
	public String mazeNumber;
	public int rows;
	public int cols;
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello world!");
	  
		String mazeN = "maze1.txt";
	  
	  
		MagicMaze m1 = new MagicMaze(mazeN, 11, 16);
		if (m1.solveMagicMaze()){
			System.out.println("Faks for maze 1");
		}else{
			System.out.println("its grits");
		}
		
	}
	
	
	// Creates a Magic maze object
	public MagicMaze(String nameOfFile, int r, int c) throws Exception {
		
		this.rows = r;
        this.cols = c;
		seen = new boolean[r][c];
		mazee = new char[r][c];
		try {
            Scanner FileIn = new Scanner(new File(nameOfFile));
            int i = 0;
            while(FileIn.hasNextLine()){
                String line = FileIn.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    mazee[i][j] = line.charAt(j);
					//System.out.print(mazee[i][j]);
                }
				//System.out.println();
                i++;
            }
            
        } catch (Exception e) {
            System.out.println("Error: "/*+e.getMessage()*/);
            System.exit(0);
        }
		mapMaker();  
    }



	//creates hashmap of transportation areas
	public void mapMaker(){
		for(int i=0; i< this.rows; i++){
			for(int j=0; j< this.cols; j++){
				if(Character.isDigit(mazee[i][j])){
					if(!transport.containsKey(mazee[i][j])){
						transport.put(mazee[i][j], new ArrayList<Integer>());
					}
					transport.get(mazee[i][j]).add(i);
					transport.get(mazee[i][j]).add(j);
				}
			}
		}
		//System.out.println(transport.get('1'));
	}

    public boolean validSpace(int x, int y){
        if(x>=0 && x<rows && y>=0 && y<cols && !seen[x][y] && mazee[x][y] != '@' ){
			System.out.println("We movin to"+x+","+y);
            return true;
        }
		System.out.println("Did not like this space" +x+","+y);
        return false;
    }

	//takes in the number of the transport spots and the row and col its in and returns the row and col of the other number in the maze
	public int[] transportation(char num, int row, int col){
		int[] ans = new int[2];
		//mazee[row][col] = "*"
		if(transport.get(num).get(0)==row && transport.get(num).get(1)==col){
			ans[0] = transport.get(num).get(2);
			ans[1] = transport.get(num).get(3);
		} else{
			ans[0] = transport.get(num).get(0);
			ans[1] = transport.get(num).get(1);
			//mazee[ans[0]][ans[1]] = "*"
		}
		//System.out.println("Transportation has been invoked");
		return ans;
	}
        
	

	public int solveMagicMazeRec(int R, int C){
		//checks out of bounds
		int x = R;
		int y = C;
		if (mazee[x][y] == 'X')
		{
			return 1;
		}
		if(Character.isDigit(mazee[x][y])){
			seen[x][y] = true;
			int newpos[] = transportation(mazee[x][y], x, y);
			if(!seen[newpos[0]][newpos[1]]){
				return solveMagicMazeRec(newpos[0], newpos[1]);
			}
		}
		if(validSpace(x-1,y))
		{
			if(solveMagicMazeRec(x-1,y) == 1)
				return 1;
		}
		if(validSpace(x+1,y))
		{
			if(solveMagicMazeRec(x+1,y) == 1)
				return 1;
		}
		if(validSpace(x,y-1))
		{
			if(solveMagicMazeRec(x,y-1) == 1)
				return 1;
		}
		if(validSpace(x,y+1))
		{
			if(solveMagicMazeRec(x,y+1) == 1)
				return 1;
		}
		return 0;
	}
	public boolean solveMagicMaze(){
		//return solveMagicMazeRec(0, 14);//does stop when x is found
		if(solveMagicMazeRec(rows-1, 0) == 1)
			return true;
		else
			return false;
	}

}
