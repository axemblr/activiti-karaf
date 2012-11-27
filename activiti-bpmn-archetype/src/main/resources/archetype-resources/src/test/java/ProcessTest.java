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

package ${package};


import org.junit.Assert;

import java.util.HashMap;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.ibatis.logging.LogFactory;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
*
* @author ${user.name}
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti.cfg.xml")
public class ProcessTest {
	
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    @Rule
    public ActivitiRule activitiSpringRule;
    
    @After
    public void closeProcessEngine() {
        // Required, since all the other tests seem to do a specific drop on the
        // end
        processEngine.close();
    }
        
    private void startProcess(String processKey, HashMap<String, Object> variables) {

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processKey, variables);
        String id = processInstance.getId();
        System.out.println("Started process instance id " + id);
        Assert.assertEquals(0, runtimeService.createProcessInstanceQuery()
                .count());

        HistoricProcessInstance historicProcessInstance = processEngine
                .getHistoryService().createHistoricProcessInstanceQuery()
                .processInstanceId(id).singleResult();
        Assert.assertNotNull(historicProcessInstance);

        System.out.println("Finished, took "
                + historicProcessInstance.getDurationInMillis() + " millis");

    }    
    
    @Test
    @Deployment(resources = { "OSGI-INF/activiti/${artifactId}.bpmn20.xml" })
    public void testHappyPath() {

        HashMap<String, Object> variables = new HashMap<String, Object>();
        startProcess("${artifactId}", variables);
    }    
}
