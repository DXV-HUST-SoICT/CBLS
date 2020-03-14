package vuongdx.search.select;

import java.util.HashMap;
import java.util.Random;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;
import vuongdx.search.ISelectNeighborLS;

public class SRandom implements ISelectNeighborLS {

	public INeighborLS select(IConstraint cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			INeighborLS[] neighborList,
			INeighborLS[] legalNeighborList) {
		Random r = new Random();
		int idx = r.nextInt(legalNeighborList.length);
		return legalNeighborList[idx];
	}

}
