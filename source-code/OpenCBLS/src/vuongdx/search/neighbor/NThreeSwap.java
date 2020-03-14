package vuongdx.search.neighbor;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;

public class NThreeSwap implements INeighborLS {
	
	public int idx1;
	public int idx2;
	public int idx3;
	
	public NThreeSwap() {
		
	}
	
	public NThreeSwap(int idx1, int idx2, int idx3) {
		this.idx1 = idx1;
		this.idx2 = idx2;
		this.idx3 = idx3;
	}

	@Override
	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			VarIntLS var1 = mVar[this.idx1];
			VarIntLS var2 = mVar[this.idx2];
			VarIntLS var3 = mVar[this.idx3];
			int val1 = var1.getValue();
			int val2 = var2.getValue();
			int val3 = var3.getValue();
			var1.setValuePropagate(val2);
			var2.setValuePropagate(val3);
			var3.setValuePropagate(val1);
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
			VarIntLS var1 = mVar[this.idx1];
			VarIntLS var2 = mVar[this.idx2];
			VarIntLS var3 = mVar[this.idx3];
			int curValue = f.getValue();
			int val1 = var1.getValue();
			int val2 = var2.getValue();
			int val3 = var3.getValue();
			var1.setValuePropagate(val2);
			var2.setValuePropagate(val3);
			var3.setValuePropagate(val1);
			int newValue = f.getValue();
			int delta = newValue - curValue;
			var1.setValuePropagate(val1);
			var2.setValuePropagate(val2);
			var3.setValuePropagate(val3);
			return delta;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			VarIntLS var1 = mVar[this.idx1];
			VarIntLS var2 = mVar[this.idx2];
			VarIntLS var3 = mVar[this.idx3];
			int curValue = cs.violations();
			int val1 = var1.getValue();
			int val2 = var2.getValue();
			int val3 = var3.getValue();
			var1.setValuePropagate(val2);
			var2.setValuePropagate(val3);
			var3.setValuePropagate(val1);
			int newValue = cs.violations();
			int delta = newValue - curValue;
			var1.setValuePropagate(val1);
			var2.setValuePropagate(val2);
			var3.setValuePropagate(val3);
			return delta;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public INeighborLS[] listNeighbor(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			ArrayList<NThreeSwap> tmpNeighborList = new ArrayList<NThreeSwap>();
			for (int i = 0; i < mVar.length; i++) {
				for (int j = i + 1; j < mVar.length; j++) {
					for (int k = j + 1; k < mVar.length; k++) {
						tmpNeighborList.add(new NThreeSwap(i, j, k));
					}
				}
			}
			NThreeSwap[] neighborList = new NThreeSwap[tmpNeighborList.size()];
			for (int i = 0; i < tmpNeighborList.size(); i++) {
				neighborList[i] = tmpNeighborList.get(i);
			}
			return neighborList;
		} catch (Exception e) {
			return null;
		}
	}

}
