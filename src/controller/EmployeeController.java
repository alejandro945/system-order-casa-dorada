package controller;

import java.io.*;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    private TableColumn<Employee, User> colCreatorEmployee;

    @FXML
    private TableColumn<Employee, User> colLastEditorEmployee;

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
    private TextField separator;

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
                    Integer.parseInt(txtIDEmployee.getText()), restaurant.getLoggedUser(restaurant.getUserIndex()));
            alert.setContentText(msg);
            trimEmployeeForm();
            alert.showAndWait();
            restaurant.saveData();
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
        restaurant.saveData();
        initEmployeeTable();
    }

    @FXML
    void updateEmployee(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setInfoEmployee(getPreSelectEmployee(), txtNameEmployee.getText(),
                txtLastNameEmployee.getText(), Integer.parseInt(txtIDEmployee.getText()),
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
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
        restaurant.saveData();
        initEmployeeTable();
    }

    @FXML
    void deselectEmployee(ActionEvent event) {
        trimEmployeeForm();
    }

    @FXML
    void exportEmployee(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export employees");
            restaurant.exportDataEmployees(selectedFile.getAbsolutePath(), separator.getText());
            alert.setContentText("The employees data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export Employees");
            alert.setContentText("The employees data was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    void importEmployee(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import employees");
            restaurant.importDataEmployees(selectedFile.getAbsolutePath());
            alert.setContentText("The employees data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initEmployeeTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import employees");
            alert.setContentText("The employees data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public void initEmployeeTable() throws IOException {
        ObservableList<Employee> employees = FXCollections
                .observableArrayList(restaurant.getEmployees(restaurant.getPeople()));
        listEmployees.setItems(employees);
        colNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        colLastNameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colIDEmployee.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colCreatorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, User>("creator"));
        colLastEditorEmployee.setCellValueFactory(new PropertyValueFactory<Employee, User>("lastEditor"));
    }

}
