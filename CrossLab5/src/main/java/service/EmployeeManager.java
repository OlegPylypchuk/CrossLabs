package service;

import com.google.gson.*;
import util.RuntimeTypeAdapterFactory;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private final Gson gson;

    public EmployeeManager() {
        // Реєстрація підкласів для серіалізації
        RuntimeTypeAdapterFactory<Employee> adapterFactory = RuntimeTypeAdapterFactory
                .of(Employee.class, "type")
                .registerSubtype(Manager.class, "Manager")
                .registerSubtype(Developer.class, "Developer")
                .registerSubtype(Intern.class, "Intern");

        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapterFactory(adapterFactory)
                .create();
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void removeEmployee(int index) {
        if (index >= 0 && index < employees.size()) {
            employees.remove(index);
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(employees, writer);
        }
    }

    public void loadFromFile(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Employee[] loaded = gson.fromJson(reader, Employee[].class);
            employees = new ArrayList<>(List.of(loaded));
        } catch (Exception e) {
            employees = new ArrayList<>();
        }
    }
}
