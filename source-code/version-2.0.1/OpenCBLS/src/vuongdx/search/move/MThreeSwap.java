package vuongdx.search.move;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.variable.VarIntLS;
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
			VarIntLS[] varList = new VarIntLS[3];
			varList[0] = this.var1;
			varList[1] = this.var2;
			varList[2] = this.var3;
			int[] valList = new int[3];
			valList[0] = val2;
			valList[1] = val3;
			valList[2] = val1;
			this.var1.getLocalSearchManager().propagate(varList, valList);
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
			VarIntLS[] varList = new VarIntLS[3];
			varList[0] = this.var1;
			varList[1] = this.var2;
			varList[3] = this.var3;
			int[] valList = new int[3];
			valList[0] = val2;
			valList[1] = val3;
			valList[2] = val1;
			int delta = (int) f.getAssignDelta(varList, valList);
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
			VarIntLS[] varList = new VarIntLS[3];
			varList[0] = this.var1;
			varList[1] = this.var2;
			varList[3] = this.var3;
			int[] valList = new int[3];
			valList[0] = val2;
			valList[1] = val3;
			valList[2] = val1;
			int delta = (int) cs.getAssignDelta(varList, valList);
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
