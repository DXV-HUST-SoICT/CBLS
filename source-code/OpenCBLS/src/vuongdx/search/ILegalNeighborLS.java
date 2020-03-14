package vuongdx.search;

import localsearch.model.IConstraint;

public interface ILegalNeighborLS {
	
	public INeighborLS[] listLegal(INeighborLS[] neighbor, IConstraint cs);
	
}