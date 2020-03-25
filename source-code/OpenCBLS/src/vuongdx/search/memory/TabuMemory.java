package vuongdx.search.memory;

import vuongdx.search.IMemoryLS;
import vuongdx.search.IMoveLS;
import vuongdx.search.ISolutionLS;

public class TabuMemory extends AbstractMemoryLS implements IMemoryLS {

	@Override
	public void rememberMove(IMoveLS m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rememberSolution(ISolutionLS s) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inMemory(IMoveLS m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inMemory(ISolutionLS s) {
		// TODO Auto-generated method stub
		return false;
	}

}
