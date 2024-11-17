package Employee_MS;

	import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;

	@WebServlet("/view")
	public class View extends HttpServlet {
				@Override
				protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        HttpSession session =req.getSession();
	        String email=(String)session.getAttribute("email");
	        System.out.println("email");
	        
	        Connection con=null;
	        PreparedStatement ps=null;
	        PrintWriter pw=resp.getWriter();
	        
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root && password=root");
			     ps=con.prepareStatement("select * from employee_managementsystem.empp where email=?");
			     ps.setString(1, email);
			     ResultSet rs=ps.executeQuery();
			     
			     if (rs.next()) {
			     int empid= rs.getInt(1);
			     String empname=rs.getString(2);
			     String position=rs.getString(3);
			     String contact=rs.getString(4);
			     String dateofbirth=rs.getString(5);
			     String password=rs.getString(7);
			     String confirmpassword=rs.getString(8);
			     
			        session.setAttribute("eid", empid);
					session.setAttribute("empn", empname);
					session.setAttribute("pos", position);
					session.setAttribute("tell", contact);
					session.setAttribute("dob", dateofbirth);
					session.setAttribute("email", email);
					session.setAttribute("password", password);
					session.setAttribute("pwd", confirmpassword);
					pw.println("<html>");
			        pw.println("<head>");
			        pw.println("<title>Account Created</title>");
			        pw.println("</head>");
			        pw.println("<style>");
			        pw.println("div{\r\n"
			        		+ "			box-sizing:0px;\r\n"
			        		+ "			background-color: pink;\r\n"
			        		+ "			margin-left: 450px;\r\n"
			        		+ "			margin-right: 450px;\r\n"
			        		+ "			border-radius: 20px;\r\n"
			        		+ "			box-shadow: 0px 4px 10px;\r\n"
			        		+ "			padding: 10px;\r\n"
			        		+ "			align-content: center;\r\n"
			        		+ "			align-items: center;		\r\n"
			        		+ "	}");
			        pw.println("button{\r\n"
			        		+ "		border-radius: 10px;\r\n"
			        		+ "		background-color: orange;\r\n"
			        		+ "		color: white;\r\n"
			        		+ "		\r\n"
			        		+ "		}"
			        		+ "h1{"
			        		+ "color:blue"
			        		+ "}");
			        pw.println("</style>");
			        pw.println("<body align=center bgcolor=hotpink>");
			        pw.println("<h1>Your Details are :</h1>");
			        pw.println("<div><p><strong>empid :</strong> " + empid + "</p>");
			        pw.println("<p><strong>empname:</strong> " + empname + "</p>");
			        pw.println("<p><strong>position:</strong> " + position + "</p>");
			        pw.println("<p><strong>Contact:</strong> " + contact + "</p>");
			        pw.println("<p><strong>Dateofbirth:</strong> " + dateofbirth + "</p>");
			        pw.println("<p><strong>email:</strong> " + email + "</p>");
			        pw.println("<p><strong>Password:</strong> " + password + "</p>");
			        pw.println("<p><strong>Confirm password:</strong> " + confirmpassword + "</p>");
			        pw.println("<form action='update' method='get'><button>Update</button></form>");
			        pw.println("<button><a href='./Delete.html'>Delete</a></button></div>");
			        pw.println("</body>");
			        pw.println("</html>");
			     } else {
		                pw.println("<h2>No employee found with this email.</h2>");
		            }
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            pw.println("<h2>Error retrieving employee details.</h2>");
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                if (con != null) con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
					
			     
			     
			     
	        }
	       
	    }
	}

 
