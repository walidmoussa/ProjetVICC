package fr.unice.vicc;

import java.util.ArrayList;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AntiAffinityVMAllocationPolicy extends VmAllocationPolicy{
    
    private Map<String, Host> vmTable;
    private Map<String, List<Host>> AvailabilityTable;
    public static int IntervalCategorie = 100;
// constructeur
    public AntiAffinityVMAllocationPolicy( List<? extends Host> list) {
        super(list);
        this.vmTable =  new HashMap<>();
        this.AvailabilityTable = new HashMap<>();//new ArrayList<MyObject>();
    }
    @Override
      public Host getHost(Vm vm) {
        // We must recover the Host which hosting Vm
        return this.vmTable.get(vm.getUid());
    }

    @Override
    public Host getHost(int vmId, int userId) {
        // We must recover the Host which hosting Vm
        return this.vmTable.get(Vm.getUid(userId, vmId));
    }
    public String getCategorie(Vm vm){
    int i = vm.getId()/100;
    
    return Integer.toString(i);
    }
    
    
    public List<Host> getAvailableHosts(Vm vm){
        if(this.AvailabilityTable.get(getCategorie(vm))==null){// si cette cetegorie de Vm n'a jamais ete alloquee
         return getHostList();
        }
        else{
          return  this.AvailabilityTable.get(getCategorie(vm));
        }      
                } 
    public void setAvailableHosts(Vm vm){
        List <Host>  available = new ArrayList<>();
     if(this.AvailabilityTable.get(getCategorie(vm))==null){
          available.addAll(getHostList());
           available.remove(getHost(vm));
           this.AvailabilityTable.put(getCategorie(vm), available);
           //System.out.println("Vm number "+getCategorie(vm)+" has N available"+available.size());
           
     }
     else{
         available = this.AvailabilityTable.get(getCategorie(vm));
          available.remove(getHost(vm));
          this.AvailabilityTable.remove(getCategorie(vm));
          this.AvailabilityTable.put(getCategorie(vm), available);
          //System.out.println("Vm number "+getCategorie(vm)+" has N available"+available.size()+"total hosts"+getHostList().size());
     }

    }

    @Override
    public boolean allocateHostForVm(Vm vm, Host host) {
        // on test si le host peut supporter la vm si oui la fct retourn true
        if (host.vmCreate(vm)) {
            //the host is appropriate, we track it
            vmTable.put(vm.getUid(), host);
            return true;
        }
        return false;
    }


    @Override
    public boolean allocateHostForVm(Vm vm) {
        
        //parcours l'ensemble des host VALIDE pour en trouver un qui support la vm qu'on veut alloquer 
        for (Host h : getAvailableHosts(vm)) {
            if (h.vmCreate(vm)) {
                //track the host
                vmTable.put(vm.getUid(), h);
                setAvailableHosts(vm);
                return true;
            }
        }
        return false;
    }

    public void deallocateHostForVm(Vm vm,Host host) {
        vmTable.remove(vm.getUid());
        host.vmDestroy(vm);// pour annuler l'allocation de la vm sur un host 
    }

    @Override
    public void deallocateHostForVm(Vm v) {
        //get the host and remove the vm
        vmTable.get(v.getUid()).vmDestroy(v);
    }

    public static Object optimizeAllocation() {
        return null;
    }

    @Override
    public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> arg0) {
        //Static scheduling, no migration, return null;
        return null;
    }
    
    
    
}
