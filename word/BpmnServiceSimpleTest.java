package test.com.hq.bpmn.test.service.simpleimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.hq.bpmn.common.bean.ProcessResult;
import com.hq.bpmn.exception.BpmnException;
import com.hq.bpmn.processinstance.bean.BpmnBatchCompleteResult;
import com.hq.bpmn.processinstance.bean.BpmnPath;
import com.hq.bpmn.processinstance.bean.BpmnProcess;
import com.hq.bpmn.processinstance.bean.BpmnProcessConstant;
import com.hq.bpmn.processinstance.bean.BpmnTicket;
import com.hq.bpmn.templatedef.bean.BpmnTask;
import com.hq.bpmn.templatedef.simpleservice.BpmnTemplateDefSimpleService;
import com.hq.bpmn.unify.bean.BatchTicketVariable;
import com.hq.bpmn.unify.bean.TicketVariable;
import com.hq.bpmn.unify.service.BpmnService;

public class BpmnServiceSimpleTest {
	@Test
	/**
	 * 批量创建任务
	 */
	public void createBpmnTask() throws Exception {
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContextSimple-bpmn.xml");
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("D:\\IdeaProjects\\simple_21873\\src\\test\\com\\hq\\bpmn\\test\\applicationContextSimple-test.xml");
		BpmnService bpmnService = (BpmnService) applicationContext.getBean("bpmnService");
		System.out.println(bpmnService);
		String userId="a";
		String bpmnType="simple";
		String ticketId="20190306200,20190306201";
		String variable="";
		try
		{
			ProcessResult<List<BpmnTicket>> pr = bpmnService.batchCreateBpmnProcessWithNextTask(userId, bpmnType, ticketId, variable);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	/**
	 * 获取到当前任务节点处理后的信息，可以包括开始和结束时间
	 */
	public void getBpmnBatchCompleteResult() throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("D:\\IdeaProjects\\simple_21873\\src\\test\\com\\hq\\bpmn\\test\\applicationContextSimple-test.xml");
		BpmnService bpmnService = (BpmnService) applicationContext.getBean("bpmnService");
		String userId="c";
		String bpmnType="simple";
		String ticketIds="20190306200,20190306201";
		String bpmnBatchTransition = "pcl";

//		String variable="[{'name':'amt','type':'string','value':'120'}]";
		String variable="";
		try
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask_Reconstruction(userId, bpmnType, ticketIds, bpmnBatchTransition,variable);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSimpleBatchCreateBpmnProcessWithNextTask() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
//		String userId="kermit";
//		String bpmnType="z";
//		//String ticketId="businessKey11,businessKey12";
//		String ticketId="businessKey1";
//		String variable="[{'name':'amt','type':'string','value':'80'}]";
		
        String userId="a";
		String bpmnType="tx1";
		String ticketId="20190306";
		String variable="";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";

//		  String userId="fozzie";
//			String bpmnType="z";
//			String ticketId="businessKey2";
//			String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<BpmnTicket>> pr = bpmnService.batchCreateBpmnProcessWithNextTask(userId, bpmnType, ticketId, variable);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testBatchCompleteBpmnTaskWithNextTask_Reconstruction() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
//		//String userId="kermit";
//		String userId="kermit";
//		String bpmnType="z";
//		//String ticketId="businessKey11,businessKey12";
//		String ticketId="businessKey1";
//		String bpmnBatchTransition = "audit";
//		String variable="[{'name':'amt','type':'string','value':'80'}]";
		
		String userId="fozzie";
		String bpmnType="salary";
		String ticketId="ausinessKey1";
		String bpmnBatchTransition = "audit";
		String variable="[{'name':'amt','type':'string','value':'120'}]";
		
//		String userId="66A31322D87CF43BCF5CE8EEC532FF8C";
//		String bpmnType="039001";
//		String ticketId="1FA3F34701C16ABCA26AD40ECFF0EB8F,698FFFF539B1EFFBD853035DBD2AD257";
//		String bpmnBatchTransition = "sendaudit";
//		String variable=null;


		try 
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask_Reconstruction(userId, bpmnType, ticketId, bpmnBatchTransition, variable);
			List<BpmnBatchCompleteResult> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnBatchCompleteResult bpmnBatchCompleteResult : list) {
				System.out.println("bpmnBatchCompleteResult:" + bpmnBatchCompleteResult.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSimpleBatchCreateBpmnProcessWithNextTaskPayPlan() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
//		String bpmnType="payPlan";
//		String ticketId="businessKey1";
		String bpmnType="salary";
		String ticketId="businessKey1,businessKey1";
		String variable="[{'name':'amount','type':'string','value':'80'},{'name':'bpmnComment','type':'string','value':'test comment'}]";
		
//        String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<BpmnTicket>> pr = bpmnService.batchCreateBpmnProcessWithNextTask(userId, bpmnType, ticketId, variable);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBatchCompleteBpmnTaskWithNextTask_ReconstructionWithComment() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		//String userId="kermit";
		String userId="fozzie";
//		String bpmnType="z";
//		String ticketId="businessKey1";
		String bpmnType="salary";
		String ticketId="ausinessKey1,ausinessKey2";
		String variable="[{'name':'amt','type':'string','value':'80'},{'name':'bpmnComment','type':'string','value':'test comment'}]";
		
		//String bpmnBatchTransition = "audit";
		String bpmnBatchTransition = "back";
		//String bpmnBatchTransition = null;
		
//		String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8,businessKey9";
//		String bpmnBatchTransition = "audit";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";

		try 
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask_Reconstruction(userId, bpmnType, ticketId, bpmnBatchTransition, variable);
			List<BpmnBatchCompleteResult> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnBatchCompleteResult bpmnBatchCompleteResult : list) {
				System.out.println("bpmnBatchCompleteResult:" + bpmnBatchCompleteResult.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testBatchCompleteBpmnTaskWithNextTask_ReconstructionWithBack() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String userId="kermit";
//		String bpmnType="payPlan";
//		String ticketId="businessKey1";
		String bpmnType="salary";
		String ticketId="ausinessKey1,ausinessKey2";
		String variable="[{'name':'amt','type':'string','value':'80'},{'name':'bpmnComment','type':'string','value':'test comment'}]";
		
		String bpmnBatchTransition = BpmnProcessConstant.BPMN_BATCH_TRANSATION_BACK;
		
//		String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8,businessKey9";
//		String bpmnBatchTransition = "audit";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";

		try 
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask_Reconstruction(userId, bpmnType, ticketId, bpmnBatchTransition, variable);
			List<BpmnBatchCompleteResult> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnBatchCompleteResult bpmnBatchCompleteResult : list) {
				System.out.println("bpmnBatchCompleteResult:" + bpmnBatchCompleteResult.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void testBatchCompleteBpmnTaskWithNextTaskPretend() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String userId="kermit";
		String bpmnType="salary";
		String ticketId="ausinessKey1,ausinessKey2";
		//String ticketId="businessKey1";
		String bpmnBatchTransition = "audit";
		//String variable="[{'name':'amt','type':'string','value':'80'}]";
		String variable="[{'name':'amt','type':'string','value':'120'},{'name':'bpmnComment','type':'string','value':'test comment'}]";
		
//		String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8,businessKey9";
//		String bpmnBatchTransition = "audit";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";

		try 
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask_Pretend(userId, bpmnType, ticketId, bpmnBatchTransition, variable);
			List<BpmnBatchCompleteResult> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnBatchCompleteResult bpmnBatchCompleteResult : list) {
				System.out.println("bpmnBatchCompleteResult:" + bpmnBatchCompleteResult.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryAllNodeInfoByTypeOfLayerSequence() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String bpmnType="z";

		try 
		{
			ProcessResult<List<BpmnTask>> pr = bpmnService.queryAllNodeInfoByTypeOfLayerSequence(bpmnType);
			List<BpmnTask> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnTask bpmnTask : list) {
				System.out.println("bpmnTask:" + bpmnTask.toString());
				System.out.println("bpmnTask:" + bpmnTask.getKey());
				System.out.println("Description:" + bpmnTask.getDescription());
				System.out.println("Name:" + bpmnTask.getName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryAllProcessHistoryPathNeiMeng() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String bpmnType1="CB30D23E6C7EA2B4E11A738EF165F01D_1500_2018";
		String ticketId1="324E3AD94BF057462218FEE9331CD1B2";
		String bpmnType2="BDF6F0FA25429D1243EC5BEC2CA4F239_1500_2018";
		//026C7ADAF23A5A991EB1FB3B12AE5AB7_1500_2018
		//String ticketId2="ausinessKey2";
		String ticketId2=null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		//Map<String, Object> map1 = null;
		Map<String, Object> map1= new HashMap<String, Object>();
		map1.put("bpmnType", bpmnType1);
		map1.put("ticketId", ticketId1);
		listMap.add(map1);
		
		Map<String, Object> map2= new HashMap<String, Object>();
		map2.put("bpmnType", bpmnType2);
		map2.put("ticketId", ticketId2);
		listMap.add(map2);

		try 
		{
			ProcessResult<List<BpmnPath>> pr = bpmnService.queryAllProcessHistoryPath(listMap);
			List<BpmnPath> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnPath bpmnPath : list) {
				System.out.println("bpmnTask:" + bpmnPath.toString());
				System.out.println("SequenceKey:" + bpmnPath.getSequenceKey());
				System.out.println("Description:" + bpmnPath.getDescription());
				System.out.println("taskName:" + bpmnPath.getTaskName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryAllProcessHistoryPath() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String bpmnType1="salary";
		String ticketId1="ausinessKey1";
		String bpmnType2="z";
		String ticketId2="ausinessKey2";
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		//Map<String, Object> map1 = null;
		Map<String, Object> map1= new HashMap<String, Object>();
		map1.put("bpmnType", bpmnType1);
		map1.put("ticketId", ticketId1);
		listMap.add(map1);
		
		Map<String, Object> map2= new HashMap<String, Object>();
		map2.put("bpmnType", bpmnType2);
		map2.put("ticketId", ticketId2);
		listMap.add(map2);

		try 
		{
			ProcessResult<List<BpmnPath>> pr = bpmnService.queryAllProcessHistoryPath(listMap);
			List<BpmnPath> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnPath bpmnPath : list) {
				System.out.println("bpmnTask:" + bpmnPath.toString());
				System.out.println("SequenceKey:" + bpmnPath.getSequenceKey());
				System.out.println("Description:" + bpmnPath.getDescription());
				System.out.println("taskName:" + bpmnPath.getTaskName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryAllProcessHistoryPathSingle() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String bpmnType1="salary";
		String ticketId1="ausinessKey1";
		String bpmnType2="z";
		//String ticketId2="ausinessKey2";
		String ticketId2=null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		//Map<String, Object> map1 = null;
		Map<String, Object> map1= new HashMap<String, Object>();
		map1.put("bpmnType", bpmnType1);
		map1.put("ticketId", ticketId1);
		listMap.add(map1);
		
		Map<String, Object> map2= new HashMap<String, Object>();
		map2.put("bpmnType", bpmnType2);
		map2.put("ticketId", ticketId2);
		listMap.add(map2);

		try 
		{
			ProcessResult<List<BpmnPath>> pr = bpmnService.queryAllProcessHistoryPath(listMap);
			List<BpmnPath> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnPath bpmnPath : list) {
				System.out.println("bpmnTask:" + bpmnPath.toString());
				System.out.println("SequenceKey:" + bpmnPath.getSequenceKey());
				System.out.println("Description:" + bpmnPath.getDescription());
				System.out.println("taskName:" + bpmnPath.getTaskName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testQueryAllProcessHistoryPathNull() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		//List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMap = null;
	
		try 
		{
			ProcessResult<List<BpmnPath>> pr = bpmnService.queryAllProcessHistoryPath(listMap);
			List<BpmnPath> list=  pr.getResult();
			System.out.println(" pr.getCode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
//			for (BpmnPath bpmnPath : list) {
//				System.out.println("bpmnTask:" + bpmnPath.toString());
//				System.out.println("SequenceKey:" + bpmnPath.getSequenceKey());
//				System.out.println("Description:" + bpmnPath.getDescription());
//				System.out.println("taskName:" + bpmnPath.getTaskName());
//			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Test
	public void testQueryBpmnCurrntPath() throws BpmnException{
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		
		String bpmnType1="salary";
		String ticketId1="ausinessKey1";
//		String bpmnType2="z";
//		String ticketId2="ausinessKey3";

		try 
		{
			ProcessResult<List<BpmnPath>> pr = bpmnService.queryBpmnCurrntPath(bpmnType1,ticketId1);
			List<BpmnPath> list=  pr.getResult();
			System.out.println(" pr.getQode():" + pr.getCode());
			System.out.println(" pr.getMessage():" + pr.getMessage());
			System.out.println(" pr.isSuccess():" + pr.isSuccess());
			for (BpmnPath bpmnPath : list) {
				System.out.println("bpmnTask:" + bpmnPath.toString());
				System.out.println("SequenceKey:" + bpmnPath.getSequenceKey());
				System.out.println("Description:" + bpmnPath.getDescription());
				System.out.println("taskName:" + bpmnPath.getTaskName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetBpmnServiceBpmnTypeNull() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		//String bpmnType="payPlan";
		String bpmnType="支付";
		try 
		{
			ProcessResult<String> pr = bpmnService.queryHisReadingTasksByUserSql(userId);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetBpmnServiceBpmnTypeNotNull() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		String bpmnType="z";
		//String bpmnType="支付";
		String ticketId="businessKey1";

		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<BpmnProcess>> pr = bpmnService.queryBpmnProcess(userId, bpmnType, ticketId, variable);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBpmnService() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		//String bpmnType="payPlan";
		String bpmnType="支付";
		String ticketId="businessKey12";
		String ticketIds="businessKey12,businessKey11";
		String destinationKey = "test";
		String bpmnProcess = null;
		String bpmnBatchTransition = "batch";

		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<BpmnBatchCompleteResult>> pr = bpmnService.batchCompleteBpmnTaskWithNextTask(userId, bpmnType, ticketIds, bpmnBatchTransition,variable);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBpmnService1() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		String bpmnType="payPlan";
		//String bpmnType="支付";
		//String bpmnProcess = null;
		List<BatchTicketVariable> batchTicketVariables = new ArrayList<BatchTicketVariable> ();
		
		BatchTicketVariable batchTicketVariable= new BatchTicketVariable();
		String[] tickets = {"businessKey1","businessKey1"};
		List<TicketVariable> ticketVariables = new ArrayList<TicketVariable>();
		ticketVariables.add(createTicketVariable());
		ticketVariables.add(createTicketVariable());
		
		batchTicketVariable.setTickets(tickets);
		batchTicketVariable.setTicketVariables(ticketVariables);
		batchTicketVariables.add(batchTicketVariable);
		

		//String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<BpmnTicket>> pr = bpmnService.batchCreateBpmnTaskWithNextTask(userId, bpmnType, batchTicketVariables);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private TicketVariable createTicketVariable() {
		TicketVariable ticketVariable = new TicketVariable();
		ticketVariable.setName("testName");
		ticketVariable.setType("string");
		ticketVariable.setValue("testValue");
		return ticketVariable;
	}
	
	@Test
	public void testQueryNodeInfoByUserId() throws BpmnException{
		String[] url =getUrl();
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		@SuppressWarnings("resource")
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		//ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		
		String bpmnType = "salary";
		String userId = "kermit"; 
		String ticketId = "ausinessKey1"; 
		
		ProcessResult<List<BpmnTask>> pr = bpmnService.queryNodeInfoByUserId(userId, bpmnType, ticketId);
		System.out.println("pr:"+pr);
		List<BpmnTask> list = pr.getResult();
		for ( BpmnTask bpmnTask:list) {
			System.out.println("name:"+bpmnTask.getName());
			System.out.println("key:"+bpmnTask.getKey());
			System.out.println("bpmnTask:"+bpmnTask);
		}

	}
	
	@Test
	public void testQueryBpmnCurrntPathSimple() throws BpmnException{
		String[] url =getUrl();
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		@SuppressWarnings("resource")
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		//ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		
		String bpmnType = "salary";
		String userId = "kermit"; 
		String ticketId = "ausinessKey1"; 
		
		ProcessResult<List<BpmnPath>> pr = bpmnService.queryBpmnCurrntPath(bpmnType, ticketId);
		System.out.println("pr:"+pr);
		List<BpmnPath> list = pr.getResult();
		for ( BpmnPath bpmnPath:list) {
			System.out.println("id:"+bpmnPath.getTaskId());
			System.out.println("name:"+bpmnPath.getTaskName());
			//System.out.println("bpmnTask:"+bpmnPath);
		}

	}
	
	
	@Test
	public void testQueryNativeWaitingTasksByUserCategory() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		//String bpmnType="支付1";
		//String bpmnType=null;
		//String bpmnType="a";
		String bpmnType="salary,z";
		//String bpmnType="z,支付";
		//String ticketId="businessKey12";

		
//        String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<List<Map<String, Object>>> pr = bpmnService.queryNativeWaitingTasksByUserCategory(userId, bpmnType);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQueryNativehisTasksByUserCategorySql() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		//String bpmnType="支付,salary";
		//String bpmnType="支付1,salary";
		//String bpmnType="salary";
		String bpmnType="salary";
		//String bpmnType="CB30D23E6C7EA2B4E11A738EF165F01D_1500_2018";
		//String bpmnType="";
		//String bpmnType=null;
		//String ticketId="businessKey12";

		
//        String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<String> pr = bpmnService.queryNativehisTasksByUserCategorySql(userId, bpmnType);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQueryNativehisTasksByUserCategorySqlWithYear() throws BpmnException{
		String[] url =getUrl();
		@SuppressWarnings("resource")
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		String bpmnType="z";
		String year = "2017";
		boolean currentYear = false;
		
		//String ticketId="businessKey12";

		
//        String userId="fozzie";
//		String bpmnType="salary";
//		String ticketId="businessKey8";
//		String variable="[{'name':'amt','type':'string','value':'120'}]";
		try 
		{
			ProcessResult<String> pr = bpmnService.queryNativehisTasksByUserCategorySql(userId, bpmnType, year, currentYear);
			System.out.println(pr.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
/*	@Test
	public void testBatchCreateBpmnProcessWithNextTask() throws BpmnException{
		
		String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContext-test.xml"};
		@SuppressWarnings("resource")
		
		ApplicationContext ctx = new FileSystemXmlApplicationContext(url);
		BpmnService bpmnService = (BpmnService) ctx.getBean("bpmnService");
		String userId="kermit";
		String bpmnType="0";
		String ticketId="8";
		String variable="";

		try 
		{
			ProcessResult<List<BpmnTicket>> pr = bpmnService.batchCreateBpmnProcessWithNextTask(userId, bpmnType, ticketId, variable);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	}

*/
	

	private String[] getUrl() {
		//String[] url = new String[]{"E:\\workspaceWFMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContextSimple-test.xml"};
		String[] url = new String[]{"D:\\IdeaProjects\\simple_21873\\src\\test\\com\\hq\\bpmn\\test\\applicationContextSimple-test.xml"};
		//String[] url = new String[]{"E:\\workspaceWFMySql\\hqbpmn\\src\\test\\com\\hq\\bpmn\\test\\applicationContextSimple-test.xml"};
		//String[] url = new String[]{"E:\\workspaceFWMySql\\hqbpmn\\Bpmn\\WEB-INF\\applicationContextSimple-bpmn.xml"};
		return url;
	}
}

