package vuongdx.search.memory;

import java.util.HashMap;

import vuongdx.search.IMoveLS;
import vuongdx.search.ISolutionLS;

public abstract class AbstractMemoryLS {
	protected HashMap<IMoveLS, Integer> move;
	protected HashMap<ISolutionLS, Integer> solution;
	protected Integer term;
}
