import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static int intervalId(double[][] intervals) {
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

    public static ArrayList<Person> initialPersons(int from, int to) {
        int bitSize = Integer.toBinaryString(to).length()-1;
        //TODO выбрать рандомные из промежутка
        ArrayList<Person> randomp = new ArrayList<Person>();
        randomp.add(new Person(0, bitSize));
        randomp.add(new Person(5, bitSize));
        randomp.add(new Person(4, bitSize));
        randomp.add(new Person(2, bitSize));
        return randomp;
    }

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
        ArrayList<Person> personPool = initialPersons(0, 16);
        int initialSize = personPool.size();
        ArrayList<Person> crossed = new ArrayList<Person>();
        int iter = 0;
        while (crossed.size() != 1) {
            for (int i = 1; i < personPool.size(); i++) {
                Person p1 = personPool.get(i - 1);
                Person p2 = personPool.get(i);
                Pair<Person, Person> crossing = Person.crossing(new Pair<Person, Person>(p1, p2));
                crossed.add(crossing.getFirst());
                crossed.add(crossing.getSecond());
            }
            personPool.addAll(crossed);
            System.out.println("pool after crossing " + personPool);
            crossed.clear();
            Collections.sort(personPool, new Comparator<Person>() {
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
            System.out.println("after sorting " + personPool);
            //отбираем первые initialSize особи
            personPool = new ArrayList<Person>(personPool.subList(0,personPool.size()/2));
            System.out.println("newloop: " +personPool);
            iter++;
            if(iter>10){
                break;
            }
        }
    }
}
