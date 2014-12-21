package fr.unice.vicc;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fabien Hermenier
 */
public class NaiveVmAllocationPolicy extends VmAllocationPolicy {

    //To track the Host for each Vm. The string is the unique Vm identifier, composed by its id and its userId
    private Map<String, Host> vmTable;
// la class VmAllocationPolicy de cloudsim a besoin comme parametre de constrcteur une list de Host
    public NaiveVmAllocationPolicy(List<? extends Host> list) {
        super(list);
        vmTable = new HashMap<>();
    }

    public Host getHost(Vm vm) {
        // We must recover the Host which hosting Vm
        return this.vmTable.get(vm.getUid());
    }

    public Host getHost(int vmId, int userId) {
        // We must recover the Host which hosting Vm
        return this.vmTable.get(Vm.getUid(userId, vmId));
    }

    public boolean allocateHostForVm(Vm vm, Host host) {
        // on test si le host peut supporter la vm si oui la fct retourn true
        if (host.vmCreate(vm)) {
            //the host is appropriate, we track it
            vmTable.put(vm.getUid(), host);
            return true;
        }
        return false;
    }

    public boolean allocateHostForVm(Vm vm) {
        //parcours l'ensemble des host pour en trouver un qui support la vm qu'on veut alloquer 
        for (Host h : getHostList()) {
            if (h.vmCreate(vm)) {
                //track the host
                vmTable.put(vm.getUid(), h);
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
