package vuongdx.search.solutiongenerator;

import localsearch.model.IConstraint;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGenerator;

public class AllDifferentSolution implements ISolutionGenerator {

	@Override
	public void generateSolution(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		for (int i = 0; i < curSol.length; i++) {
			curSol[i].setValuePropagate(curSol[i].getMinValue() + i);
		}
	}

}
