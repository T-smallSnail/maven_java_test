package cn.pancho.web.common.servlet;


import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author sw
 */
@WebServlet(name = "InitConstsServlet", urlPatterns = {"/init"}, loadOnStartup = 20)
public class InitConstsServlet extends HttpServlet {
    private final static Logger logger = (Logger) LoggerFactory.getLogger(InitConstsServlet.class);

//InitDao initDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String t = req.getParameter("t");
        PrintWriter writer = resp.getWriter();
        if ("reinit".equals(t)) {

        } else if ("receipt".equals(t)) {
            //this.initReceiptMessageConfig();
        }

        writer.write("Data updated successfully!");
        writer.close();
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {


        //WebApplicationContext webApplicationContext = (WebApplicationContext) servletConfig.getServletContext().getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        //initDao = webApplicationContext.getBean(InitDao.class);


    }


}
