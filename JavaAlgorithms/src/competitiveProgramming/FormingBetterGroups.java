/**
 * 
 */
package competitiveProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * ICPC 2018 JAVERIANA PROBLEM B
 *
 */
public class FormingBetterGroups {

	public static void main(String[] args) throws Exception {

		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(
				new OutputStreamWriter(System.out));

		String[] info = in.readLine().split(" ");
		int n = Integer.parseInt(info[0]);
		int b = Integer.parseInt(info[1]);
		String[] students = in.readLine().split(" ");
		int[] m = new int[students.length];
		for (int i = 0; i < m.length; i++) {
			m[i] = Integer.parseInt(students[i]);
		}
//		(1<<n)-1
		int res = solve(m, b, n );
		
		out.write(res+"\n");
		in.close();
		out.close();
	}
	// CUANDO SE CORRE BITS NO SE AFECTA EL NUMERO
	public static int solve(int[] m, int budge, int mask) {

		int total = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = i + 1; j < m.length && ((mask >> i) & 1) > 0; j++) {
//				&& ((mask >> i) & 1) > 0
				int maskK = mask;
				for (int k = j + 1; k < m.length
						&& ((mask >> j) & 1) > 0; k++) {
					boolean pass = pass(budge, m[i], m[j], m[k]);
					maskK = (mask ^ (1 << i));
					maskK = (maskK ^ (1 << j));
					maskK = (maskK ^ (1 << k));
					if (pass) {						
						total++;
					}
					total += solve(m, budge, maskK);
				}
			}
		}
		return total;
	}

	public static boolean pass(int budge, int... numbers) {
		return Math.max(numbers[0], Math.max(numbers[1], numbers[2])) - Math
				.min(numbers[0], Math.min(numbers[1], numbers[2])) <= budge;

	}
}
