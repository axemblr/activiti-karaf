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

package org.activiti.karaf.support;

import org.springframework.core.io.Resource;
/**
 * bean to collect the bpmn artifact deployments
 * 
 * @author Srinivasan Chikkala
 *
 */
public class BpmnArtifact {
    private Resource bpmnXml;
    private Boolean startOnDeploy;
    private Boolean undeployWithBundle;
        
    public BpmnArtifact() {
        super();
    }
    public BpmnArtifact(Resource bpmnXml) {
        this(bpmnXml, null, null); // let the deployer decide.
    }
    public BpmnArtifact(Resource bpmnXml, Boolean startOnDeploy, Boolean undeployWithBundle) {
        super();
        this.bpmnXml = bpmnXml;
        this.startOnDeploy = startOnDeploy;
        this.undeployWithBundle = undeployWithBundle;
    }
    
    public Resource getBpmnXml() {
        return bpmnXml;
    }
    public void setBpmnXml(Resource bpmnXml) {
        this.bpmnXml = bpmnXml;
    }
    public Boolean isStartOnDeploy() {
        return startOnDeploy;
    }
    public void setStartOnDeploy(Boolean startOnDeploy) {
        this.startOnDeploy = startOnDeploy;
    }
    public Boolean isUndeployWithBundle() {
        return undeployWithBundle;
    }
    public void setUndeployWithBundle(Boolean undeployWithBundle) {
        this.undeployWithBundle = undeployWithBundle;
    }
    
}
