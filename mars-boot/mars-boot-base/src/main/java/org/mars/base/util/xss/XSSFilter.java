package org.mars.base.util.xss;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.mars.base.config.XssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 跨站点脚本编制
 *
 * @since 2020年4月10日
 * @version V1.0
 */
@ServletComponentScan
@Configuration
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
public class XSSFilter implements Filter {

    @Autowired
    private XssConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 获取路径
        String url = request.getRequestURI();
        if (!hasUrl(url)) {
            request = new XSSServletRequest(request);
        }
        filterChain.doFilter(request, res);

    }

    private boolean hasUrl(String u) {
        List<String> urls = config.getExcludeUrls();
        for (String url : urls) {
            if (PathUtil.isMatch(url, u)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }

}
