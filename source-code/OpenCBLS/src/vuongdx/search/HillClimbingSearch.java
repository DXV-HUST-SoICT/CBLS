package vuongdx.search;

import vuongdx.search.legal.LBestNeighbor;
import vuongdx.search.select.SRandom;
import vuongdx.search.INeighborLS;;

public final class HillClimbingSearch extends LocalSearch {
	public HillClimbingSearch(INeighborLS neighborRule) {
		this.neighborRule = neighborRule;
		this.legalNeighborRule = new LBestNeighbor();
		this.selectNeighborRule = new SRandom();
	}
}
