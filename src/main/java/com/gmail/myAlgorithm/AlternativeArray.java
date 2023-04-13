package com.gmail.myAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class AlternativeArray {
    public Alternative[] alternatives;
    public int betterA;
    public int worseA;

    public void assignSize(int[] alternativeOptions) {
        int size = 1;
        for (int i = 0; i < alternativeOptions.length; ++i) {
            size *= alternativeOptions[i];
        }
        alternatives = new Alternative[size];
        for (int i = 0; i < size; ++i) {
            alternatives[i] = new Alternative();
        }
    }

    public void fillArray(int[] alternativeOptions) {
        int[] firstValues = new int[alternativeOptions.length];
        for (int i = 0; i < alternativeOptions.length; ++i) {
            firstValues[i] = 1;
        }
        alternatives[0].assignAlternativeValue(firstValues);
        for (int i = 1; i < alternatives.length; ++i) {
            Alternative currentAlternative = alternatives[i - 1];
            alternatives[i].assignAlternativeValue(currentAlternative.plusOne(alternativeOptions));
        }
    }

    public Alternative findBest() {
        return alternatives[0];
    }

    public Alternative findWorst() {
        return alternatives[alternatives.length - 1];
    }

    public void automaticComparison(Alternative alternative) {
        betterA = 0;
        worseA = 0;
        System.out.println("\nПорівняння з: ");
        alternative.printAlternatives();
        List<Alternative> better = new ArrayList<>();
        List<Alternative> worse = new ArrayList<>();
        List<Alternative> incomparable = new ArrayList<>();
        for (int i = 0; i < alternatives.length; ++i) {
            if (alternative.compareAlternatives(alternatives[i]) == 1) {
                better.add(alternatives[i]);
            }
            if (alternative.compareAlternatives(alternatives[i]) == 0) {
                incomparable.add(alternatives[i]);
            }
            if (alternative.compareAlternatives(alternatives[i]) == -1) {
                worse.add(alternatives[i]);
            }
        }
        betterA = better.size();
        worseA = worse.size();
    }

    public void advancedAutomaticComparison(Alternative alternative, List<Integer> centerindexes, int[] g1, int[] g2, int iter) {
        g1[iter] = 0;
        g2[iter] = 0;
        int iterator = 0;
        List<Alternative> better = new ArrayList<>();
        List<Alternative> worse = new ArrayList<>();
        List<Alternative> incomparable = new ArrayList<>();
        for (int i = 0; i < alternatives.length; ++i) {
            if (i != centerindexes.get(iterator)) {
                if (alternative.compareAlternatives(alternatives[i]) == 1) {
                    better.add(alternatives[i]);
                }
                if (alternative.compareAlternatives(alternatives[i]) == 0) {
                    incomparable.add(alternatives[i]);
                }
                if (alternative.compareAlternatives(alternatives[i]) == -1) {
                    worse.add(alternatives[i]);
                }
            }
            if (i == centerindexes.get(iterator))
                ++iterator;
        }
        g1[iter] = better.size();
        g2[iter] = worse.size();
    }

    public List<Integer> findWorse(Alternative alternative, List<Integer> centerindexes) {
        List<Integer> resultArray = new ArrayList<>();
        int iterator = 0;
        for (int i = 0; i < alternatives.length; ++i) {
            if (i != centerindexes.get(iterator)) {
                if (alternative.compareAlternatives(alternatives[i]) == -1) {
                    resultArray.add(i);
                }
            }
            if (i == centerindexes.get(iterator))
                ++iterator;
        }
        return resultArray;
    }

    public List<Integer> findBetter(Alternative alternative, List<Integer> centerindexes) {
        List<Integer> resultArray = new ArrayList<>();
        int iterator = 0;
        for (int i = 0; i < alternatives.length; ++i) {
            if (i != centerindexes.get(iterator)) {
                if (alternative.compareAlternatives(alternatives[i]) == 1) {
                    resultArray.add(i);
                }
            }
            if (i == centerindexes.get(iterator))
                ++iterator;
        }
        return resultArray;
    }

    public void printArray() {
        for (int i = 0; i < alternatives.length; ++i) {
            alternatives[i].printAlternatives();
            if ((i + 1) % 5 == 0)
                System.out.println();
        }
        System.out.println("\nКількість можливих альтернатив: " + alternatives.length);
    }
}