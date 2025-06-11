package org.example;

import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {
    private EmployeeRepository repo;
    private TableView<Employee> table;

    private void refreshTable() throws SQLException {
        table.setItems(FXCollections.observableArrayList(repo.getAll()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        repo = new EmployeeRepository();
        table = new TableView<>();

        // Колонки
        table.getColumns().addAll(
                new TableColumn<Employee, String>("ПІБ") {{ setCellValueFactory(new PropertyValueFactory<>("name")); }},
                new TableColumn<Employee, String>("Відділ") {{ setCellValueFactory(new PropertyValueFactory<>("department")); }},
                new TableColumn<Employee, String>("Посада") {{ setCellValueFactory(new PropertyValueFactory<>("position")); }},
                new TableColumn<Employee, String>("Кваліфікація") {{ setCellValueFactory(new PropertyValueFactory<>("qualification")); }},
                new TableColumn<Employee, Integer>("Години") {{ setCellValueFactory(new PropertyValueFactory<>("hoursWorked")); }},
                new TableColumn<Employee, Double>("Ставка") {{ setCellValueFactory(new PropertyValueFactory<>("hourlyRate")); }},
                new TableColumn<Employee, Double>("До виплати") {{ setCellValueFactory(new PropertyValueFactory<>("totalPayment")); }}
        );

        // Поля вводу
        TextField tfName = new TextField(); tfName.setPromptText("ПІБ");
        TextField tfDept = new TextField(); tfDept.setPromptText("Відділ");
        TextField tfPos = new TextField(); tfPos.setPromptText("Посада");
        TextField tfQual = new TextField(); tfQual.setPromptText("Кваліфікація");
        TextField tfHours = new TextField(); tfHours.setPromptText("Години");
        TextField tfRate = new TextField(); tfRate.setPromptText("Ставка");

        Button btnAdd = new Button("Додати");
        btnAdd.setOnAction(e -> {
            try {
                Employee emp = new Employee(0, tfDept.getText(), tfName.getText(), tfPos.getText(), tfQual.getText(),
                        Integer.parseInt(tfHours.getText()), Double.parseDouble(tfRate.getText()));
                repo.add(emp);
                refreshTable();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnDelete = new Button("Видалити");
        btnDelete.setOnAction(e -> {
            var selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    repo.delete(selected.getId());
                    refreshTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        TextField tfSearchPos = new TextField(); tfSearchPos.setPromptText("Пошук: Посада");
        TextField tfSearchQual = new TextField(); tfSearchQual.setPromptText("Кваліфікація");
        Button btnSearch = new Button("Знайти");
        btnSearch.setOnAction(e -> {
            try {
                var result = repo.search(tfSearchPos.getText(), tfSearchQual.getText());
                table.setItems(FXCollections.observableArrayList(result));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnRefresh = new Button("Оновити");
        btnRefresh.setOnAction(e -> {
            try {
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        HBox actions = new HBox(5, btnAdd, btnDelete, btnSearch, btnRefresh);
        HBox searchFields = new HBox(5, tfSearchPos, tfSearchQual);
        VBox form = new VBox(5, tfName, tfDept, tfPos, tfQual, tfHours, tfRate, actions, searchFields);
        VBox root = new VBox(10, table, form);

        refreshTable();

        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setTitle("Employee Info System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
