package sadafpkg;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/bookevent")
public class bookevent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public bookevent() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String date = request.getParameter("event_date");
        String eventType = request.getParameter("event_type");

        Database db = new Database();
        Connection con = db.getConnection();

        try {
            String sql = "INSERT INTO bookings (full_name, email, phone, event_date, event_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, date);
            ps.setString(5, eventType);

            int result = ps.executeUpdate();

            if (result > 0) {
                response.getWriter().println("<script>alert('✅ Event Registered Successfully!'); window.location.href = 'index.html';</script>");
            } else {
                response.getWriter().println("<h2 style='color:red;'>❌ Failed to Register Event!</h2>");
            }
        } catch (Exception e) {
            response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
}
