package com.temp.common.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/**
 * 文件上传过滤器
 *
 * @author xinre
 * @date 2019/5/10 13:28
 */
public class FileUploadFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadFilter.class);

    // 合法文件后缀
    private String legalFileSuffixExtension;

    // 非法文件后缀
    private String illegalFileSuffixExtension;

    private final String enctype = "multipart/form-data";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        legalFileSuffixExtension = filterConfig.getInitParameter("legalFileSuffixExtension");
        LOGGER.info(" ########## legalFileSuffixExtension={}", legalFileSuffixExtension);
        illegalFileSuffixExtension = filterConfig.getInitParameter("illegalFileSuffixExtension");
        LOGGER.info(" ########## illegalFileSuffixExtension={}", illegalFileSuffixExtension);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request = getRequest(request);
        if (request instanceof MultipartHttpServletRequest) {
            boolean illegalFlag = false;
            String originalFilename = "";
            MultipartHttpServletRequest mhsReq = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiFileMap = mhsReq.getMultiFileMap();
            Set<String> set = multiFileMap.keySet();
            if (set != null && !set.isEmpty()) {
                for (String key :
                        set) {
                    List<MultipartFile> multipartFiles = multiFileMap.get(key);
                    if (multipartFiles != null && !multipartFiles.isEmpty()) {
                        for (MultipartFile m :
                                multipartFiles) {
                            originalFilename = m.getOriginalFilename();
                            String fileSuffixExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                            if (StringUtils.hasText(legalFileSuffixExtension) && StringUtils.hasText(illegalFileSuffixExtension)) {
                                if (!(legalFileSuffixExtension.contains(fileSuffixExtension) || !illegalFileSuffixExtension.contains(fileSuffixExtension))) {
                                    illegalFlag = true;
                                    break;
                                }
                            } else if (StringUtils.hasText(legalFileSuffixExtension)) {
                                if (!legalFileSuffixExtension.contains(fileSuffixExtension)) {
                                    illegalFlag = true;
                                    break;
                                }
                            } else if (StringUtils.hasText(illegalFileSuffixExtension)) {
                                if (illegalFileSuffixExtension.contains(fileSuffixExtension)) {
                                    illegalFlag = true;
                                    break;
                                }
                            } else {
                                illegalFlag = true;
                                LOGGER.error(" #################### 请配置文件上传过滤器合法或非法参数范围！ ####################");
                                break;
                            }
                            LOGGER.info(" ########## upload file is : {}", originalFilename);
                        }
                        if (illegalFlag) {
                            break;
                        }
                    }
                }
            }
            if (illegalFlag) {
                LOGGER.info(" ########## upload illegal file is : {}", originalFilename);
                System.out.println(" ########## upload illegal file is : " + originalFilename);



                response.setContentType("text/html;charset=utf-8");
                ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
                //创建输出流
                PrintWriter writer = response.getWriter();
                writer = response.getWriter();
                writer.write("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>错误页面</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "上传非法文件！" +
                        "</body>\n" +
                        "</html>");
                writer.flush();
                writer.close();
            } else {
                chain.doFilter(mhsReq, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private ServletRequest getRequest(ServletRequest req) {
        String contentType = req.getContentType();
        if (contentType != null && contentType.contains(enctype)) {
            HttpServletRequest r = (HttpServletRequest) req;
            MultipartResolver resolver = new CommonsMultipartResolver(r.getSession().getServletContext());
            return resolver.resolveMultipart(r);
        }
        return req;
    }

    @Override
    public void destroy() {

    }
}
