package pw.highprophet.ssocenter.filter;

import pw.highprophet.ssocenter.web.common.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HighProphet945 on 2017/7/30.
 */
@WebFilter(urlPatterns = {"/*"})
public class ParamFillingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前请求路径
        HttpServletRequest req = (HttpServletRequest) request;
        String redirectPath = request.getParameter("redirectAddress");
        OverriddenRequest overriddenRequest = new OverriddenRequest(req);
        if (redirectPath == null || "".equals(redirectPath)) {
            System.out.println("fill request parameter 'redirectAddress' with '/index'");
            overriddenRequest.overrideParameter("redirectAddress", "/index");
        }
        chain.doFilter(overriddenRequest, response);
    }

    @Override
    public void destroy() {
    }

    static class OverriddenRequest extends HttpServletRequestWrapper {

        private Map<String, String> overriddenParameters;

        OverriddenRequest(HttpServletRequest request) {
            super(request);
            overriddenParameters = new HashMap<>();
        }

        void overrideParameter(String name, String value) {
            overriddenParameters.put(name, value);
        }

        @Override
        public String getPathInfo() {
            String pathInfo = super.getPathInfo();
            if (Utils.getInstance().isBlank(pathInfo)) {
                pathInfo = "/index";
            }
            return pathInfo;
        }

        @Override
        public String getParameter(String name) {
            if (overriddenParameters.containsKey(name)) {
                return overriddenParameters.get(name);
            }
            return super.getParameter(name);
        }

    }
}
