/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress,int N){
        
        
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int checkedListsCount=0;
        int servers =skds.getRegisteredServersCount();
        int rango = servers/N;
        
        System.out.println(servers);
        System.out.println(rango);
        int inicio=0,fin=rango;
        System.out.println(servers%N);
        
        ArrayList<ThreadBlack> grupohilos= new ArrayList<ThreadBlack>();
                
        for (int hilos=0; hilos<N;hilos++){
            if(hilos==N-1){
                rango+=servers%N;
                fin=inicio+rango;
            }
//            System.out.println(inicio);
//            System.out.println(fin);
            grupohilos.add(new ThreadBlack(inicio, fin, ipaddress));
            inicio=fin;
            fin=inicio+rango;
        }
        
        for(ThreadBlack i:grupohilos){
            i.start();
        }
        
        for(ThreadBlack j:grupohilos){
            try {
                j.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(HostBlackListsValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(ThreadBlack l:grupohilos){
            blackListOcurrences.addAll(l.getBlackListOcur());
            checkedListsCount+=l.getChecked();  
        }

//        for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
//            checkedListsCount++;
//            
//            if (skds.isInBlackListServer(i, ipaddress)){
//                
//                blackListOcurrences.add(i);
//                
//                ocurrencesCount++;
//            }
//        }
        
        if (blackListOcurrences.size()>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
