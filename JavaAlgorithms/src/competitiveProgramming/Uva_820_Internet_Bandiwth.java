package competitiveProgramming;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Uva_820_Internet_Bandiwth {

    public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	int net = 1;
	for (String line = br.readLine(); br.ready(); line = br.readLine(),net++) {
	    int nodes = Integer.parseInt(line);
	    if (nodes == 0) {
		break;
	    }
	    int[][] graph = new int[nodes][nodes];
	    String[] info = br.readLine().split(" ");
	    int s = Integer.parseInt(info[0]) - 1;
	    int t = Integer.parseInt(info[1]) - 1;
	    int c = Integer.parseInt(info[2]);
	    HashMap<Integer, ArrayList<Integer>> adj= new HashMap<>();
	    adj.put(s, new ArrayList<>());
	    adj.put(t, new ArrayList<>());

	    while (c-- > 0) {
		info = br.readLine().split(" ");
		int i = Integer.parseInt(info[0]) - 1;
		int j = Integer.parseInt(info[1]) - 1;
		int cost = Integer.parseInt(info[2]);
		if(adj.containsKey(i)) {
		    adj.put(i, new ArrayList<>());
		}
		if(adj.containsKey(j)) {
		    adj.put(j, new ArrayList<>());
		}
		
		adj.get(i).add(j);
		adj.get(j).add(i);
		
		graph[i][j] = cost;
	    }
	    
	    System.out.println("Network "+net);
	    System.out.println("The bandwidth is "+maxFlow(s,t,graph,graph.length));
	}
	br.close();
	bw.close();
    }

    private static int maxFlow(int s, int t, int[][] graph, int n) {
	
	boolean[][] edges = new boolean[n][n];
	return -1;
    }

}
