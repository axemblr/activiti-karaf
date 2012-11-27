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

import org.w3c.dom.*;
import org.w3c.dom.events.*;

/**
 * This class is there to resolve the OSGi imports by the bundle plugin as the classes used in web.xml and other
 * resoruce files are not visible to the plugin and hence is not adding the osgi imports.
 * 
 * As a workaroud, this class defines a marker classses and or varaibales to inform the bundle plugin to add the 
 * imports (there may have been a proper way to do this in terms of bundle plugin configuration, but I am not aware
 * of it and this works for now)
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyOSGiImports {
	public static void dummyConfig() {
		// throw new ActivitiException("For now, Dummy method to resolve osgi imports");
		org.w3c.dom.ElementTraversal et = null;
		org.w3c.dom.events.CustomEvent ce = null;
	}
}
