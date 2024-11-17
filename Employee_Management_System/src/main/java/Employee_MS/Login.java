package Employee_MS;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class Login extends HttpServlet {
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  String email=req.getParameter("email");
	  String password=req.getParameter("password");
	  PrintWriter pw=resp.getWriter();
	  
	  String qry="select * from  empp where email=? and password=?"; 
	  try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_managementsystem?user=root&& password=root");
		PreparedStatement ps=con.prepareStatement(qry);
		ps.setString(1, email);
		ps.setString(2,password );
		ResultSet rs=ps.executeQuery();
		RequestDispatcher rd=null;

	    if(rs.next() ){            
				rd=req.getRequestDispatcher("view.html");
				rd.forward(req, resp);
				String email1 = rs.getString(6);
				HttpSession session=req.getSession();
			    session.setAttribute("email", email1);

			}else {
				pw.println("<html> <body bgcolor=purple align=center>");
				pw.println("<h2>Sorry,you dont have an account..login here</h2>");
				pw.println("<button><a href=./login.html>login</a></button>");
				pw.println("</body> </html>");
				rd=req.getRequestDispatcher("./create-account.html");
				rd.include(req, resp);
				
	    
	    
	    }
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		pw.println("<html> <body bgcolor=purple align=center>");
		pw.println("<h2>Sorry,you dont have an account..Please login here</h2>");
		pw.println("<button><a href=./login.html>login</a></button>");
		pw.println("</body> </html>");
			}
		
  }
}
