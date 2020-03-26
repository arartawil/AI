package com.company;

public class XOR
{
    private double  [] x1={1};
    private double [] x2={1};
    private double [] yt={2};
    private double [] yact=new double[4];
    private double e=1;
    private double w13=0.5
            ,w14=0.9
            ,w23=0.4
            ,w24= 1.0
            ,w35=-1.2
            ,w45=1.1,th3=0.8,th4=-0.1,th5=-0.3,w16=0.5,w26=0.6,th6=0.2,w65=0.5,w17=0.5,w27=-0.2,th7=0.1,w75=0.2;
    private int epoch=0;
    private  double  y3,y4,y5,l3,l4,l5,y6,l6,y7,l7;

    private boolean flag=false;
    private double leanrate=0.1;

    public XOR()
    {
        startLeran();
    }

    private void startLeran()
    {
     while (Math.abs(e) >= 0.1)
     {
         epoch++;
         System.out.println(epoch + " "+ e + " "+ yact[0]);
         for(int i=0;i<1;i++)
         {
            y3=sig(x1[i]*w13,x2[i]*w23,th3);
            y4=sig(x1[i]*w14,x2[i]*w24,th4);
            y6=sig(x1[i]*w16,x2[i]*w26,th6);
            y7=sig(x1[i]*w17,x2[i]*w27,th7);
            y5=sig(y3*w35,y4*w45,th5);
            yact[i]=y5;
            e=yt[i]-y5;

            l5=e*devsig(y5);
            l3=l5*w35*devsig(y3);
            l4=l5*w45*devsig(y4);
            l6=l5*w65*devsig(y6);
            l7=l5*w75*devsig(y7);

            modifywigth(x1[i],x2[i]);

         }
     }

          for(int i=0;i<1;i++)
            System.out.println(yact[i]);

        System.out.println(w13 +" "+w14+" "+w24+" "+ w23+" "+w45+" "+w35+" "+w65+" "+w16+" "+w26+" " +" "+th4+" "+th3+" "+th5+" "+th6);


    }

    private void modifywigth(double x1,double x2)
    {
        double dw35=(leanrate*l5*y3);
        double dw45=(leanrate*l5*y4);
        double dth5=(leanrate*l5*-1);

        double dw13=(leanrate*l3*x1);
        double dw23=(leanrate*l3*x2);
        double dth3=(-1*l3*leanrate);

        double dw14=(leanrate*l4*x1);
        double dw24=(leanrate*l4*x2);
        double dth4=(-1*leanrate*l4);

        double dw16=(leanrate*l6*x1);
        double dw26=(leanrate*l6*x2);
        double dth6=(-1*leanrate*l6);

        double dw17=(leanrate*l7*x1);
        double dw27=(leanrate*l7*x2);
        double dth7=(-1*leanrate*l7);

        w35=w35+dw35;
        w45=w45+dw45;
        th5=dth5+th5;

        w13+=dw13;
        w23+=dw23;
        th3+=dth3;

        w14+=dw14;
        w24+=dw24;
        th4+=dth4;

        w16+=dw16;
        w26+=dw26;
        th6+=dth6;

        w17+=dw17;
        w27+=dw27;
        th7+=dth7;

    }

    private double devsig(double y)
    {
        return  y*(1-y);

    }
    private double sig(double num1,double num2,double th)
    {
        double nu=num1+num2 - th;
        double n=1 / (1 + Math.exp(-nu));
        return n;
    }


    private double sig(double y )
    {
        return 1 / (1 + Math.exp(-y));
    }
}
