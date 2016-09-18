package com.tao.jimmy.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class SimulationSwitchListener implements ServletContextListener {
	private static final String SIM_PARAM_NAME = "simulation";
	
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	String param = event.getServletContext().getInitParameter(SIM_PARAM_NAME);
    	Simulation.setSimulation("true".equals(param));
    	System.out.println("SimulationSwitchListener: simulation=" + param);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
        // TODO Auto-generated method stub
    }
	
}
