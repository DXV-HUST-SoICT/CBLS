package vuongdx.search;

import java.util.HashMap;

import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.VarIntLS;
import vuongdx.search.legal.BestMove;
import vuongdx.search.select.RandomSelection;

public class TabuSearch extends LocalSearch {
	public TabuSearch(ConstraintSystem cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			IMoveLS moveRule) {
		super(cs, f, dVar, moveRule, new BestMove(), new RandomSelection());
	}
}
