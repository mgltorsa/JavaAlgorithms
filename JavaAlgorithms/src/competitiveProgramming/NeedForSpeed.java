/**
 * 
 */
package competitiveProgramming;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Miguel
 *
 */
public class NeedForSpeed {

	public static void main(String[] args) {
//		int a = 6;
//		double b = (double)6/4;
//		System.out.println(b);
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int t = sc.nextInt();
		int[] dArray = new int[n];
		int[] sArray = new int[n];
		for (int i = 0; i < n; i++) {
			dArray[i] = sc.nextInt();
			sArray[i] = sc.nextInt();
		}

	

		System.out.println("" + calculateC(dArray, sArray, t));

		sc.close();
	}

	/**
	 * @param dArray
	 * @param sArray
	 * @param t
	 * @return
	 */
	private static String calculateC(int[] dArray, int[] sArray, int t) {
		DecimalFormat format = new DecimalFormat("#0.000000");
		double c = -sArray[0];
		double min = 1e-9;
	
		for (int i = 0; i < sArray.length; i++) {
			c = Math.max(c, (-sArray[i]+min));
		}
		double highInteger = 2000100;
		int steps = 500;
		while(steps-->0 && compSum(c,highInteger,min)==-1) {
			double mid = (c+highInteger)*0.5;
			double ti = calculateSum(dArray,sArray,mid);
			int comp = compSum(ti, t, min);
			if(comp==-1) {
				highInteger=mid;
			}
			else if(comp==1){
				c=mid;
			}else {
				break;
			}			
		}
		return format.format(c);
	}

	/**
	 * @param dArray
	 * @param sArray
	 * @param mid
	 * @return
	 */
	private static double calculateSum(int[] dArray, int[] sArray, double c) {
		double sum=0.0;
		for (int i = 0; i < sArray.length; i++) {
			sum+=(double)dArray[i]/((double)sArray[i]+c);
		}
		return sum;
	}

	/**
	 * @param c
	 * @param highInteger
	 * @param min
	 * @return
	 */
	private static int compSum(double c, double highInteger, double min) {
		int response=0;
		if(c<=highInteger+min) {
			if(c+min<highInteger) {
				return -1;
			}
			return 0;
		}
		
		return 1;
	}


}
