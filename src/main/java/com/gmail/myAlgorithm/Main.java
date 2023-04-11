package com.gmail.myAlgorithm;

public class Main {
    public static void main(String[] args) {
        int[] alternativeOptions = {2, 2, 2, 2, 3};
        AlternativeArray alternativeArray = new AlternativeArray();
        alternativeArray.assignSize(alternativeOptions);
        alternativeArray.fillArray(alternativeOptions);
        alternativeArray.printArray();
        Classifier classifier = new Classifier();
        classifier.fillK(alternativeArray);
        classifier.fillG1();
        Integer check = 0;
        for (int i = 0; i < 15; ++i) {
            if (!classifier.checkG()) {
                System.out.println("\nIteration #" + (i + 1));
                classifier.determineCenters(alternativeArray);
                classifier.findD();
                classifier.findP();
                classifier.findG(alternativeArray);
                classifier.findF();
                classifier.printAllInfo();
                classifier.fillG(i, alternativeArray, check);
            }
            if (check == 1) break;
        }
        System.out.println("Result:");
        classifier.printResultInfo();
    }
}

