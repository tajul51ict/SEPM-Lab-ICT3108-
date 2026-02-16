import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClass extends DBConnection {

    public boolean insertDB(String deptName, int totalStudent) {
        this.getConnection();
        String sql = "INSERT INTO MYDEPARTMENT(DEPT_NAME, TOTAL_STUDENT) VALUES(?, ?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, deptName);
            ps.setInt(2, totalStudent);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public List<String> viewDB() {
        List<String> result = new ArrayList<>();
        this.getConnection();
        String sql = "SELECT DEPT_NAME, TOTAL_STUDENT FROM MYDEPARTMENT";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add("Department: " + rs.getString("DEPT_NAME") +
                        ", Students: " + rs.getInt("TOTAL_STUDENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return result;
    }

    public boolean updateDB(String deptName, int totalStudent) {
        this.getConnection();
        String sql = "UPDATE MYDEPARTMENT SET TOTAL_STUDENT = ? WHERE DEPT_NAME = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, totalStudent);
            ps.setString(2, deptName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public boolean deleteDB(String deptName) {
        this.getConnection();
        String sql = "DELETE FROM MYDEPARTMENT WHERE DEPT_NAME = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, deptName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }
}
