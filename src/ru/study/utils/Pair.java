package ru.study.utils;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    @Override
    public String toString() {
        return "Pair{"+first +
                ";" +second +
                '}';
    }

    public S getSecond() {
        return second;
    }
}
