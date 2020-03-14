package vuongdx.search.solutiongenerator;

import localsearch.model.VarIntLS;
import vuongdx.search.ISolutionGeneratorLS;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class GSudoku implements ISolutionGeneratorLS {

	@Override
	public void generateSolution(VarIntLS[] dVar) {
		int n = (int) Math.sqrt(dVar.length);
		ArrayList<Integer> value = new ArrayList<Integer>();
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				value.add(j);
			}
			for (int j = 0; j < n; j++) {
				int idx = r.nextInt(value.size());
				dVar[i * n + j].setValuePropagate(value.get(idx) + 1);
				value.remove(idx);
			}
		}
	}

}
