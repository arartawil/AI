package com.company.SerachingAlgo.CuttleFish;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int Pubulaionsize =500;
        int Dimension = 3;
        double Upper =-10;
        double Lower = 10;
        double Globaloptimization = 1;
        double Stopcondition = 3;
        int Maxiterations = 500;
        double R1 = 1;
        double R2 = -0.5;
        double V1 = 2;
        double V2 = -2;
        Random RandomGenerator  = new Random();

        CFA cf = new CFA(Pubulaionsize,
                Dimension,
                Upper,
                Lower,
                Globaloptimization,
                Stopcondition,
                Maxiterations,
                R1,
                R2,
                V1,
                V2,
                RandomGenerator
        );


        cf.run();

        CCells best = cf.GetBestCell();
        double bestFitness = best.Fitness;

        int lastIteration = cf.GetLastIteration();
        int nofe = cf.GetNoOfFunctionEvaluation();

        String bestPoints = "";
        for (int j = 0; j < best.Points.length; j++)
            bestPoints += best.Points[j] + " , ";

        String s = "";
        if (cf.SUCCESS)
            s = " Global optimum is <<  found  >>" +"\n";
        else
            s = " Global optimum is <<  Not found  >>" + "\n";

        s += " the algorithm stopt at Iteraion: " + lastIteration + "\n";
        s += " Best Fitness = " + bestFitness + "\n";
        s += " Best Points = {" + bestPoints + "}" +"\n";
        s += " Number of function evaluation = " + nofe;
        System.out.println();
        System.out.println(s);


    }
}
