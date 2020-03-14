package vuongdx.search.neighbor;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;

public class NSudokuTwoSwap implements INeighborLS {
	
	int row;
	int col1;
	int col2;
	
	public NSudokuTwoSwap() {
		
	}
	
	public NSudokuTwoSwap(int row, int col1, int col2) {
		this.row = row;
		this.col1 = col1;
		this.col2 = col2;
	}

	@Override
	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			int n = (int) Math.sqrt(mVar.length);
			VarIntLS var1 = mVar[row * n + col1];
			VarIntLS var2 = mVar[row * n + col2];
			int val1 = var1.getValue();
			int val2 = var2.getValue();
			var1.setValuePropagate(val2);
			var2.setValuePropagate(val1);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			int n = (int) Math.sqrt(mVar.length);
			VarIntLS var1 = mVar[row * n + col1];
			VarIntLS var2 = mVar[row * n + col2];
			return f.getSwapDelta(var1, var2);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			int n = (int) Math.sqrt(mVar.length);
			VarIntLS var1 = mVar[row * n + col1];
			VarIntLS var2 = mVar[row * n + col2];
			return cs.getSwapDelta(var1, var2);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public INeighborLS[] listNeighbor(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			ArrayList<NSudokuTwoSwap> tmpNeighborList = new ArrayList<NSudokuTwoSwap>();
			int n = (int) Math.sqrt(mVar.length);
			for (int i = 0; i < n; i++) {
				for (int j1 = 0; j1 < n; j1++) {
					for (int j2 = j1 + 1; j2 < n; j2++) {
						tmpNeighborList.add(new NSudokuTwoSwap(i, j1, j2));
					}
				}
			}
			NSudokuTwoSwap[] neighborList = new NSudokuTwoSwap[tmpNeighborList.size()];
			for (int i = 0; i < tmpNeighborList.size(); i++) {
				neighborList[i] = tmpNeighborList.get(i);
			}
			return neighborList;
		} catch (Exception e) {
			return null;
		}
		
	}

}
