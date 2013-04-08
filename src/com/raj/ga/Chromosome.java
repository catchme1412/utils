package com.raj.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Chromosome {

    private List allowedOperands;

    private CustomizedArrayList expression;

    private Random rand;

    private int maxLiteral;

    private int geneSize;

    public Chromosome() {
        allowedOperands = Arrays.asList(new String[] { "+", "-", "*", "/" });
        expression = new CustomizedArrayList();
        geneSize = 20;
        rand = new Random();
        maxLiteral = 12;
        for (int i = 0; i < geneSize; i++) {
            if (rand.nextBoolean()) {
                expression.add(allowedOperands.get(rand.nextInt(allowedOperands.size())));
            } else {
                expression.add(rand.nextInt(maxLiteral));
            }
        }
    }

    public static void main(String[] args) {
        Chromosome s = new Chromosome();
        System.out.println(s.getExpression());
        System.out.println(s.getDecodedExpression());
        System.out.println(s.getValidExpression());
        System.out.println(s.getFitness());
    }

    public List getAllowedOperands() {
        return allowedOperands;
    }

    public void setAllowedOperands(List allowedOperands) {
        this.allowedOperands = allowedOperands;
    }

    public List getExpression() {
        return expression;
    }

    public void setExpression(List expression) {
        this.expression = new CustomizedArrayList(expression);
    }

    public int getMaxLiteral() {
        return maxLiteral;
    }

    public void setMaxLiteral(int maxLiteral) {
        this.maxLiteral = maxLiteral;
    }

    public int getGeneSize() {
        return geneSize;
    }

    public void setGeneSize(int geneSize) {
        this.geneSize = geneSize;
    }

    public List getDecodedExpression() {
        boolean isPreviousOperand = false;
        List decoded = new ArrayList();
        for (Object i : expression) {
            if (allowedOperands.contains(i) && !isPreviousOperand) {
                isPreviousOperand = true;
                decoded.add(i);
            } else if (!allowedOperands.contains(i) && isPreviousOperand) {
                isPreviousOperand = false;
                decoded.add(i);
            }
        }
        return decoded;
    }

    public List getValidExpression() {
        List decoded = getDecodedExpression();
        if ("*".equals(decoded.get(0)) || "/".equals(decoded.get(0))) {
            decoded.remove(0);
        }
        int size = decoded.size() - 1;
        if ("+".equals(decoded.get(size)) || "-".equals(decoded.get(size)) || "*".equals(decoded.get(size))
                || "/".equals(decoded.get(size))) {
            decoded.remove(size);
        }
        return decoded;
    }

    public void crossOver(Chromosome n) {
        int index = rand.nextInt(expression.size());
        List temp = expression.subList(index, expression.size());
        List temp2 = n.getExpression().subList(index, expression.size());
        expression.removeRange(index, expression.size());
        ((CustomizedArrayList) n.getExpression()).removeRange(index, expression.size());
        expression.addAll(temp2);
        n.getExpression().addAll(temp);
    }

    public void mutate() {
        int index = rand.nextInt(expression.size());
        Object temp = expression.get(index);
        Object temp2 = expression.get(0);
        expression.set(0, temp);
        expression.set(index, temp2);
    }

    public double getFitness() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            String foo = getValidExpression().toString().replaceAll(",", "")
                    .replaceAll("\\[", "").replaceAll("]", "");
            System.out.println("FFFFF:" + foo);
            return Double.parseDouble(engine.eval(foo).toString());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    private class CustomizedArrayList extends ArrayList {
        public CustomizedArrayList(List expression) {
            super(expression);
        }

        public CustomizedArrayList() {
            super();
        }

        public void removeRange(int fromIndex, int toIndex) {
            // TODO Auto-generated method stub
            super.removeRange(fromIndex, toIndex);
        }
    }

}
