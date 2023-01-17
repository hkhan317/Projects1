/* Ilham Mukati
   Dr. Steinberg
   COP3503 Fall 2022
   Programming Assignment 1
*/

import java.io.*;
import java.util.*;

public class GreedyChildren {
	
	public int[] greedy;
    public int[] sweet;
    public int happy;
    public int angry;
    public int piecess;
    public int childrenn;

	
	/*public static void main(String[] args) throws Exception {
        GreedyChildren test = new GreedyChildren(105, 100, "children1.txt", "candy1.txt");
    }*/
    public void read(String greedyFile, String sweetFile, int pieces, int children){
        try {
            Scanner FileIn = new Scanner(new File(greedyFile));
            int i = 0;
            while(i<children){
                this.greedy[i] = FileIn.nextInt();
                i++;
            }
            FileIn.close();
            
        } catch (Exception e) {
            System.out.println("Error: ");
            System.exit(0);
        }
        try {
            Scanner FileIn2 = new Scanner(new File(sweetFile));
            int i = 0;
            while(i<pieces){
                this.sweet[i] = FileIn2.nextInt();
                i++;
            }
            FileIn2.close();
            
        } catch (Exception e) {
            System.out.println("Error: ");
            System.exit(0);
        }
    }

    public GreedyChildren(int pieces, int children, String greedyFile, String sweetFile) throws Exception{
        this.sweet = new int[pieces];
        this.greedy = new int[children];
        read(greedyFile, sweetFile, pieces, children);
        Arrays.sort(greedy);
        Arrays.sort(sweet);
        childrenn = children;
        piecess = pieces;
    }

    

    public void greedyCandy()
    {
        System.out.println("entered greedy Candy");
        int i= 0;
        for(i = 0; i < childrenn; i++){
            for(int j=0; j<piecess; j++){
                if(greedy[i]<=sweet[j]){
                	sweet[j] = -1;
                    happy++;
                    i++;
                }
            }
        }
        angry = childrenn-happy;
    }

    public void display(){
        System.out.println("There are " + happy +" happy children.");
        System.out.println("There are " + angry +" angry children.");
        
    }


}