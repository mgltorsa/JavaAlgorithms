/**
 * 
 */
package competitiveProgramming;

import java.io.*;
import java.util.Arrays;
/**
 * @author Miguel
 *
 */
public class UvaJudge_10080_GopherII {

	public static class DoublePoint {
		public double x;
		public double y;
		public DoublePoint using;

		public DoublePoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double distance(DoublePoint point) {
			double sDistance = Math.pow(Math.abs(point.x - x), 2.0)
					+ Math.pow(Math.abs(point.y - y), 2);
			return Math.sqrt(sDistance);
		}
	}

	public static void main(String[] args) throws java.lang.Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(System.out));
		for (String line = br.readLine(); br.ready(); line = br.readLine()) {

			String[] info = line.split(" ");
			int n = Integer.parseInt(info[0]);
			int m = Integer.parseInt(info[1]);
			int s = Integer.parseInt(info[2]);
			int v = Integer.parseInt(info[3]);

			DoublePoint[] gophers = new DoublePoint[n];
			DoublePoint[] holes = new DoublePoint[m];

			for (int i = 0; i < n; i++) {
				String[] point = br.readLine().split(" ");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				gophers[i] = new DoublePoint(x, y);
			}
			for (int i = 0; i < m; i++) {
				String[] point = br.readLine().split(" ");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				holes[i] = new DoublePoint(x, y);
			}
			boolean[][] graph = createGraph(s, v, gophers, holes);
			int max = maximumBipartiteMatching(graph);

			bw.write((n-max) + "\n");
		}
		br.close();
		bw.close();
	}

	private static boolean[][] createGraph(int s, int v, DoublePoint[] gophers,
			DoublePoint[] holes) {
		boolean[][] graph = new boolean[gophers.length][holes.length];
		for (int i = 0; i < gophers.length; i++) {
			DoublePoint p = gophers[i];
			for (int j = 0; j < holes.length; j++) {
				double dist = p.distance(holes[j]);
				if ((v * s) >= dist) {

					graph[i][j] = true;

				}
			}
		}
		return graph;
	}

	public static int maximumBipartiteMatching(boolean[][] graph) {
//		printGraph(graph);
		int holes = graph[0].length;
		int gophers = graph.length;
		int[] matchs= new int[holes];
		Arrays.fill(matchs, -1);
		int result = 0;
		for (int i = 0; i < gophers; i++) {
			boolean visited[] = new boolean[holes];
			if (canAssigned(graph, i, visited, matchs)) {
				result++;
			}
		}
		
//		printMatchs(matchs);
		return result;
	}

	/**
	 * 
	 */
	private static void printMatchs(int[] matchs) {
		System.out.println();
		System.out.println("Printing matchs");
		for (int i = 0; i < matchs.length; i++) {
			System.out.print(i+" ");
		}
		System.out.println();
		
	}

	/**
	 * @param graph
	 */
	private static void printGraph(boolean[][] graph) {
		System.out.println("PRINTING GRAPH");
		System.out.println();
		System.out.print("  ");
		for (int j = 0; j < graph[0].length; j++) {
			System.out.print(j+" ");

		}
		System.out.println();
		
		for (int i = 0; i < graph.length; i++) {
			System.out.print(i+" ");
			for (int j = 0; j < graph[0].length; j++) {
				int t = graph[i][j] ? 1:0;
				String space =" ";
				if(j-9>=0) {
					space="  ";
				}
				System.out.print(t+space);
			}
			System.out.println();
		}
		
	}

	static boolean canAssigned(boolean graph[][], int currentMatching,
			boolean visited[], int matchs[]) {
		int holes = graph[0].length;
		for (int j = 0; j < holes; j++) {
			if (graph[currentMatching][j] && !visited[j]) {
				visited[j] = true;
				if (matchs[j] < 0
						|| canAssigned(graph, matchs[j], visited, matchs)) {
					matchs[j] = currentMatching;
					return true;
				}
			}
		}
		return false;
	}

}
