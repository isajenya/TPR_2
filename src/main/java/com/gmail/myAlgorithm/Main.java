package com.gmail.myAlgorithm;

public class Main {
    public static void main(String[] args) {
        double[] alternativeOptions = {2.0, 2.0, 2.0, 2.0, 3.0};
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
                System.out.println("\nІтерація: №" + (i + 1));
                classifier.determineCenters(alternativeArray);
                classifier.findD();
                classifier.findP();
                classifier.findG(alternativeArray);
                classifier.findF();
                classifier.printAllInfo();
                check = classifier.fillG(i, alternativeArray, check);
            }
            if (check == 1) break;
        }
        System.out.println("\nРезультат:");
        classifier.printResultInfo();
    }
}

