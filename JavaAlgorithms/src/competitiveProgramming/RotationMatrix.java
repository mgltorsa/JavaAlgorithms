package competitiveProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Miguel
 *
 */
public class RotationMatrix {
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int[][] matrix = readMatrix(br);
		sortByRotations(matrix);
	}

	/**
	 * @param matrix
	 */
	private static void sortByRotations(int[][] matrix) {
		//
		
	}

	/**This method reads an integer n for the creation of an nxn array,
	 * then reads lines with space-separated integers 
	 * @param br
	 * @return
	 * @throws Exception 
	 */
	private static int[][] readMatrix(BufferedReader br) throws Exception {
		int n = Integer.parseInt(br.readLine());
		int[][] matrix = new int[n][n];
		String line;
		while((line=br.readLine())!=null && !line.equals("")) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					matrix[i][j]=Integer.parseInt(line.charAt(j)+"");
				}
			}
		}
		return matrix;
	}
}
