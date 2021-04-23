package com.temp.common.cmd.executor;

import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/4/2  11:32
 */
public interface ICommandLine {
    /**
     * 构建执行命令
     *
     * @param params
     * @return
     */
    String buildJarCommand(CommandBuild commandBuild, List<String> params);

    /**
     * 构建执行命令
     *
     * @return
     */
    String buildCommand(CommandBuild commandBuild, AppType appType, List<String> args);

    /**
     * 执行命令
     *
     * @param command
     */
    void exec(String command, String charSet);

}
