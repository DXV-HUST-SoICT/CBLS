package practice;

import java.util.HashMap;

import localsearch.constraints.basic.AND;
import localsearch.constraints.basic.Implicate;
import localsearch.constraints.basic.IsEqual;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.OR;
import localsearch.functions.basic.FuncPlus;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.legal.LBestMove;
import vuongdx.search.move.MOneChange;
import vuongdx.search.select.SRandom;
import vuongdx.search.solutiongenerator.GRandom;

public class BinPacking2D implements ISolver {
	
	private LocalSearchManager lsm;
	private ConstraintSystem cs;
	
	private int N;
	private int W;
	private int H;
	private int[] h;
	private int [] w;
	
	private VarIntLS[] x;
	private VarIntLS[] y;
	private VarIntLS[] o;
	
	public BinPacking2D(int N, int H, int W, int[] h, int[] w) {
		this.N = N;
		this.W = W;
		this.H = H;
		this.h = h;
		this.w = w;
	}

	@Override
	public void stateModel() {
		lsm = new LocalSearchManager();
		cs = new ConstraintSystem(lsm);
		this.x = new VarIntLS[N];
		this.y = new VarIntLS[N];
		this.o = new VarIntLS[N];
		for (int i = 0; i < N; i++) {
			x[i] = new VarIntLS(lsm, 0, W);
			y[i] = new VarIntLS(lsm, 0, H);
			o[i] = new VarIntLS(lsm, 0, 1);
			IConstraint[] tmp1 = new IConstraint[2];
			tmp1[0] = new LessOrEqual(new FuncPlus(x[i], w[i]), W);
			tmp1[1] = new LessOrEqual(new FuncPlus(y[i], h[i]), H);
			cs.post(new Implicate(new IsEqual(o[i], 0), new AND(tmp1)));
			IConstraint[] tmp2 = new IConstraint[2];
			tmp2[0] = new LessOrEqual(new FuncPlus(x[i], h[i]), W);
			tmp2[1] = new LessOrEqual(new FuncPlus(y[i], w[i]), H);
			cs.post(new Implicate(new IsEqual(o[i], 1), new AND(tmp2)));
		}
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				IConstraint[] lt1 = new IConstraint[2];
				IConstraint[] rt1 = new IConstraint[4];
				lt1[0] = new IsEqual(o[i], 0);
				lt1[1] = new IsEqual(o[j], 0);
				rt1[0] = new LessOrEqual(new FuncPlus(x[i], w[i]), x[j]);
				rt1[1] = new LessOrEqual(new FuncPlus(x[j], w[j]), x[i]);
				rt1[2] = new LessOrEqual(new FuncPlus(y[i], h[i]), y[j]);
				rt1[3] = new LessOrEqual(new FuncPlus(y[j], h[j]), y[i]);
				cs.post(new Implicate(new AND(lt1), new OR(rt1)));
				
				IConstraint[] lt2 = new IConstraint[2];
				IConstraint[] rt2 = new IConstraint[4];
				lt2[0] = new IsEqual(o[i], 0);
				lt2[1] = new IsEqual(o[j], 1);
				rt2[0] = new LessOrEqual(new FuncPlus(x[i], w[i]), x[j]);
				rt2[1] = new LessOrEqual(new FuncPlus(x[j], h[j]), x[i]);
				rt2[2] = new LessOrEqual(new FuncPlus(y[i], h[i]), y[j]);
				rt2[3] = new LessOrEqual(new FuncPlus(y[j], w[j]), y[i]);
				cs.post(new Implicate(new AND(lt2), new OR(rt2)));
				
				IConstraint[] lt3 = new IConstraint[2];
				IConstraint[] rt3 = new IConstraint[4];
				lt3[0] = new IsEqual(o[i], 1);
				lt3[1] = new IsEqual(o[j], 0);
				rt3[0] = new LessOrEqual(new FuncPlus(x[i], h[i]), x[j]);
				rt3[1] = new LessOrEqual(new FuncPlus(x[j], w[j]), x[i]);
				rt3[2] = new LessOrEqual(new FuncPlus(y[i], w[i]), y[j]);
				rt3[3] = new LessOrEqual(new FuncPlus(y[j], h[j]), y[i]);
				cs.post(new Implicate(new AND(lt3), new OR(rt3)));
				
				IConstraint[] lt4 = new IConstraint[2];
				IConstraint[] rt4 = new IConstraint[4];
				lt4[0] = new IsEqual(o[i], 1);
				lt4[1] = new IsEqual(o[j], 1);
				rt4[0] = new LessOrEqual(new FuncPlus(x[i], h[i]), x[j]);
				rt4[1] = new LessOrEqual(new FuncPlus(x[j], h[j]), x[i]);
				rt4[2] = new LessOrEqual(new FuncPlus(y[i], w[i]), y[j]);
				rt4[3] = new LessOrEqual(new FuncPlus(y[j], w[j]), y[i]);
				cs.post(new Implicate(new AND(lt4), new OR(rt4)));
			}
		}
		lsm.close();
	}

	@Override
	public void search() {
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("x", x);
		dVar.put("y", y);
		dVar.put("o", o);
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new MOneChange(),
				new LBestMove(),
				new SRandom());
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

	@Override
	public void printResult() {
		System.out.println("Violation: " + cs.violations());
		for (int i = 0; i < this.N; i++) {
			System.out.println(x[i].getValue() + " " + y[i].getValue() + " " + o[i].getValue());
		}
		
	}
	
	public static void main(String[] args) {
		int N = 3;
		int H = 6;
		int W = 4;
		int[] w = {3, 3, 1};
		int[] h = {2, 4, 6};
		BinPacking2D solver = new BinPacking2D(N, H, W, h, w);
		solver.stateModel();
		solver.search();
		solver.printResult();
	}

}
