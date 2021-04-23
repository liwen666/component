package com.temp.common.cmd.executor;

import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/4/2  11:48
 */
public interface CommandBuild {
    String build(AppType appType, List<String> args);

    String build(List<String> params);
}
