package practice;

import localsearch.model.*;
import localsearch.model.variable.*;
import localsearch.constraint.*;
import localsearch.function.math.*;

public class NQueen {
	int n; // number of queens
	LocalSearchManager mgr; // manager object
	VarIntLS[] x; // decision variables
	ConstraintSystem S;
	
	public void stateModel() {
		mgr = new LocalSearchManager();
		
		x = new VarIntLS[n];
		for (int i = 0; i < n; i++) {
			x[i] = new VarIntLS(0, 0, n - 1, mgr);
		}
		
		S = new ConstraintSystem();
		S.post(new AllDifferent(x));
		
		IFunction[] f1 = new IFunction[n];
		for (int i = 0; i < n; i++) {
			f1[i] = new FuncPlus(x[i], i);
		}
		S.post(new AllDifferent(f1));
		
		IFunction[] f2 = new IFunction[n];
		for (int i = 0; i < n; i++) {
			f2[i] = new FuncPlus(x[i], -i);
		}
		S.post(new AllDifferent(f2));
		
		mgr.close();
	}
	
	public void search() {
//		MinMaxSelector mms = new MinMaxSelector(S);
	}
}
