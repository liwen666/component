//package com.temp.common.mybatis.geneator;
//
//import com.hq.bpmn.bpmnanalysis.bean.ProinsTaskAnalysisDomain;
//import com.hq.bpmn.common.util.DataConversion;
//import org.apache.ibatis.annotations.*;
//import org.apache.ibatis.type.JdbcType;
//
//import java.util.List;
//import java.util.Map;
//
//public interface IRankingListAnalysisDao {
//
//	/*<resultMap id="tempAnalysisResult" type="com.hq.bpmn.bpmnanalysis.bean.ProinsTaskAnalysisDomain" >
//		<result column="PROC_INST_ID_" property="procInsId" jdbcType="VARCHAR" />
//		<result column="PROC_DEF_ID_" property="procDefId" jdbcType="VARCHAR" />
//		<result column="DEPLOYMENT_ID_" property="deploymentId" jdbcType="VARCHAR"/>
//		<result column="ASSIGNEE_" property="assignee" jdbcType="VARCHAR" />
//		<result column="ACT_ID_" property="actId" jdbcType="VARCHAR" />
//		<result column="ACT_NAME_" property="actName" jdbcType="VARCHAR" />
//		<result column="TASK_TYPE_" property="taskType" jdbcType="INTEGER" />
//		<result column="START_TIME_" property="startTime" jdbcType="VARCHAR"/>
//		<result column="END_TIME_" property="endTime" jdbcType="VARCHAR"/>
//		<result column="DURATION_" property="duration" jdbcType="INTEGER"/>
//		<result column="APP_ID_" property="appId" jdbcType="VARCHAR"/>
//		<result column="YEAR_" property="year" jdbcType="VARCHAR"/>
//		<result column="taskCount" property="taskCount" jdbcType="INTEGER"/>
//		<result column="tempName" property="tempName" jdbcType="VARCHAR"/>
//		<result column="procInsCount" property="procInsCount" jdbcType="INTEGER"/>
//	</resultMap>*/
//
//	@Results(id = "tempAnalysisResult", value = {
//			@Result(column = "PROC_INST_ID_", property = "procInsId", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "PROC_DEF_ID_", property = "procDefId", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "DEPLOYMENT_ID_", property = "deploymentId", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ASSIGNEE_", property = "assignee", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ACT_ID_", property = "actId", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ACT_NAME_", property = "actName", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "TASK_TYPE_", property = "taskType", jdbcType = JdbcType.INTEGER),
//			@Result(column = "START_TIME_", property = "startTime", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "END_TIME_", property = "endTime", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "DURATION_", property = "duration", jdbcType = JdbcType.INTEGER),
//			@Result(column = "APP_ID_", property = "appId", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "YEAR_", property = "year", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "taskCount", property = "taskCount", jdbcType = JdbcType.INTEGER),
//			@Result(column = "tempName", property = "tempName", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "procInsCount", property = "procInsCount", jdbcType = JdbcType.INTEGER)
//	})
//	@Select("select x.*,count(*) taskCount from (select aht.act_id_,aht.act_name_,(select name from act_hq_tem_def where deployment_id= aht.deployment_id_) tempName from act_hq_taskanaly aht where aht.task_type_='1')x   group by x.act_id_ ,x.act_name_,tempName")
//	List<ProinsTaskAnalysisDomain> selectWaitTaskRanking();
//	@Select(" select aht.assignee_, count(*) taskCount from act_hq_taskanaly aht "+
//        " where aht.task_type_ = '0'  group by aht.assignee_  order by taskCount desc")
//	@ResultMap("tempAnalysisResult")
//	List<ProinsTaskAnalysisDomain> selectFinishTaskRanking();
//	@Select("    select aht.act_name_ ,aht.duration_ ,(select name from act_hq_tem_def  where deployment_id = aht.deployment_id_) tempName ,aht.app_id_ from act_hq_taskanaly  aht where aht.duration_ is not null  order by aht.duration_ desc ")
//	@ResultMap("tempAnalysisResult")
//	List<ProinsTaskAnalysisDomain> selectTaskDurationRanking();
//	@Select("select count(*) taskCount ,aht.act_id_,aht.act_name_||'['||(select name  from act_hq_tem_def where deployment_id = aht.deployment_id_ )  ||']' tempName from act_hq_taskanaly  aht where aht.task_type_='1' and aht.assignee_= #{assignee} group by aht.deployment_id_,aht.act_id_,aht.act_name_ ")
//	@ResultMap("tempAnalysisResult")
//	List<ProinsTaskAnalysisDomain> findFinishTaskByAssignee(String assignee);
//
//	 @SelectProvider(type = RankingListAnalysisDaoProvider.class, method = "selectWaitTaskRanking")
//	 @ResultMap("tempAnalysisResult")
//	List<ProinsTaskAnalysisDomain> selectWaitTaskRankingByName(Map<String, String> param);
//	 @SelectProvider(type = RankingListAnalysisDaoProvider.class, method = "selectFinishTaskRanking")
//	 @ResultMap("tempAnalysisResult")
//	 List<ProinsTaskAnalysisDomain> selectFinishTaskRankingByName(
//             Map<String, String> param);
//	 @SelectProvider(type = RankingListAnalysisDaoProvider.class, method = "selectTaskDurationRanking")
//	 @ResultMap("tempAnalysisResult")
//	 List<ProinsTaskAnalysisDomain> selectTaskDurationRankingByName(
//             Map<String, String> param);
//
//	 class RankingListAnalysisDaoProvider {
//	        public String selectWaitTaskRanking(Map<String, String> param) {
//	           String sql ="    select x.*,count(*) taskCount from (select aht.act_id_,aht.act_name_,"
//                           +"(select name from act_hq_tem_def where deployment_id= aht.deployment_id_) tempName "
//                           + "from act_hq_taskanaly aht where aht.task_type_='0' ";
//
//	            if(param.get("name")!=null){
//	                sql += "and aht.act_name_ like  '%'||#{name}||'%'";
//	            }
//	            sql +=")x group by x.act_id_ ,x.act_name_,tempName";
//
//	            return DataConversion.sqlConversion(sql);
//	        }
//	        public String selectFinishTaskRanking(Map<String, String> param) {
//	        	String sql =" select aiu.first_ username ,x.assignee_,x.taskCount from act_id_user aiu  join"
//	        			+ " ( select aht.assignee_, count(*) taskCount from act_hq_taskanaly aht where aht.task_type_ = '1' "
//	        			+ " group by aht.assignee_ )x on aiu.id_=x.assignee_  order by taskCount desc";
//
////	        	if(param.get("name")!=null){
////	        		sql += "and aht.assignee_ like  '%'||#{name}||'%'";
////	        	}
////	        	sql +=" group by aht.assignee_ )x  on aiu.id_=x.assignee_ order by taskCount desc ";
//
//	        	return DataConversion.sqlConversion(sql);
//	        }
//	        public String selectTaskDurationRanking(Map<String, String> param) {
//	        	String sql ="   select aht.act_name_ ,aht.duration_ ,(select name from act_hq_tem_def  where deployment_id = aht.deployment_id_) "
//	        			    + "tempName ,aht.app_id_ from act_hq_taskanaly  aht "
//	        			    + "where aht.duration_ !=0  ";
//	        	if(param.get("name")!=null){
//	        		sql += "and aht.act_name_ like  '%'||#{name}||'%'";
//	        	}
////	        	sql +="order by aht.duration_ desc ";
//
//	        	return DataConversion.sqlConversion(sql);
//	        }
//
//	    }
//
//
//}
