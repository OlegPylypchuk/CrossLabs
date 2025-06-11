package ui;

import model.*;
import service.EmployeeManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EmployeeApp extends JFrame {
    private EmployeeManager manager = new EmployeeManager();
    private DefaultListModel<Employee> listModel = new DefaultListModel<>();
    private JList<Employee> employeeList = new JList<>(listModel);
    private final String FILE_NAME = "employees.json";

    public EmployeeApp() {
        setTitle("Система обліку зарплатні");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            manager.loadFromFile(FILE_NAME);
            for (Employee e : manager.getEmployees()) {
                listModel.addElement(e);
            }
        } catch (Exception e) {
            System.out.println("Файл не знайдено або помилка читання.");
        }

        JButton addBtn = new JButton("Додати");
        JButton removeBtn = new JButton("Видалити");
        JButton salaryBtn = new JButton("Розрахувати зарплату");
        JButton saveBtn = new JButton("Зберегти");

        addBtn.addActionListener(e -> {
            String[] options = {"Manager", "Developer", "Intern"};
            String choice = (String) JOptionPane.showInputDialog(this, "Посада:", "Виберіть", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            String name = JOptionPane.showInputDialog("Ім'я:");
            int id = listModel.size() + 1;

            Employee emp = null;

            switch (choice) {
                case "Manager" -> {
                    double base = Double.parseDouble(JOptionPane.showInputDialog("Базова ставка:"));
                    emp = new Manager(name, id, base);
                }
                case "Developer" -> {
                    int hours = Integer.parseInt(JOptionPane.showInputDialog("Кількість годин:"));
                    double rate = Double.parseDouble(JOptionPane.showInputDialog("Оплата за годину:"));
                    emp = new Developer(name, id, hours, rate);
                }
                case "Intern" -> {
                    double stipend = Double.parseDouble(JOptionPane.showInputDialog("Стипендія:"));
                    emp = new Intern(name, id, stipend);
                }
            }

            if (emp != null) {
                manager.addEmployee(emp);
                listModel.addElement(emp);
            }
        });

        removeBtn.addActionListener(e -> {
            int index = employeeList.getSelectedIndex();
            if (index != -1) {
                manager.removeEmployee(index);
                listModel.remove(index);
            }
        });

        salaryBtn.addActionListener(e -> {
            Employee selected = employeeList.getSelectedValue();
            if (selected != null) {
                JOptionPane.showMessageDialog(this, "Зарплата: " + selected.calculateSalary());
            }
        });

        saveBtn.addActionListener(e -> {
            try {
                manager.saveToFile(FILE_NAME);
                JOptionPane.showMessageDialog(this, "Збережено у файл.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Помилка збереження.");
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(salaryBtn);
        buttons.add(saveBtn);

        add(new JScrollPane(employeeList), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeApp().setVisible(true));
    }
}
