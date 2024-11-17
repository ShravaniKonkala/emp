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
import javax.servlet.http.HttpSession;

@WebServlet("/update")

public class Update extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        String emp = (String) session.getAttribute("empn");
        String pos = (String) session.getAttribute("pos");
        String dob = (String) session.getAttribute("dob");
        String tell = (String) session.getAttribute("tell");
        String pwd = (String) session.getAttribute("pwd");
        String id = (String) session.getAttribute("id");

        PrintWriter pw = resp.getWriter();
        pw.println("<!DOCTYPE html>");
        pw.println("<html lang='en'>");
        pw.println("<head>");
        pw.println("<meta charset='UTF-8'>");
        pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        pw.println("<title>Update Account Information</title>");
        pw.println("<style>");
        pw.println("body { font-family: Arial, sans-serif; }");
        pw.println("div { box-sizing: border-box; background-color: pink; margin: auto; width: 50%; padding: 20px; border-radius: 20px; box-shadow: 0px 4px 10px; }");
        pw.println("input { width: 100%; padding: 10px; margin: 5px 0; border: 1px solid #ccc; border-radius: 5px; }");
        pw.println("button { height: 50px; width: 100px; border-radius: 10px; background-color: orange; color: white; }");
        pw.println("h2 { color: blue; text-align: center; }");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div>");
        pw.println("<h2>Update Account Information</h2>");
        pw.println("<form action='update' method='post'>");
        
        pw.println("<label for='empn'>Emp Name:</label>");
        pw.println("<input type='text' name='empn' id='empn' value='" + emp + "' required>");
        
        pw.println("<label for='pos'>Position:</label>");
        pw.println("<input type='text' name='pos' id='pos' value='" + pos + "' required>");
        
        pw.println("<label for='contact'>Contact:</label>");
        pw.println("<input type='tel' name='contact' id='contact' value='" + tell + "' required>");
        
        pw.println("<label for='dob'>Dateofbirth:</label>");
        pw.println("<input type='dob' name='dob' id='dob' value='" + dob + "' required>");
        
        pw.println("<label for='pwd'>Password:</label>");
        pw.println("<input type='password' name='pwd' id='pwd' value='" + pwd + "' required>");
        
        pw.println("<label for='id'>ID:</label>");
        pw.println("<input type='text' name='id' id='id' value='" + id + "' required>");
        
        pw.println("<button type='submit'>Update Account</button>");
        pw.println("</form>");
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");

        String empname = req.getParameter("empn");
        String position=req.getParameter("pos");
        String contact=req.getParameter("tell");
        String dateofbirth=req.getParameter("dob");
        String password = req.getParameter("pwd");
        String id = req.getParameter("id");
      
        

        Connection con = null;
        PreparedStatement ps = null;
        PrintWriter pw = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_managementsystem", "root", "root");

            String sql = "update empp SET empname=?,position=?, contact=?,dateofbirth=?, password=? where email=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, empname);
            ps.setString(2, position);
             ps.setString(3, contact);
            ps.setString(4, dateofbirth);
            ps.setString(5, password);
            ps.setString(6,email);
      

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                session.setAttribute("id", id);
                pw.println("<html><body align='center' bgcolor='aqua'>");
                pw.println("<h1>Updated Successfully</h1>");
                pw.println("<button><a href='./Delete.html'>Delete</a></button>");
                pw.println("<button><a href='./Home.html'>Logout</a></button>");
                pw.println("</body></html>");
            } else {
                pw.println("<html><body align='center' bgcolor='aqua'>");
                pw.println("<h1>Update Failed</h1>");
                pw.println("<button><a href='./Home.html'>Home</a></button>");
                pw.println("</body></html>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
