package com.gmail.myAlgorithm;


public class Alternative {
    public double[] attributes;

    public void assignAlternativeValue(double[] values) {
        attributes = values;
    }

    public int compareAlternatives(Alternative alternative) {
        int[] evaluationArray = new int[attributes.length];
        int result = 5;
        int count1 = 0;
        int countm1 = 0;
        for (int i = 0; i < attributes.length; ++i) {
            if (attributes[i] > alternative.attributes[i]) {
                evaluationArray[i] = 1;
            }
            if (attributes[i] == alternative.attributes[i]) {
                evaluationArray[i] = 0;
            }
            if (attributes[i] < alternative.attributes[i]) {
                evaluationArray[i] = -1;
            }
        }
        for (int i = 0; i < evaluationArray.length; ++i) {
            if (evaluationArray[i] == 1)
                ++count1;
            if (evaluationArray[i] == -1)
                ++countm1;
        }
        if (count1 > 0 && countm1 == 0)
            result = 1;
        if (count1 > 0 && countm1 > 0)
            result = 0;
        if (count1 == 0 && countm1 > 0)
            result = -1;
        return result;
    }

    public double[] plusOne(double[] alternativeOptions) {
        double[] newAttributes = new double[attributes.length];
        for (int i = alternativeOptions.length - 1; i >= 0; --i) {
            if (i != alternativeOptions.length - 1) {
                newAttributes[i + 1] = 1.0;
            }
            if (attributes[i] < alternativeOptions[i]) {
                newAttributes[i] = attributes[i] + 1.0;
                for (int j = i - 1; j >= 0; --j) {
                    newAttributes[j] = attributes[j];
                }
                break;
            } else {
                newAttributes[i] = attributes[i];
            }
        }
        return newAttributes;
    }

    public void printAlternatives() {
        for (int i = 0; i < attributes.length; ++i) {
            if (i == 0)
                System.out.print("( ");
            System.out.print("k" + (i + 1) + attributes[i] + " ");
            if (i == attributes.length - 1)
                System.out.print(") ");
        }
    }
}
