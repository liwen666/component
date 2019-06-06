package com.temp.common.common.filter.interceptor;

import com.fportal.common.controller.BaseController;
import com.fportal.common.util.CheckCookie;
import com.fportal.login.service.IUserLoginService;
import com.longtu.framework.util.ServiceFactory;
import gov.mof.fasp2.ca.user.dto.UserDTO;
import gov.mof.fasp2.ca.user.service.IUserService;
import gov.mof.fasp2.sec.syncuserinfo.manager.IUserSyncManager;
import gov.mof.fasp2.sec.util.SecUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *      添加门户单点登录白名单
 *  /fportal/page/homepage/ssoTrans.jsp
 */
@Controller
public class FOALoginController extends BaseController {


    private static Logger log=Logger.getLogger(SSOLoginControl.class);
    public IUserService getUserService() {
        return (IUserService) ServiceFactory.getBean("fasp2.ca.user.service");
    }
    @Resource(name="userLoginService")
    private IUserLoginService userLoginService ;
    public IUserSyncManager getUserSyncManager() {
        return (IUserSyncManager) ServiceFactory.getBean("fasp.ca.UserSyncManager");
    }
    @IncludeInterceptor
    @RequestMapping(value="/FOALogin ",produces="appliaction/json;charset=UTF-8",method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void ssoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("==========================单点登录开始============================");
        String loginName = request.getParameter("passport");
        String usertoken = request.getParameter("usertoken");
        String APP_CODE = "NDFYS";//应用标识
        boolean validUser = true;
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/fportal/page/homepage/ssoTrans.jsp";
        if (validUser) {
//            String sql = " code='" + loginName + "'";
            String sql = " code='8700_zycs'";
            try {
                List<UserDTO> ulist = getUserService().queryUsersBySql(sql);
                if (ulist.size() == 0) {
                    basePath+="?tokenid=1";
                    response.sendRedirect(basePath); return;
                }
                UserDTO dto = ulist.get(0);
                //从数据库中获取财年
                String year = "2019";
                if(year == null || "".equals(year)){
                    basePath+="?tokenid=1";
                    response.sendRedirect(basePath); return;
                }
                String province = "15000";
                String token = getUserSyncManager().doLogin(dto, new Integer(year), province);
                this.getRequest().setAttribute(SecUtil.TOKENID, token);
                CheckCookie.setFasp2Cookie(request, response, token.substring(32,40), token);
                basePath+="?tokenid="+token;
            } catch (Exception e ) {
                basePath+="?tokenid=1";
                log.error(e.getMessage());
                response.sendRedirect(basePath); return;
            }
        }else{
            basePath+="?tokenid=1";
        }
        response.sendRedirect(basePath);
        return;
    }
}
