package ua.nure.danylenko.epam.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The EncodingFilter class implements Filter and provides manipulation with filter (init, destroy) and encoding
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class EncodingFilter implements Filter {

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    private String encoding;

    public void destroy() {
        WEB_LOG.info("Filter destruction starts");
        // no op
        WEB_LOG.info("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        WEB_LOG.info("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        WEB_LOG.info("Request uri --> " + httpRequest.getRequestURI());

        String requestEncoding = request.getCharacterEncoding();
        if (requestEncoding == null) {
            WEB_LOG.error("Request encoding = null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }

        WEB_LOG.info("Filter finished");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        WEB_LOG.info("Filter initialization starts");
        encoding = fConfig.getInitParameter("encoding");
        WEB_LOG.trace("Encoding from web.xml --> " + encoding);
        WEB_LOG.info("Filter initialization finished");
    }

}
