package practice;

import localsearch.constraint.AllDifferent;
import localsearch.function.math.FuncPlus;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.variable.VarIntLS;
import vuongdx.search.HillClimbingSearch;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.move.MThreeSwap;
import vuongdx.search.solutiongenerator.GAllDifferentAllSameRange;

import java.util.HashMap;

public class NQueen2 implements ISolver {

    private int n; // number of queens
    private LocalSearchManager mgr; // manager object
    private VarIntLS[] x; // decision variables
    private ConstraintSystem cs;

    private NQueen2(int n) {
        this.n = n;
    }

    @Override
    public void stateModel() {
        mgr = new LocalSearchManager();
        x = new VarIntLS[n];
        for (int i = 0; i < n; i++) {
            x[i] = new VarIntLS(0, 0, n - 1, mgr);
        }

        cs = new ConstraintSystem();
        cs.post(new AllDifferent(x));

        IFunction[] f1 = new IFunction[n];
        for (int i = 0; i < n; i++) {
            f1[i] = new FuncPlus(x[i], i);
        }
        cs.post(new AllDifferent(f1));

        IFunction[] f2 = new IFunction[n];
        for (int i = 0; i < n; i++) {
            f2[i] = new FuncPlus(x[i], -i);
        }
        cs.post(new AllDifferent(f2));

        mgr.close();
    }

    @Override
    public void search() {
        HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
        dVar.put("main", x);

        LocalSearch s = new HillClimbingSearch(cs, null, dVar, new MThreeSwap());
        GAllDifferentAllSameRange ds = new GAllDifferentAllSameRange();
        ds.generateSolution(dVar);
        int curVio = (int) cs.getViolation();
        for (int it = 0; it < 1000; it++) {
            System.out.println(it + " " + curVio);
            if (curVio == 0) {
                break;
            }
            curVio = s.search();
        }
    }

    private void printResult() {
        System.out.println(cs.getViolation());
        for (int i = 0; i < this.n; i++) {
            System.out.print(Integer.toString(x[i].getValue()) + ' ');
        }
    }

    public static void main(String[] args) {
        NQueen2 nqueen = new NQueen2(200);
        nqueen.stateModel();
        nqueen.search();
        nqueen.printResult();
    }
}
