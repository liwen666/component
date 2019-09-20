//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.temp.common.message;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import vip.dcpay.alert.sdk.enums.AlertLevelEnum;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.redis.service.RedisService;
import vip.dcpay.saas.application.utils.AlertUtil;
import vip.dcpay.saas.application.utils.HttpConnectionUtils;
import vip.dcpay.saas.application.utils.SpringUtil;
import vip.dcpay.vo.basic.Result;

public class NECaptchaVerifier {
    private static RedisService redisService;
    public static final String VERIFY_API = "http://c.dun.163yun.com/api/v2/verify";
    public static final String REQ_VALIDATE = "NECaptchaValidate";
    private static final String VERSION = "v2";
    private static final String captchaId = "7c80c423944941819e409d0d6639c4dd";
    private static final String secretId = "87cf360a08e067c754845028baec6b1d";
    private static final String secretKey = "341410ee009f4d1b94af9caf222ded28";

    public NECaptchaVerifier() {
    }

    public static Result verify(String validate) {
        if (!validateIsOpen()) {
            return Result.success();
        } else if (!StringUtils.isEmpty(validate) && !StringUtils.equals(validate, "null")) {
            String user = "";
            Map<String, String> params = new HashMap();
            params.put("captchaId", "7c80c423944941819e409d0d6639c4dd");
            params.put("validate", validate);
            params.put("user", user);
            params.put("secretId", "87cf360a08e067c754845028baec6b1d");
            params.put("version", "v2");
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("nonce", String.valueOf(ThreadLocalRandom.current().nextInt()));
            String signature = sign("341410ee009f4d1b94af9caf222ded28", params);
            params.put("signature", signature);
            String resp = "";

            try {
                resp = HttpConnectionUtils.readContentFromPost("http://c.dun.163yun.com/api/v2/verify", params);
            } catch (IOException var8) {
                MyLogManager.error("网易云滑动验证校验失败", var8);
                AlertUtil.sendAlertMsg("网易云验证", "网易云滑动验证校验失败", AlertLevelEnum.WARN);
            }

            MyLogManager.develop("网易云验证结果:" + resp);
            if (StringUtils.isEmpty(resp)) {
                return Result.error("二次校验获取数据为空");
            } else {
                JSONObject object = JSONObject.parseObject(resp);
                Boolean flag = object.getBoolean("result");
                if (flag) {
                    return Result.success();
                } else {
                    String msg = object.getString("msg");
                    MyLogManager.warn("网易云滑动验证失败:" + msg);
                    return Result.error(msg);
                }
            }
        } else {
            return Result.error("获取的校验数据为空");
        }
    }

    public static String sign(String secretKey, Map<String, String> params) {
        String[] keys = (String[])params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuffer sb = new StringBuffer();
        String[] var4 = keys;
        int var5 = keys.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String key = var4[var6];
            sb.append(key).append((String)params.get(key));
        }

        sb.append(secretKey);

        try {
            return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    private static boolean validateIsOpen() {
        if (redisService == null) {
            redisService = (RedisService)SpringUtil.getBean("redisService");
        }

        String s = redisService.get("SAAS:NECAPTCHAVERIFIER:SWITCH");
        if (StringUtils.isEmpty(s)) {
            redisService.save("SAAS:NECAPTCHAVERIFIER:SWITCH", "1");
            return true;
        } else {
            return !s.equals("0");
        }
    }
}
