/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{
    
    int A;
    int B;
    CountThread(int a,int b)
    {
      super("my extending thread");
      System.out.println("my thread created" + this);
      this.A=a;
      this.B=b;
      run();
    }
   public void run()
   {
     try
     {
        for (int i=A ;i<B;i++)
        {
           
           System.out.println("Printing the count " + i);
           Thread.sleep(1000);
        }
     }
     catch(InterruptedException e)
     {
        System.out.println("my thread interrupted");
     }
     System.out.println("My thread run is over" );
   }
}
//class ExtendingExample
//{
//   public static void main(String args[])
//   {
//      CountThread cnt = new CountThread();
//      try
//      {
//         while(cnt.isAlive())
//         {
//           System.out.println("Main thread will be alive till the child thread is live");
//           Thread.sleep(1500);
//         }
//      }
//      catch(InterruptedException e)
//      {
//        System.out.println("Main thread interrupted");
//      }
//      System.out.println("Main thread's run is over" );
//   }
//}
