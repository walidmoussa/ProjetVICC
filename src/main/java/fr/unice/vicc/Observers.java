package fr.unice.vicc;

import java.util.List;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.power.PowerHost;

/**
 * Just a container to declare your home-made observers.
 *
 * @see fr.unice.vicc.PeakPowerObserver for a sample observer
 * @author Fabien Hermenier
 */
public class Observers {

    public static  AntiAffinityObserver OBS;// Observer for AntiAffinity mode 
    
    public void build(List<PowerHost> hosts2, List<Vm> vms) {
      List<PowerHost> hosts = null;
	OBS = new AntiAffinityObserver(hosts);
    OBS.startEntity();
    }
}
