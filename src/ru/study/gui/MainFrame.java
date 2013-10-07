package ru.study.gui; /**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */


import ru.study.core.GeneticAlgSolver;
import ru.study.core.Person;
import ru.study.utils.MathExpressionParser;
import ru.study.utils.Pair;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;


/**
 * Created by Mark
 */
public class MainFrame extends JFrame {


    private MainFrame() {
        setTitle("genetic");
        initGUI();
    }

    private void initGUI() {
        int WIDTH = 700;
        int HEIGHT = 500;
        Color lightBlue = new Color(51, 204, 255);   // light blue
        Color lightYellow = new Color(255, 255, 215);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //init
        panelCenter = new JPanel();
        panelTop = new JPanel();
        panelBottom = new JPanel();
        buttonCalc = new JButton("calc");
        fieldFunction = new JTextField("(2-3)*x^2+16*x+5");
        fieldFrom = new JTextField("0");
        fieldTo = new JTextField("16");
        textAreaLog = new JTextArea();
        textAreaLog.setFont(new Font("Courier New", Font.BOLD, 14));
        textAreaLog.setAutoscrolls(true);
        textAreaLog.setEditable(false);
        JScrollPane jsp = new JScrollPane(textAreaLog);
        //autoscroll
        DefaultCaret caret = (DefaultCaret) textAreaLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //set bg Color
        panelTop.setBackground(lightBlue);
        panelCenter.setBackground(lightYellow);
        panelBottom.setBackground(lightBlue);
        buttonCalc.setBackground(lightBlue);
        textAreaLog.setBackground(lightYellow);
        //preffered size
        panelTop.setPreferredSize(new Dimension(WIDTH, (int) (0.1 * HEIGHT)));
        panelCenter.setPreferredSize(new Dimension(WIDTH, (int) (0.65 * HEIGHT)));
        panelBottom.setPreferredSize(new Dimension(WIDTH, (int) (0.05 * HEIGHT)));
        int currentHeight  = (int) fieldFunction.getPreferredSize().getHeight();
        fieldFunction.setPreferredSize(new Dimension((int)(0.25*WIDTH),currentHeight));
        // borders
        panelTop.setBorder(BorderFactory.createEtchedBorder());

        //layouts
        panelTop.setLayout(new FlowLayout());
        panelCenter.setLayout(new BorderLayout());

        // action listeners
        buttonCalc.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                buttonOkActionPerformed();
            }
        });


        // add components to panels
        panelTop.add(funcLabel);
        panelTop.add(fieldFunction);
        panelTop.add(labelFrom);
        panelTop.add(fieldFrom);
        panelTop.add(labelTo);
        panelTop.add(fieldTo);
        panelTop.add(buttonCalc);
        panelTop.add(labelResult);
        panelTop.add(labelExpectedResult);
        panelCenter.add(jsp);
        //add panels to frame
        getContentPane().add(panelCenter, BorderLayout.CENTER);
        getContentPane().add(panelTop, BorderLayout.NORTH);
        getContentPane().add(panelBottom, BorderLayout.SOUTH);
        pack();
    }


    private void buttonOkActionPerformed() {
        try {
            textAreaLog.setText("");
            MathExpressionParser MEP = new MathExpressionParser(fieldFunction.getText());
            Integer from = Integer.parseInt(fieldFrom.getText());
            Integer to = Integer.parseInt(fieldTo.getText());
            if (genAlgSolver == null) {
                genAlgSolver = new GeneticAlgSolver(this, MEP);
            } else {
                genAlgSolver.setMEP(MEP);
            }
            Person res = genAlgSolver.solve(from, to);
            labelResult.setText("result -> " + functionFormat(res.getValue(), res.getAdaptation()));
            Pair<Integer, Integer> exRes = genAlgSolver.getExpectedResult(from, to);
            labelExpectedResult.setText("expected -> " + functionFormat(exRes.getFirst(), exRes.getSecond()));
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "function parse error,check syntax");
            repaint();
        } catch (EmptyStackException ex) {
            JOptionPane.showMessageDialog(this, "function parse error,check syntax(unary substraction not supported, use (2-3) instead)");
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "unexpected error" + ex.getMessage() + ")");
        }
    }

    private String functionFormat(int at, int value) {
        return "f(" + at + ")=" + value;
    }


    public void addlog(String s) {
          textAreaLog.append("\n"+s);
    }

    public static void main(String[] args) throws
            ClassNotFoundException,
            UnsupportedLookAndFeelException,
            InstantiationException,
            IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        }).run();
    }

    private GeneticAlgSolver genAlgSolver;
    //-----------------------
    private JPanel panelTop;
    private JPanel panelBottom;
    private JPanel panelCenter;
    private JButton buttonCalc;
    private JTextArea textAreaLog;
    private JTextField fieldFunction;
    private JTextField fieldFrom;
    private JTextField fieldTo;
    private JLabel funcLabel = new JLabel("func:");
    private JLabel labelFrom = new JLabel("from:");
    private JLabel labelTo = new JLabel("to:");
    private JLabel labelResult = new JLabel();
    private JLabel labelExpectedResult = new JLabel();

}

