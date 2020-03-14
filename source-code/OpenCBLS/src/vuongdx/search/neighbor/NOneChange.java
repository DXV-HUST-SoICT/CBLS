package vuongdx.search.neighbor;

import java.util.ArrayList;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;

public class NOneChange implements INeighborLS {
	
	public VarIntLS var;
	public int val;
	
	public NOneChange() {
		
	}
	
	public NOneChange(VarIntLS var, int val) {
		this.var = var;
		this.val = val;
	}

	public int movePropagate() {
		this.var.setValuePropagate(this.val);
		return 0;

	}

	public int getMoveDelta(IFunction f) {
		int delta = f.getAssignDelta(this.var, this.val);
		return delta;
	}
	
	public int getMoveDelta(IConstraint cs) {
		int delta = cs.getAssignDelta(this.var, this.val);
		return delta;
	}

	public INeighborLS[] listNeighbor(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		ArrayList<NOneChange> tmpNeighborList = new ArrayList<NOneChange>();
		for (int i = 0; i < curSol.length; i++) {
			VarIntLS var = curSol[i];
			for (int val = var.getMinValue(); val <= var.getMaxValue(); val++) {
				tmpNeighborList.add(new NOneChange(var, val));
			}
		}
		NOneChange[] neighborList = new NOneChange[tmpNeighborList.size()];
		for (int i = 0; i < tmpNeighborList.size(); i++) {
			neighborList[i] = tmpNeighborList.get(i);
		}
		return neighborList;
	}
}
