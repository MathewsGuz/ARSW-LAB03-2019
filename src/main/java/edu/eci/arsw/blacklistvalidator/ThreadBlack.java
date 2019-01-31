/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 *
 * @author 2108263
 */
public class ThreadBlack extends Thread{
    int ini,fin;
    String addres;
    int ocurrencesCount=0;

   

    public int getOcurrencesCount() {
        return ocurrencesCount;
    }
     HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();

    
    ThreadBlack(int ini,int fin,String addres)
    {
      System.out.println("my thread created" + this);
      this.ini=ini;
      this.fin=fin;
      this.addres=addres;
      run();      
    }
    
    
   public void run()
   {
       for (int i=ini;i<fin && ocurrencesCount<5;i++){

            if (skds.isInBlackListServer(i, addres)){
//                blackListOcurrences.add(i); 
                ocurrencesCount++;
            }
        }
   }
   
   
   
}
