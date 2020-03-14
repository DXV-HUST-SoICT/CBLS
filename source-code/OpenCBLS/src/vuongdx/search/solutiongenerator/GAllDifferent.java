package vuongdx.search.solutiongenerator;

import localsearch.model.IConstraint;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;

public class GAllDifferent implements ISolutionGeneratorLS {

	@Override
	public void generateSolution(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		for (int i = 0; i < curSol.length; i++) {
			curSol[i].setValuePropagate(curSol[i].getMinValue() + i);
		}
	}

}
