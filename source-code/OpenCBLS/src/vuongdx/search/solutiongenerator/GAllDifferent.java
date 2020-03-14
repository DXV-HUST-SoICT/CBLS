package vuongdx.search.solutiongenerator;

import java.util.ArrayList;
import java.util.Random;

import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;

public class GAllDifferent implements ISolutionGeneratorLS {

	@Override
	public void generateSolution(VarIntLS[] dVar) {
		ArrayList<Integer> value = new ArrayList<Integer>();
		for (int i = 0; i < dVar.length; i++) {
			value.add(i);
		}
		Random r = new Random();
		for (int i = 0; i < dVar.length; i++) {
			int idx = r.nextInt(value.size());
			dVar[i].setValuePropagate(dVar[i].getMinValue() + value.get(idx));
			value.remove(idx);
		}
	}

}
