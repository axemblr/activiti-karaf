package org.activiti.karaf.support;

import org.activiti.engine.impl.form.AbstractFormType;
import org.activiti.spring.SpringProcessEngineConfiguration;

/**
 * Marker class for process engine configuration and also may allow us to add more
 * configuration methods like.
 * 
 * for example, activiti explorer is trying to register user form type which should
 * be done on the engine configuration.  this class may provide utility methods to 
 * allow runtime configuration updates to process engine. 
 *  
 * @author Srinivasan Chikkala
 *
 */
public class ProcessEngineConfig extends SpringProcessEngineConfiguration {

	public ProcessEngineConfig() {
		super();
	}
	
	public void addFormType(AbstractFormType formType) {
		this.getFormTypes().addFormType(formType);
	}
}
