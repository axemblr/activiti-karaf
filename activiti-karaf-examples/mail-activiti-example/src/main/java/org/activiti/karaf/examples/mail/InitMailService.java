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

package org.activiti.karaf.examples.mail;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * 
 * @author Srinivasan Chikkala
 *
 */
public class InitMailService implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
    	
        ActivityExecution ae = (ActivityExecution) execution;
        System.out.printf("\n*** Executing Service Task... Execution Id:[%s] Activity: %s[%s]\n",
                ae.getId(), ae.getActivity().getId(), ae.getActivity().getProperty("name"));

        String from = "ordershipping@activiti.org";
        boolean male = true;
        String recipientName = "John Doe";
        String recipient = "johndoe@activiti.org";
        Date now = new Date();
        String orderId = "123456";
        
        execution.setVariable("sender", from);
        execution.setVariable("recipient", recipient);
        execution.setVariable("recipientName", recipientName);
        execution.setVariable("male", male);
        execution.setVariable("now", now);
        execution.setVariable("orderId", orderId);
        
    }
}
