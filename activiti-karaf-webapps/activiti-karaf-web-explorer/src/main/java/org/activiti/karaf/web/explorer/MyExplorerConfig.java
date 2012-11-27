package org.activiti.karaf.web.explorer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.explorer.demo.DemoDataGenerator;
import org.activiti.explorer.form.UserFormType;
import org.activiti.karaf.support.BpmnContext;

/**
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyExplorerConfig {
	private static final Logger LOG = Logger.getLogger(MyExplorerConfig.class.getName());
	private BpmnContext bpmnContext;
	private UserFormType userFormType;
	private boolean demoOn = true;
	
	public BpmnContext getBpmnContext() {
		return bpmnContext;
	}

	public void setBpmnContext(BpmnContext bpmnContext) {
		this.bpmnContext = bpmnContext;
	}

	public UserFormType getUserFormType() {
		return userFormType;
	}

	public void setUserFormType(UserFormType userFormType) {
		this.userFormType = userFormType;
	}
	
	public boolean isDemoOn() {
		return demoOn;
	}

	public void setDemoOn(boolean demoOn) {
		this.demoOn = demoOn;
	}

	public void init() {
		
		try {
			// set user form
			this.bpmnContext.getProcessEngineConfig().addFormType(this.userFormType);
			// init demo deployment here.
			if (this.isDemoOn()) {
				MyDemoDataGenerator demo = new MyDemoDataGenerator();
				ProcessEngine pe = this.bpmnContext.getProcessEngine();
				demo.setProcessEngine(pe);
				demo.setIdentityService(pe.getIdentityService());
				demo.initDemo();
			}
		} catch (Exception ex) {
			LOG.log(Level.INFO, "Error initializing explorer demo data ", ex);
		}
	}
		
}
