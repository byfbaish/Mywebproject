package ysx.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class start extends HttpServlet {
    Logger logger = Logger.getGlobal();

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("this is start int");
        logger.setLevel(Level.ALL);
        logger.info("this is start int");
    }

    @Override
    public final void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        System.out.println("this is start post welcome");
        logger.info("this is start post welcome");
    }

    @Override
    public void destroy() {
        System.out.println("this is start.destroy");
        logger.info("this is start.destroy");
    }

}
