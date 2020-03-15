package vuongdx.search.select;

import java.util.HashMap;
import java.util.Random;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.variable.VarIntLS;
import vuongdx.search.IMoveLS;
import vuongdx.search.ISelectMoveLS;

public class SRandom implements ISelectMoveLS {

	public IMoveLS select(IConstraint cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			IMoveLS[] moveList,
			IMoveLS[] legalMoveList) {
		Random r = new Random();
		int idx = r.nextInt(legalMoveList.length);
		return legalMoveList[idx];
	}

}
