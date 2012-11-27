package org.activiti.karaf.bpmn.webui.platform;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.signavio.platform.core.Platform;
import com.signavio.platform.core.PlatformInstance;

/**
 * Modeler entry point for the webapp in OSGi environment.
 *  
 * This is the entry point for the application. 
 * System configuration is loaded here.
 * 
 * It is implemented as a ServletContextListener.
 * So, this is the first and the last code that is called when starting
 * the server.
 * 
 * see web.xml for its usage. 
 * 
 * @see com.MyEntryPoint.platform.listeners.EntryPoint
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyEntryPoint implements ServletContextListener {
	
	private static final Logger LOG = Logger.getLogger(MyEntryPoint.class.getName());
		
	public void contextDestroyed(ServletContextEvent sce) {
		LOG.info("Destroying platform...");
		Platform.shutdownInstance();
		LOG.info("Done destroying platform!");
	}

	/**
	 * Boot the platform using the default {@link PlatformInstance} implementation
	 * for the servlet container.
	 */
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Initializing platform...");
		Platform.bootInstance(MyPlatformInstanceImpl.class, sce.getServletContext());
		LOG.info("Done initializing platform!");
	}

}
