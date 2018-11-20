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
public class Examen {

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(
				new OutputStreamWriter(System.out));

		int n = Integer.parseInt(in.readLine());
		while (n-- > 0) {
			String[] info = in.readLine().split(" ");
			int pc = Integer.parseInt(info[0]);
			int t = Integer.parseInt(info[1]);
			Queue<Character> que = new LinkedList<>();
			Stack<Character> stack = new Stack<>();
			for (int i = 0; i < pc; i++) {
				stack.push((char) ('A' + i));
			}
			String[] tan = in.readLine().split(" ");
			for (int i = 0; i < tan.length; i++) {
				int nec = Integer.parseInt(tan[i]);
				int res = Math.abs(nec - que.size());
				for (int j = 0; j < res; j++) {
					if (nec > que.size()) {
						que.add(stack.pop());
					} else if (nec < que.size()) {
						stack.push(que.poll());
					} else {
						break;
					}
				}

			}
			int size = que.size();
			ArrayList<String> response = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				stack.push(que.poll());
			}
			while (!stack.isEmpty()) {
				response.add(stack.pop() + " ");
			}

			String rest = "";
			for (int j = response.size() - 1; j >= 0; j--) {
				rest += response.get(j);
			}
			out.write(rest + "\n");

		}
		
		in.close();
		out.close();
	}
}
