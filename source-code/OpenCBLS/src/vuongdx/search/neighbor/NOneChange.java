package vuongdx.search.neighbor;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;

public class NOneChange implements INeighborLS {
	
	public int idx;
	public int val;
	
	public NOneChange() {
		
	}
	
	public NOneChange(int idx, int val) {
		this.idx = idx;
		this.val = val;
	}

	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			VarIntLS var = mVar[this.idx];
			var.setValuePropagate(this.val);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			VarIntLS var = mVar[this.idx];
			int delta = f.getAssignDelta(var, this.val);
			return delta;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			VarIntLS var = mVar[this.idx];
			int delta = cs.getAssignDelta(var, this.val);
			return delta;
		} catch (Exception e) {
			return -1;
		}
	}

	public INeighborLS[] listNeighbor(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		VarIntLS[] mVar;
		try {
			mVar = dVar.get("main");
			ArrayList<NOneChange> tmpNeighborList = new ArrayList<NOneChange>();
			for (int i = 0; i < mVar.length; i++) {
				VarIntLS var = mVar[i];
				for (int val = var.getMinValue(); val <= var.getMaxValue(); val++) {
					tmpNeighborList.add(new NOneChange(i, val));
				}
			}
			NOneChange[] neighborList = new NOneChange[tmpNeighborList.size()];
			for (int i = 0; i < tmpNeighborList.size(); i++) {
				neighborList[i] = tmpNeighborList.get(i);
			}
			return neighborList;
		} catch (Exception e) {
			return null;
		}
	}
}
