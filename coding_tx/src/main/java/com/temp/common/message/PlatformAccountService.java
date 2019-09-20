//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.temp.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlatformAccountService {
    private static final Logger log = LoggerFactory.getLogger(PlatformAccountService.class);

    public PlatformAccountService() {
    }

    public Result<PlatformAccountInfoVo> getUserInfo(Long id) {
            PlatformAccountInfoVo platformVo = new PlatformAccountInfoVo();

            PlatformInfo platformInfo = new PlatformInfo();
            platformVo.setPlatformId(1231l);
            platformVo.setPlatformName(platformInfo.getName());
            platformVo.setUid(platformInfo.getUid());
            return Result.success(platformVo);
    }

}
