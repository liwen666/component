# 简单访问登录  http://localhost:8080/login.do
##  URL配置
    <servlet-mapping>
        <servlet-name>springServlet</servlet-name>
        <!--<url-pattern>/hqbpmn/bpmnAction/*</url-pattern>-->
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>