package practice;

import java.util.HashMap;

import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.LessThan;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.HillClimbingSearch;
import vuongdx.search.ISolver;
import vuongdx.search.move.MOneChange;

public class BCA implements ISolver {
	private int N = 12;
	private int P = 4;
	int[] credit = {2, 1, 2, 1, 3, 2, 1, 3, 2, 3, 1, 3};
	int[][] pre = {
			{1, 0},
			{5, 8},
			{4, 5},
			{4, 7},
			{3, 10},
			{5, 11}
	};
	private int a = 3;
	private int b = 3;
	private int c = 5;
	private int d = 7;
	LocalSearchManager mgr;
	VarIntLS[] X;
	ConstraintSystem S;
	private IFunction[] numberCoursesPeriod;
	private IFunction[] numberCreditsPeriod;

	@Override
	public void stateModel() {
		mgr = new LocalSearchManager();
		X = new VarIntLS[N];
		for (int i = 0; i < N; i++) {
			X[i] = new VarIntLS(mgr, 0, P - 1);
		}
		S = new ConstraintSystem(mgr);
		for (int k = 0; k < pre.length; k++) {
			IConstraint c = new LessThan(X[pre[k][0]], X[pre[k][1]]);
			S.post(c);
		}
		
		numberCoursesPeriod = new IFunction[P];
		numberCreditsPeriod = new IFunction[P];
		
		for (int j = 0; j < P; j++) {
			numberCoursesPeriod[j] = new ConditionalSum(X, j);
			numberCreditsPeriod[j] = new ConditionalSum(X, credit, j);
			S.post(new LessOrEqual(numberCoursesPeriod[j], b));
			S.post(new LessOrEqual(a, numberCoursesPeriod[j]));
			S.post(new LessOrEqual(numberCreditsPeriod[j], d));
			S.post(new LessOrEqual(c, numberCreditsPeriod[j]));
			
		}
		
		mgr.close();

	}

	@Override
	public void search() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", X);
		HillClimbingSearch searcher = new HillClimbingSearch(S, null, dVar, new MOneChange());
		searcher.search(100000);
	}
	
	public void printResult() {
		System.out.println(S.violations());
		for (int i = 0; i < N; i++) {
			System.out.print(X[i].getValue() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		BCA solver = new BCA();
		solver.stateModel();
		solver.search();
		solver.printResult();
	}

}
