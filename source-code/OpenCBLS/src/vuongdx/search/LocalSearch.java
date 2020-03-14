package vuongdx.search;

import java.util.HashMap;

import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;

public class LocalSearch {
	public ConstraintSystem cs;
	public IFunction[] f;
	public HashMap<String, VarIntLS[]> dVar;
	public INeighborLS neighborRule;
	public ILegalNeighborLS legalNeighborRule;
	public ISelectNeighborLS selectNeighborRule;
	
	public LocalSearch() {
		
	}
	
	public LocalSearch(ConstraintSystem cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			INeighborLS neighborRule,
			ILegalNeighborLS legalNeighborRule,
			ISelectNeighborLS selectNeighborRule) {
		this.cs = cs;
		this.f = f;
		this.dVar = dVar;
		this.neighborRule = neighborRule;
		this.legalNeighborRule = legalNeighborRule;
		this.selectNeighborRule = selectNeighborRule;
	}
	
	public int search() {
		INeighborLS[] neighborList = this.neighborRule.listNeighbor(cs, null, dVar);
		INeighborLS[] legalNeighborList = this.legalNeighborRule.listLegal(cs, null, dVar, neighborList);
		INeighborLS selectedNeighbor = this.selectNeighborRule.select(cs, null, dVar, neighborList, legalNeighborList);
		selectedNeighbor.movePropagate(dVar);
		return cs.violations();
	}
	
	public int search(int numIter) {
		int it = 0;
		while (cs.violations() > 0 && it < numIter) {
			it++;
			this.search();
		}
		return cs.violations();
	}
}
