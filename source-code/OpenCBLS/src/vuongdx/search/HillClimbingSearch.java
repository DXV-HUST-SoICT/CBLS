package vuongdx.search;

import vuongdx.search.legal.LBestNeighbor;
import vuongdx.search.select.SRandom;

import java.util.HashMap;

import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.INeighborLS;;

public final class HillClimbingSearch extends LocalSearch {
	public HillClimbingSearch(ConstraintSystem cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			INeighborLS neighborRule) {
		super(cs, f, dVar, neighborRule, new LBestNeighbor(), new SRandom());
	}
}
