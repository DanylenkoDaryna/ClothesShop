package ua.nure.danylenko.epam.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LocaleTagServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WEB_LOG.info("LocaleTagServlet starts");

//        Charset currentCharset = Charset.defaultCharset();

//        WEB_LOG.info("Default Charset = " + currentCharset.toString());

        String[] pLanguage=request.getParameter("Language").split("_");
        String language = pLanguage[0];
        String country = pLanguage[1];

        Locale locale = new Locale(language, country);

//        response.setCharacterEncoding("KOI8-U");
//        //response.setContentType("text/html; charset=KOI8-U");

        WEB_LOG.info(" request.getContentType() = " +  request.getContentType());
        WEB_LOG.info(" response.getContentType() = " +  response.getContentType());
        WEB_LOG.info(" response.getCharacterEncoding() = " +  response.getCharacterEncoding());

//        request.setCharacterEncoding("UTF-8");

        request.setAttribute("country", locale.getDisplayCountry());
        request.setAttribute("language", request.getParameter("Language"));

        WEB_LOG.info("LocaleTagServlet ends");

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String forward = Path.PAGE_GOOD;

        RequestDispatcher rd = req.getRequestDispatcher(forward);

        rd.forward(req, resp);
    }

}
