import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deptName = request.getParameter("deptName");
        String totalStr = request.getParameter("totalStudent");
        String action = request.getParameter("action");

        ServiceClass service = new ServiceClass();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><style>");
        out.println("table { border-collapse: collapse; width: 50%; }");
        out.println("th, td { border: 1px solid #333; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style></head><body>");

        out.println("<h2>Action: " + action + "</h2>");

        int totalStudent = 0;
        if (totalStr != null && !totalStr.isEmpty()) {
            totalStudent = Integer.parseInt(totalStr);
        }

        switch (action) {

            case "Insert":
                if (service.insertDB(deptName, totalStudent)) {
                    out.println("<p>Inserted successfully.</p>");
                } else {
                    out.println("<p>Insertion failed.</p>");
                }
                break;

            case "View":
                List<String> departments = service.viewDB();
                if (departments.isEmpty()) {
                    out.println("<p>No departments found.</p>");
                } else {
                    out.println("<table>");
                    out.println("<tr><th>Department Name</th><th>Total Students</th></tr>");

                    for (String dept : departments) {
                        String[] parts = dept.split(", Students: ");
                        String name = parts[0].replace("Department: ", "");
                        String students = parts.length > 1 ? parts[1] : "";

                        out.println("<tr><td>" + name + "</td><td>" + students + "</td></tr>");
                    }

                    out.println("</table>");
                }
                break;

            case "Update":
                if (service.updateDB(deptName, totalStudent)) {
                    out.println("<p>Updated successfully.</p>");
                } else {
                    out.println("<p>Update failed.</p>");
                }
                break;

            case "Delete":
                if (service.deleteDB(deptName)) {
                    out.println("<p>Deleted successfully.</p>");
                } else {
                    out.println("<p>Delete failed.</p>");
                }
                break;

            default:
                out.println("<p>Unknown action.</p>");
        }

        out.println("</body></html>");
    }
}
