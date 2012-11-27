package org.activiti.karaf.web.explorer;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.explorer.form.UserFormType;

/**
 * extending the UserFormType to just use the specific processEngine instead of using
 * ProcessEngines default. Default may not be the one that is initialized in OSGi env.
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyUserFormType extends UserFormType {
	private ProcessEngine processEngine;

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	
	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
	    // Check if user exists
	    if(propertyValue != null) {
	      // TODO: perhaps better wiring mechanism for service
	      long count = this.processEngine
	      .getIdentityService()
	      .createUserQuery()
	      .userId(propertyValue).count();
	      
	      if(count == 0) {
	        throw new ActivitiException("User " + propertyValue + " does not exist");
	      }
	      return propertyValue;
	    }
	    return null;
	}

}
