package practice;

import localsearch.model.*;

import java.util.HashMap;

import localsearch.constraints.alldifferent.*;
import localsearch.functions.basic.*;
import vuongdx.search.HillClimbingSearch;
import vuongdx.search.ISolver;
import vuongdx.search.LocalSearch;
import vuongdx.search.TabuSearch;
import vuongdx.search.legal.BestMove;
import vuongdx.search.move.SingleVarChangeValue;
import vuongdx.search.move.ThreeVarSwap;
import vuongdx.search.move.TwoVarSwap;
import vuongdx.search.select.RandomSelection;
import vuongdx.search.solutiongenerator.GAllDifferentAllSameRange;
import vuongdx.search.solutiongenerator.GRandom;

public class NQueenTabu implements ISolver {
    private int n; // number of queens
    private LocalSearchManager mgr; // manager object
    private VarIntLS[] x; // decision variables
    private ConstraintSystem cs;

    private NQueenTabu(int n) {
        this.n = n;
    }

    public void stateModel() {
        mgr = new LocalSearchManager();

        x = new VarIntLS[n];
        for (int i = 0; i < n; i++) {
            x[i] = new VarIntLS(mgr, 0, n - 1);
        }

        cs = new ConstraintSystem(mgr);
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

    public void search() {
        HashMap<String, VarIntLS[]> dVar = new HashMap<String, VarIntLS[]>();
        dVar.put("main", x);
        GAllDifferentAllSameRange g = new GAllDifferentAllSameRange();
        g.generateSolution(dVar);
        TabuSearch s = new TabuSearch(cs, null, dVar, new TwoVarSwap(),10);
        s.search(1000, 50, g);
    }

    public void printResult() {
        System.out.println(cs.violations());
        for (int i = 0; i < this.n; i++) {
            System.out.print(Integer.toString(x[i].getValue()) + ' ');
        }
    }

    public static void main(String[] args) {
        NQueenTabu nqueen = new NQueenTabu(200);
        nqueen.stateModel();
        nqueen.search();
        nqueen.printResult();
    }
}
