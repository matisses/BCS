package co.matisses.bcs.rest;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dbotero
 */
@WebFilter("/res/*")
public class HeaderDecorationFilter implements Filter {

    private static final Logger log = Logger.getLogger(HeaderDecorationFilter.class.getSimpleName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //log.log(Level.INFO, "-----------------------------");
        //log.log(Level.INFO, "Request headers ");
        //Enumeration<String> headers = ((HttpServletRequest) request).getHeaderNames();
        //while (headers.hasMoreElements()) {
        //    String headerName = headers.nextElement();
        //log.log(Level.INFO, "{0}: {1}", new Object[]{headerName, ((HttpServletRequest) request).getHeader(headerName)});
        //}
        //log.log(Level.INFO, "-----------------------------");
        //log.log(Level.INFO, "Response headers before");
        //for (String header : ((HttpServletResponse) response).getHeaderNames()) {
        //log.log(Level.INFO, "{0}: {1}", new Object[]{header, ((HttpServletResponse) response).getHeader(header)});
        //}

        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST, PUT, DELETE");

        //log.log(Level.INFO, "-----------------------------");
        //log.log(Level.INFO, "Response headers after");
        //for (String header : ((HttpServletResponse) response).getHeaderNames()) {
        //    log.log(Level.INFO, "{0}: {1}", new Object[]{header, ((HttpServletResponse) response).getHeader(header)});
        //}
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
