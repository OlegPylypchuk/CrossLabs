package org.example;

public class Employee {
    private int id;
    private String department, name, position, qualification;
    private int hoursWorked;
    private double hourlyRate;

    public Employee(int id, String department, String name, String position, String qualification, int hoursWorked, double hourlyRate) {
        this.id = id;
        this.department = department;
        this.name = name;
        this.position = position;
        this.qualification = qualification;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getId() { return id; }
    public String getDepartment() { return department; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getQualification() { return qualification; }
    public int getHoursWorked() { return hoursWorked; }
    public double getHourlyRate() { return hourlyRate; }

    public double getTotalPayment() {
        return hoursWorked * hourlyRate;
    }
}
