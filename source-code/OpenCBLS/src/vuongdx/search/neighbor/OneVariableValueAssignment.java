package vuongdx.search.neighbor;

import java.util.ArrayList;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighbor;

public class OneVariableValueAssignment implements INeighbor {
	
	public VarIntLS var;
	public int val;
	
	public OneVariableValueAssignment() {
		
	}
	
	public OneVariableValueAssignment(VarIntLS var, int val) {
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

	public INeighbor[] listNeighbor(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		ArrayList<OneVariableValueAssignment> neighbor = new ArrayList<OneVariableValueAssignment>();
		for (int i = 0; i < curSol.length; i++) {
			VarIntLS var = curSol[i];
			for (int val = var.getMinValue(); val <= var.getMaxValue(); val++) {
				neighbor.add(new OneVariableValueAssignment(var, val));
			}
		}
		OneVariableValueAssignment[] result = new OneVariableValueAssignment[neighbor.size()];
		for (int i = 0; i < neighbor.size(); i++) {
			result[i] = neighbor.get(i);
		}
		return result;
	}
}
