package practice;

import java.util.HashMap;

import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.model.ConstraintSystem;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.LocalSearch;
import vuongdx.search.legal.LBestNeighbor;
import vuongdx.search.neighbor.NSudokuTwoSwap;
import vuongdx.search.select.SRandom;
import vuongdx.search.solutiongenerator.GSudoku;

public class Sudoku2 {
	private LocalSearchManager mgr;
	private VarIntLS[][] X;
	private ConstraintSystem cs;
	
	private void stateModel() {
		mgr = new LocalSearchManager();
		X = new VarIntLS[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				X[i][j] = new VarIntLS(mgr, 1, 9);
				X[i][j].setValue(j + 1);
			}
		}
		
		cs = new ConstraintSystem(mgr);
		for (int i = 0; i < 9; i++) {
			VarIntLS[] y = new VarIntLS[9];
			for (int j = 0; j < 9; j++) {
				y[j] = X[i][j];
			}
			cs.post(new AllDifferent(y));
		}
		
		for (int i = 0; i < 9; i++) {
			VarIntLS[] y = new VarIntLS[9];
			for (int j = 0; j < 9; j++) {
				y[j] = X[j][i];
			}
			cs.post(new AllDifferent(y));
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
				cs.post(new AllDifferent(y));
			}
		}
		
		mgr.close();
	}
	
	private void search() {
		VarIntLS[] tmpX = new VarIntLS[81];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				tmpX[i * 9 + j] = X[i][j];
			}
		}
		HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
		dVar.put("main", tmpX);
		GSudoku ss = new GSudoku();
		ss.generateSolution(dVar.get("main"));
		LocalSearch s = new LocalSearch(cs, null, dVar,
				new NSudokuTwoSwap(),
				new LBestNeighbor(),
				new SRandom());
		int curVio = cs.violations();
		for (int it = 0; it < 100; it++) {
			System.out.println(it + " " + curVio);
			if (curVio == 0) {
				break;
			}
			curVio = s.search();
		}
	}
	
	private void printResult() {
		System.out.println(cs.violations());
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
