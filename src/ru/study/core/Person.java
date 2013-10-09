package ru.study.core;

import ru.study.utils.FixedBitSet;
import ru.study.utils.MathExpressionParser;
import ru.study.utils.Pair;

import java.util.BitSet;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Person {
    private static final double MUTATION_PROBABILITY = 0.09;
    private int bitSize;
    private int value;
    private FixedBitSet bits;
    private int adaptation;
    private MathExpressionParser MEP;

    public Person(int value, int bitSize, MathExpressionParser MEP) {
        this.bitSize = bitSize;
        this.MEP = MEP;
        setValue(value);

    }

    public Person(FixedBitSet set, MathExpressionParser MEP) {
        this.bitSize = set.length();
        this.MEP = MEP;
        setValue(set);
    }

    public void setValue(FixedBitSet set) {
        bits = set;
        value = Integer.parseInt(bsString(), 2);
        adaptation = calcAdaptation();
    }

    public void setValue(int value) {
        this.value = value;
        bits = initBitset(value);
        adaptation = calcAdaptation();
    }

    public String bsString() {
        return bsString(bits);
    }

    public String bsString(BitSet bs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bs.length(); i++) {
            boolean bit = bs.get(i);
            sb.append(bit ? 1 : 0);
        }
        return sb.toString();
    }

    public BitSet getBits() {
        return bits;
    }


    public MathExpressionParser getMEP() {
        return MEP;
    }

    //
    public static Pair<Person, Person> crossing(Pair<Person, Person> pair) {
        Person p1 = pair.getFirst();
        Person p2 = pair.getSecond();
        int bitSizes = p1.getBits().length();
        int crossingPoint = (int) (1 + (Math.random() * (bitSizes - 1)));

        FixedBitSet b1 = new FixedBitSet(bitSizes);
        FixedBitSet b2 = new FixedBitSet(bitSizes);
        for (int i = 0; i < bitSizes; i++) {
            if (i <= crossingPoint) {
                crossAndMutation(b1, i, p1.getBits().get(i));
                crossAndMutation(b2, i, p2.getBits().get(i));
            } else {

                crossAndMutation(b1, i, p2.getBits().get(i));
                crossAndMutation(b2, i, p1.getBits().get(i));
            }
        }
        Pair<Person, Person> res = new Pair<Person, Person>(new Person(b1, p1.getMEP()),
                new Person(b2, p1.getMEP()));
        System.out.println("crossing(" + crossingPoint + "): [" +
                p1.bsString() + "," +
                p2.bsString() + "] -> [" +
                res.getFirst().bsString() +
                "," + res.getSecond().bsString() + "]");
        return res;
    }

    private static void crossAndMutation(BitSet destination, int index, boolean value) {
        double r = Math.random();
        destination.set(index, value);
        if (r < MUTATION_PROBABILITY) {
            System.out.println("mutation occurs at index " + index + "(old value = " + value + ")");
            destination.flip(index);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "bitSize=" + bitSize +
                ", value=" + value +
                ", bits=" + (bsString() != null ? bsString() : "null") +
                ", adaptation=" + adaptation +
                '}';
    }

    public int getAdaptation() {
        return adaptation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (adaptation != person.adaptation) return false;
        if (bitSize != person.bitSize) return false;
        if (value != person.value) return false;
        if (bits != null ? !bits.equals(person.bits) : person.bits != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bitSize;
        result = 31 * result + value;
        result = 31 * result + (bits != null ? bits.hashCode() : 0);
        result = 31 * result + adaptation;
        return result;
    }

    public int getValue() {
        return value;
    }

    //Internal
    private FixedBitSet initBitset(int val) {
        //Дописывает слева 0, если длина бинарного представления меньше чем ожидаемая
        String binary = Integer.toBinaryString(val);
        if (binary.length() < bitSize) {
            int iterCount = bitSize - binary.length();
            for (int i = 0; i < iterCount; i++) {
                binary = "0" + binary;
            }
        }
        //Переводит в bitset
        FixedBitSet bs = new FixedBitSet(bitSize);
        char[] binaryChars = binary.toCharArray();
        for (int i = 0; i < binaryChars.length; i++) {
            if (binaryChars[i] == '1') {
                bs.set(i, true);
            }
        }


        return bs;
    }

    private int calcAdaptation() {
        return (int) MEP.solve(value);
    }

}
