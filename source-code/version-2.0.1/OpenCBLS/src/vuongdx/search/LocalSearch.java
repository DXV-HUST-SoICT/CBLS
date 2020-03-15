package vuongdx.search;

import java.util.HashMap;

import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.variable.VarIntLS;

public class LocalSearch {
	public ConstraintSystem cs;
	public IFunction[] f;
	public HashMap<String, VarIntLS[]> dVar;
	public IMoveLS moveRule;
	public ILegalMoveLS legalMoveRule;
	public ISelectMoveLS selectMoveRule;
	
	public LocalSearch() {
		
	}
	
	public LocalSearch(ConstraintSystem cs,
			IFunction[] f,
			HashMap<String, VarIntLS[]> dVar,
			IMoveLS moveRule,
			ILegalMoveLS legalMoveRule,
			ISelectMoveLS selectMoveRule) {
		this.cs = cs;
		this.f = f;
		this.dVar = dVar;
		this.moveRule = moveRule;
		this.legalMoveRule = legalMoveRule;
		this.selectMoveRule = selectMoveRule;
	}
	
	public int search() {
		IMoveLS[] moveList = this.moveRule.listMove(cs, null, dVar);
		IMoveLS[] legalMoveList = this.legalMoveRule.listLegal(cs, null, dVar, moveList);
		IMoveLS selectedMove = this.selectMoveRule.select(cs, null, dVar, moveList, legalMoveList);
		selectedMove.movePropagate(dVar);
		return (int) cs.getViolation();
	}
	
	public int search(int numIter) {
		int it = 0;
		while (cs.getViolation() > 0 && it < numIter) {
			it++;
			this.search();
		}
		return (int) cs.getViolation();
	}
}
