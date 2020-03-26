package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Genatic {

    double[] rand = new double[24];//4*4
    int[] afterrand = new int[24];
    int[] binary = new int[6];
    float[][] fit = new float[6][2];
    int [][] newg=new int[6][4];
    int stop=0;
    boolean falg2=false;
    boolean falg1=false;

    boolean found=false;
    int sum=0;

    ArrayList<Integer> selectValue = new ArrayList<Integer>();
    ArrayList<String> selectNoYes = new ArrayList<String>();

    ArrayList<Float> cdfLL = new ArrayList<Float>();
    ArrayList<Float> cdfUl = new ArrayList<Float>();

    ArrayList<Character> temp1=new ArrayList<Character>();
    ArrayList<Character> temp2=new ArrayList<Character>();
    int start=0;
    ArrayList<Character> tem=new ArrayList<Character>();

    public Genatic() {
        while (!found) {
            start();
        }

        System.out.println("Done");
    }



    public void start() {
        cdfLL.add(0.0f);
        for (int i = 0; i < 24; i++) {

            rand[i] = Math.random();
            if (rand[i] > 0.5)
                afterrand[i] = (int) Math.ceil(rand[i]);
            else
                afterrand[i] = (int) Math.floor(rand[i]);
        }

        toBinary();
        calFit();
        calCdf();
        select();
        corssOver();
        mutation();
        validresult();
    }

    private void validresult()
    {
        for (int i=0;i<6;i++)
        {
            for(int j=0;j<4;j++)
                System.out.print(newg[i][j]);

            System.out.println();
        }
        String data="";
        int x=0;
        for(int i=0;i<6;i++)
        {
            x=-1;
            data="";
            for(int j=0;j<4;j++) {
               data+= Integer.parseInt(String.valueOf(newg[i][j]),2);
            }
           // System.out.println();
             x=Integer.parseInt(data,2);
             int result=3*(x*x)-3*x-6;
             if(result==0) {
                 System.out.println(x);
                 found=true;
             }

        }
    }


    private void mutation()
    {
        float randomnum=0;
        int change=0,index=0;
        boolean f=true;
        for(int i=0;i<6;i++)
        {
            if(Math.random()==0.001)
            {
                index=i;
                f=false;
            }
        }
        if(!f) {
            randomnum = (float) Math.random();
            if (randomnum > 0 && randomnum <= 0.25)
                change = 0;
            else if (randomnum > 0.25 && randomnum <= 0.5)
                change = 1;
            else if (randomnum > 0.5 && randomnum <= 0.75)
                change = 2;
            else if (randomnum > 0.75 && randomnum <= 0.99)
                change = 3;

            int[] temp = newg[index];
            if (temp[change] == 0)
                temp[change] = 1;
            else
                temp[change] = 0;
            newg[index] = temp;
        }

    }

    private void corssOver() {
        int count=0;
        stop=0;
        String ch1="";
        String ch2="";
        for (int i = 0; i < 3; i++) {

            falg2=false;falg1=false;
            //System.out.println(selectValue.get(count)+" "+" "+selectValue.get(count+1)+" " );

            if (selectNoYes.get(i).equals("Yes"))
            {
                temp1=new ArrayList<>();
                temp2=new ArrayList<>();
                tem=new ArrayList<>();

                ch1=printBinaryFormat(selectValue.get(count));
                ch2=printBinaryFormat(selectValue.get(count+1));

                for(int j=0;j<4;j++)
                {
                 temp1.add(ch1.charAt(j));
                 temp2.add(ch2.charAt(j));
                }

                //System.out.println(temp1 +" "+ temp2);
                float randsize=(float)Math.random();
                if(randsize>0 && randsize<=0.33)
                  start=1;
                else if(randsize > 0.33 && randsize<=0.66)
                    start=2;
                else  if(randsize > 0.66 && randsize<=0.99)
                    start=3;

                for(int j=0;j<start;j++)
                {
                    tem.add(temp2.get(j));
                    temp2.set(j,temp1.get(j));
                }

                for(int j=0;j<start;j++)
                {
                 temp1.set(j,tem.get(j));
                }

                //System.out.println(temp1+" "+temp2+" "+ start+" "+randsize);

           }
           else
            {
                temp1=new ArrayList<>();
                temp2=new ArrayList<>();
                ch1=printBinaryFormat(selectValue.get(count));
                ch2=printBinaryFormat(selectValue.get(count+1));

                while(!falg2)
                {
                    for(int n=0;n<4;n++)
                    {
                        newg[stop][n]=Character.getNumericValue(ch1.charAt(n));
                    }
                    for(int n=0;n<4;n++)
                    {
                        newg[stop+1][n]=Character.getNumericValue(ch2.charAt(n));
                    }
                    falg2=true;
                }
                stop+=2;
            }

            count+=2;
            if(temp2.size()!=0 && temp1.size()!=0)
            {
                while(!falg1){
                    for (int n = 0; n < 4; n++) {
                        newg[stop][n] = Character.getNumericValue(temp1.get(n));
                    }
                    for (int n = 0; n < 4; n++) {
                        newg[stop + 1][n] = Character.getNumericValue(temp2.get(n));
                    }

                    falg1=true;
                }
                stop += 2;

            }
        }



    }

    private void select() {
        int x = 6 / 2;//divid the size on 2;
        int x1 = 0, x2 = 0;
        float rand1 = 0, rand2 = 0, randCoss = 0;
        int count = 0;
        for (int i = 0; i < 3; i++) {
            rand1 = (float) Math.random();
            rand2 = (float) Math.random();
            x1 = validrand(rand1);
            x2 = validrand(rand2);
            while (x1 == -1) {
                x1 = validrand(rand1);
            }
            while (x2 == -1) {
                x2 = validrand(rand1);
            }
            selectValue.add(x1);
            selectValue.add(x2);
            randCoss = (float) Math.random();
            if (randCoss > 0.5)
                selectNoYes.add("Yes");
            else
                selectNoYes.add("No");
           System.out.println(x1 + " " + x2 + " " + randCoss + " " + selectNoYes.get(i));

        }
    }

    private int validrand(float x) {
        for (int i = 0; i < 6; i++) {
            if (x > cdfLL.get(i) && x <= cdfUl.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private void calCdf() {

        for (int i = 0; i < 6; i++) {
            cdfUl.add((float) cdfLL.get(i) + fit[i][1]);
            cdfLL.add((float) cdfUl.get(i));
        }
    }

    private void calFit() {
        sum=0;
        fit = new float[6][2];

        DecimalFormat df = new DecimalFormat("#.##");


        for (int i = 0; i < 6; i++) {
            fit[i][0]=(3*(binary[i]*binary[i])-3*binary[i]-6);
            //fit[i][0]=(binary[i]*binary[i]-binary[i]-1);
           // fit[i][0] = (15 * binary[i] - binary[i] * binary[i]);
                sum =(int)(sum +fit[i][0]);
            System.out.println(binary[i]+" "+fit[i][0]+" "+sum);
        }

        for (int i = 0; i < 6; i++) {

            //System.out.println(fit[i][0]+" "+sum);

            fit[i][1] = Float.parseFloat(df.format(fit[i][0] / sum));
        }

    }

    private void toBinary() {
        String temp = "";
        int count = 0;
        for (int i = 0; i < 24; i += 4) {
            temp = "";
            for (int j = 0; j < 4; j++) {
                temp += String.valueOf(afterrand[j + i]);
            }
            binary[count] = Integer.parseInt(temp, 2);
            count++;
        }
    }

    public String printBinaryFormat(int number){
        int binary[] = new int[25];
        String data="";
        int index = 0;
        while(number > 0){
            binary[index++] = number%2;
            number = number/2;
        }
        for(int i = 3;i >= 0;i--){
            data+=binary[i];
        }
           return data;
        }
}
