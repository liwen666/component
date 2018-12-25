package com.temp.common.common.mybatis;

import com.temp.common.common.bean.BpmnProcessConstant;
import com.temp.common.common.bean.BpmnSystemParam;
import com.temp.common.common.bean.DatabaseLogoEnum;
import com.temp.common.common.util.WfLogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * @author mfl
 */
public class WfSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Logger logger = LoggerFactory.getLogger(WfSqlSessionFactoryBean.class);

    private static final String WF_MAPPER_XML_END_IDENTIFIER = "WfSqlMapper.xml";

	private DataSource dataSource;

	@SuppressWarnings("unused")
	private Resource configLocation;

	private Resource[] mapperLocations;

	@SuppressWarnings("unused")
	private TransactionFactory transactionFactory;

	@Override
	public void setTransactionFactory(TransactionFactory transactionFactory) {
		super.setTransactionFactory(transactionFactory);
	}

	@Override
	public void setConfigLocation(Resource configLocation) {
		super.setConfigLocation(configLocation);
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			this.dataSource = ((TransactionAwareDataSourceProxy) dataSource)
					.getTargetDataSource();
		} else {
			this.dataSource = dataSource;
		}
		super.setDataSource(this.dataSource);
	}

	@Override
	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
		super.setMapperLocations(this.getCurrentDatabaseResources());
	}

	private Resource[] getCurrentDatabaseResources() {

		Resource[] currentDatabaseResources = new Resource[this.mapperLocations.length];
		String databaseLogo = DatabaseLogoEnum.ORACLE.toString();
		try {
			databaseLogo = this.getDatabaseIdProvider().getDatabaseId(dataSource);
		} catch (SQLException e) {
			logger.error(WfLogUtils.wfLogFormat("获取数据库类型错误！"));
			e.printStackTrace();
		}
		this.setBpmnDatabaseType(databaseLogo);
		for (int i = 0; i < mapperLocations.length; i++) {
			URL url = null;
			String path;
			try {
				url = mapperLocations[i].getURL();
			} catch (IOException e) {
				logger.error(WfLogUtils.wfLogFormat("获取mapper.xml文件url信息错误！"));
				e.printStackTrace();
			}
			if (url != null) {
				path = url.getPath();
				if (this.wfMapperXmlPath(path)) {
					if (path.contains(databaseLogo.toLowerCase())) {
						logger.info("hqbpmn *Mapper.xml path is <===> {}", path);
						currentDatabaseResources[i] = mapperLocations[i];
					}
				}else{
					currentDatabaseResources[i] = mapperLocations[i];
				}
			}
		}
		return currentDatabaseResources;
	}

	private void printDatabaseLogo(String databaseLogo) {
		if("oracle".equalsIgnoreCase(databaseLogo)){
			BpmnSystemParam.BPMN_DATABASE_TYPE="oracle";
		}else if ("mysql".equalsIgnoreCase(databaseLogo)) {
			BpmnSystemParam.BPMN_DATABASE_TYPE="mysql";
		}
		System.out.println("##" + "database " + "type " + "is " + databaseLogo);
	}

	private void setBpmnDatabaseType(String databaseLogo) {
        this.printDatabaseLogo(databaseLogo);
        if(StringUtils.isEmpty(BpmnProcessConstant.BPMN_DATABASE_TYPE) && !(DatabaseLogoEnum.H2.toString()).equalsIgnoreCase(databaseLogo)){
            BpmnProcessConstant.BPMN_DATABASE_TYPE = databaseLogo.toLowerCase();
        }
    }

	private boolean wfMapperXmlPath(String mapperXmlPath) {
        if (mapperXmlPath.endsWith(WfSqlSessionFactoryBean.WF_MAPPER_XML_END_IDENTIFIER)) {
			return true;
        } else {
            return false;
        }
	}


}
