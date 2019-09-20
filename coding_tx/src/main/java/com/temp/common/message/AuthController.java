////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.temp.common.message;
//
//import com.alibaba.fastjson.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import vip.dcpay.validate.sdk.MyValidateManager;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//@RequestMapping({"/outer/auth"})
//@RestController
//public class AuthController {
//    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
//    @Resource
//    private PlatformAccountService platformAccountService;
//    @Resource
//    private MyValidateManager myValidateManager;
//
//    public AuthController() {
//    }
//
//
//    @RequestMapping({"/sendVerifyCode"})
//    public Result<VerifyTypeResponse> sendVerifyCode(HttpServletRequest request, @RequestParam String validateToken, @RequestParam String validateType, @RequestParam(required = false) String receiver, @RequestParam(required = false) String NECaptchaValidate) {
//        vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum validateItemType = ValidateItemTypeEnum.getEnumByName(validateType);
//        if (!ValidateItemTypeEnum.SMS.equals(validateItemType) && !ValidateItemTypeEnum.MAIL.equals(validateItemType)) {
//            return Result.error("非支持的验证码发送类型");
//        } else {
//            String referer = request.getHeader("Referer");
//            if (StringUtils.isEmpty(referer)) {
//                return Result.error("非法访问!");
//            } else {
//                if (referer.endsWith("/auth/forget")) {
//                    vip.dcpay.vo.basic.Result validateResult = NECaptchaVerifier.verify(NECaptchaValidate);
//                    if (!validateResult.getSuccess()) {
//                        return Result.error("网易云验证失败");
//                    }
//                }
//
//                LoginedUser loginedUser = new LoginedUser();
//                PlatformAccountInfoVo info = null;
//                vip.dcpay.vo.basic.Result result;
//                if (loginedUser != null) {
//                    result = this.platformAccountService.getUserInfo(loginedUser.getId());
//                    if (!result.getSuccess()) {
//                        return Result.error("未找到当前操作用户");
//                    }
//
//                    info = (PlatformAccountInfoVo)result.getData();
//                } else {
////                    result = this.platformAccountService.getUserInfoByAccount(receiver);
////                    if (!result.getSuccess()) {
////                        return Result.error("用户名或密码错误");
////                    }
////
////                    info = (PlatformAccountInfoVo)result.getData();
//                }
//
//                if (validateItemType == ValidateItemTypeEnum.SMS) {
//                    receiver = info.getFullPhone();
//                } else {
//                    receiver = StringUtils.isEmpty(info.getEmail()) ? receiver : info.getEmail();
//                }
//
//                result = this.myValidateManager.sendVerifyCode(validateToken, validateItemType, receiver);
//                String content = String.format("操作人%s发送%s,发送结果:%s", info.getFullPhone(), validateItemType, JSON.toJSONString(result));
////                MyLogManager.optLog(info.getPhone(), content, "", result, request);
//                return result;
//            }
//        }
//    }
//
//}
