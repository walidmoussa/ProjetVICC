package fr.unice.vicc;

import java.util.List;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerHost;

public class VMantiAffinity {

	/** The event id, must be unique. */
    public static final int OBSERVE = 728078;

    private List<PowerHost> hosts;

    private double peak;

    private float delay;

    public static final float DEFAULT_DELAY = 1;

    public VMantiAffinity(List<PowerHost> hosts) {
        this(hosts, DEFAULT_DELAY);
    }

    public VMantiAffinity(List<PowerHost> hosts, float delay) {
        super();
        this.hosts = hosts;
        this.delay = delay;
    }


    /**
     * Get the datacenter instantaneous power.
     * @return a number in Watts
     */
    private double getPower() {
        double p = 0;
        for (PowerHost h : hosts) {
            p += h.getPower();
        }
        return p;
    }

    public void processEvent(SimEvent ev) {
        //I received an event
        switch(ev.getTag()) {
            case OBSERVE:
                //I must observe the datacenter
                double cur = getPower();
                if (cur > peak) {
                    peak = cur;
                }
                //Observation loop, re-observe in `delay` seconds
                send(this.getId(), delay, OBSERVE, null);
        }
    }

    private Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Get the peak power consumption.
     * @return a number of Watts
     */
    public double getPeak() {
        return peak;
    }

    public void shutdownEntity() {
        Log.printLine(getName() + " is shutting down...");
    }

    private String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void startEntity() {
        Log.printLine(getName() + " is starting...");
        //I send to myself an event that will be processed in `delay` second by the method
        //`processEvent`
        send(this.getId(), delay, OBSERVE, null);
    }

	private void send(Object id, float delay2, int observe2, Object object) {
		// TODO Auto-generated method stub
		
	}
}
