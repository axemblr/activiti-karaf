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

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.activiti.engine.impl.javax.el.ELContext;
import org.activiti.engine.impl.javax.el.ELResolver;
import org.activiti.spring.ApplicationContextElResolver;
import org.springframework.context.ApplicationContext;

/**
 * Activiti EL Resolver that finds bean references from the spring application
 * contexts in a osgi bundles. Used in evaluating the EL variables in BPMN
 * scripts and the delegate expression evaluation.
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class BpmnElResolver extends ELResolver {
    private static final Logger LOG = Logger.getLogger(BpmnElResolver.class.getName());
    private Map<String, ApplicationContextElResolver> elResolverMap;

    public BpmnElResolver() {
        this.elResolverMap = new TreeMap<String, ApplicationContextElResolver>();
    }

    public void addApplicationContext(ApplicationContext appCtx) {
        LOG.info("Adding application context EL Resolver for " + appCtx.getId());
        this.elResolverMap.put(appCtx.getId(), new ApplicationContextElResolver(appCtx));
    }

    public void removeApplicationContext(ApplicationContext appCtx) {
        LOG.info("Removing application context EL Resolver for " + appCtx.getId());
        this.elResolverMap.remove(appCtx.getId());
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return Object.class;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        return Object.class;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        LOG.info("############# ELResolver getValue called...");
        Object value = null;
        if (base == null) {
            for (ApplicationContextElResolver elResolver : this.elResolverMap.values()) {
                value = elResolver.getValue(context, base, property);
                if (value != null) {
                    break;
                } else {
                    LOG.info("Bean " + property + " Not found");
                }
            }
        }

        return value;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (base == null) {
            String key = (String) property;
            for (ApplicationContextElResolver elResolver : this.elResolverMap.values()) {
                // TODO. check if the bean is in the app context and break;
                // for now. if found it throws exception and hence breaks the
                // loop.
                elResolver.setValue(context, base, property, value);
            }
        }
    }
}
