package vuongdx.search.select;

import java.util.Random;

import localsearch.model.IConstraint;
import vuongdx.search.INeighborLS;
import vuongdx.search.ISelectNeighborLS;

public class SRandom implements ISelectNeighborLS {

	public INeighborLS select(INeighborLS[] legalNeighborList, INeighborLS[] neighborList, IConstraint cs) {
		Random r = new Random();
		int idx = r.nextInt(legalNeighborList.length);
		return legalNeighborList[idx];
	}

}
