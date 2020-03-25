package vuongdx.search.select;

import java.util.HashMap;
import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.IMoveLS;
import vuongdx.search.ISelectMoveLS;

public class RandomSTMSelection extends RandomSelection implements ISelectMoveLS {
	
	private HashMap<IMoveLS, Integer> mem;
	private Integer it;
	private Integer memLen;
	private Integer best;
	
	public RandomSTMSelection(HashMap<IMoveLS, Integer> mem, Integer it, Integer memLen, Integer best) {
		this.mem = mem;
		this.it = it;
		this.memLen = memLen;
		this.best = best;
	}
	
	public IMoveLS select(IConstraint cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			IMoveLS[] moveList,
			IMoveLS[] legalMoveList) {
		IMoveLS selectedMove = super.select(cs, f, dVar, moveList, legalMoveList);
		mem.put(selectedMove, it + memLen);
		it++;
		return selectedMove;
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
