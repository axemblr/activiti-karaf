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

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.osgi.context.BundleContextAware;

/**
 * Deployer that should be used to controll the activiti artifacts
 * deploy/undeploy from the bundle.
 * 
 * This bean will be added through spring configuration in a osgi bundle that
 * contains the activiti artifacts.
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class BpmnDeployer implements ApplicationContextAware, BundleContextAware, InitializingBean,
        DisposableBean {

    private static final Logger LOG = Logger.getLogger(BpmnDeployer.class.getName());

    private ApplicationContext appCtx;
    private BundleContext bundleCtx;
    private BpmnContext bpmnCtx;

    private List<BpmnArtifact> bpmnArtifacts;

    private boolean startOnDeploy = false;
    private boolean undeployWithBundle = true;

    public BpmnDeployer() {
        this.bpmnArtifacts = new ArrayList<BpmnArtifact>();
    }

    public boolean isStartOnDeploy() {
        return startOnDeploy;
    }

    public void setStartOnDeploy(boolean startOnDeploy) {
        this.startOnDeploy = startOnDeploy;
    }

    public boolean isUndeployWithBundle() {
        return undeployWithBundle;
    }

    public void setUndeployWithBundle(boolean undeployWithBundle) {
        this.undeployWithBundle = undeployWithBundle;
    }

    public Resource getBpmnResource() {
        if (this.bpmnArtifacts.size() > 0) {
            return bpmnArtifacts.get(0).getBpmnXml();
        } else {
            return null;
        }
    }

    public void setBpmnResource(Resource bpmnResource) {
        this.bpmnArtifacts.add(new BpmnArtifact(bpmnResource));
    }
    
    public void setBpmnResources(Resource[] bpmnResources) {
        for (Resource bpmnXml : bpmnResources) {
            setBpmnResource(bpmnXml);
        }
    }
   
    public List<BpmnArtifact> getBpmnArtifacts() {
        return bpmnArtifacts;
    }

    public void setBpmnArtifacts(List<BpmnArtifact> bpmnArtifacts) {
        this.bpmnArtifacts.addAll(bpmnArtifacts);
    }

    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        LOG.info("###### Setting the application context");
        // new Exception().printStackTrace();
        this.appCtx = ac;
    }

    public ApplicationContext getApplicationContext() {
        return this.appCtx;
    }

    public void setBundleContext(BundleContext bundleContext) {
        LOG.info("######## Setting the bundle context");
        // new Exception().printStackTrace();
        this.bundleCtx = bundleContext;
    }

    public BundleContext getBundleContext() {
        return this.bundleCtx;
    }

    public <T> T lookupBean(String name, Class<T> clazz) {
        Object bean = null;
        if (this.appCtx != null) {
            bean = this.appCtx.getBean(name, clazz);
        } else {
            LOG.warning("Application context is NULL");
        }
        return (T) bean;
    }

    public <T> T lookupOSGiService(Class<T> clazz) {
        Object service = null;
        if (this.bundleCtx != null) {
            ServiceReference ref = this.bundleCtx.getServiceReference(clazz.getName());
            if (ref != null) {
                service = this.bundleCtx.getService(ref);
            } else {
                LOG.info("Service Reference is NULL. locating service using service tracker....");
                // start service tracker to lookup the service
                try {
                    // attempt to wait for the service to come up.
                    ServiceTracker tracker = new ServiceTracker(this.bundleCtx, clazz.getName(), null);
                    tracker.open();
                    service = tracker.waitForService(120000);
                    tracker.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // TODO. log warning
            LOG.info("Bundle Context is NULL");
        }
        return (T) service;
    }

    /**
     * should be called only after app, bundle context properties set from
     * spring
     */
    protected final void init() {
        if (this.appCtx == null || this.bundleCtx == null) {
            LOG.warning("Application or Bundle Context NOT initialized. Cannot deploy Bpmn artifacts.");
            return;
        }
        if (this.bpmnCtx == null) {
            // initialize bpmn engine service references
            LOG.info("Looking up Bpmn Context service");
            this.bpmnCtx = lookupOSGiService(BpmnContext.class);
        }
        if (this.bpmnCtx == null) {
            LOG.warning("BPMN Context is not initialized. Cannot deploy bpmn artifacts");
        }
    }

    private void registerResolvers() {
        if (this.appCtx != null) {
            this.bpmnCtx.getBpmnExpressionManager().addApplicationContext(this.appCtx);
            this.bpmnCtx.getBpmnScriptResolverFactory().addApplicationContext(this.appCtx);
        } else {
            LOG.info("Can not register Resolvers - AppContext is NULL");
        }
    }

    private void unregisterResolvers() {
        if (this.appCtx != null) {
            this.bpmnCtx.getBpmnExpressionManager().removeApplicationContext(this.appCtx);
            this.bpmnCtx.getBpmnScriptResolverFactory().removeApplicationContext(this.appCtx);
        } else {
            LOG.info("Can not unregister Resolvers - AppContext is NULL");
        }
    }

    private String getDeploymentName() {
    	String deploymentName = this.getBundleContext().getBundle().getSymbolicName();
    	return deploymentName;
    }
    
    private void deploy() {
        if (isDeployNeeded()) {
            LOG.info("###### deploying bpmn artifacts....");
            for (BpmnArtifact bpmnArtifact : this.bpmnArtifacts) {
                deployBpmnArtifacts(bpmnArtifact);
            }
        }
    }

    private boolean isDeployNeeded() {
        boolean deploy = false;
        String deploymentName = getDeploymentName();
        long lastModified = this.bundleCtx.getBundle().getLastModified();
        LOG.info("Bundle last modified " + new Date(lastModified));
        RepositoryService repositoryService = this.bpmnCtx.getProcessEngine().getRepositoryService();
        List<Deployment> depList = repositoryService.createDeploymentQuery().deploymentName(deploymentName)
                                .orderByDeploymenTime().desc().list();
        if (depList.size() == 0) {
            // no. deployments. deploy now
            deploy = true;
        } else {
            Deployment deployment = depList.get(0); // last deployment.
            long lastDeployed = deployment.getDeploymentTime().getTime();
            LOG.info("BPMN Artifacts last deployed " + new Date(lastDeployed));
            deploy = (lastModified > lastDeployed);
        }
        return deploy;
    }
    
