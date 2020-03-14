package vuongdx.search;

import localsearch.model.IConstraint;

public interface ISelect {

	public INeighbor select(INeighbor[] legal, INeighbor[] neighbor, IConstraint cs);

}