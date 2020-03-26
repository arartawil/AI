package com.company;

public class NUor
{
    private int [] x1= {0,0,1,1};
    private int [] x2= {0,1,0,1};
    private int [] yd= {0,1,1,1};
    private int [] erroe=new int[4];
    private int [] yact =new int[4];
    private double w1=0.3;
    private double w2=-0.1;
    private double lean=0.1;
    private int count=0;
    private boolean falg=false;
    public NUor()
    {
        startNu();
    }

    private void startNu()
    {
        while (!falg)
        {
            for (int i=0;i<4;i++)
            {
                double res=w1*x1[i] + w2*x2[i] -0.2;
                if(res >= 0)
                    yact[i]=1;
                else
                    yact[i]=0;

                erroe[i]=yd[i]-yact[i];

                modifyWigth(erroe[i], x1[i],x2[i]);
            }
            showError();

        }

        for (int i=0;i<4;i++)
        {
            System.out.println(erroe[i]+" "+yact[i]);
        }
        System.out.println(w1 +" "+w2);
    }

    private void showError()
    {
        count=0;
        for(int i=0;i<4;i++)
        {

          if(erroe[i]!=1 && erroe[i]!=-1)count++;

        }
        if (count==4)falg=true;

    }

    private void modifyWigth(int error,int x1,int x2)
    {
        if(error == 1 || error == -1)
        {
            if(x1 == 1)
            {
                double  y =x1*lean*error;
                w1+=y;

            }
             if(x2 == 1)
            {
                double  y =x2*lean*error;
                w2+=y;
            }
        }
    }


}
