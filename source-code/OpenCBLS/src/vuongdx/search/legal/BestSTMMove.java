package vuongdx.search.legal;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.ILegalMoveLS;
import vuongdx.search.IMoveLS;

public class BestSTMMove implements ILegalMoveLS {
	
	private HashMap<IMoveLS, Integer> mem;
	private Integer it;
	private Integer memLen;
	private Integer best;
	
	public BestSTMMove(HashMap<IMoveLS, Integer> mem, Integer it, Integer memLen, Integer best) {
		this.mem = mem;
		this.it = it;
		this.memLen = memLen;
		this.best = best;
	}

	@Override
	public IMoveLS[] listLegal(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar, IMoveLS[] moveList) {
		ArrayList<IMoveLS> tmpLegalMoveList = new ArrayList<IMoveLS>();
		Integer minDelta = Integer.MAX_VALUE;
		for (int i = 0; i < moveList.length; i++) {
			IMoveLS tmpMove = moveList[i];
			int delta = tmpMove.getMoveDelta(cs, dVar);
			if (delta <= minDelta) {
				if (delta < minDelta) {
					minDelta = delta;
					tmpLegalMoveList.clear();
				}
				tmpLegalMoveList.add(tmpMove);
			}
		}
		IMoveLS[] legalMoveList = tmpLegalMoveList.toArray(new IMoveLS[0]);
		return legalMoveList;
	}
	
	public HashMap<IMoveLS, Integer> getMem() {
		return mem;
	}

	public void setMem(HashMap<IMoveLS, Integer> mem) {
		this.mem = mem;
	}

	public Integer getIt() {
		return it;
	}

	public void setIt(Integer it) {
		this.it = it;
	}

	public Integer getMemLen() {
		return memLen;
	}

	public void setMemLen(Integer memLen) {
		this.memLen = memLen;
	}
	
	public Integer getBest() {
		return best;
	}

	public void setBest(Integer best) {
		this.best = best;
	}

}
