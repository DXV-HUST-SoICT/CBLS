package vuongdx.search.neighbor;

import java.util.ArrayList;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;

public class NTwoSwap implements INeighborLS {
	
	public VarIntLS var1;
	public VarIntLS var2;
	
	public NTwoSwap() {
		
	}
	
	public NTwoSwap(VarIntLS var1, VarIntLS var2) {
		this.var1 = var1;
		this.var2 = var2;
	}

	@Override
	public int movePropagate() {
		int val1 = this.var1.getValue();
		int val2 = this.var2.getValue();
		var1.setValuePropagate(val2);
		var2.setValuePropagate(val1);
		return 0;
	}

	@Override
	public int getMoveDelta(IFunction f) {
		return f.getSwapDelta(this.var1, this.var2);
	}

	@Override
	public int getMoveDelta(IConstraint cs) {
		return cs.getSwapDelta(this.var1, this.var2);
	}

	@Override
	public INeighborLS[] listNeighbor(IConstraint cs) {
		ArrayList<NTwoSwap> tmpNeighborList = new ArrayList<NTwoSwap>();
		VarIntLS[] curSol = cs.getVariables();
		for (int i = 0; i < curSol.length; i++) {
			for (int j = i + 1; j < curSol.length; j++) {
				tmpNeighborList.add(new NTwoSwap(curSol[i], curSol[j]));
			}
		}
		NTwoSwap[] neighborList = new NTwoSwap[tmpNeighborList.size()];
		for (int i = 0; i < tmpNeighborList.size(); i++) {
			neighborList[i] = tmpNeighborList.get(i);
		}
		return neighborList;
	}

}
