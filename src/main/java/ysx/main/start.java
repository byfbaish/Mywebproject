package ysx.main;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class start extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("this is start int");
    }

    @Override
    public final void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        System.out.println("this is start int welcome");

    }
}
