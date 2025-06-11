package model;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    protected String name;
    protected int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
