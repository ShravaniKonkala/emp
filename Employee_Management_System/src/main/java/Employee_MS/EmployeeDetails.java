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

@WebServlet("/employeeDetails")

public class EmployeeDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String email = (String) session.getAttribute("email");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String jdbcURL = "jdbc:mysql://localhost:3306/employee_details";
        String jdbcUsername = "root";
        String jdbcPassword = "root";

        String sql = "SELECT * FROM employee WHERE email = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Employee Details</title>");
            out.println("<style>");
            out.println("body { display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: violet; font-family: Arial, sans-serif; }");
            out.println(".container { width: 80%; max-width: 600px; padding: 20px; background-color: skyblue; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);  }");
            out.println(".detail-box { display: flex; justify-content: space-between; padding: 10px; margin: 10px 0; background-color: #f9f9f9; border-radius: 5px; box-shadow: 0 0 5px rgba(0, 0, 0, 0.1); }");
            out.println("h2 { color: #333; }");
            out.println(".button-container { margin-top: 20px; display: flex; justify-content: space-evenly; }");
            out.println("button { padding: 10px 20px; border: none; border-radius: 5px; color: white; font-size: 16px; cursor: pointer; transition: background-color 0.3s ease; }");
            out.println(".update-button { background-color: lightgreen; }");
            out.println(".delete-button { background-color: white; }");
            out.println(".logout-button { background-color: skyblue; }");
            out.println(".update-button:hover { background-color: darkgreen; }");
            out.println(".delete-button:hover { background-color: darkred; }");
            out.println(".logout-button:hover { background-color: darkblue; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            if (rs.next()) {
                out.println("<div class='container'>");
                out.println("<h2>Employee Details</h2>");
                out.println("<div class='detail-box'><span>ID:</span><span>" + rs.getInt("eid") + "</span></div>");
                out.println("<div class='detail-box'><span>Name:</span><span>" + rs.getString("fname") + " " + rs.getString("last_name") + "</span></div>");
                out.println("<div class='detail-box'><span>Position:</span><span>" + rs.getString("lname") + "</span></div>");
                out.println("<div class='detail-box'><span>Department:</span><span>" + rs.getString("contact") + "</span></div>");
                out.println("<div class='detail-box'><span>Salary:</span><span>" + rs.getDouble("dateofbirth") + "</span></div>");
                out.println("<div class='detail-box'><span>Hire Date:</span><span>" + rs.getDate("email") + "</span></div>");
                out.println("<div class='detail-box'><span>Email:</span><span>" + rs.getString("password") + "</span></div>");
                
                
                out.println("<div class='button-container'>");

                // Form for Update Button
                out.println("<form action='update_employee' method='post'>");
                out.println("<input type='hidden' name='emp_id' value='" + rs.getInt("emp_id") + "'>");
                out.println("<button type='submit' class='update-button'>Update</button>");
                out.println("</form>");

                // Form for Delete Button
                out.println("<form action='delete_employee' method='post'>");
                out.println("<input type='hidden' name='emp_id' value='" + rs.getInt("emp_id") + "'>");
                out.println("<button type='submit' class='delete-button'>Delete</button>");
                out.println("</form>");
                
                out.println("<button class='logout-button' onclick=\"window.location.href='./logout'\">Logout</button>");

                out.println("</div>");
                out.println("</div>");
            } else {
                out.println("<div class='container' style='background-color: lightcoral;'>");
                out.println("<h2 style='color: #fff;'>Employee not found.</h2>");
                out.println("<button onclick=\"window.location.href='./home'\">Go Home</button>");
                out.println("</div>");
            }

            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("error-pages/exception.html");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

