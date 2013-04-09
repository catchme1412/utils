package com.raj.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Chromosome {

	private List<String> allowedOperands;

	private CustomizedArrayList expression;

	private Random rand;

	private int maxLiteral;

	private int geneSize;

	private Map<String, Integer> weightageForOperand;

	public Chromosome() {
		allowedOperands = Arrays.asList(new String[] { "+", "-", "*", "/" });
		weightageForOperand = new HashMap();
		int weight = 0;
		for (String op : allowedOperands) {
			weightageForOperand.put(op, ++weight);
		}
		expression = new CustomizedArrayList();
		geneSize = 21;
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
		System.out.println(s.difficultyScore());
		System.out.println(s.getFitness(.5));
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
		if (decoded.size() > 0 && ("*".equals(decoded.get(0)) || "/".equals(decoded.get(0)))) {
			decoded.remove(0);
		}
		int size = decoded.size() - 1;
		if (size > 0
				&& ("+".equals(decoded.get(size)) || "-".equals(decoded.get(size)) || "*".equals(decoded.get(size)) || "/"
						.equals(decoded.get(size)))) {
			decoded.remove(size);
		}
		return decoded;
	}

	public void crossOver(Chromosome n) {
		int size = expression.size();
		int index = rand.nextInt(size);
		List temp = new ArrayList(expression.subList(index, size));
		List temp2 = new ArrayList(n.getExpression().subList(index, size));
		expression.removeRange(index, size);
		((CustomizedArrayList) n.getExpression()).removeRange(index, size);
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

	// Add up the contents of the decoded chromosome
	public final double difficultyScore() {

		Set uniqueOperands = new HashSet();
		// number of literals
		// number of operands
		// number of unique operands
		// TODO
		// how many division, how many multiplication etc
		// value of literals
		// order of operands ?
		// Decode our chromo
		List validExpr = getValidExpression();
		// Total
		int tot = 0;
		double literalCount = 0;
		double operandCount = 0;
		int totalOperandWeight = 0;
		// Find the first number
		int ptr = 0;
		for (Object o : validExpr) {
			if (allowedOperands.contains(o)) {
				operandCount++;
				totalOperandWeight += weightageForOperand.get(o);
				uniqueOperands.add(o);
			} else {
				literalCount++;
			}
		}

		if (literalCount == 0 || operandCount == 0) {
			return 0;
		}
		double d = (1- uniqueOperands.size() /operandCount )*.5 + (uniqueOperands.size() /literalCount )*.5;
		int maxOperatorAllowed = validExpr.size() - (validExpr.size() + 1) / 2;

		return d ;//;
	}

	public double getFitness(double expectedDifficultyLevel) {
		double difficultyScore = difficultyScore();
		// if (difficultyScore - expectedDifficultyLevel == 0) {
		// return 1;
		// }
		// double fitness = 1.0 - (Math.abs(difficultyScore -
		// expectedDifficultyLevel) / expectedDifficultyLevel);
		double fitness = 1.0 - difficultyScore * expectedDifficultyLevel;

		return fitness;
	}

	public double evaluateExpression() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			String foo = getValidExpressionString();
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

	private String getValidExpressionString() {
		return getValidExpression().toString().replaceAll(",", "").replaceAll("\\[", "").replaceAll("]", "");
	}

	private class CustomizedArrayList extends ArrayList {
		public CustomizedArrayList(List expression) {
			super(expression);
		}

		public CustomizedArrayList() {
			super();
		}

		public void removeRange(int fromIndex, int toIndex) {
			super.removeRange(fromIndex, toIndex);
		}
	}

	// public void stepByStepEvaluation()
	// {
	// char[] a = getValidExpressionString().toCharArray();
	// int N =a.length;
	// Stack s = new Stack();
	// for (int i = 0; i<N; i++)
	// {
	// if (a[i]=='+')
	// {
	// s.push(s.pop() + s.pop());
	// }
	// if (a[i]=='*')
	// {
	// s.push(s.pop() * s.pop());
	// }
	// if ((a[i] >= '0') && (a[i] <= '9'))
	// {
	// s.push(0);
	// }
	// while ((a[i] >= '0') && (a[i] <= '9'))
	// {
	// s.push(10*s.pop() + (a[i++]-'0'));
	// }
	// Out.println(s.pop() + "");
	// }
	// }

}
