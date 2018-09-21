/**
 * 
 */
package competitiveProgramming;

import java.io.*;
import java.util.*;

/**
 * @author Miguel
 *
 */
public class RomeoAndJulietSecrets {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		for (int i = 0; i < n; i++) {
			String line = br.readLine();
			String pattern = br.readLine();
			int k = Integer.parseInt(br.readLine());
			String[] patterns = getPatterns(pattern,k);
			int count=countPatterns(patterns,line);
			bw.write(count+"\n");
		}
		
		br.close();
		bw.close();
	}

	/**
	 * @param patterns
	 * @param line
	 * @return
	 */
	private static int countPatterns(String[] patterns, String line) {
		int count=0;
		int[][] find = new int[patterns.length][line.length()];
		boolean[] mark = new boolean[line.length()];
		for (int i = 0; i < find.length; i++) {
			int[] z = findPatterns(patterns[i], line);
			find[i]=z;
		}
		for(int j=0;j<find[0].length;j++) {
			int k = find[0][j];
			if(k==1) {
				count++;
				mark[j]=true;
			}
		}
		for (int i = 1; i < find.length; i++) {
			for (int j = 0; j < find[0].length; j++) {
				int  k = find[i][j];
				if(k==1 && !mark[j]) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * @param pattern
	 * @param line
	 * @return
	 */
	private static int[] findPatterns(String pattern, String line) {
		char[] patt = pattern.toCharArray();
		char[] li = line.toCharArray();
		int[] array = new int[line.length()];
		for (int i = 0; i < li.length;i++) {
			Boolean isPattern=false;
			comp(array,patt,li,i, isPattern);

			
		}
		return array;
	}

	/**
	 * @param patt
	 * @param li
	 * @return
	 */
	private static void comp(int[] array, char[] patt, char[] li, int i, Boolean isPattern) {
		int k=0;
		int count=0;
		int j=i;
		for (; j < li.length&&k<patt.length; j++,k++) {
			char lij = li[j];
			char pattj = patt[k];
			count++;
			if( !(lij==pattj || pattj=='1' ) ) {
				isPattern=false;
				break;
			}
			isPattern=true;
		}
		if(count<patt.length) {
			isPattern=false;
		}
		if(isPattern) {
			array[j-1]=1;
		}
	}

	/**
	 * @param pattern
	 * @param k
	 * @return
	 */
	private static String[] getPatterns(String pattern, int k) {
		int i=0;
		int z = k-1;
		String[] patterns = new String[pattern.length()-(z)];
		for (int j = 0; j < patterns.length; j++) {
			if(i<pattern.length()) {
			patterns[j]=getPattern(pattern,i,k);
			i++;
			}else {
				break;
			}
		}
		
		
		return patterns;
	}

	/**
	 * @param pattern
	 * @param i
	 * @param k
	 * @return
	 */
	private static String getPattern(String pattern, int i, int k) {
		char[] chr = pattern.toCharArray();
		int z=0;
		while(z<k) {
			chr[i]='1';
			z++;
			i++;
		}
		return new String(chr);
	}
	
}
