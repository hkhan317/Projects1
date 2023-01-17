/* Hasan Khan
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 3
 */

public class LCS 
{
	private String A; //String 1
	private String B; // String 2
	private String xLCS = ""; // LCS String
	public LCS(String B, String A) // Constructor Class
	{
		this.B = B;
		this.A = A;
	}
	public String getA()// Getter Class
	{
		return A;
	}
	public String getB()//Getter Class
	{
		return B;
	}
	public void lcsDynamicSol()//Dynamic Solution
	{
		int m = A.length(); //Gets length of string.
		int n = B.length();
		int[][] tableLCS = new int[m+1][n+1]; //Initializes 2d ARRAY using string length
		for(int i = 0; i <= m; i++){
			for(int j = 0; j <= n; j++){
				if(i == 0||j == 0)
					tableLCS[i][j]=0; // sets values to zero
				else if(getA().charAt(i-1) == getB().charAt(j-1)) //adds one to previous row and column alongside this one
					tableLCS[i][j] = tableLCS[i - 1][j - 1] + 1;
				else
					tableLCS[i][j] = Math.max(tableLCS[i - 1][j], tableLCS[i][j - 1]); //find max value of previous row or column
			}
		}
		int index = tableLCS[m][n];
		int temp = index;
		char[] yLCS = new char[index + 1]; //char array for characters that will be in LCS
		yLCS[index] = '\0'; // terminating character
		int i = m; //Starts at bottom corner towards the end
		int j = n;
		while(i > 0 && j > 0)
		{
			if(getA().charAt(i - 1) == getB().charAt(j - 1)) //checks if characters are same
			{
				yLCS[index - 1] = getA().charAt(i - 1);	//adds character to char array			 
                i--; //reduce value  by 1
                j--;
                index--;	
			}
			else if (tableLCS[i-1][j] > tableLCS[i][j-1]) //go in larger direction
				i--;
			else
				j--;
		}
		for(int k = 0; k < temp; k++) //Uses for loop to add each character to the string.
			xLCS = xLCS + yLCS[k];
	}
	public String getLCS() //Getter for LCS
	{
		return xLCS;
	}
}
