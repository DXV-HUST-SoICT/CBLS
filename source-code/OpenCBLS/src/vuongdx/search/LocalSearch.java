package vuongdx.search;

import localsearch.model.ConstraintSystem;

public class LocalSearch {
	public INeighborLS neighborRule;
	public ILegalNeighborLS legalNeighborRule;
	public ISelectNeighborLS selectNeighborRule;
	public ConstraintSystem cs;
	
	public LocalSearch() {
		
	}
	
	public LocalSearch(INeighborLS neighborRule, ILegalNeighborLS legalNeighborRule, ISelectNeighborLS selectNeighborRule, ConstraintSystem cs) {
		this.neighborRule = neighborRule;
		this.legalNeighborRule = legalNeighborRule;
		this.selectNeighborRule = selectNeighborRule;
		this.cs = cs;
	}
	
	public int search() {
		INeighborLS[] neighborList = this.neighborRule.listNeighbor(cs);
		INeighborLS[] legalNeighborList = this.legalNeighborRule.listLegal(neighborList, cs);
		INeighborLS selectedNeighbor = this.selectNeighborRule.select(legalNeighborList, neighborList, cs);
		selectedNeighbor.movePropagate();
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
