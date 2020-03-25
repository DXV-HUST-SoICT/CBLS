package vuongdx.search;

public interface IMemoryLS {
	public void rememberMove(IMoveLS m);
	public void rememberSolution(ISolutionLS s);
	public boolean inMemory(IMoveLS m);
	public boolean inMemory(ISolutionLS s);
}
