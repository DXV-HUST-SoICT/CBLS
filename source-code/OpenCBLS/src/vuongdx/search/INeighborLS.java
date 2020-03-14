package vuongdx.search;

import java.util.HashMap;

import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;

public interface INeighborLS {

	public int movePropagate(HashMap<String, VarIntLS[]> dVar);
	
	public int getMoveDelta(IFunction f, HashMap<String, VarIntLS[]> dVar);
	
	public int getMoveDelta(IConstraint cs, HashMap<String, VarIntLS[]> dVar);
	
	public INeighborLS[] listNeighbor(IConstraint cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar);

}