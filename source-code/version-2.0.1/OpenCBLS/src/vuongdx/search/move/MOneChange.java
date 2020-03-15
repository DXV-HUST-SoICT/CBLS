package vuongdx.search.move;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.variable.VarIntLS;
import vuongdx.search.IMoveLS;

public class MOneChange implements IMoveLS {
	
	private VarIntLS var;
	private int val;
	
	public MOneChange() {
		
	}
	
	public MOneChange(VarIntLS var, int val) {
		this.var = var;
		this.val = val;
	}

	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		try {
			this.var.setValuePropagate(this.val);
			return 0;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar) {
		try {
			int delta = (int) f.getAssignDelta(this.var, this.val);
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		try {
			int delta = (int) cs.getAssignDelta(this.var, this.val);
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}

	public IMoveLS[] listMove(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		ArrayList<MOneChange> tmpMoveList = new ArrayList<MOneChange>();
		for (String key : dVar.keySet()) {
			VarIntLS[] mVar = dVar.get(key);
			for (int i = 0; i < mVar.length; i++) {
				VarIntLS var = mVar[i];
				for (int val = var.getMin(); val <= var.getMax(); val++) {
					tmpMoveList.add(new MOneChange(var, val));
				}
			}
		}
		MOneChange[] moveList = tmpMoveList.toArray(new MOneChange[0]);
		return moveList;
	}
}
