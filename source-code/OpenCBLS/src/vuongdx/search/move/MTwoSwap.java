package vuongdx.search.move;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.IMoveLS;

public class MTwoSwap implements IMoveLS {
	
	private VarIntLS var1;
	private VarIntLS var2;
	
	public MTwoSwap() {
		
	}
	
	public MTwoSwap(VarIntLS var1, VarIntLS var2) {
		this.var1 = var1;
		this.var2 = var2;
	}

	@Override
	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		try {
			this.var1.swapValuePropagate(this.var2);
			return 0;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar) {
		try {
			int delta = f.getSwapDelta(this.var1, this.var2);
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		try {
			int delta = cs.getSwapDelta(this.var1, this.var2);
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public IMoveLS[] listMove(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		ArrayList<MTwoSwap> tmpMoveList = new ArrayList<MTwoSwap>();
		for (String key : dVar.keySet()) {
			VarIntLS[] mVar = dVar.get(key);
			for (int i = 0; i < mVar.length; i++) {
				for (int j = i + 1; j < mVar.length; j++) {
					tmpMoveList.add(new MTwoSwap(mVar[i], mVar[j]));
				}
			}
		}
		MTwoSwap[] moveList = tmpMoveList.toArray(new MTwoSwap[0]);
		return moveList;
	}

}
