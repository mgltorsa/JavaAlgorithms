/**
 * 
 */
package competitiveProgramming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Miguel
 *
 */
public class Uva_Largest_sum_game {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while( (line=br.readLine())!=null && !line.equals("") ){
			String[] info = line.split(" ");
			int[] array = new int[info.length];
			for(int i=0; i<info.length; i++){
				array[i]=Integer.parseInt(info[i]);
			}
			int sum = sum(array);
			System.out.println(sum);
		}
		br.close();
	}

	public static int sum (int[] array){

		int sum =0;
		int pivot =0;
		for (int num : array) {
			sum = Math.max(sum, pivot);
			pivot =(num>pivot+num) ? num : pivot+num;
		}
		
		return Math.max(sum, pivot);
	}
}
