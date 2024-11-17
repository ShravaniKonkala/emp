package Employee_MS;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/create")
public class Resgister extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int empid=Integer.parseInt(req.getParameter("eid"));
		String empname=req.getParameter("empn");
		String position=req.getParameter("pos");
		String contact=req.getParameter("tell");
		String dateofbirth=req.getParameter("dob");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String confirmpassword=req.getParameter("pwd");
		PrintWriter pw=resp.getWriter();
		
		//System.out.println(email+""+password);
		//Connection con=null;
		//PreparedStatement ps=null;
		  String qry="insert into empp values(?,?,?,?,?,?,?,?)";
		  if(password.equals(confirmpassword)) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_managementsystem?user=root&& password=root");
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setInt(1, empid);
			ps.setString(2, empname);
			ps.setString(3, position);
			ps.setString(4, contact);
			ps.setString(5,dateofbirth );
			ps.setString(6, email);
			ps.setString(7,password );
			ps.setString(8, confirmpassword);
			
			ps.executeUpdate();
			pw.println("<html><body bgcolor='silver' align='center' >");
			pw.println("<h1>registered Succesfully</h1>");
			pw.println("<button><a href=./login.html>loginhere</a></button>");
			pw.println("</body></html>");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("<html><body bgcolor='silver'>");
			pw.println("<h1>something went wrong please check again<h1>");
			pw.println("<a href=./create-account.html>create here</a>");
			pw.println("</html></body>");
		}
	}
		  else {
			  pw.println("<h1>password doesnot match.please try again</h1>");
		  }
		  }

}
