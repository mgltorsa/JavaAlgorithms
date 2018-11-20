package competitiveProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

//FROM http://acmicpc-pacnw.org/ProblemSet/2017/alled.pdf
public class RainbowRoads {

    public static void main(String[] args) {
	// TEST CASE
	// 10 1 2 3 2 5 6 7 8 9 1
	ArrayList b = new ArrayList<>();
	b.add(new String("papah"));
	System.out.println(b.get(0));
	Scanner sc = new Scanner(System.in);
	int n = sc.nextInt();
	int[] array = new int[n];
	for (int i = 0; i < n; i++) {
	    array[i] = sc.nextInt();
	}

	int r =lis(array, n);
	System.out.println(r+"");
	sc.close();
    }

    private static int lis(int[] array, int n) {
	int max = 0;
	int[] s = new int[n];
	for (int i = 0; i < n; i++) {
	    s[i] = 1;
	    for (int j = 0; j < i - 1; j++) {
		if (array[j] < array[i] && s[i] < s[j] + 1) {
		    s[i] = s[j] + 1;
		    if (s[i] > max) {
			max = s[i];
		    }
		}
	    }
	}
	
	return max;

    }

}
