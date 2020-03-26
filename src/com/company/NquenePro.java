package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NquenePro {
    private int[][] pop = new int[6][4];
    private int [][] newG=new int[6][4];
    private int[][] ChessBord = new int[4][4];
    private float[][] pdf = new float[6][2];
    private int[] locx = new int[4];
    private int[] locy = new int[4];
    private Random rand = new Random();
    int pdfc=0;
    float sumPdf=0;
    int result=0;
    boolean pdfT=true;
    boolean finsh=true;
    int epock=0;
    ArrayList<Float> cdfLL=new ArrayList<>();
    ArrayList<Float>cdfUl=new ArrayList<>();

    int [] selectValue=new int[6];
    int []selectYesNo=new int[6];
    public NquenePro() {
        Createpop();
        while (finsh) {
            start();
            epock++;
        }
        System.out.println(epock);
    }

    public void start()
    {
        cdfLL.add((float) 0);
        inCheese();//insert location Q in Cheese Borad
        prePdf();
        calCdf();
        select();
        crosseover();
        mutation();
        vlaidation();

        for(int i=0;i<6;i++){
            for(int j=0;j<4;j++)
            {
                pop[i][j]=newG[i][j];
            }

        }

        pdf=new float[6][4];
        pdfc=0;

    }

    private void mutation()
    {
        float randomnum = 0;
        int change = 0, index = 0;
        boolean f = true;
        for (int i = 0; i < 6; i++) {
            if (Math.random() >=0.4) {
                index = i;
                f = false;
            }
        }
        if (!f) {
            randomnum = (float) Math.random();
            if (randomnum > 0 && randomnum <= 0.25)
                change = 0;
            else if (randomnum > 0.25 && randomnum <= 0.5)
                change = 1;
            else if (randomnum > 0.5 && randomnum <= 0.75)
                change = 2;
            else if (randomnum > 0.75 && randomnum <= 0.99)
                change = 3;

            int[] temp = newG[index];

            temp[change] = rand.nextInt((3 - 0) + 1) + 0;;
            newG[index] = temp;
        }
    }
    private  void vlaidation()
    {
        inCheeseNewG();


    }

    private void crosseover()
    {


        ArrayList<Integer>temp1=new ArrayList<>();
        ArrayList<Integer>temp2=new ArrayList<>();
        ArrayList<Integer>tem=new ArrayList<>();
        float rand=0;
        int start=0;
        int numberofLine=0;
        for(int i=0;i<3;i++)
        {
            temp1.clear();
            temp2.clear();
            tem.clear();
            int x1=selectValue[i],x2=selectValue[i+1];
            int result=selectYesNo[i];
            if(result==1)
            {
                for (int j = 0; j < 4; j++) {
                    temp1.add(pop[x1][j]);
                    temp2.add(pop[x2][j]);
                }

                rand = (float) Math.random();
                if (rand > 0 && rand <= 0.33)
                    start = 1;
                else if (rand > 0.33 && rand <= 0.66)
                    start = 2;
                else if (rand > 0.66 && rand <= 0.99)
                    start = 3;


                int co=3;
                for(int j=0;j<start;j++)
                {
                    tem.add(temp2.get(j));
                }

                for(int j=0;j<start;j++)
                {
                    temp2.set(co,temp1.get(j));
                    co--;
                }

               for(int j=0;j<start;j++)
                {
                    temp1.set(j,tem.get(j));
                }


                for(int m=0;m<4;m++)
                {
                    newG[numberofLine][m]=temp1.get(m);
                    newG[numberofLine+1][m]=temp2.get(m);
                }
                numberofLine+=2;

            }
            else
                {
                    x1=selectValue[i];x2=selectValue[i+1];

                    for (int j = 0; j < 4; j++) {
                            temp1.add(pop[x1][j]);
                            temp2.add(pop[x2][j]);
                        }
                    for(int m=0;m<4;m++)
                    {
                        newG[numberofLine][m]=temp1.get(m);

                        newG[numberofLine+1][m]=temp2.get(m);
                    }
                    numberofLine+=2;

                }
        }

    }

    private void select()
    {
        float rand1=0;
        float rand2=0;
        float randCross=0;
       int x1,x2;
        int count=0;
        for(int i=0;i<3;i++)
        {
            rand1=(float)Math.random();
            rand2=(float)Math.random();
            x1 = selectCdf(rand1);
            x2 = selectCdf(rand2);
            List sV= Arrays.asList(selectValue);

            while (x1==x2) {
                while (x1 == -1)
                {
                    x1 = selectCdf(rand1);
                }
                while (x2 == -1)
                {
                    x2 = selectCdf(rand2);
                }
                rand1=(float)Math.random();
                rand2=(float)Math.random();
                x1 = selectCdf(rand1);
                x2 = selectCdf(rand2);

            }

            selectValue[count++]=x1;
            selectValue[count]=x2;

            randCross=(float) Math.random();

            if(randCross>=0.7)
            {
                selectYesNo[i]=1;
            }
            else
                selectYesNo[i]=0;
            count++;


        }
    }

    private int selectCdf(float rand1)
    {
        for(int i=0;i<6;i++)
        {
            if(rand1>cdfLL.get(i) && rand1<=cdfUl.get(i))
            {
                return i;
            }
        }
        return -1;
    }

    private void prePdf()
    {
        for(int i=0;i<6;i++)
        {
            pdf[i][1]+=pdf[i][0]/sumPdf;
        }
    }

    private void calCdf()
    {
        for (int i = 0; i < 6; i++) {
            cdfUl.add((float) cdfLL.get(i) + pdf[i][1]);
            cdfLL.add((float) cdfUl.get(i));
        }
    }

    private void Createpop() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                pop[i][j] = rand.nextInt((3 - 0) + 1) + 0;
              //  System.out.print(pop[i][j]);
            }
           // System.out.println();
        }

    }

    private void inCheese()
    {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            ChessBord = new int[4][4];
            for (int j = 0; j < 4; j++) {
                int x = 3 - pop[i][j];
                ChessBord[x][j] = 1;
            }
            Findlocation();
            calPdfFit();
        }

    }
    private void inCheeseNewG()
    {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            ChessBord = new int[4][4];
            for (int j = 0; j < 4; j++) {
                int x = 3 - newG[i][j];
                ChessBord[x][j] = 1;
            }
            Findlocation();
          result=foundFit();

          if(result==6 )
          {
              finsh=false;
              break;
          }
        }
    }

    private void Findlocation() {
        int x = 0, y = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (ChessBord[i][j] == 1)
                {
                    locy[y] = j;
                    locx[x] = i;
                    y++;
                    x++;
                }
            }
        }
    }

    private void calPdfFit() {

        int fit = foundFit();
        pdf[pdfc][0]=fit;
        sumPdf+=pdf[pdfc][0];
        pdfc++;

    }

    private int foundFit() {
        int fit = 0;
        int count=0;
        int co=0;
        int number=3;
        boolean falg=false;
        int [] temx=new int[16];
        int [] temy=new int[16];
        for(int i=0;i<4;i++)
        {
            temx=new int[16];
            temy=new int[16];
            put(temx,temy);
           co=0;

            count=0;
            int x=locx[i],y=locy[i];

            while(y<=3)
            {
                temx[count]=x;
                temy[count]=++y;
                count++;
            }
            x=locx[i];y=locy[i];
            while (x>=0 && y<=3)
            {
                temx[count]=--x;
                temy[count]=++y;
                count++;
            }

            x=locx[i];y=locy[i];
                while (x>=0 && y>=0)
                {
                    temx[count]=--x;
                    temy[count]=--y;
                    count++;
                }

        x=locx[i];y=locy[i];
        while (x<=3 && y<=3)
        {
            temx[count]=++x;
            temy[count]=++y;
            count++;
        }
        x=locx[i];y=locy[i];
        while (x<=3 && y>=0)
        {
            temx[count]=++x;
            temy[count]=--y;
            count++;
        }

       co=0;
       for(int n=i+1;n<4;n++)
         for(int m=0;m<16;m++)
            {
                if(temx[m]==locx[n] && temy[m]==locy[n])
                {
                 co++;
                }

        }



        if(co==0) {
            fit += number--;

        }
        else if(co>0)
        {
            fit+=(number-- - co);
        }

       }
        //System.out.println(fit);
        return fit;
    }

    private void put(int[] temx, int[] temy)
    {
        for(int i=0;i<16;i++)
        {
            temx[i]=-1;
            temy[i]=-1;
        }
    }

}
