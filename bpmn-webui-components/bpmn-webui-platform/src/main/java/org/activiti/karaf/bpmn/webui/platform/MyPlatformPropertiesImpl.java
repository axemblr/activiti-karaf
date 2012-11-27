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
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.signavio.platform.core.PlatformProperties;

/**
 * Platform Properties impl independent of the configuration properties file
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class MyPlatformPropertiesImpl implements PlatformProperties {
	private static final Logger LOG = Logger
			.getLogger(MyPlatformPropertiesImpl.class.getName());

	/** this is not actually serverName. it is "http://{host}:{port}" */
	private String serverName;
	private String platformUri;
	private String explorerUri;
	private String editorUri;
	private String libsUri;
	private String supportedBrowserEditorRegExp;
	private String rootDirectoryPath;
	private Set<String> admins;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPlatformUri() {
		return platformUri;
	}

	public void setPlatformUri(String platformUri) {
		this.platformUri = platformUri;
	}

	public String getExplorerUri() {
		return explorerUri;
	}

	public void setExplorerUri(String explorerUri) {
		this.explorerUri = explorerUri;
	}

	public String getEditorUri() {
		return editorUri;
	}

	public void setEditorUri(String editorUri) {
		this.editorUri = editorUri;
	}

	public String getLibsUri() {
		return libsUri;
	}

	public void setLibsUri(String libsUri) {
		this.libsUri = libsUri;
	}

	public String getSupportedBrowserEditorRegExp() {
		return supportedBrowserEditorRegExp;
	}

	public void setSupportedBrowserEditorRegExp(
			String supportedBrowserEditorRegExp) {
		this.supportedBrowserEditorRegExp = supportedBrowserEditorRegExp;
	}

	public String getRootDirectoryPath() {
		return rootDirectoryPath;
	}

	public void setRootDirectoryPath(String rootDirectoryPath) {
		this.rootDirectoryPath = rootDirectoryPath;
	}

	public Set<String> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<String> admins) {
		this.admins = admins;
	}

	
	@Override
	public String toString() {
		return "MyPlatformPropertiesImpl [serverName=" + serverName
				+ ", platformUri=" + platformUri + ", explorerUri="
				+ explorerUri + ", editorUri=" + editorUri + ", libsUri="
				+ libsUri + ", supportedBrowserEditorRegExp="
				+ supportedBrowserEditorRegExp + ", rootDirectoryPath="
				+ rootDirectoryPath + ", admins=" + admins + "]";
	}

	private static String createValidDirectoryPath(String repoRoot) {
		String dirPath = null;
		try {
			File dir = new File(repoRoot);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			dirPath = dir.getCanonicalPath().replace("\\", "/");
			if (dirPath.endsWith("/")) {
				dirPath = dirPath.substring(0, dirPath.length()-1);
			}
		} catch (Exception ex) {
			LOG.log(Level.INFO, "Unable to validate/create a Repository root "
					+ repoRoot, ex);
		}
		LOG.info("### Activiti Modeler Repo ROOT " + dirPath);
		return dirPath;
	}
	
	public static PlatformProperties newInstance(ServletContext context) {
		return newInstance(context, System.getProperties());
	}

	/**
	 * TODO: initialize completely from the properties object. so that it is
	 * independent of servletContext
	 * 
	 * @param context
	 * @return
	 */
	public static PlatformProperties newInstance(ServletContext context, Properties configProps) {
		MyPlatformPropertiesImpl impl = new MyPlatformPropertiesImpl();

		impl.setPlatformUri(context.getContextPath() + "/p");
		impl.setExplorerUri(context.getContextPath() + "/explorer");
		impl.setEditorUri(context.getContextPath() + "/editor");
		impl.setLibsUri(context.getContextPath() + "/libs");
		
		impl.setSupportedBrowserEditorRegExp(context.getInitParameter("supportedBrowserEditor"));

		impl.setServerName(configProps.getProperty("activiti.modeler.url.root",
				"http://localhost:8181"));

		String repoRoot = configProps.getProperty("activiti.modeler.repo.root", "");
		repoRoot = repoRoot.trim();
		if (repoRoot.isEmpty()) {
			LOG.info("## No system property found for modeler repo root... using default root");
			// get the root as {user.home}/activiti-repo
			try {
				repoRoot = (new File(System.getProperty("user.home"),
						"activiti-repo")).getCanonicalPath();
			} catch (Exception ex) {
				repoRoot = System.getProperty("user.home");
			}
		}
		impl.setRootDirectoryPath(createValidDirectoryPath(repoRoot));
		LOG.info(impl.toString());
		return impl;
	}

}
