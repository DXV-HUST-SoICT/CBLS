package vuongdx.search;

import localsearch.model.IConstraint;

public interface ISelectNeighborLS {

	public INeighborLS select(INeighborLS[] legal, INeighborLS[] neighbor, IConstraint cs);

}