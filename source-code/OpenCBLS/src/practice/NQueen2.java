package practice;

import localsearch.model.*;
import localsearch.constraints.alldifferent.*;
import localsearch.functions.basic.*;
import vuongdx.search.Search;
import vuongdx.search.legal.MinViolation;
import vuongdx.search.neighbor.OneVariableValueAssignment;
import vuongdx.search.neighbor.SwapTwoVariableValue;
import vuongdx.search.select.SelectRandom;
import vuongdx.search.solutiongenerator.AllDifferentSolution;
import vuongdx.search.solutiongenerator.RandomSolution;

public class NQueen2 {
	private int n; // number of queens
	private LocalSearchManager mgr; // manager object
	private VarIntLS[] x; // decision variables
	private ConstraintSystem cs;
	
	private NQueen2(int n) {
		this.n = n;
	}
	
	private void stateModel() {
		mgr = new LocalSearchManager();
		
		x = new VarIntLS[n];
		for (int i = 0; i < n; i++) {
			x[i] = new VarIntLS(mgr, 0, n - 1);
		}
		
		cs = new ConstraintSystem(mgr);
		cs.post(new AllDifferent(x));
		
		IFunction[] f1 = new IFunction[n];
		for (int i = 0; i < n; i++) {
			f1[i] = new FuncPlus(x[i], i);
		}
		cs.post(new AllDifferent(f1));
		
		IFunction[] f2 = new IFunction[n];
		for (int i = 0; i < n; i++) {
			f2[i] = new FuncPlus(x[i], -i);
		}
		cs.post(new AllDifferent(f2));
		
		mgr.close();
	}
	
	private void search() {
		System.out.println("\nAlgo 1");
		Search s1 = new Search(new OneVariableValueAssignment(),
				new MinViolation(), new SelectRandom(), cs);
		RandomSolution rs = new RandomSolution();
		rs.generateSolution(cs);
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s1.search();
		}
		System.out.println("\nAlgo 2");
		Search s2 = new Search(new SwapTwoVariableValue(),
				new MinViolation(), new SelectRandom(), cs);
		AllDifferentSolution ds = new AllDifferentSolution();
		ds.generateSolution(cs);
		curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s2.search();
		}
	}
	
	private void printResult() {
		System.out.println(cs.violations());
		for (int i = 0; i < this.n; i++) {
			System.out.print(Integer.toString(x[i].getValue()) + ' ');
		}
	}
	
	public static void main(String[] args) {
		NQueen2 nqueen = new NQueen2(100);
		nqueen.stateModel();
		nqueen.search();
		nqueen.printResult();
	}
}
