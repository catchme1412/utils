package com.raj.ga;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	private static final double _0_5 = 0.95;

	public static void main(String[] args) {
		new GeneticAlgorithm().doIt(25);
	}

	private Random rand;

	private void doIt(int target) {

		rand = new Random();
		int gen = 0;

		int poolSize = 100;
		// Create the pool
		ArrayList pool = new ArrayList(poolSize);
		ArrayList newPool = new ArrayList(pool.size());

		// Generate unique cromosomes in the pool
		for (int x = 0; x < poolSize; x++)
			pool.add(new Chromosome());

		// Loop until solution is found
		while (true) {
			// Clear the new pool
			newPool.clear();

			// Add to the generations
			gen++;

			// Loop until the pool has been processed
			for (int x = pool.size() - 1; x >= 0; x -= 2) {
				// Select two members
				Chromosome n1 = selectMember(pool, _0_5);
				Chromosome n2 = selectMember(pool, 0.5);
				// Cross over and mutate
				n1.crossOver(n2);
				n1.mutate();
				n2.mutate();

				// Rescore the nodes
				// n1.scoreChromo(target);
				// n2.scoreChromo(target);
				System.out.println(n1.getValidExpression() + "::" + n1.getFitness(_0_5));
				// Check to see if either is the solution
				if (n1.getFitness(_0_5) > 0.9) {
					System.out.println("DONE:" + n1.getValidExpression() + "::" + n1.getFitness(_0_5));
				} else if (n2.getFitness(_0_5) > .9) {
					System.out.println("DONE:" + n2.getValidExpression() + "::" + n2.getFitness(_0_5));
				}
				// Add to the new pool
				newPool.add(n1);
				newPool.add(n2);
			}

			// Add the newPool back to the old pool
			pool.addAll(newPool);
		}

	}

	// ---- Chromosome Class -----
	private Chromosome selectMember(ArrayList<Chromosome> l, double expectedDifficultyLevel) {

		// Get the total fitness
		double tot = 0.0;
		for (int x = l.size() - 1; x >= 0; x--) {
			double score = ((Chromosome) l.get(x)).getFitness(expectedDifficultyLevel);
			tot += score;
		}
		double slice = tot * rand.nextDouble();

		// Loop to find the node
		double ttot = 0.0;
		for (int x = l.size() - 1; x >= 0; x--) {
			Chromosome node = (Chromosome) l.get(x);
			ttot += node.getFitness(expectedDifficultyLevel);
			if (ttot >= slice) {
				l.remove(x);
				return node;
			}
		}

		return (Chromosome) l.remove(l.size() - 1);
	}
}
