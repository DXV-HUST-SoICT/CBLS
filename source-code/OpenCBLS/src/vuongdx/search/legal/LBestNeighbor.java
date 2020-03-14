package vuongdx.search.legal;

import java.util.ArrayList;

import localsearch.model.IConstraint;
import vuongdx.search.ILegalNeighborLS;
import vuongdx.search.INeighborLS;

public class LBestNeighbor implements ILegalNeighborLS {

	public INeighborLS[] listLegal(INeighborLS[] neighbor, IConstraint cs) {
		ArrayList<INeighborLS> tmpLegalNeighborList = new ArrayList<INeighborLS>();
		Integer minDelta = Integer.MAX_VALUE;
		for (int i = 0; i < neighbor.length; i++) {
			INeighborLS tmp = neighbor[i];
			int delta = tmp.getMoveDelta(cs);
			if (delta <= minDelta) {
				if (delta < minDelta) {
					minDelta = delta;
					tmpLegalNeighborList.clear();
				}
				tmpLegalNeighborList.add(tmp);
			}
		}
		INeighborLS[] legalNeighborList = new INeighborLS[tmpLegalNeighborList.size()];
		for (int i = 0; i < tmpLegalNeighborList.size(); i++) {
			legalNeighborList[i] = tmpLegalNeighborList.get(i);
		}
		return legalNeighborList;
	}
}
