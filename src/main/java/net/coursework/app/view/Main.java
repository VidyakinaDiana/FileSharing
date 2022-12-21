package net.coursework.app.view;

import net.coursework.app.model.DataBase;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;

public class Main extends HttpServlet {
	private static DataBase dataBase;
  
	@Override
	public void init() {
		dataBase = new DataBase("FileSharingPro-1.0-DataBase.xml");
		File repository = new File("FileSharingPro-1.0-Repository");
		if(!repository.exists()) {
			repository.mkdir();
		}
		File zip = new File("FileSharingPro-1.0-Zip");
		if(!zip.exists()) {
			zip.mkdir();
		}
	}
  
	@Override
	public void destroy() {
		dataBase.saveDataBase();
	}
  
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println("<head><link rel=\"stylesheet\" href=\"style.css\"></head>");
    
		pw.println("<body>");
    
		pw.println("<div id=\"head\">");
		pw.println("<a href=\"MainPage\">MainPage</a>");
		pw.println("<a href=\"GeneralDirectoryPage\">All files</a>");
    
		HttpSession session = req.getSession();
		String login = (String)session.getAttribute("login");
		if (login != null) {
			pw.println("<a href=\"LogoutPage\">Logout</a>" +
						"<a href=\"MyDirectoryPage\">My files</a>");
			pw.println("<h2>Hello, " + login + "</h2>");
		} else {
			pw.println("<a href=\"LoginPage\">Login</a>");
			pw.println("<a href=\"RegistrationPage\">Registration</a>");
		}
		pw.println("</div>");
		
		pw.println("<div class=\"body\">");

		pw.println("</div>");
		
		pw.println("</body>");
		pw.close();
	}
  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		String login = (String)session.getAttribute("login");
		if (login == null) { 
			resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
			return; 
		}
			
		resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
	}
  
	public static DataBase getDataBase() {
		return dataBase;
	}
}
