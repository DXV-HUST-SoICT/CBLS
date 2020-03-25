package practice;

import localsearch.model.*;

import java.util.HashMap;

import localsearch.constraints.alldifferent.*;
import localsearch.functions.basic.*;
import vuongdx.search.HillClimbingSearch;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.legal.BestMove;
import vuongdx.search.move.SingleVarChangeValue;
import vuongdx.search.move.ThreeVarSwap;
import vuongdx.search.move.TwoVarSwap;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GAllDifferentAllSameRange;
import vuongdx.search.solutiongenerator.GRandom;

public class NQueen2 implements ISolver {
	private int n; // number of queens
	private LocalSearchManager mgr; // manager object
	private VarIntLS[] x; // decision variables
	private ConstraintSystem cs;
	
	private NQueen2(int n) {
		this.n = n;
	}
	
	public void stateModel() {
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
	
	public void search() {
		
	}
	
	private void search1() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new SingleVarChangeValue(),
				new BestMove(),
				new RandomSelection());
		GRandom rs = new GRandom();
		rs.generateSolution(dVar);
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	private void search2() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new TwoVarSwap(),
				new BestMove(),
				new RandomSelection());
		GAllDifferentAllSameRange ds = new GAllDifferentAllSameRange();
		ds.generateSolution(dVar);
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	private void search3() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new HillClimbingSearch(cs, null, dVar, new TwoVarSwap());
		GAllDifferentAllSameRange ds = new GAllDifferentAllSameRange();
		ds.generateSolution(dVar);
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	@SuppressWarnings("unused")
	private void search4() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new HillClimbingSearch(cs, null, dVar, new ThreeVarSwap());
		GAllDifferentAllSameRange ds = new GAllDifferentAllSameRange();
		ds.generateSolution(dVar);
		int curVio = cs.violations();
		for (int it = 0; it < 100; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	public void printResult() {
		System.out.println(cs.violations());
		for (int i = 0; i < this.n; i++) {
			System.out.print(Integer.toString(x[i].getValue()) + ' ');
		}
	}
	
	public static void main(String[] args) {
		NQueen2 nqueen = new NQueen2(200);
		nqueen.stateModel();
		nqueen.search1();
		nqueen.printResult();
		nqueen.search2();
		nqueen.printResult();
		nqueen.search3();
		nqueen.printResult();
		nqueen.search4();
		nqueen.printResult();
	}
}
