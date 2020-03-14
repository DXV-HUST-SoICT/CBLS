package vuongdx.search;

import localsearch.model.IConstraint;

public interface ILegal {
	
	public INeighbor[] listLegal(INeighbor[] neighbor, IConstraint cs);
	
}