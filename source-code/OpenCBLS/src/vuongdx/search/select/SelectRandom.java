package vuongdx.search.select;

import java.util.Random;

import localsearch.model.IConstraint;
import vuongdx.search.INeighbor;
import vuongdx.search.ISelect;

public class SelectRandom implements ISelect {

	public INeighbor select(INeighbor[] legal, INeighbor[] neighbor, IConstraint cs) {
		Random r = new Random();
		int idx = r.nextInt(legal.length);
		return legal[idx];
	}

}
