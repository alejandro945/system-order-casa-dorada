package controller;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTimePicker;

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
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private Button btnGenerate;

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

    @FXML
    void generateReport(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Report employees");
            restaurant.exportEmployeeReport(selectedFile.getAbsolutePath(), separator.getText(),
                    getFilteredOrders(dateStart, dateEnd, startTime, endTime));
            alert.setContentText("The employees report was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Report Employees");
            alert.setContentText("The employees report was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    public List<Order> getFilteredOrders(DatePicker sd, DatePicker ed, JFXTimePicker st, JFXTimePicker et) {
        List<Order> o = new ArrayList<>();
        for (int i = 0; i < restaurant.getOrders().size(); i++) {
            LocalDate localDate = LocalDate.parse(restaurant.getOrders().get(i).getDate(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime localTime = LocalTime.parse(restaurant.getOrders().get(i).getHour());
            if (localDate.compareTo(sd.getValue()) >= 0 && localDate.compareTo(ed.getValue()) <= 0) {
                if (localDate.compareTo(sd.getValue()) == 0 && localTime.compareTo(st.getValue()) >= 0) {
                    o.add(restaurant.getOrders().get(i));
                } else if (localDate.compareTo(ed.getValue()) == 0 && localTime.compareTo(et.getValue()) <= 0) {
                    o.add(restaurant.getOrders().get(i));
                } else if (localDate.compareTo(sd.getValue()) > 0 && localDate.compareTo(ed.getValue()) < 0) {
                    o.add(restaurant.getOrders().get(i));
                }
            }
        }
        return o;
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
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
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
    void event(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(dateStart)) {
            initbtnReportDate();
        }
        if (e.equals(dateEnd)) {
            initbtnReportDate();
        }
        if (e.equals(startTime)) {
            initbtnReportTime();
        }
        if (e.equals(endTime)) {
            initbtnReportTime();
        }
    }

    public void initbtnReportDate() {
        if (dateEnd.getValue().compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0) {
            btnGenerate.setDisable(true);
        } else if (dateStart.getValue().compareTo(dateEnd.getValue()) < 0) {
            btnGenerate.setDisable(false);
        } else if (dateStart.getValue().compareTo(dateEnd.getValue()) > 0) {
            btnGenerate.setDisable(true);
        } else {
            btnGenerate.setDisable(false);
        }
    }

    public void initbtnReportTime() {
        if (dateEnd.getValue().compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) == 0
                && endTime.getValue().compareTo(LocalTime.parse(cGui.getHour())) > 0) {
            btnGenerate.setDisable(true);
        } else if (dateStart.getValue().compareTo(dateEnd.getValue()) < 0) {
            btnGenerate.setDisable(false);
        } else if (startTime.getValue().compareTo(endTime.getValue()) < 0) {
            btnGenerate.setDisable(false);
        } else if (startTime.getValue().compareTo(endTime.getValue()) > 0) {
            btnGenerate.setDisable(true);
        }
    }

    public void initReport() {
        String date = cGui.date();
        String hour = cGui.getHour();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime localTime1 = LocalTime.parse("00:00:00");
        LocalTime localTime2 = LocalTime.parse(hour);
        dateStart.setValue(localDate);
        dateEnd.setValue(localDate);
        startTime.setValue(localTime1);
        endTime.setValue(localTime2);
    }

    @FXML
    void importEmployee(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
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
