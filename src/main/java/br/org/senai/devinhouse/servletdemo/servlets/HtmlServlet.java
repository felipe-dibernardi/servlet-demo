package br.org.senai.devinhouse.servletdemo.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "htmlServlet", value = "/html-servlet")
public class HtmlServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");

        // Hello
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello World</h1>");
        out.println("</body></html>");
	}

}
