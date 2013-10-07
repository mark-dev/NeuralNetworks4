package ru.study.test;

import ru.study.core.GeneticAlgCore;
import ru.study.core.Person;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {


    public static void main(String[] args) {
//        double[][] intervals = {{0,0.13},
//                {0.13,0.22},
//                {0.22,0.45},
//                {0.45,0.75},
//                {0.75,1}};
//        for (int i = 0; i < 10; i++) {
//            int id = intervalId(intervals);
//            System.out.println( "->{" + intervals[id][0] + ";"+intervals[id][1] +"}");
//        }
        GeneticAlgCore gac = new GeneticAlgCore(null);
        ArrayList<Person> personPool = gac.initialPersons(0, 16);
        System.out.println("initial pool = " + personPool);
        while (!gac.isBestPersonFound(personPool)) {
            //Попарно скрещиваем,и добавляем к уже имеющимся особям
            personPool = gac.crossing(personPool);
            System.out.println("pool after crossing " + personPool);
            //Отбираем лучших
            personPool = gac.getBestPersons(personPool);
        }
        System.out.println("answer is : " + personPool.get(0));
    }
}
