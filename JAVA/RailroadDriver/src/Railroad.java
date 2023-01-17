/* Hasan Khan
   Dr. Steinberg
   COP3503 Fall 2022
   Programming Assignment 5
*/
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Edge{ // Edge class to hold cities
    String src;
    String dest;
    int cost;
    public Edge(String src, String dest, int cost)
    {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
    }
    public String toString() // Function to compare city to print in alphabetical order
    {
        if(src.compareTo(dest) < 0){
            return src + "---" + dest + "\t$"+cost;
        }

        return dest + "---" + src + "\t$"+cost;
    }
}

class SpaningTreeSubset{ //Subset class from Modules
    String parent;
    int level;
    SpaningTreeSubset(String parent, int level)
    {
        this.parent = parent;
        this.level = level;
    }
    public void setParent(String parent){
        this.parent = parent;
    }
    public void setLevel(int parent){
        this.level = parent;
    }
}

public class Railroad {
    LinkedList<Edge> Edges; //Linked List for graph
    HashSet<String> vertexSet; //Hashset for city names and numbers

    public Railroad(int count, String fileName) throws FileNotFoundException //Constructor class
    {
        Edges = new LinkedList<Edge>();
        vertexSet = new HashSet<String>();
        Edges = readFile(fileName);
        for (Edge Edge : Edges) {
            vertexSet.add(Edge.src);
            vertexSet.add(Edge.dest);
        }
    }

    private String findParent(Hashtable<String, SpaningTreeSubset> minimumSpaningTree, String current) //Gets the parent from teh spanning tree
    {
        if(minimumSpaningTree.get(current).parent == current)
        {
            return current;
        }
        minimumSpaningTree.get(current).setParent(findParent(minimumSpaningTree, minimumSpaningTree.get(current).parent));

        return minimumSpaningTree.get(current).parent;
    }

    private void mergeSpaningTrees(Hashtable<String, SpaningTreeSubset> minimumSpaningTree, String src, String dest) //Merges spanning trees for graph
    {
        String srcParent = findParent(minimumSpaningTree, src);
        String destParent = findParent(minimumSpaningTree, dest);

        if(minimumSpaningTree.get(srcParent).level < minimumSpaningTree.get(destParent).level){
            minimumSpaningTree.get(srcParent).setParent(destParent);
        }
        else if(minimumSpaningTree.get(srcParent).level < minimumSpaningTree.get(destParent).level){
            minimumSpaningTree.get(destParent).setParent(srcParent);
        }
        else{
            minimumSpaningTree.get(destParent).setParent(srcParent);
            minimumSpaningTree.get(srcParent).setLevel( minimumSpaningTree.get(srcParent).level+1);
        }
    }


    public String buildRailroad() //function to apply Kruskals algorithm
    {
        LinkedList<Edge> minimumSpaningTree = new LinkedList<Edge>();
        Hashtable<String, SpaningTreeSubset> subset = new Hashtable<String, SpaningTreeSubset>();

        Edges.sort((Edge r1, Edge r2)->{
            return (r1.cost - r2.cost);
        });

        for(String v : vertexSet){
            subset.put(v, new SpaningTreeSubset(v, 0)); 
        }

        for (Edge Edge : Edges) {
            String x = findParent(subset, Edge.src);
            String y = findParent(subset, Edge.dest);

            if(x != y){
                minimumSpaningTree.add(Edge);
                mergeSpaningTrees(subset, x, y);
            }
        }
        String finalLine = "";
        int totalcost = 0;
        for (Edge Edge : minimumSpaningTree) {
            finalLine += Edge + "\n";
            totalcost += Edge.cost;
        }
        finalLine += "The cost of the railroad is $"+totalcost+".";
        return finalLine;
    }

    private LinkedList<Edge> readFile(String fileName) throws FileNotFoundException //Reads files and uses
    {
        LinkedList<Edge> Edges = new LinkedList<Edge>();
        File fp = new File(fileName);
        Scanner scanner = new Scanner(fp);
        String line, values[];

        while(scanner.hasNextLine()){ //Scans the line and splits it allowing corresponding values to get to edges
            line = scanner.nextLine();
            values = line.split(" ");
            Edges.add(new Edge(values[0], values[1], Integer.parseInt(values[2])));

        }

        scanner.close();
        return Edges;
    }
}
