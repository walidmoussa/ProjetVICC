/*
 * Cette class represente l observer antiaffinity
 */
package fr.unice.vicc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerHost;


public class AntiAffinityObserver extends SimEntity {

        /** The custom event id, must be unique. */
    public static final int OBSERVEAntiAffinity = 111111;
  
    private List<PowerHost> hosts;
    private List<Vm[]> coLocatedVm ;
    //private float delay;
    public static final float AA_DELAY = 1;
    //constructeurs
    public AntiAffinityObserver(List<PowerHost> hosts) {
        super("AntiAffinityObserver");
        this.hosts = hosts;
        // initialisation de colocated vm 
        coLocatedVm = new ArrayList<Vm[]>();
             
    }
   
    // methodes
    public List<Vm[]> getCoLocatedVm() {
        return coLocatedVm;
    }

    public void setCoLocatedVm(List<Vm[]> coLocatedVm) {
        this.coLocatedVm = coLocatedVm;
    }
    
    
        @Override
    public void processEvent(SimEvent ev) {
        
        switch(ev.getTag()) {
            case OBSERVEAntiAffinity: 
                 Log.printLine("event received antiAffinity");
               detectAffinity();
               send(this.getId(), AA_DELAY, OBSERVEAntiAffinity, null);
        }
    }
        
   public int getCategorie(Vm vm){
    int i = vm.getId()/100;
    return i;
    }
    
    public void detectAffinity(){
        for(PowerHost h : hosts){
        Map<Integer, Vm> vmTable = new HashMap<>(); 
            for(Vm lvm :h.getVmList()){
                if(vmTable.get(getCategorie(lvm))== null){
                    vmTable.put(getCategorie(lvm), lvm);
                    Log.printLine("verification antiAffinity passed , No defection detected for "+lvm.getId());
                    System.out.println("verification antiAffinity passed , No defection detected!");
                }
                else{// dans le cas de detection de colocation on ajoute 
            Vm[] vv = {vmTable.get(getCategorie(lvm)), lvm};
            coLocatedVm.add(vv);
            String Msage = "the VM with id: "+vv[0].getId()+" and IID"+vv[0].getUid()+" is colocated with the VM with id: "+vv[1].getId()+" and IID"+vv[1].getUid();
             Log.printLine(Msage);
             System.err.println(Msage);
            }}
        }

    }


    @Override
    public void shutdownEntity() {
        Log.printLine(getName() + " is shutting down...");
    }

    @Override
    public void startEntity() {
        Log.printLine(getName() + " is starting...");
        send(this.getId(), AA_DELAY, OBSERVEAntiAffinity, null);
    }

    @Override
    public int getId() {
        return OBSERVEAntiAffinity;
    }
    
    
}
