package ru.study.utils;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathExpressionParser {

    /**
     * Input expression.
     */
    private String expression;

    /**
     * Expression in reverse polish notation.
     */
    private StringBuffer reversePolishNotation = new StringBuffer();

    /**
     * Stack for keeping mathematical operators while parsing input expression
     * to reverse polish notation.
     */
    private Stack<String> operators = new Stack<String>();

    /**
     * Stack for solving expression in reverse polish notation.
     */
    private Stack<String> solver = new Stack<String>();

    public MathExpressionParser(String expression) {
        this.expression = expression;
        reversePolishNotation = toReversePolishNotation();
    }

    /**
     * Parses input expression to reverse polish notation.
     *
     * @return expression in reverse polish notation
     */
    public StringBuffer toReversePolishNotation() {

        String delemiters = "+-*/()^";
        StringTokenizer tokenizer = new StringTokenizer(expression, delemiters,
                true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.matches("\\d+\\.\\d+||\\d+") || token.matches("x")
                    || token.matches("e") || token.equals("PI")) {

                parseNumber(token);

            } else {

                if (operators.isEmpty() || token.equals("(")
                        || getPriority(token) > getPriority(operators.peek())) {

                    parseHighPriorityOperator(token);

                } else if (token.equals(")")) {
                    parseRigthBracket();
                }

                else {
                    parseLowPriorityOperator(token);
                }
            }
        }
        while (!operators.empty()) {
            reversePolishNotation.append(operators.pop()).append(" ");
        }

        return reversePolishNotation;
    }


    public double solve(double xValue) {
        String str = reversePolishNotation.toString();

        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.matches("\\d+\\.\\d+||\\d+")) {
                solver.push(token);
            } else if (token.matches("x")) {
                solver.push(String.valueOf(xValue));
            } else if (token.matches("e")) {
                solver.push(String.valueOf(Math.E));
            } else if (token.equals("PI")) {
                solver.push(String.valueOf(Math.PI));
            } else if (token.equals("(") || token.equals(")")) {
                throw new IllegalArgumentException("Unmatched brackets");
            } else {

                if (token.equals("+"))
                    add();
                if (token.equals("-"))
                    subtract();
                if (token.equals("*"))
                    multiply();
                if (token.equals("/"))
                    divide();
                if (token.equals("^"))
                    involute();
                if (token.equals("sin"))
                    sin();
                if (token.equals("cos"))
                    cos();
                if (token.equals("tan"))
                    tan();
                if (token.equals("sqrt"))
                    sqrt();
                if (token.equals("log"))
                    log();
            }
        }

        if (solver.size() != 1) {
            throw new IllegalArgumentException("Expression syntax error");
        }

        return Double.parseDouble(solver.pop());
    }

    /**
     * Gets priority of mathematical operation.
     *
     * @param token
     * @return priority of mathematical operation
     */
    private int getPriority(String token) {

        if (token.equals("(") || token.equals(")"))
            return 1;

        if (token.equals("+") || token.equals("-"))
            return 2;

        if (token.equals("*") || token.equals("/"))
            return 3;

        if (token.equals("^") || token.equals("sin") || token.equals("cos")
                || token.equals("tan") || token.equals("sqrt")
                || token.equals("log"))
            return 4;
        return 0;
    }

    private void parseNumber(String token) {
        reversePolishNotation.append(token).append(" ");
    }

    private void parseHighPriorityOperator(String token) {
        operators.push(token);
    }

    private void parseLowPriorityOperator(String token) {
        while (getPriority(token) <= getPriority(operators.peek())) {

            reversePolishNotation.append(operators.pop()).append(" ");
            if (operators.isEmpty())
                break;
        }
        operators.push(token);
    }

    private void parseRigthBracket() {
        while (!operators.peek().equals("(")) {
            reversePolishNotation.append(operators.pop()).append(" ");
            if (operators.isEmpty()) {
                throw new IllegalArgumentException("Unmatched brackets");
            }
        }
        operators.pop();
    }

    private void add() {
        double secondAddendum = Double.parseDouble(solver.pop());
        double firstAddendum = Double.parseDouble(solver.pop());
        solver.push(Double.toString(firstAddendum + secondAddendum));
    }

    private void subtract() {
        double subtrahend = Double.parseDouble(solver.pop());
        double minuend = Double.parseDouble(solver.pop());
        solver.push(Double.toString(minuend - subtrahend));
    }

    private void multiply() {
        double secondFactor = Double.parseDouble(solver.pop());
        double firstFactor = Double.parseDouble(solver.pop());
        solver.push(Double.toString(firstFactor * secondFactor));
    }

    private void divide() {
        double divider = Double.parseDouble(solver.pop());
        double dividend = Double.parseDouble(solver.pop());
        solver.push(Double.toString(dividend / divider));
    }

    /**
     * First argument raises to the power of the second argument
     */
    private void involute() {
        double degree = Double.parseDouble(solver.pop());
        double base = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.pow(base, degree)));
    }

    private void sin() {
        double argument = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.sin(argument)));
    }

    private void cos() {
        double argument = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.cos(argument)));
    }

    private void tan() {
        double argument = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.tan(argument)));
    }

    private void sqrt() {
        double argument = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.sqrt(argument)));
    }

    /**
     * The natural logarithm (base <i>e</i>) of a <code>double</code> value.
     */
    private void log() {
        double argument = Double.parseDouble(solver.pop());
        solver.push(Double.toString(Math.log(argument)));
    }

    public static void main(String[] args) {
        MathExpressionParser parser = new MathExpressionParser("sqrt(1+x^2)");
        double value = parser.solve( 10);
        System.out.println("Function's value= " + value);
    }
}

