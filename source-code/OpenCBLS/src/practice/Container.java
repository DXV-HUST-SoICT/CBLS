package practice;

import java.util.HashMap;

import localsearch.constraints.basic.AND;
import localsearch.constraints.basic.Implicate;
import localsearch.constraints.basic.IsEqual;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.LessThan;
import localsearch.constraints.basic.OR;
import localsearch.functions.basic.FuncPlus;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.TabuSearch;
import vuongdx.search.legal.BestMove;
import vuongdx.search.move.SingleVarChangeValue;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GRandom;

public class Container implements ISolver {
	
	LocalSearchManager lsm;
	ConstraintSystem cs;
	int[] xx;
	int[] yy;
	VarIntLS[] x;
	VarIntLS[] y;
	VarIntLS[] o;
	int N;
	int X;
	int Y;
	
	public Container(int N, int X, int Y, int[] xx, int[] yy) {
		this.N = N;
		this.X = X;
		this.Y = Y;
		this.xx = xx;
		this.yy = yy;
	}

	@Override
	public void stateModel() {
		// TODO Auto-generated method stub
		lsm = new LocalSearchManager();
		cs = new ConstraintSystem(lsm);
		this.x = new VarIntLS[N];
		this.y = new VarIntLS[N];
		this.o = new VarIntLS[N];
		for (int i = 0; i < N; i++) {
			x[i] = new VarIntLS(lsm, 0, X);
			y[i] = new VarIntLS(lsm, 0, Y);
			o[i] = new VarIntLS(lsm, 0, 1);
			IConstraint[] tmp1 = new IConstraint[2];
			tmp1[0] = new LessOrEqual(new FuncPlus(x[i], xx[i]), X);
			tmp1[1] = new LessOrEqual(new FuncPlus(y[i], yy[i]), Y);
			cs.post(new Implicate(new IsEqual(o[i], 0), new AND(tmp1)));
			IConstraint[] tmp2 = new IConstraint[2];
			tmp2[0] = new LessOrEqual(new FuncPlus(x[i], yy[i]), X);
			tmp2[1] = new LessOrEqual(new FuncPlus(y[i], xx[i]), Y);
			cs.post(new Implicate(new IsEqual(o[i], 1), new AND(tmp2)));
		}
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				IConstraint[] lt1 = new IConstraint[2];
				IConstraint[] rt1 = new IConstraint[4];
				lt1[0] = new IsEqual(o[i], 0);
				lt1[1] = new IsEqual(o[j], 0);
				rt1[0] = new LessOrEqual(new FuncPlus(x[i], xx[i]), x[j]);
				rt1[1] = new LessOrEqual(new FuncPlus(x[j], xx[j]), x[i]);
				rt1[2] = new LessOrEqual(new FuncPlus(y[i], yy[i]), y[j]);
				rt1[3] = new LessOrEqual(new FuncPlus(y[j], yy[j]), y[i]);
				cs.post(new Implicate(new AND(lt1), new OR(rt1)));
				IConstraint[] t11 = new IConstraint[2];
				IConstraint[] t12 = new IConstraint[2];
				IConstraint[] t13 = new IConstraint[2];
				t11[0] = new LessOrEqual(new FuncPlus(x[j], 1), new FuncPlus(x[i], xx[i]));
				t11[1] = new LessOrEqual(new FuncPlus(x[i], xx[i]), new FuncPlus(x[j], xx[j]));
				t12[0] = new LessOrEqual(new FuncPlus(x[i], 1), new FuncPlus(x[j], xx[j]));
				t12[1] = new LessOrEqual(new FuncPlus(x[j], xx[j]), new FuncPlus(x[i], xx[i]));
				t13[0] = new AND(t11);
				t13[1] = new AND(t12);
				IConstraint tmp1 = new Implicate(new OR(t13), new LessThan(y[i], y[j]));
				cs.post(new Implicate(new AND(lt1), tmp1));
				
				
				IConstraint[] lt2 = new IConstraint[2];
				IConstraint[] rt2 = new IConstraint[4];
				lt2[0] = new IsEqual(o[i], 0);
				lt2[1] = new IsEqual(o[j], 1);
				rt2[0] = new LessOrEqual(new FuncPlus(x[i], xx[i]), x[j]);
				rt2[1] = new LessOrEqual(new FuncPlus(x[j], yy[j]), x[i]);
				rt2[2] = new LessOrEqual(new FuncPlus(y[i], yy[i]), y[j]);
				rt2[3] = new LessOrEqual(new FuncPlus(y[j], xx[j]), y[i]);
				cs.post(new Implicate(new AND(lt2), new OR(rt2)));
				IConstraint[] t21 = new IConstraint[2];
				IConstraint[] t22 = new IConstraint[2];
				IConstraint[] t23 = new IConstraint[2];
				t21[0] = new LessOrEqual(new FuncPlus(x[j], 1), new FuncPlus(x[i], xx[i]));
				t21[1] = new LessOrEqual(new FuncPlus(x[i], xx[i]), new FuncPlus(x[j], yy[j]));
				t22[0] = new LessOrEqual(new FuncPlus(x[i], 1), new FuncPlus(x[j], yy[j]));
				t22[1] = new LessOrEqual(new FuncPlus(x[j], yy[j]), new FuncPlus(x[i], xx[i]));
				t23[0] = new AND(t21);
				t23[1] = new AND(t22);
				IConstraint tmp2 = new Implicate(new OR(t23), new LessThan(y[i], y[j]));
				cs.post(new Implicate(new AND(lt2), tmp2));
				
				IConstraint[] lt3 = new IConstraint[2];
				IConstraint[] rt3 = new IConstraint[4];
				lt3[0] = new IsEqual(o[i], 1);
				lt3[1] = new IsEqual(o[j], 0);
				rt3[0] = new LessOrEqual(new FuncPlus(x[i], yy[i]), x[j]);
				rt3[1] = new LessOrEqual(new FuncPlus(x[j], xx[j]), x[i]);
				rt3[2] = new LessOrEqual(new FuncPlus(y[i], xx[i]), y[j]);
				rt3[3] = new LessOrEqual(new FuncPlus(y[j], yy[j]), y[i]);
				cs.post(new Implicate(new AND(lt3), new OR(rt3)));
				IConstraint[] t31 = new IConstraint[2];
				IConstraint[] t32 = new IConstraint[2];
				IConstraint[] t33 = new IConstraint[2];
				t31[0] = new LessOrEqual(new FuncPlus(x[j], 1), new FuncPlus(x[i], yy[i]));
				t31[1] = new LessOrEqual(new FuncPlus(x[i], yy[i]), new FuncPlus(x[j], xx[j]));
				t32[0] = new LessOrEqual(new FuncPlus(x[i], 1), new FuncPlus(x[j], xx[j]));
				t32[1] = new LessOrEqual(new FuncPlus(x[j], xx[j]), new FuncPlus(x[i], yy[i]));
				t33[0] = new AND(t31);
				t33[1] = new AND(t32);
				IConstraint tmp3 = new Implicate(new OR(t33), new LessThan(y[i], y[j]));
				cs.post(new Implicate(new AND(lt3), tmp3));
				
				IConstraint[] lt4 = new IConstraint[2];
				IConstraint[] rt4 = new IConstraint[4];
				lt4[0] = new IsEqual(o[i], 1);
				lt4[1] = new IsEqual(o[j], 1);
				rt4[0] = new LessOrEqual(new FuncPlus(x[i], yy[i]), x[j]);
				rt4[1] = new LessOrEqual(new FuncPlus(x[j], yy[j]), x[i]);
				rt4[2] = new LessOrEqual(new FuncPlus(y[i], xx[i]), y[j]);
				rt4[3] = new LessOrEqual(new FuncPlus(y[j], xx[j]), y[i]);
				cs.post(new Implicate(new AND(lt4), new OR(rt4)));
				IConstraint[] t41 = new IConstraint[2];
				IConstraint[] t42 = new IConstraint[2];
				IConstraint[] t43 = new IConstraint[2];
				t41[0] = new LessOrEqual(new FuncPlus(x[j], 1), new FuncPlus(x[i], yy[i]));
				t41[1] = new LessOrEqual(new FuncPlus(x[i], yy[i]), new FuncPlus(x[j], yy[j]));
				t42[0] = new LessOrEqual(new FuncPlus(x[i], 1), new FuncPlus(x[j], yy[j]));
				t42[1] = new LessOrEqual(new FuncPlus(x[j], yy[j]), new FuncPlus(x[i], yy[i]));
				t43[0] = new AND(t41);
				t43[1] = new AND(t42);
				IConstraint tmp4 = new Implicate(new OR(t43), new LessThan(y[i], y[j]));
				cs.post(new Implicate(new AND(lt4), tmp4));
			}
		}
		lsm.close();
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("x", x);
		dVar.put("y", y);
		dVar.put("o", o);
		GRandom rs = new GRandom();
		
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new SingleVarChangeValue(),
				new BestMove(),
				new RandomSelection());
		rs.generateSolution(dVar);
		int curVio = cs.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
		
//		TabuSearch s = new TabuSearch(cs, null, dVar, new SingleVarChangeValue(), 5);
//		s.search(10000, 100, rs);
	}

	@Override
	public void printResult() {
		// TODO Auto-generated method stub
		System.out.println("Violation: " + cs.violations());
		for (int i = 0; i < this.N; i++) {
			System.out.println(x[i].getValue() + " " + y[i].getValue() + " " + o[i].getValue());
		}
	}
	
	public static void main(String[] args) {
		int[] xx = new int[]{1, 3, 2, 3, 1, 2};
        int[] yy = new int[]{4, 1, 2, 1, 4, 3};
        int X = 6;
        int Y = 4;
        int N = 6;
        ISolver solver = new Container(N, X, Y, xx, yy);
        solver.stateModel();
        solver.search();
        solver.printResult();
	}
}
