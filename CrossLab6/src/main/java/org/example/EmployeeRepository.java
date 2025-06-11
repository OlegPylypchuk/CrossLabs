package org.example;

import java.sql.*;
import java.util.*;

public class EmployeeRepository {
    private final Connection conn;

    public EmployeeRepository() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/lab6db", "sa", "");
        conn.createStatement().execute("CREATE TABLE IF NOT EXISTS employees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "department VARCHAR, name VARCHAR, position VARCHAR, qualification VARCHAR, " +
                "hours_worked INT, hourly_rate DOUBLE)");
    }

    public void add(Employee emp) throws SQLException {
        var stmt = conn.prepareStatement("INSERT INTO employees (department, name, position, qualification, hours_worked, hourly_rate) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, emp.getDepartment());
        stmt.setString(2, emp.getName());
        stmt.setString(3, emp.getPosition());
        stmt.setString(4, emp.getQualification());
        stmt.setInt(5, emp.getHoursWorked());
        stmt.setDouble(6, emp.getHourlyRate());
        stmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        var stmt = conn.prepareStatement("DELETE FROM employees WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> list = new ArrayList<>();
        var rs = conn.createStatement().executeQuery("SELECT * FROM employees");
        while (rs.next()) {
            list.add(new Employee(rs.getInt("id"), rs.getString("department"), rs.getString("name"),
                    rs.getString("position"), rs.getString("qualification"), rs.getInt("hours_worked"),
                    rs.getDouble("hourly_rate")));
        }
        return list;
    }

    public List<Employee> search(String position, String qualification) throws SQLException {
        List<Employee> list = new ArrayList<>();
        var stmt = conn.prepareStatement("SELECT * FROM employees WHERE position=? AND qualification=?");
        stmt.setString(1, position);
        stmt.setString(2, qualification);
        var rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new Employee(rs.getInt("id"), rs.getString("department"), rs.getString("name"),
                    rs.getString("position"), rs.getString("qualification"), rs.getInt("hours_worked"),
                    rs.getDouble("hourly_rate")));
        }
        return list;
    }
}
