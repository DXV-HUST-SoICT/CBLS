package vuongdx.search;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;

public interface INeighborLS {

	public int movePropagate();

	public int getMoveDelta(IFunction f);
	
	public int getMoveDelta(IConstraint cs);
	
	public INeighborLS[] listNeighbor(IConstraint cs);

}