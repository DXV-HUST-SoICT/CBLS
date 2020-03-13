package practice;

import java.util.Random;

import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;

public class ConditionalSumExample {
	
	public static void main(String[] args) {
		LocalSearchManager mgr = new LocalSearchManager();
		int M = 7;
		int N = 3;
		int[] w = new int[M];
		VarIntLS[] X = new VarIntLS[M];
		for (int i = 0; i < M; i++) {
			X[i] = new VarIntLS(mgr, 0, N - 1);
		}
		IFunction f = new ConditionalSum(X, w, 1);
		mgr.close();
		Random R = new Random();
		for (int i = 0; i < M; i++) {
			w[i] = R.nextInt(100);
			int tmp = R.nextInt(N);
			X[i].setValuePropagate(tmp);
			System.out.print(w[i] + " ");
		}
		System.out.println();
		
		for (int i = 0; i < M; i++) {
			System.out.print(X[i].getValue() + " ");
		}
		System.out.println();
		
		
		System.out.println("f = " + f.getValue());
		int tmp = R.nextInt(N);
		System.out.println("tmp = " + tmp);
		int d = f.getAssignDelta(X[6], tmp);
		System.out.println("d = " + d + ", f = " + f.getValue());
		X[6].setValuePropagate(tmp);
		System.out.println("f = " + f.getValue());
	}

}
