${artifactId} BPMN Bundle
--------------------------

The project contains artifacts to build a ${artifactId} BPMN bundle 
to develop, test and deploy the bpmn process into Activiti Karaf.

The project source consists of
    * bpmn process definition
    * spring configuration files
    * unit test to test the process using junit

Building and Deploying
----------------------
From the directory that contains this README.txt, Run the following
1. mvn clean install
2. copy the jar file from target directory to the deploy directory
   of the Activiti Karaf
   Note: priour to deploying, you should run the activiti karaf.   
