package vuongdx.search;

import java.util.HashMap;

import localsearch.model.variable.VarIntLS;

public interface ISolutionGeneratorLS {
	public void generateSolution(HashMap<String, VarIntLS[]> dVar);
}
