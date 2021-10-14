package com.douzone.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce)  {
		System.out.println(sce.getServletContext().getInitParameter("contextConfigLocation"));
		System.out.println("Application Started");
	}

    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }

	
}
