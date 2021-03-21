package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;

public class EmployeeController {

    private Employee preSelectEmployee;

    private int idxEmployee;

    @FXML
    private TableView<Employee> listEmployees;

    @FXML
    private TableColumn<Employee, String> colNameEmployee;

    @FXML
    private TableColumn<Employee, String> colLastNameEmployee;

    @FXML
    private TableColumn<Employee, Integer> colIDEmployee;

    @FXML
    private TableColumn<Employee, String> colCreatorEmployee;

    @FXML
    private TableColumn<Employee, String> colLastEditorEmployee;

    @FXML
    private TextField txtNameEmployee;

    @FXML
    private TextField txtLastNameEmployee;

    @FXML
    private TextField txtIDEmployee;

    @FXML
    private Button btnCreate;

    @FXML
    private CheckBox cbDisableEmployee;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public EmployeeController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public Employee getPreSelectEmployee() {
        return this.preSelectEmployee;
    }

    public int getIdxEmployee() {
        return this.idxEmployee;
    }

    public void setPreSelectEmployee(Employee preSelectEmployee) {
        this.preSelectEmployee = preSelectEmployee;
    }

    public void setIdxEmployee(int idxEmployee) {
        this.idxEmployee = idxEmployee;
    }

    @FXML
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    void createEmployee(ActionEvent event) throws FileNotFoundException, IOException {
        boolean validateFields = employeeValidation(txtNameEmployee.getText(), txtLastNameEmployee.getText(),
                txtIDEmployee.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addEmployee(txtNameEmployee.getText(), txtLastNameEmployee.getText(),
                    Integer.parseInt(txtIDEmployee.getText()));
            alert.setContentText(msg);
            trimEmployeeForm();
            alert.showAndWait();
            restaurant.saveEmployees();
            initEmployeeTable();
        }
    }

    public boolean employeeValidation(String name, String lastName, String id) {
        boolean complete = true;
        if (name.equals("") || lastName.equals("") || id.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimEmployeeForm() {
        txtNameEmployee.setText("");
        txtLastNameEmployee.setText("");
        txtIDEmployee.setText("");
        btnCreate.setDisable(false);
    }

    @FXML
    void selectedEmployee(MouseEvent event) {
        Employee sltEmployee = listEmployees.getSelectionModel().getSelectedItem();
        if (sltEmployee != null) {
            int idxUser = listEmployees.getSelectionModel().getSelectedIndex();
            setIdxEmployee(idxUser);
            setPreSelectEmployee(sltEmployee);
            setForm(sltEmployee);
            btnCreate.setDisable(true);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("The row selected does not have any costumer");
            alert.showAndWait();
        }
    }

    public void setForm(Employee sltEmployee) {
        txtNameEmployee.setText(sltEmployee.getName());
        txtLastNameEmployee.setText(sltEmployee.getLastName());
        txtIDEmployee.setText(String.valueOf(sltEmployee.getId()));
        cbDisableEmployee.setSelected(!sltEmployee.getState());
    }

    @FXML
    void deselectEmployee(ActionEvent event) {
        trimEmployeeForm();
    }

    @FXML
    void exportEmployee(ActionEvent event) {

    }

    @FXML
    void importEmployee(ActionEvent event) {

    }

    @FXML
    void setStateEmployee(ActionEvent event) throws FileNotFoundException, IOException {
        String msg = "";
        if (cbDisableEmployee.isSelected()) {
            msg = restaurant.disableEmployee(preSelectEmployee);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableEmployee(preSelectEmployee);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimEmployeeForm();
        restaurant.saveEmployees();
        initEmployeeTable();
    }

    @FXML
    void updateEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        restaurant.setInfoEmployee(getPreSelectEmployee(), txtNameEmployee.getText(), txtLastNameEmployee.getText(),
                Integer.parseInt(txtIDEmployee.getText()), restaurant.getLoggedUser().getName());
        restaurant.saveEmployees();
        restaurant.loadEmployees();
        trimEmployeeForm();
        setPreSelectEmployee(null);
        initEmployeeTable();
    }

    @FXML
    void deleteEmployee(ActionEvent event) throws FileNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteUser(getIdxEmployee());
        alert.setContentText(msg);
        trimEmployeeForm();
        alert.showAndWait();
        restaurant.saveEmployees();
        initEmployeeTable();
    }

    public void initEmployeeTable() throws IOException {
        ObservableList<Employee> employees = FXCollections.observableArrayList(restaurant.getEmployees());
        listEmployees.setItems(employees);
        colNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        colLastNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colIDEmployee.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colCreatorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("creator"));
        colLastEditorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastEditor"));
    }

}
