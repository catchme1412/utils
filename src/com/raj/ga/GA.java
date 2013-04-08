package com.raj.math;

import java.util.ArrayList;
import java.util.Collection;

public class GA {

    private void doIt(int target) {

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
                Chromosome n1 = selectMember(pool);
                Chromosome n2 = selectMember(pool);

                // Cross over and mutate
                n1.crossOver(n2);
                n1.mutate();
                n2.mutate();

                // Rescore the nodes
//                n1.scoreChromo(target);
//                n2.scoreChromo(target);

                // Check to see if either is the solution
                if (n1.getFitness() > 0.9) {
                    System.out.println(n1);
                }else if (n2.getFitness() > .9) {
                    System.out.println(n2);
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
    private Chromosome selectMember(ArrayList l) {

        // Get the total fitness
        double tot = 0.0;
        for (int x = l.size() - 1; x >= 0; x--) {
            double score = ((Chromosome) l.get(x)).score;
            tot += score;
        }
        double slice = tot * rand.nextDouble();

        // Loop to find the node
        double ttot = 0.0;
        for (int x = l.size() - 1; x >= 0; x--) {
            Chromosome node = (Chromosome) l.get(x);
            ttot += node.score;
            if (ttot >= slice) {
                l.remove(x);
                return node;
            }
        }

        return (Chromosome) l.remove(l.size() - 1);
    }
}
