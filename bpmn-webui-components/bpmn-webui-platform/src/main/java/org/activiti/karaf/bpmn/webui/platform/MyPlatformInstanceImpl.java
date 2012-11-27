/*
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
package org.activiti.karaf.bpmn.webui.platform;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import com.signavio.platform.account.business.FsAccountManager;
import com.signavio.platform.core.Directory;
import com.signavio.platform.core.HandlerDirectory;
import com.signavio.platform.core.PlatformInstance;
import com.signavio.platform.core.PlatformProperties;
import com.signavio.platform.exceptions.InitializationException;
import com.signavio.platform.security.business.FsAccessToken;
import com.signavio.platform.security.business.FsRootObject;
import com.signavio.platform.security.business.FsSecurityManager;
import com.signavio.platform.tenant.business.FsTenant;
import com.signavio.platform.tenant.business.FsTenantManager;
import com.signavio.usermanagement.business.FsRoleManager;
import com.signavio.warehouse.business.FsEntityManager;
import com.signavio.warehouse.directory.business.FsDirectory;
import com.signavio.warehouse.directory.business.FsRootDirectory;
import com.signavio.warehouse.model.business.ModelTypeManager;

/**
 * signavio platform instance impl that works with webapp in OSGi platform
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyPlatformInstanceImpl implements PlatformInstance {
	
	private HandlerDirectory handlerManger;
	private ServletContext servletContext;
	private PlatformProperties platformProperties;
	
	public HandlerDirectory getHandlerDirectory() {
		return this.handlerManger;
	}

	public PlatformProperties getPlatformProperties() {
		return this.platformProperties;
	}
	
	public URL getResource(String path) {
		try {
			return this.servletContext.getResource(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void shutdownInstance() {
		// NOOP
		
	}
	
	public void bootInstance(Object... parameters) {
		if (parameters.length < 1 || (parameters.length >= 1 && !(parameters[0] instanceof ServletContext))) {
			throw new InitializationException("Boot of servlet container PlatformInstance failed, because ServletContext parameter is missing.");
		}
		// load configuration
		this.servletContext = (ServletContext) parameters[0];

		this.platformProperties = MyPlatformPropertiesImpl.newInstance(servletContext);
		
		FsRootDirectory.createInstance(this.platformProperties.getRootDirectoryPath());
		ModelTypeManager.createInstance();
		
		this.handlerManger = new MyHandlerDirectory(servletContext);
		this.handlerManger.start();
	
		FsAccessToken token = null;
		try {
			token = FsSecurityManager.createToken("root", "root", null);
		} catch (Exception e) {
			// cannot happen
		}
		FsRootObject root = FsRootObject.getRootObject(token);
		@SuppressWarnings("unused")
		FsAccountManager accountManager = root.getAccountManager();
		FsTenantManager tenantManager = root.getTenantManager();
		
		FsTenant onlyTenant = tenantManager.getChildren(FsTenant.class).iterator().next();
		@SuppressWarnings("unused")
		FsRoleManager roleManagerForTenant = FsRoleManager.getTenantManagerInstance(FsRoleManager.class, onlyTenant, token);
		FsEntityManager entityManagerForTenant = FsEntityManager.getTenantManagerInstance(FsEntityManager.class, onlyTenant, token);
		@SuppressWarnings("unused")
		FsDirectory rootDir = entityManagerForTenant.getTenantRootDirectory();
		
	}	
}
