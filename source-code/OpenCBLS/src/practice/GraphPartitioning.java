package practice;

import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.legal.BestObjective;
import vuongdx.search.move.TwoVarSwap;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GraphPartitioningGenerator;

import java.util.HashMap;

import localsearch.constraints.basic.IsEqual;
import localsearch.functions.basic.FuncMinus;
import localsearch.functions.basic.FuncMult;
import localsearch.functions.max_min.Max;
import localsearch.functions.sum.Sum;

public class GraphPartitioning implements ISolver {
	
	int n;
	int[][] c;
	VarIntLS[] x;
	LocalSearchManager lsm;
	ConstraintSystem cs;
	IFunction f;
	
	GraphPartitioning(int n, int[][] c) {
		this.n = n;
		this.c = c;
	}

	@Override
	public void stateModel() {
		lsm = new LocalSearchManager();
		cs = new ConstraintSystem(lsm);
		x = new VarIntLS[n];
		for (int i = 0; i < n; i++) {
			x[i] = new VarIntLS(lsm, 0, 1);
		}
		cs.post(new IsEqual(new Sum(x), n / 2));
		IFunction[] t = new IFunction[n * n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				IFunction[] tmp = new IFunction[2];
				tmp[0] = new FuncMinus(x[i], x[j]);
				tmp[1] = new FuncMinus(x[j], x[i]);
				t[i * n + j] = new FuncMult(new Max(tmp), c[i][j]);
			}
		}
		f = new Sum(t);
		lsm.close();
	}

	@Override
	public void search() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<>();
		dVar.put("main", x);
		ISolutionGeneratorLS g = new GraphPartitioningGenerator();
		g.generateSolution(dVar);
		IFunction[] f = new IFunction[1];
		f[0] = this.f;
		LocalSearch s = new LocalSearch(cs, f, dVar, new TwoVarSwap(), new BestObjective(), new RandomSelection());
		for (int i = 0; i < 100; i++) {
			System.out.println(i + " " + f[0].getValue());
			s.search();
		}
	}

	@Override
	public void printResult() {
		System.out.println(f.getValue());
		for (int i = 0; i < n; i++) {
			System.out.print(x[i].getValue() + " ");
		}
		System.out.println();
		int sum = 0;
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (x[i].getValue() != x[j].getValue()) {
					int ii = i;
					int jj = j;
					if (x[i].getValue() > x[j].getValue()) {
						int tmp = ii;
						ii = jj;
						jj = tmp;
					}
					System.out.println(ii + " " + jj + " " + c[ii][jj]);
					sum += c[ii][jj];
					count++;
				}
			}
		}
		System.out.println(sum + " " + count);
	}
	
	public static void main(String[] args) {
		int n = 10;
		int[][] c = {
				{0, 0, 5, 0, 1, 0, 7, 0, 2, 8},
				{0, 0, 8, 2, 0, 0, 0, 3, 0, 0},
				{5, 8, 0, 8, 7, 0, 0, 4, 6, 0},
				{0, 2, 8, 0, 0, 1, 0, 0, 5, 0},
				{1, 0, 7, 0, 0, 0, 8, 9, 0, 0},
				{0, 0, 0, 1, 0, 0, 0, 0, 4, 5},
				{7, 0, 0, 0, 8, 0, 0, 0, 0, 4},
				{0, 3, 4, 0, 9, 0, 0, 0, 0, 0},
				{2, 0, 6, 5, 0, 4, 0, 0, 0, 3},
				{8, 0, 0, 0, 0, 5, 4, 0, 3, 0}};
		GraphPartitioning solver = new GraphPartitioning(n, c);
		solver.stateModel();
		solver.search();
		solver.printResult();
	}

}
