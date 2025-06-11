package model;

public class Manager extends Employee {
    private double baseSalary;

    public Manager(String name, int id, double baseSalary) {
        super(name, id);
        this.baseSalary = baseSalary;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + 2000;
    }
}
