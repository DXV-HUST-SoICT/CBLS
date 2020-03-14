package vuongdx.search.legal;

import java.util.ArrayList;

import localsearch.model.IConstraint;
import vuongdx.search.ILegal;
import vuongdx.search.INeighbor;

public class MinViolation implements ILegal {

	public INeighbor[] listLegal(INeighbor[] neighbor, IConstraint cs) {
		ArrayList<INeighbor> legal = new ArrayList<INeighbor>();
		Integer minDelta = Integer.MAX_VALUE;
		for (int i = 0; i < neighbor.length; i++) {
			INeighbor tmp = neighbor[i];
			int delta = tmp.getMoveDelta(cs);
			if (delta <= minDelta) {
				if (delta < minDelta) {
					minDelta = delta;
					legal.clear();
				}
				legal.add(tmp);
			}
		}
		INeighbor[] result = new INeighbor[legal.size()];
		for (int i = 0; i < legal.size(); i++) {
			result[i] = legal.get(i);
		}
		return result;
	}
}
