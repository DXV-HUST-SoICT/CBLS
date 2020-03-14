package vuongdx.search.solutiongenerator;

import java.util.Random;

import localsearch.model.IConstraint;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;

public class GRandom implements ISolutionGeneratorLS {

	@Override
	public void generateSolution(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		Random r = new Random();
		for (int i = 0; i < curSol.length; i++) {
			int v = r.nextInt(curSol[i].getMaxValue() - curSol[i].getMinValue() + 1) + curSol[i].getMinValue();
			curSol[i].setValuePropagate(v);
		}
	}

}
