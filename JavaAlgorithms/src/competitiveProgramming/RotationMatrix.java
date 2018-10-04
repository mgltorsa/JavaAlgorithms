package competitiveProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Miguel
 *
 */
public class RotationMatrix {

	public static void main(String[] args) throws Exception {
		File file = new File(".");
		
		FileInputStream fr = new FileInputStream(file);
		fr.read();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int[][] matrix = readMatrix(br);
		//Test, TODO totalRotation method.
		int total = totalRotations(matrix);		
		bw.write("Total " + (total));
		bw.close();
		br.close();
	}

	/**
	 * @param matrix
	 */
	private static int totalRotations(int[][] matrix) {
		int iDestination, jDestination, relativePosition, total = 0;
		for (int i = 0; i < matrix.length; i++) {

			for (int j = 0; j < matrix.length; j++) {
				relativePosition = matrix[i][j] / matrix.length;
				iDestination = relativePosition;	
				jDestination = matrix[i][j] - (relativePosition) -1 ;				
				total += Math.abs(iDestination - i) + Math.abs(jDestination - j);

			}
		}
		return total;
	}

	/**
	 * This method reads an integer n for the creation of an nxn matrix, then reads
	 * lines with space-separated integers
	 * 
	 * @param br
	 * @return
	 * @throws Exception
	 */
	private static int[][] readMatrix(BufferedReader br) throws Exception {
		int n = Integer.parseInt(br.readLine());
		int[][] matrix = new int[n][n];
		String line = br.readLine();
		int i = 0;

		do {
			String[] data = line.split(" ");

			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = Integer.parseInt(data[j]);
			}
			
			i++;
			line = br.readLine();
		} while (line != null && !line.equals(""));
		return matrix;
	}
}
