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

package ${package};

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;

/**
 *
 * @author ${user.name}
 */
public class Main {

	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngineConfiguration
			.createStandaloneInMemProcessEngineConfiguration()
			.buildProcessEngine();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		repositoryService.createDeployment()
			.addClasspathResource("OSGI-INF/activiti/${artifactId}.bpmn20.xml")
			.deploy();
		
		runtimeService.startProcessInstanceByKey("${artifactId}");
	}
}
