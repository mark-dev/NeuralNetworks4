package ru.study.core;

import ru.study.core.GeneticAlgCore;
import ru.study.core.Person;
import ru.study.gui.MainFrame;
import ru.study.utils.MathExpressionParser;
import ru.study.utils.Pair;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneticAlgSolver {
    private MainFrame mf;
    private MathExpressionParser MEP;

    public GeneticAlgSolver(MainFrame mf, MathExpressionParser MEP) {
        this.mf = mf;
        this.MEP = MEP;
    }

    public Person solve(int from, int to) {
        GeneticAlgCore gac = new GeneticAlgCore(MEP);
        ArrayList<Person> personPool = gac.initialPersons(from, to);
        mf.addlog("initial pool = " + personPool);
        while (!gac.isBestPersonFound(personPool)) {
            //Попарно скрещиваем,и добавляем к уже имеющимся особям
            personPool = gac.crossing(personPool);
            mf.addlog("pool after crossing " + personPool);
            //Отбираем лучших
            personPool = gac.getBestPersons(personPool);
        }
        mf.addlog("answer is : " + personPool.get(0));
        return personPool.get(0);
    }

    public void setMEP(MathExpressionParser MEP) {
        this.MEP = MEP;
    }

    public Pair<Integer, Integer> getExpectedResult(int from, int to) {
        int max = (int) MEP.solve(from);
        int at = 0;
        for (int i = from; i < to; i++) {
            int calc = (int) MEP.solve(i);
            if (calc > max) {
                max = calc;
                at = i;
            }
        }
        return new Pair<Integer, Integer>(at, max);
    }
}
