/*
 * Copyright 2012 Cisco Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.karaf.support.internal;

import org.activiti.engine.ProcessEngine;
import org.activiti.karaf.support.BpmnContext;
import org.activiti.karaf.support.BpmnExpressionManager;
import org.activiti.karaf.support.BpmnScriptResolverFactory;
import org.activiti.karaf.support.ProcessEngineConfig;


/**
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class BpmnContextImpl implements BpmnContext {
    private ProcessEngine processEngine;
    private ProcessEngineConfig processEngineConfig;    
    private BpmnExpressionManager bpmnExpressionManager;
    private BpmnScriptResolverFactory bpmnScriptResolverFactory;

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }
    
    public ProcessEngineConfig getProcessEngineConfig() {
		return processEngineConfig;
	}

	public void setProcessEngineConfig(ProcessEngineConfig processEngineConfig) {
		this.processEngineConfig = processEngineConfig;
	}

	public BpmnExpressionManager getBpmnExpressionManager() {
        return bpmnExpressionManager;
    }

    public void setBpmnExpressionManager(BpmnExpressionManager bpmnExpressionManager) {
        this.bpmnExpressionManager = bpmnExpressionManager;
    }

    public BpmnScriptResolverFactory getBpmnScriptResolverFactory() {
        return this.bpmnScriptResolverFactory;
    }

    public void setBpmnScriptResolverFactory(BpmnScriptResolverFactory bpmnScriptResolverFactory) {
        this.bpmnScriptResolverFactory = bpmnScriptResolverFactory;
    }
    
}
