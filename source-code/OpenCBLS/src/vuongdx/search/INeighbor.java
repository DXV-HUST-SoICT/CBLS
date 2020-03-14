package vuongdx.search;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;

public interface INeighbor {

	public int movePropagate();

	public int getMoveDelta(IFunction f);
	
	public int getMoveDelta(IConstraint cs);
	
	public INeighbor[] listNeighbor(IConstraint cs);

}