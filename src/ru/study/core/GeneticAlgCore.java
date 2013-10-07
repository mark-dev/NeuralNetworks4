package ru.study.core;

import ru.study.utils.MathExpressionParser;
import ru.study.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneticAlgCore {
    private MathExpressionParser MEP;

    public GeneticAlgCore(MathExpressionParser MEP) {
        this.MEP = MEP;
    }

    public   ArrayList<Person> initialPersons(int from, int to) {
        int bitSize = Integer.toBinaryString(to).length() - 1;
        //TODO выбрать рандомные из промежутка
        ArrayList<Person> randomp = new ArrayList<Person>();
        randomp.add(new Person(0, bitSize,MEP));
        randomp.add(new Person(5, bitSize,MEP));
        randomp.add(new Person(4, bitSize,MEP));
        randomp.add(new Person(2, bitSize,MEP));
        return randomp;
    }

    public   int intervalId(double[][] intervals) {
        double r = Math.random();
        System.out.println("r=" + r);
        for (int i = 1; i < intervals.length; i++) {
            double from = intervals[i - 1][0];
            double to = intervals[i][0];
            if (r >= from && r <= to) {
                return i - 1;
            }
        }
        return intervals.length - 1;
    }

    //HashSet не допускает дублирования, так что если его длина = 1 то
    //значит что все элементы списка - одинаковые - а значит можно заканчивать
    public   boolean isBestPersonFound(ArrayList<Person> personsPool) {
        return new HashSet<Person>(personsPool).size() == 1;
    }
    //Попарно скрещивает
    public   ArrayList<Person> crossing(ArrayList<Person> personsPool) {
        ArrayList<Person> crossed = new ArrayList<Person>();
        for (int i = 1; i < personsPool.size(); i++) {
            Person p1 = personsPool.get(i - 1);
            Person p2 = personsPool.get(i);
            Pair<Person, Person> crossing = Person.crossing(new Pair<Person, Person>(p1, p2));
            crossed.add(crossing.getFirst());
            crossed.add(crossing.getSecond());
        }
        personsPool.addAll(crossed);
        return personsPool ;
    }

    public   ArrayList<Person> getBestPersons(ArrayList<Person> personsPool) {
        //Сортируем и берем первую половину
        Collections.sort(personsPool, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getAdaptation() < o2.getAdaptation()) {
                    return 1;
                } else if (o1.getAdaptation() == o2.getAdaptation()) {
                    return 0;
                } else if (o1.getAdaptation() > o2.getAdaptation()) {
                    return -1;
                }
                return 0;
            }
        });
        return new ArrayList<Person>(personsPool.subList(0, personsPool.size() / 2));
    }
}
