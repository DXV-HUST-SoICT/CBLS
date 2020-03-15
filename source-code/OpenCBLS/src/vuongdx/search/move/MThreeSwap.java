package vuongdx.search.move;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.IMoveLS;

public class MThreeSwap implements IMoveLS {
	
	private VarIntLS var1;
	private VarIntLS var2;
	private VarIntLS var3;
	
	public MThreeSwap() {
		
	}
	
	public MThreeSwap(VarIntLS var1, VarIntLS var2, VarIntLS var3) {
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}

	@Override
	public int movePropagate(HashMap<String, VarIntLS[]> dVar) {
		try {
			int val1 = this.var1.getValue();
			int val2 = this.var2.getValue();
			int val3 = this.var3.getValue();
			this.var1.setValuePropagate(val2);
			this.var2.setValuePropagate(val3);
			this.var3.setValuePropagate(val1);
			return 0;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar) {
		try {
			int val1 = this.var1.getValue();
			int val2 = this.var2.getValue();
			int val3 = this.var3.getValue();
			this.var1.setValuePropagate(val2);
			this.var2.setValuePropagate(val3);
			this.var3.setValuePropagate(val1);
			int tmpValue = f.getValue();
			this.var1.setValuePropagate(val1);
			this.var2.setValuePropagate(val2);
			this.var3.setValuePropagate(val3);
			int delta = tmpValue - f.getValue();
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar) {
		try {
			int val1 = this.var1.getValue();
			int val2 = this.var2.getValue();
			int val3 = this.var3.getValue();
			this.var1.setValuePropagate(val2);
			this.var2.setValuePropagate(val3);
			this.var3.setValuePropagate(val1);
			int tmpValue = cs.violations();
			this.var1.setValuePropagate(val1);
			this.var2.setValuePropagate(val2);
			this.var3.setValuePropagate(val3);
			int delta = tmpValue - cs.violations();
			return delta;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public IMoveLS[] listMove(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar) {
		ArrayList<MThreeSwap> tmpMoveList = new ArrayList<MThreeSwap>();
		for (String key : dVar.keySet()) {
			VarIntLS[] mVar = dVar.get(key);
			for (int i = 0; i < mVar.length; i++) {
				for (int j = i + 1; j < mVar.length; j++) {
					for (int k = i + 1; k < mVar.length; k++) {
						if (j != k) {
							tmpMoveList.add(new MThreeSwap(mVar[i], mVar[j], mVar[k]));
						}
					}
				}
			}
		}
		MThreeSwap[] moveList = tmpMoveList.toArray(new MThreeSwap[0]);
		return moveList;
	}

}
