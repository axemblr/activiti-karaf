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

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Srinivasan Chikkala
 */
public class KarafActivitiEngineTest {

    public KarafActivitiEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    protected InputStream getResourceAsStream(String resourcePath) throws IOException {
        return this.getClass().getResourceAsStream(resourcePath);
    }

    /**
     * Test of getReviewInfo method, of class PRRQClient.
     */
    @Test
    public void testArtiBpmnEngine() throws Exception {
    	System.out.println("TODO: implmenet this test");
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}