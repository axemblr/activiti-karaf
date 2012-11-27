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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.signavio.platform.core.HandlerDirectory;
import com.signavio.platform.core.HandlerEntry;
import com.signavio.platform.handler.AbstractHandler;
/**
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyHandlerDirectory extends HandlerDirectory {
	private static final Logger LOG = Logger
	.getLogger(MyHandlerDirectory.class.getName());

	private static final long serialVersionUID = 1L;
	private ServletContext servletContext;

	public MyHandlerDirectory(ServletContext sc) {
		super(sc);
		this.servletContext = sc;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void registerHandlersOfPackage(String packageName) {
		throw new UnsupportedOperationException(
				"trying to use this method to register handler for "
						+ packageName);
	}

	@Override
	public void start() {
		registerMyHandlers();
	}

	@Override
	public void stop() {
		// NOOP
	}

	protected void registerHandler(AbstractHandler handler) {
		try {
			Class<? extends AbstractHandler> cls = handler.getClass();
			// Create a new HandlerEntry with the given classifier
			HandlerEntry he = new HandlerEntry(cls);
			he.setHandlerInstance(handler);
			// Add the classifier to the map
			this.put(cls.getName(), he);
		} catch (Exception ex) {
			LOG.log(Level.INFO, "Unable to register handler ", ex);
		}
	}

	protected void registerMyHandlers() {
//		this.registerHandlersOfPackage("com.signavio.editor.handler");
		registerHandler(new com.signavio.editor.handler.EditorHandler(this.getServletContext()));
		registerHandler(new com.signavio.editor.handler.PluginsHandler(this.getServletContext()));
		
//		this.registerHandlersOfPackage("com.signavio.editor.stencilset.handler");
		registerHandler(new com.signavio.editor.stencilset.handler.SSExtensionsHandler(this.getServletContext()));
		registerHandler(new com.signavio.editor.stencilset.handler.StencilSetHandler(this.getServletContext()));		
		
//		this.registerHandlersOfPackage("com.signavio.explorer.handler");
		registerHandler(new com.signavio.explorer.handler.ExplorerHandler(this.getServletContext()));
		registerHandler(new com.signavio.explorer.handler.PluginsHandler(this.getServletContext()));		
		
//		this.registerHandlersOfPackage("com.signavio.platform.config.handler");
		registerHandler(new com.signavio.platform.config.handler.ConfigHandler(this.getServletContext()));
		
//		this.registerHandlersOfPackage("com.signavio.usermanagement.user.handler");
		registerHandler(new com.signavio.usermanagement.user.handler.InfoHandler(this.getServletContext()));
		registerHandler(new com.signavio.usermanagement.user.handler.UserHandler(this.getServletContext()));	
		
//		this.registerHandlersOfPackage("com.signavio.warehouse.directory.handler");
		registerHandler(new com.signavio.warehouse.directory.handler.InfoHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.directory.handler.DirectoryHandler(this.getServletContext()));	
		registerHandler(new com.signavio.warehouse.directory.handler.ParentDirectoriesHandler(this.getServletContext()));
		
//		this.registerHandlersOfPackage("com.signavio.warehouse.model.handler");
		registerHandler(new com.signavio.warehouse.model.handler.InfoHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.JsonHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.ModelHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.NotificationHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.ParentDirectoriesHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.PngHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.SvgHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.model.handler.SyntaxCheckerHandler(this.getServletContext()));
		
//		this.registerHandlersOfPackage("com.signavio.warehouse.revision.handler");
		registerHandler(new com.signavio.warehouse.revision.handler.InfoHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.revision.handler.JsonHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.revision.handler.PngHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.revision.handler.RevisionHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.revision.handler.SvgHandler(this.getServletContext()));
		registerHandler(new com.signavio.warehouse.revision.handler.ThumbnailHandler(this.getServletContext()));
		
//		this.registerHandlersOfPackage("com.signavio.warehouse.search.handler");
		registerHandler(new com.signavio.warehouse.search.handler.SearchHandler(this.getServletContext()));
		
	}

}
