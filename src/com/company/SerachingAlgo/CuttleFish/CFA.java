package com.company.SerachingAlgo.CuttleFish;

import java.util.Arrays;
import java.util.Random;

public class CFA
{
    private CCells [] cells =null;
    private CCells Best_Cells=null;
    private int Populiation_Size;
    private int Dimension;
    private double upperlimte;
    private double lowerlimite;
    private double GlobOptization;
    private double stop_Condition;
    private int no_of_itr;
    private double R_1;
    private double R_2;
    private double V_1;
    private double V_2;
    public boolean SUCCESS;

    int NOFE;
    private int lastitr;
    private Random generater=null;

    public CFA(int pubulaionsize,
               int dimension,
               double upper,
               double lower,
               double globaloptimization,
               double stopcondition,
               int iterations,
               double r1,
               double r2,
               double v1,
               double v2,
               Random rgenerator

    )
    {
        this.Populiation_Size = pubulaionsize;
        this.Dimension = dimension;
        this.upperlimte = upper;
        this.lowerlimite = lower;
        this.GlobOptization= globaloptimization;
        this.stop_Condition = stopcondition;
        this.no_of_itr = iterations;
        this.R_1 = r1;
        this.R_2 = r2;
        this.V_1 = v1;
        this.V_2= v2;
        this.generater = rgenerator;
        ////////////////
        this.lastitr = 0;
        this.NOFE = 0;
        this.SUCCESS = false;
    }
    public void run()
    {
        Best_Cells=new CCells();
        Best_Cells.Points=new double[Dimension];
        Best_Cells.Fitness=0;


        this.cells=new CCells[this.Populiation_Size];

        for (int i = 0; i <this.Populiation_Size ; i++) {

            cells[i] = new CCells();
            cells[i].Points = new double[this.Dimension];

            for (int j = 0; j < this.Dimension; j++)
            {
                cells[i].Points[j] = this.random(this.upperlimte, this.lowerlimite);
            }
            cells[i].Fitness = calculateFitness(cells[i].Points);


            if (   Best_Cells.Fitness< cells[i].Fitness)
            {
                Best_Cells.Fitness = cells[i].Fitness;
                System.arraycopy(cells[i].Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

             }

        }

        int siz = this.Populiation_Size / 4;
        CCells[] firstGroup = new CCells[siz];
        CCells[] secondGroup = new CCells[siz];
        CCells[] therdGroup = new CCells[siz];
        CCells[] forthGroup = new CCells[this.Populiation_Size - siz * 3];

        int L = 0;
        for (int i = 0; i < siz; i++)
        {
            firstGroup[i] = new CCells();
            firstGroup[i] = cells[L];
            L++;

        }
        for (int i = 0; i < siz; i++)
        {
            secondGroup[i] = new CCells();
            secondGroup[i] = cells[L];
            L++;

        }
        for (int i = 0; i < siz; i++)
        {
            therdGroup[i] = new CCells();
            therdGroup[i] = cells[L];
            L++;

        }
        for (int i = 0; i < this.Populiation_Size - siz * 3; i++)
        {
            forthGroup[i] = new CCells();
            forthGroup[i] = cells[L];
            L++;
        }

        double f = 0;
        NOFE = 0;
        this.SUCCESS = false;

        for (int itr = 1; itr <= no_of_itr; itr++)//main loop
        {
            double result = 0;

            if (Best_Cells.Fitness >= this.GlobOptization)
                result = Best_Cells.Fitness - this.GlobOptization;
            else
                result = this.GlobOptization - Best_Cells.Fitness;

            if (result == 2 || result==3)
            {
                this.SUCCESS = true;
                break;
            }

            double[] temp_Points = new double[this.Dimension];

            double refliction = 0;
            double visibility = 0;
            //cells =  sort(cells);
            double av = 0;
            for (int k = 0; k < this.Dimension; k++)
                av += Best_Cells.Points[k];
            av = av / this.Dimension;

            for (int i = 0; i < firstGroup.length; i++)
            {
                for (int j = 0; j < this.Dimension; j++)
                {

                    refliction = random(R_1, R_2) * firstGroup[i].Points[j];


                    visibility = (Best_Cells.Points[j] - firstGroup[i].Points[j]);


                    temp_Points[j] = refliction + visibility;

                    if (temp_Points[j] > this.upperlimte) temp_Points[j] = this.upperlimte;
                    if (temp_Points[j] < this.lowerlimite) temp_Points[j] = this.lowerlimite;

                }

                f = calculateFitness(temp_Points);
                NOFE++;
                if (Best_Cells.Fitness < f)
                {
                    Best_Cells.Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
                if (f < firstGroup[i].Fitness)
                {
                    firstGroup[i].Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
            }

            for (int i = 0; i < secondGroup.length; i++)
            {

                for (int j = 0; j < this.Dimension; j++)
                {
                    refliction = Best_Cells.Points[j];
                    visibility = random(V_1, V_2) * (Best_Cells.Points[j] - secondGroup[i].Points[j]);
                    temp_Points[j] = refliction + visibility;
                    if (temp_Points[j] > this.upperlimte) temp_Points[j] = this.upperlimte;
                    if (temp_Points[j] < this.lowerlimite) temp_Points[j] = this.lowerlimite;
                }

                f = calculateFitness(temp_Points);
                NOFE++;
                if (Best_Cells.Fitness < f)
                {
                    Best_Cells.Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
                if (f < secondGroup[i].Fitness)
                {
                    secondGroup[i].Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
            }


            for (int i = 0; i < therdGroup.length; i++)
            {
                for (int j = 0; j < this.Dimension; j++)
                {
                    refliction = Best_Cells.Points[j];
                    visibility = random(V_1, V_2) * (Best_Cells.Points[j] - av);
                    temp_Points[j] = refliction + visibility;
                    if (temp_Points[j] > this.upperlimte) temp_Points[j] = this.upperlimte;
                    if (temp_Points[j] < this.lowerlimite) temp_Points[j] = this.lowerlimite;
                }
                f = calculateFitness(temp_Points);
                NOFE++;
                if (Best_Cells.Fitness < f)
                {
                    Best_Cells.Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
                if (f < therdGroup[i].Fitness)
                {
                    therdGroup[i].Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);


                }
            }

            for (int i = 0; i < forthGroup.length; i++)
            {
                for (int j = 0; j < this.Dimension; j++)
                {
                    refliction = random(this.upperlimte, this.lowerlimite);
                    visibility = 0;

                    temp_Points[j] = refliction + visibility;

                    if (temp_Points[j] > this.upperlimte) temp_Points[j] = this.upperlimte;
                    if (temp_Points[j] < this.lowerlimite) temp_Points[j] = this.lowerlimite;
                }

                f = calculateFitness(temp_Points);
                NOFE++;
                if (Best_Cells.Fitness < f)
                {
                    Best_Cells.Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
                if (f < forthGroup[i].Fitness)
                {
                    forthGroup[i].Fitness = f;
                    System.arraycopy(temp_Points, 0, Best_Cells.Points, 0,Best_Cells.Points.length);

                }
            }

            lastitr = itr;


        }



    }

    private int random(double high, double low)
    {
        double range = high - low;
        double fraction = range * this.generater.nextDouble() + low;
        return ((int)fraction);
    }

    private int calculateFitness(double [] XD)
    {
        int count=0;
        for(int i=0;i<XD.length;i++)
        {
            double result=(Math.pow(XD[i],2)-6*XD[i] + 8);
            if(result==0)count++;
        }


        return  count;

        /*double[] ai = new double[25];
        double[] bi = new double[25];
        for (int i = 0; i < 25; i++)
        {
            ai[i] = 16 * ((i % 5) - 2);
            bi[i] = 16 * ((i / 5) - 2);
        }

        double sum = 0;
        for (int j = 0; j < 25; j++)
        {
            sum += (1 / (1 + j + Math.pow(XD[0] - ai[j], 6) + Math.pow(XD[1] - bi[j], 6)));
        }
        double z = 1 / 500 + sum;
        z = Math.pow(z, -1);
        return z;
        */


    }

    public int GetLastIteration()
    {
        return this.lastitr;
    }
    public CCells GetBestCell()
    {
        return this.Best_Cells;
    }
    public int GetNoOfFunctionEvaluation()
    {
        return this.NOFE;
    }


}
