package vuongdx.search;

import localsearch.model.ConstraintSystem;

public class Search {
	private INeighbor neighbor;
	private ILegal legal;
	private ISelect select;
	private ConstraintSystem cs;
	
	public Search(INeighbor neighbor, ILegal legal, ISelect select, ConstraintSystem cs) {
		this.neighbor = neighbor;
		this.legal = legal;
		this.select = select;
		this.cs = cs;
	}
	
	public int search() {
		INeighbor[] neighbor = this.neighbor.listNeighbor(cs);
		INeighbor[] legal = this.legal.listLegal(neighbor, cs);
		INeighbor select = this.select.select(legal, neighbor, cs);
		select.movePropagate();
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
