package practice;

import localsearch.model.*;

import java.util.HashMap;

import localsearch.constraints.alldifferent.*;
import localsearch.functions.basic.*;
import vuongdx.search.HillClimbingSearch;
import vuongdx.search.LocalSearch;
import vuongdx.search.legal.LBestNeighbor;
import vuongdx.search.neighbor.NOneChange;
import vuongdx.search.neighbor.NThreeSwap;
import vuongdx.search.neighbor.NTwoSwap;
import vuongdx.search.select.SRandom;
import vuongdx.search.solutiongenerator.GAllDifferent;
import vuongdx.search.solutiongenerator.GRandom;

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
	
	private void search1() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new NOneChange(),
				new LBestNeighbor(),
				new SRandom());
		GRandom rs = new GRandom();
		rs.generateSolution(dVar.get("main"));
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
				new NTwoSwap(),
				new LBestNeighbor(),
				new SRandom());
		GAllDifferent ds = new GAllDifferent();
		ds.generateSolution(dVar.get("main"));
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
		LocalSearch s = new HillClimbingSearch(cs, null, dVar, new NTwoSwap());
		GAllDifferent ds = new GAllDifferent();
		ds.generateSolution(dVar.get("main"));
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
//		System.out.println(cs.getVariables().length);
//		System.out.println(dVar.get("main").length);
//		int count = 0;
//		for (int i = 0; i < 500; i++) {
//			if (cs.getVariables()[i] != dVar.get("main")[i]) {
//				count++;
//			}
//		}
//		System.out.println(count);
	}
	
	private void search4() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", x);
		LocalSearch s = new HillClimbingSearch(cs, null, dVar, new NThreeSwap());
		GAllDifferent ds = new GAllDifferent();
		ds.generateSolution(dVar.get("main"));
		int curVio = cs.violations();
		for (int it = 0; it < 100; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	private void printResult() {
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
