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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.script.ScriptEngineManager;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.scripting.BeansResolverFactory;
import org.activiti.engine.impl.scripting.ResolverFactory;
import org.activiti.engine.impl.scripting.VariableScopeResolverFactory;
import org.activiti.osgi.blueprint.BundleDelegatingClassLoader;
import org.activiti.osgi.blueprint.ClassLoaderWrapper;
import org.activiti.osgi.blueprint.ProcessEngineFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

/**
 * BPMN Engine factory that combines the spring configuration and osgi
 * configuration to initialize the bpmn engine
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class BpmnEngineFactoryBean extends ProcessEngineFactoryBean implements BundleContextAware {
    private static final Logger LOG = Logger.getLogger(BpmnEngineFactoryBean.class.getName());
    private BundleContext bundleCtx;
    private Bundle bundle;
    private BpmnExpressionManager expMgr;
    private BpmnScriptResolverFactory resolverFactory;

    @Override
    public ProcessEngine getObject() throws Exception {

        ClassLoader previous = Thread.currentThread().getContextClassLoader();

        try {
            ClassLoader cl = new BundleDelegatingClassLoader(bundle);
            ClassLoader wrapperCL = new ClassLoaderWrapper(
                    cl,
                    BpmnEngineFactoryBean.class.getClassLoader(),
                    ProcessEngineFactory.class.getClassLoader(),
                    ProcessEngineConfiguration.class.getClassLoader(),
                    previous
                    );

            Thread.currentThread().setContextClassLoader(wrapperCL);
            getProcessEngineConfiguration().setClassLoader(wrapperCL);

            initializeResolverFactories();
            
            ProcessEngine pe = super.getObject();
            registerGroovyScriptEngine(wrapperCL);
            return pe;

        } finally {
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleCtx = bundleContext;
        if (this.bundle == null) {
            this.bundle = this.bundleCtx.getBundle();
        }
    }

    public BundleContext getBundleContext() {
        return this.bundleCtx;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public BpmnExpressionManager getBpmnExpressionManager() {
        return this.expMgr;
    }

    public void setBpmnExpressionManager(BpmnExpressionManager expMgr) {
        this.expMgr = expMgr;
    }

    @Override
    protected void initializeExpressionManager() {
        // TODO Auto-generated method stub
        if (this.expMgr == null) {
            LOG.info("BpmnExpressionManager is NULL");
            this.expMgr = new BpmnExpressionManager();
        }
        if (applicationContext != null) {
            this.expMgr.addApplicationContext(applicationContext);
        }
        processEngineConfiguration.setExpressionManager(this.expMgr);
    }

    public BpmnScriptResolverFactory getBmpmScriptResolverFactory() {
        return this.resolverFactory;
    }

    public void setBpmnScriptResolverFactory(BpmnScriptResolverFactory resolverFactory) {
        this.resolverFactory = resolverFactory;
    }

    protected void initializeResolverFactories() {
        List<ResolverFactory> resolverFactories = new ArrayList<ResolverFactory>();
        resolverFactories.add(new VariableScopeResolverFactory());
        resolverFactories.add(this.resolverFactory);
        resolverFactories.add(new BeansResolverFactory());
        processEngineConfiguration.setResolverFactories(resolverFactories);
    }
    
    
    protected void registerGroovyScriptEngine(ClassLoader wrapperCL) {
    	
//    	ScriptEngineFactory gFactory = new GroovyScriptEngineFactory() {
//
//			@Override
//			public String getEngineName() {
//				return "groovy";
//			}
//    		
//    	};
//    	processEngineConfiguration.getScriptingEngines().addScriptEngineFactory(gFactory);
    	
    	
//    	this.groovySEMgr = new ScriptEngineManager(wrapperCL);
//    	ScriptEngineFactory seFactory = new GroovyScriptEngineFactory();
//    	String name1 = "groovy";
//    	String name2 = "Groovy";
//    	String extension = "groovy";
//    	String type = "application/x-groovy";
//    	this.groovySEMgr.registerEngineName(name1, seFactory);
//    	this.groovySEMgr.registerEngineName(name1, seFactory);
//    	this.groovySEMgr.registerEngineExtension(extension, seFactory);
//    	this.groovySEMgr.registerEngineMimeType(type, seFactory);
    }
}
