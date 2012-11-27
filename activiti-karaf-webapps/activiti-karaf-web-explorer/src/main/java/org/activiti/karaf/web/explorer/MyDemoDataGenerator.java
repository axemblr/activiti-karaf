package org.activiti.karaf.web.explorer;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.explorer.demo.DemoDataGenerator;
/**
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyDemoDataGenerator extends DemoDataGenerator {
	@Override
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	
	public void initDemo() {
	    initDemoGroups();
	    initDemoUsers();
	    String deploymentName = "Demo processes";
	      Deployment dep = processEngine.getRepositoryService()
	      .createDeploymentQuery().deploymentName(deploymentName).singleResult();
	    if (dep != null) {
	    	LOGGER.info("Deployment " + deploymentName + " already exists");
	    } else {
	    	this.initProcessDefinitions();
	    }
	}

}
