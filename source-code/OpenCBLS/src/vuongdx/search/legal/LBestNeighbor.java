package vuongdx.search.legal;

import java.util.ArrayList;
import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.ILegalNeighborLS;
import vuongdx.search.INeighborLS;

public class LBestNeighbor implements ILegalNeighborLS {

	public INeighborLS[] listLegal(IConstraint cs, IFunction[] f, HashMap<String, VarIntLS[]> dVar, INeighborLS[] neighborList) {
		ArrayList<INeighborLS> tmpLegalNeighborList = new ArrayList<INeighborLS>();
		Integer minDelta = Integer.MAX_VALUE;
		for (int i = 0; i < neighborList.length; i++) {
			INeighborLS tmpNeighbor = neighborList[i];
			int delta = tmpNeighbor.getMoveDelta(cs, dVar);
			if (delta <= minDelta) {
				if (delta < minDelta) {
					minDelta = delta;
					tmpLegalNeighborList.clear();
				}
				tmpLegalNeighborList.add(tmpNeighbor);
			}
		}
		INeighborLS[] legalNeighborList = new INeighborLS[tmpLegalNeighborList.size()];
		for (int i = 0; i < tmpLegalNeighborList.size(); i++) {
			legalNeighborList[i] = tmpLegalNeighborList.get(i);
		}
		return legalNeighborList;
	}
}
