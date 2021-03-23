package controller;

import java.io.*;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;

public class EmployeeController {

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
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private CheckBox cbDisableEmployee;

    private Employee preSelectEmployee;
    private int idxEmployee;

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
    void createEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
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
            String msg = restaurant.addPerson(txtNameEmployee.getText(), txtLastNameEmployee.getText(),
                    Integer.parseInt(txtIDEmployee.getText()), restaurant.getLoggedUser().getName());
            alert.setContentText(msg);
            trimEmployeeForm();
            alert.showAndWait();
            restaurant.savePeople();
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
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        cbDisableEmployee.setDisable(true);
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
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisableEmployee.setDisable(false);
        }
    }

    public void setForm(Employee sltEmployee) {
        txtNameEmployee.setText(sltEmployee.getName());
        txtLastNameEmployee.setText(sltEmployee.getLastName());
        txtIDEmployee.setText(String.valueOf(sltEmployee.getId()));
        cbDisableEmployee.setSelected(!sltEmployee.getState());
    }

    @FXML
    void setStateEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
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
        restaurant.savePeople();
        initEmployeeTable();
    }

    @FXML
    void updateEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setInfoEmployee(getPreSelectEmployee(), txtNameEmployee.getText(),
                txtLastNameEmployee.getText(), Integer.parseInt(txtIDEmployee.getText()),
                restaurant.getLoggedUser().getName());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.savePeople();
        restaurant.loadPeople();
        trimEmployeeForm();
        setPreSelectEmployee(null);
        initEmployeeTable();
    }

    @FXML
    void deleteEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteEmployee(getPreSelectEmployee());
        alert.setContentText(msg);
        trimEmployeeForm();
        alert.showAndWait();
        restaurant.savePeople();
        initEmployeeTable();
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

    public void initEmployeeTable() throws IOException {
        ObservableList<Employee> employees = FXCollections
                .observableArrayList(restaurant.getEmployees(restaurant.getPeople()));
        listEmployees.setItems(employees);
        colNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        colLastNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colIDEmployee.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colCreatorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("creator"));
        colLastEditorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastEditor"));
    }

}
