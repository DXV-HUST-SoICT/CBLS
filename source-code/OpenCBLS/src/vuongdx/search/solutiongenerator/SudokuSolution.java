package vuongdx.search.solutiongenerator;

import localsearch.model.IConstraint;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGenerator;
import java.lang.Math;

public class SudokuSolution implements ISolutionGenerator {

	@Override
	public void generateSolution(IConstraint cs) {
		VarIntLS[] curSol = cs.getVariables();
		int n = (int) Math.sqrt(curSol.length); 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				curSol[i * n + j].setValuePropagate(j + 1);
			}
		}
	}

}
