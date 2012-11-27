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

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.scripting.Resolver;
import org.activiti.engine.impl.scripting.ResolverFactory;
import org.springframework.context.ApplicationContext;
/**
 * Script resolver that resolves the script (groovy) variables to the beans in the spring application 
 * context in the osgi bundles - registered through BPMN deployer spring configuration.
 * 
 * @author Srinivasan Chikkala
 *
 */
public class BpmnScriptResolverFactory implements ResolverFactory, Resolver {
    private static final Logger LOG = Logger.getLogger(BpmnScriptResolverFactory.class.getName());
    private Map<String, ApplicationContext> appCtxMap;

    public BpmnScriptResolverFactory() {
        this.appCtxMap = new TreeMap<String, ApplicationContext>();
    }

    public void addApplicationContext(ApplicationContext appCtx) {
        LOG.info("Adding application context to Resolver factory for " + appCtx.getId());
        this.appCtxMap.put(appCtx.getId(), appCtx);
    }

    public void removeApplicationContext(ApplicationContext appCtx) {
        LOG.info("Removing application context to Resolver factory for " + appCtx.getId());
        this.appCtxMap.remove(appCtx.getId());
    }

    @Override
    public boolean containsKey(Object key) {
        for (ApplicationContext appCtx : this.appCtxMap.values()) {
            if (appCtx.containsBean((String)key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {       
        for (ApplicationContext appCtx : this.appCtxMap.values()) {
            Object bean = appCtx.getBean((String)key);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public Resolver createResolver(VariableScope variableScope) {
        // TODO Auto-generated method stub
        return this;
    }

}
