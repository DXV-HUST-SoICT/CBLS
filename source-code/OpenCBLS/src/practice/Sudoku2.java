package practice;

import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.model.ConstraintSystem;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.Search;
import vuongdx.search.legal.MinViolation;
import vuongdx.search.neighbor.SwapTwoVariableValue;
import vuongdx.search.select.SelectRandom;
import vuongdx.search.solutiongenerator.SudokuSolution;

public class Sudoku2 {
	private LocalSearchManager mgr;
	private VarIntLS[][] X;
	private ConstraintSystem S;
	
	private void stateModel() {
		mgr = new LocalSearchManager();
		X = new VarIntLS[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				X[i][j] = new VarIntLS(mgr, 1, 9);
				X[i][j].setValue(j + 1);
			}
		}
		
		S = new ConstraintSystem(mgr);
		for (int i = 0; i < 9; i++) {
			VarIntLS[] y = new VarIntLS[9];
			for (int j = 0; j < 9; j++) {
				y[j] = X[i][j];
			}
			S.post(new AllDifferent(y));
		}
		
		for (int i = 0; i < 9; i++) {
			VarIntLS[] y = new VarIntLS[9];
			for (int j = 0; j < 9; j++) {
				y[j] = X[j][i];
			}
			S.post(new AllDifferent(y));
		}
		
		for (int I = 0; I <= 2; I++) {
			for (int J = 0; J <= 2; J++) {
				VarIntLS[] y = new VarIntLS[9];
				int idx = -1;
				for (int i = 0; i <= 2; i++) {
					for (int j = 0; j <= 2; j++) {
						idx++;
						y[idx] = X[3 * I + i][3 * J + j];
					}
				}
				S.post(new AllDifferent(y));
			}
		}
		
		mgr.close();
	}
	
	private void search() {
		SudokuSolution ss = new SudokuSolution();
		ss.generateSolution(S);
		Search s = new Search(new SwapTwoVariableValue(),
			new MinViolation(), new SelectRandom(), S);
		int curVio = S.violations();
		for (int it = 0; it < 1000; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	private void printResult() {
		System.out.println(S.violations());
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(X[i][j].getValue() + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Sudoku2 sudoku = new Sudoku2();
		sudoku.stateModel();
		sudoku.search();
		sudoku.printResult();
	}
}