//    private DeploymentBuilder createDeploymentBuilder() {
//    	String deploymentName = getDeploymentName();
//        RepositoryService repositoryService = this.bpmnCtx.getProcessEngine().getRepositoryService();
//        DeploymentBuilder deployBldr = repositoryService.createDeployment().name(deploymentName);
//        return deployBldr;
//    }
//    private void closeBpmnResourceInputStreams(List<InputStream> isList) {
//    	for (InputStream is : isList) {
//    		try {
//				is.close();
//			} catch (IOException e) {
//				//Ignore. // e.printStackTrace();
//			}
//    	}
//    }
//    
//	private List<InputStream> addBpmnArtifactResources(
//			DeploymentBuilder deployBldr, BpmnArtifact bpmnArtifact)
//			throws IOException {
//		List<InputStream> isList = new ArrayList<InputStream>();
//		try {
//			Resource bpmnResource = bpmnArtifact.getBpmnXml();
//			InputStream is = bpmnResource.getInputStream();
//			String resourceName = bpmnResource.getURI().toString();
//			LOG.info("Deploying BPMN XML..... " + resourceName);
//			deployBldr.addInputStream(resourceName, is);
//			return isList;
//		} catch (IOException ex) {
//			closeBpmnResourceInputStreams(isList);
//			throw ex;
//		}
//	}

    public void deployBpmnArtifacts(BpmnArtifact bpmnArtifact) {

        try {
            String bundleLoc = this.getBundleContext().getBundle().getLocation();
            String deploymentName = getDeploymentName();
            
            String infoMsg = MessageFormat.format("Deploying BPMN Artifacts from={0}[{1}]",
                    new Object[] { deploymentName, bundleLoc});
            LOG.info(infoMsg);
            
            // System.out.println("Deploying BPMN Artifacts from " +
            // deploymentName);
            //TODO. find and deploy the bpmn image (.png) also.
            Resource bpmnResource = bpmnArtifact.getBpmnXml();
            InputStream is = bpmnResource.getInputStream();
            String resourceName = bpmnResource.getURI().toString();
            LOG.info("Deploying BPMN XML..... " + resourceName);

            RepositoryService repositoryService = this.bpmnCtx.getProcessEngine().getRepositoryService();
            DeploymentBuilder deployBldr = repositoryService.createDeployment().name(deploymentName);

            deployBldr.addInputStream(resourceName, is);

            Deployment deployment = deployBldr.deploy();
            
            String dId = deployment.getId();
            String dName = deployment.getName();
            String dTime = deployment.getDeploymentTime().toString();

            // use one msg formatter to print to both log and console.
            infoMsg = MessageFormat.format("Process Deployment info= {0}:{1}[{2}]",
                    new Object[] { dId, dName, dTime });
            LOG.log(Level.INFO, infoMsg);
            
            // System.out.printf("Process Deployment info= %s:%s[%s] \n", dId,
            // dName, dTime);

            ProcessDefinition pDef = repositoryService.createProcessDefinitionQuery()
                                     .deploymentId(dId).singleResult();
            String pId = pDef.getId();
            String pName = pDef.getName();
            
            infoMsg = MessageFormat.format("Deployed Process Definition: id={0}, name={1}]",
                    new Object[] { pId, pName });
            LOG.log(Level.INFO, infoMsg);
            
            Boolean start = bpmnArtifact.isStartOnDeploy(); // get artifact choice
            if (start == null) {
                start = this.isStartOnDeploy(); // get the deployers choice
            }
            if (start != null && start) {
                RuntimeService runtimeService = this.bpmnCtx.getProcessEngine().getRuntimeService();
                LOG.info("Starting process instnace " + pId);
                // System.out.println("Starting process instanace " + pId);
                runtimeService.startProcessInstanceById(pId);
            }

        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    private void undeploy() {
        // TODO use bundle tracker to track the bundle uninstall event and then
        // call this undeploy from there.
        int bundleState = this.getBundleContext().getBundle().getState();
        LOG.info("Bundle state on undeploy bpmn artifacts  " + bundleState);
        if (bundleState != Bundle.UNINSTALLED) {
            LOG.info("##### Invalid bundle state for undeployment of bpmn artifacts");
            return;
        }
        // ready to undeploy
        String deploymentName = getDeploymentName();
        RepositoryService repositoryService = this.bpmnCtx.getProcessEngine().getRepositoryService();
        List<Deployment> depList = repositoryService.createDeploymentQuery().deploymentName(deploymentName)
                                .orderByDeploymenTime().desc().list();
        for (Deployment dep : depList) {
            String deploymentID = dep.getId();
            repositoryService.deleteDeployment(deploymentID);
            LOG.info("Undeployed " + deploymentID);
        }
    }

    public void destroy() throws Exception {
        // TODO use bundle tracker to track the bundle uninstall event and then
        // call this undeploy from there.
        LOG.info("############## Destroying the BpmnDeployer bean");
        if (this.isUndeployWithBundle()) {
            undeploy(); // try undeploying.
        }
        unregisterResolvers();
    }

    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        LOG.info("############## initializing BpmnDeployer bean");
        init();
        registerResolvers();
        deploy();
    }

}
