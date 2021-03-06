package vuongdx.search.legal;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.variable.VarIntLS;
import vuongdx.search.ILegalMoveLS;
import vuongdx.search.IMoveLS;

public class LBestMove implements ILegalMoveLS {

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
}
