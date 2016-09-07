package com.dcca.aspen.samples.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;







import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
//import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
//import org.kie.services.client.api.RemoteRestRuntimeEngineFactoryBuilderImpl;
//import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;

public class BPMService {

	//private RuntimeEngine engine;
	
	
	private String USERNAME="test";
	private String PASSWORD="Password1)1)";
	// RemoteRuntimeEngine engine ;
	RuntimeEngine engine;
	private static BPMService instance = null;
	
	public static BPMService getInstance() {
	     instance=null;
	         instance = new BPMService();
	      
	      return instance;
	   }
	
	private BPMService()  {
		System.out.println("Inside constructor");
		try {
			 URL SERVER_URL= new URL("http://192.168.90.9:8080/business-central/");
			
		//	 RemoteRestRuntimeFactory restSessionFactory 
	     //       = new RemoteRestRuntimeFactory("Enrollments:AspenTest:1.0", SERVER_URL, USERNAME, PASSWORD);
			 
	      //   engine = restSessionFactory.newRuntimeEngine();
	         
			 
			
			engine = RemoteRuntimeEngineFactory.newRestBuilder()	
			        .addDeploymentId("Enrollments:AspenTest:1.0").addUserName(USERNAME)
			        .addPassword(PASSWORD).addUrl(SERVER_URL).build();
	
		//auditService = engine.getAuditService();
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	public void start855Process(String userName) {
		 KieSession ksession;
		 TaskService taskService;
		 AuditService auditService;
		taskService = engine.getTaskService();
		ksession = engine.getKieSession();
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("validnpi ", "N123456");
	    params.put("providername", "test1");
	  //  ksession.startProcess ( PROCESS_ID, params);
		System.out.println("Inside start855Process");
		ProcessInstance processInstance= ksession.startProcess("AspenTest.AspenDemoProcess",params);
		System.out.println("Got processInstance:"+processInstance.getId());
	}
	
/*    Map<String, Object> params =  new HashMap<>();
    params.put(T_APPROVAL_VAR, approve);
    taskService.claim(taskId, USERNAME);
    taskService.start(taskId, USERNAME);
    taskService.complete(taskId, USERNAME, params);
    */
}

