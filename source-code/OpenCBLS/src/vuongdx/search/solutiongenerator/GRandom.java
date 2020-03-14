package vuongdx.search.solutiongenerator;

import java.util.Random;

import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;

public class GRandom implements ISolutionGeneratorLS {

	@Override
	public void generateSolution(VarIntLS[] dVar) {
		Random r = new Random();
		for (int i = 0; i < dVar.length; i++) {
			int v = r.nextInt(dVar[i].getMaxValue() - dVar[i].getMinValue() + 1) + dVar[i].getMinValue();
			dVar[i].setValuePropagate(v);
		}
	}

}
