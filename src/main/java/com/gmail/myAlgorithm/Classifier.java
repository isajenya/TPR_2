package com.gmail.myAlgorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Classifier {
    int[] decisions = {1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
    int[] class1center = {1, 1, 1, 1, 1};
    int[] class2center = {2, 2, 2, 2, 3};
    List<Integer> class1indexes;
    List<Integer> class2indexes;
    int[] K1 = new int[48];
    int[] K2 = new int[48];
    int[] K3 = new int[48];
    int[] K4 = new int[48];
    int[] K5 = new int[48];
    int[] G = new int[48];
    int[] d1 = new int[48];
    int[] d2 = new int[48];
    double[] p1 = new double[48];
    double[] p2 = new double[48];
    int[] g1 = new int[48];
    int[] g2 = new int[48];
    double[] f1 = new double[48];
    double[] f2 = new double[48];
    double[] F = new double[48];

    public void fillK(AlternativeArray alternativeArray) {
        for (int i = 0; i < 48; ++i) {
            K1[i] = alternativeArray.alternatives[i].attributes[0];
            K2[i] = alternativeArray.alternatives[i].attributes[1];
            K3[i] = alternativeArray.alternatives[i].attributes[2];
            K4[i] = alternativeArray.alternatives[i].attributes[3];
            K5[i] = alternativeArray.alternatives[i].attributes[4];
        }
    }

    public void fillG1() {
        G[0] = 1;
        G[47] = 2;
        for (int i = 1; i < 47; ++i) {
            G[i] = -1;
        }
    }

    public void determineCenters(AlternativeArray alternativeArray) {

        class1indexes = new ArrayList<>();
        class2indexes = new ArrayList<>();
        for (int i = 0; i < 48; ++i) {
            if (G[i] == 1)
                class1indexes.add(i);
            if (G[i] == 2)
                class2indexes.add(i);
        }
        for (int i = 0; i < 5; ++i) {
            class1center[i] = 0;
            class2center[i] = 0;
        }
        for (int i = 0; i < class1indexes.size(); ++i) {
            class1center[0] += alternativeArray.alternatives[class1indexes.get(i)].attributes[0];
            class1center[1] += alternativeArray.alternatives[class1indexes.get(i)].attributes[1];
            class1center[2] += alternativeArray.alternatives[class1indexes.get(i)].attributes[2];
            class1center[3] += alternativeArray.alternatives[class1indexes.get(i)].attributes[3];
            class1center[4] += alternativeArray.alternatives[class1indexes.get(i)].attributes[4];
        }
        for (int i = 0; i < class2indexes.size(); ++i) {
            class2center[0] += alternativeArray.alternatives[class2indexes.get(i)].attributes[0];
            class2center[1] += alternativeArray.alternatives[class2indexes.get(i)].attributes[1];
            class2center[2] += alternativeArray.alternatives[class2indexes.get(i)].attributes[2];
            class2center[3] += alternativeArray.alternatives[class2indexes.get(i)].attributes[3];
            class2center[4] += alternativeArray.alternatives[class2indexes.get(i)].attributes[4];
        }
        for (int i = 0; i < 5; ++i) {
            class1center[i] /= class1indexes.size();
            class2center[i] /= class2indexes.size();
        }
    }

    public void findD() {
        for (int i = 0; i < 48; ++i) {
            d1[i] = Math.abs(class1center[0] - K1[i]) + Math.abs(class1center[1] - K2[i])
                    + Math.abs(class1center[2] - K3[i]) + Math.abs(class1center[3] - K4[i])
                    + Math.abs(class1center[4] - K5[i]);
            d2[i] = Math.abs(class2center[0] - K1[i]) + Math.abs(class2center[1] - K2[i])
                    + Math.abs(class2center[2] - K3[i]) + Math.abs(class2center[3] - K4[i])
                    + Math.abs(class2center[4] - K5[i]);
        }
    }

    public void findP() {
        double D = -1;
        for (int i = 0; i < 48; ++i) {
            if (d1[i] > D)
                D = d1[i];
        }
        for (int i = 0; i < 48; ++i) {
            p1[i] = (D - d1[i]) / (D - d1[i] + D - d2[i]);
        }
        for (int i = 0; i < 48; ++i) {
            p2[i] = 1 - p1[i];
        }
    }

    public void findG(AlternativeArray alternativeArray) {
        for (int i = 0; i < 48; ++i) {
            g1[i] = 0;
            g2[i] = 0;
        }
        for (int i = 0; i < class2indexes.size(); ++i) {
            class1indexes.add(class2indexes.get(i));
        }
        Collections.sort(class1indexes);
        int iterator = 0;
        for (int i = 0; i < 48; ++i) {
            if (i != class1indexes.get(iterator)) {
                alternativeArray.advancedAutomaticComparison(alternativeArray.alternatives[i], class1indexes, g1, g2, i);
            }
            if (i == class1indexes.get(iterator)) {
                ++iterator;
            }
        }
    }

    public void findF() {
        for (int i = 0; i < 48; ++i) {
            f1[i] = p1[i] * g1[i];
            f2[i] = p2[i] * g2[i];
        }
        for (int i = 0; i < 48; ++i) {
            F[i] = f1[i] + f2[i];
        }
    }

    public void fillG(int iteration, AlternativeArray alternativeArray, Integer check) {
        int FMAX = -1;
        double Fcomp = -2;
        List<Integer> AdjustDecisions = new ArrayList<>();
        for (int i = 0; i < 48; ++i) {
            if (F[i] > Fcomp) {
                Fcomp = F[i];
                FMAX = i;
            }
        }
        if (Fcomp == 0) {
            check = 1;
            System.out.println("Paradox!");
            return;
        }
        System.out.println("Chosen item: ");
        alternativeArray.alternatives[FMAX].printAlternatives();
        if (decisions[iteration] == 1) {
            G[FMAX] = 1;
            AdjustDecisions = alternativeArray.findBetter(alternativeArray.alternatives[FMAX], class1indexes);
        }
        if (decisions[iteration] == 2) {
            G[FMAX] = 2;
            AdjustDecisions = alternativeArray.findWorse(alternativeArray.alternatives[FMAX], class1indexes);
        }
        for (int i = 0; i < AdjustDecisions.size(); ++i) {
            G[AdjustDecisions.get(i)] = decisions[iteration];
        }
    }

    public boolean checkG() {
        for (int i = 0; i < 48; ++i) {
            if (G[i] == -1) return false;
        }
        return true;
    }

    public void printAllInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println(String.format("%-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s",
                "K1", "K2", "K3", "K4", "K5", "G", "d1", "d2", "p1", "p2", "g1", "g2", "F1", "F2", "F"));
        for (int i = 0; i < 48; ++i) {
            System.out.println(String.format("%-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s",
                    K1[i], K2[i], K3[i], K4[i], K5[i], G[i], d1[i], d2[i], decimalFormat.format(p1[i]), decimalFormat.format(p2[i]),
                    g1[i], g2[i], decimalFormat.format(f1[i]), decimalFormat.format(f2[i]), decimalFormat.format(F[i])));
        }
    }

    public void printResultInfo() {
        System.out.println("K1\tK2\tK3\tK4\tK5\tG");
        for (int i = 0; i < 48; ++i) {
            System.out.println(K1[i] + "\t" + K2[i] + "\t" + K3[i] + "\t" +
                    K4[i] + "\t" + K5[i] + "\t" + G[i]);
        }
    }
}