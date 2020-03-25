package practice;

import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.model.ConstraintSystem;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.TabuSearch;
import vuongdx.search.legal.BestMove;
import vuongdx.search.move.TwoVarSwap;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GAllDifferentAllSameRange;

import java.util.HashMap;

public class SudokuTabu implements ISolver {

    private LocalSearchManager mgr;
    private VarIntLS[][] X;
    private ConstraintSystem cs;

    public void stateModel() {
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

    public void search() {
        HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
        for (int i = 0; i < 9; i++)  {
            dVar.put("main_" + i, X[i]);
        }
        GAllDifferentAllSameRange g = new GAllDifferentAllSameRange();
        g.generateSolution(dVar);
        TabuSearch s = new TabuSearch(cs, null, dVar, new TwoVarSwap(),10);
        s.search(1000, 50, g);
    }

    public void printResult() {
        System.out.println(cs.violations());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(X[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuTabu sudoku = new SudokuTabu();
        sudoku.stateModel();
        sudoku.search();
        sudoku.printResult();
    }
}