package practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.NotEqual;
import localsearch.constraints.basic.OR;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.TabuSearch;
import vuongdx.search.legal.BestMove;
import vuongdx.search.move.SingleVarChangeValue;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GRandom;

public class LiquidStoring implements ISolver {

	public int n = 20;
	public int m = 5;
	public int[] c = {60, 70, 90, 90, 100};
	public int[] w = {20, 15, 10, 20, 20,
			25, 30, 15, 10, 10,
			20, 25, 20, 10, 30,
			40, 25, 35, 10, 10};
	ArrayList<HashSet<Integer>> f = new ArrayList<>();
	VarIntLS[] s = new VarIntLS[w.length];
	LocalSearchManager lsm = new LocalSearchManager();
	ConstraintSystem cs = new ConstraintSystem(lsm);
	
	@Override
	public void stateModel() {
		// TODO Auto-generated method stub
		
		HashSet<Integer> tmp;
		tmp = new HashSet<>(); tmp.add(0); tmp.add(1); f.add(tmp);
		tmp = new HashSet<>(); tmp.add(7); tmp.add(8); f.add(tmp);
		tmp = new HashSet<>(); tmp.add(12); tmp.add(17); f.add(tmp);
		tmp = new HashSet<>(); tmp.add(8); tmp.add(9); f.add(tmp);
		tmp = new HashSet<>(); tmp.add(1); tmp.add(2); tmp.add(9); f.add(tmp);
		tmp = new HashSet<>(); tmp.add(0); tmp.add(9); tmp.add(12); f.add(tmp);
		
		
		for (int i = 0; i < s.length; i++) {
			s[i] = new VarIntLS(lsm, 0, c.length - 1);
		}
		
		
		for (int i = 0; i < c.length; i++) {
			IFunction sum = new ConditionalSum(s, w, i);
			cs.post(new LessOrEqual(sum, c[i]));
		}
		
		for (int i = 0; i < f.size(); i++) {
			ArrayList<Integer> fl = new ArrayList<>();
			fl.addAll(f.get(i));
			VarIntLS[] a = new VarIntLS[fl.size()];
			for (int j = 0; j < fl.size(); j++) {
				a[j] = s[fl.get(j)];
			}
			IConstraint[] tc = new IConstraint[fl.size() - 1];
			for (int j = 0; j < fl.size() - 1; j++) {
				tc[j] = new NotEqual(a[j], a[j + 1]);
			}
			cs.post(new OR(tc));
		}
		cs.close();
		lsm.close();
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", s);
		GRandom ss = new GRandom();
		ss.generateSolution(dVar);
//		LocalSearch solver = new LocalSearch(cs, null, dVar,
//				new SingleVarChangeValue(),
//				new BestMove(),
//				new RandomSelection());
		TabuSearch solver = new TabuSearch(cs, null, dVar,
				new SingleVarChangeValue(), 10);
		solver.search(10000, 50, ss);
//		int curVio = cs.violations();
//		for (int it = 0; it < 10000; it++) {
//			System.out.println(it + " " + curVio);
//			if (curVio == 0) {
//				break;
//			}
//			curVio = solver.search();
//		}
	}

	@Override
	public void printResult() {
		// TODO Auto-generated method stub
		System.out.println("Violations: " + cs.violations());
		for (int i = 0; i < m; i++) {
			System.out.print("Bin " + i + ": ");
			for (int j = 0; j < n; j++) {
				if (s[j].getValue() == i) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		LiquidStoring solver = new LiquidStoring();
		solver.stateModel();
		solver.search();
		solver.printResult();
	}

}
