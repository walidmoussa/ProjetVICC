package fr.unice.vicc;

import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class VmAllocationPolicyFactory {
// c'est la methode dans laquelle on cree des policy en utilisant un ensemble de hosts
    VmAllocationPolicy make(String id, List<PowerHost> hosts) {
        switch (id) {
            case "naive":  return new NaiveVmAllocationPolicy(hosts);
            case "antiAffinity": return new AntiAffinityVMAllocationPolicy(hosts);
            case "balance": return new BalanceVmAllocationPolicy(hosts);
        }
        throw new IllegalArgumentException("No such policy '" + id + "'");
    }
}
