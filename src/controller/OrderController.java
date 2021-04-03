package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.*;

public class OrderController {

    @FXML
    private TableView<Order> listOrders;

    @FXML
    private TableColumn<Order, Integer> colCodeOrders;

    @FXML
    private TableColumn<Order, String> colDateOrders;

    @FXML
    private TableColumn<Order, String> colHourOrder;

    @FXML
    private TableColumn<Order, State> colStateOrders;

    @FXML
    private TableColumn<Order, String> colProductOrder;

    @FXML
    private TableColumn<Order, String> colAmountOfProducts;

    @FXML
    private TableColumn<Order, Costumer> colCostumerNameOrders;

    @FXML
    private TableColumn<Order, Employee> colEmployeeOrders;

    @FXML
    private TableColumn<Order, Double> colTotalOrder;

    @FXML
    private TableColumn<Order, String> colSuggestionsOrder;

    @FXML
    private TableColumn<Order, User> colCreatorOrders;

    @FXML
    private TableColumn<Order, User> colLastEditorOrders;

    @FXML
    private TextField txtCostumerNameOrders;

    @FXML
    private ComboBox<State> comboBoxStateOrder;

    @FXML
    private TextField txtAmountProducts;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField separator;

    @FXML
    private ComboBox<Employee> comboBoxEmployeeOrders;

    @FXML
    private ComboBox<Product> comboProductOrders;

    @FXML
    private ComboBox<Costumer> comboBoxCostumers;

    @FXML
    private Button btnCart;

    @FXML
    private Button btnClean;

    @FXML
    private TextArea txtAreaAmountProduct;

    @FXML
    private TextArea txtAreaCostumerInfo;

    @FXML
    private TextField txtTotalToPay;

    @FXML
    private TextField txtSuggestionsOrder;
    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private Button btnGenerate;

    private Employee preEmployee;
    private List<Product> preProduct = new ArrayList<>();
    private Product preProd;
    private State preState;
    private List<Integer> preAmount = new ArrayList<>();
    private Costumer preCostumer;
    private Order preSelectOrder;
    private int idxOrder;

    private Restaurant restaurant;
    private Stage modal;
    private ControllerRestaurantGUI cGui;

    public OrderController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public Employee getPreEmployee() {
        return this.preEmployee;
    }

    public void setPreEmployee(Employee preEmployee) {
        this.preEmployee = preEmployee;
    }

    public List<Product> getPreProduct() {
        return this.preProduct;
    }

    public Order getPreSelectOrder() {
        return this.preSelectOrder;
    }

    public void setPreSelectOrder(Order preSelectOrder) {
        this.preSelectOrder = preSelectOrder;
    }

    public int getIdxOrder() {
        return this.idxOrder;
    }

    public void setIdxOrder(int idxOrder) {
        this.idxOrder = idxOrder;
    }

    public void setPreProduct(List<Product> preProduct) {
        this.preProduct = preProduct;
    }

    public State getPreState() {
        return this.preState;
    }

    public void setPreState(State preState) {
        this.preState = preState;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public List<Integer> getPreAmount() {
        return this.preAmount;
    }

    public void setPreAmount(List<Integer> preAmount) {
        this.preAmount = preAmount;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ControllerRestaurantGUI getCGui() {
        return this.cGui;
    }

    public void setCGui(ControllerRestaurantGUI cGui) {
        this.cGui = cGui;
    }

    @FXML
    public void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    public void createOrder(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        boolean validateFields = orderValidation(txtSuggestionsOrder.getText(), txtTotalToPay.getText(),
                txtAreaAmountProduct.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a order");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addOrders(restaurant.getCodeOrder(), preState, preProduct, preAmount, preCostumer,
                    preEmployee, cGui.date(), txtSuggestionsOrder.getText(),
                    restaurant.getLoggedUser(restaurant.getUserIndex()), Double.parseDouble(txtTotalToPay.getText()),
                    cGui.getHour());
            alert.setContentText(msg);
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            trimOrderForm();
            initOrderTable();
        }
    }

    public boolean orderValidation(String suggestion, String totalPrice, String products) {
        boolean complete = true;
        if (comboBoxCostumers.getSelectionModel().getSelectedItem() == null
                || comboBoxEmployeeOrders.getSelectionModel().getSelectedItem() == null
                || comboBoxStateOrder.getSelectionModel().getSelectedItem() == null || products.equals("")
                || totalPrice.equals("") || suggestion.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimOrderForm() {
        txtTime.setText("");
        txtAreaCostumerInfo.setText("");
        comboBoxStateOrder.setValue(null);
        comboBoxCostumers.setValue(null);
        preProduct.clear();
        comboProductOrders.setValue(null);
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnClean.setDisable(true);
        comboBoxEmployeeOrders.setValue(null);
        txtAreaAmountProduct.setText("");
        preAmount.clear();
        txtTotalToPay.setText("");
        txtSuggestionsOrder.setText("");
        txtAmountProducts.setText("");
    }

    @FXML
    public void cleanProducts(ActionEvent event) {
        preProduct.clear();
        txtAreaAmountProduct.setText("");
        preAmount.clear();
        txtAmountProducts.setText("");
        txtTotalToPay.setText("");
        btnClean.setDisable(true);
    }

    public void initBtnClear() {
        if (txtAreaAmountProduct.getText() != "") {
            btnClean.setDisable(false);
        }
    }

    @FXML
    public void searchCostumer(ActionEvent event) {
        Long startTime = System.nanoTime();
        Costumer c = restaurant.searchBinary(preCostumer.getId(), restaurant.sortCostumerById());
        txtAreaCostumerInfo.setText(
                "Nombre: " + c.getName() + " Apellido: " + c.getLastName() + " ID: " + c.getId() + " Telephone: "
                        + c.getTelephone() + " Address: " + c.getAddress() + " Suggestions: " + c.getSuggestions());
        Long endTime = System.nanoTime() - startTime;
        txtTime.setText(String.valueOf(endTime));
    }

    @FXML
    public void updateOrder(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setOrderInfo(preSelectOrder, cGui.date(), cGui.getHour(), preState, preProduct,
                preAmount, preCostumer, preEmployee, txtSuggestionsOrder.getText(),
                restaurant.getLoggedUser(restaurant.getUserIndex()), Double.parseDouble(txtTotalToPay.getText()));
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        trimOrderForm();
        setPreSelectOrder(null);
        initOrderTable();
    }

    @FXML
    public void selectedOrder(MouseEvent event) {
        Order sltOrder = listOrders.getSelectionModel().getSelectedItem();
        if (sltOrder != null) {
            int idxProduct = listOrders.getSelectionModel().getSelectedIndex();
            setIdxOrder(idxProduct);
            setPreSelectOrder(sltOrder);
            setForm(sltOrder);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            initBtnClear();
        }
    }

    public void setForm(Order selectOrder) {
        txtSuggestionsOrder.setText(selectOrder.getSuggestion());
        txtAreaAmountProduct.setText(selectOrder.getInvoice());
        txtTotalToPay.setText(String.valueOf(selectOrder.getTotalPrice()));
        Costumer c = selectOrder.getCostumer();
        txtAreaCostumerInfo.setText(
                "Nombre: " + c.getName() + " Apellido: " + c.getLastName() + " ID: " + c.getId() + " Telephone: "
                        + c.getTelephone() + " Address: " + c.getAddress() + " Suggestions: " + c.getSuggestions());
        comboBoxCostumers.setValue(selectOrder.getCostumer());
        comboBoxEmployeeOrders.setValue(selectOrder.getEmployee());
        comboBoxStateOrder.setValue(selectOrder.getState());
        preProduct = selectOrder.getProducts();
        preAmount = selectOrder.getAmount();
    }

    @FXML
    public void deleteOrder(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteOrder(getIdxOrder(), preSelectOrder.getState());
        alert.setContentText(msg);
        alert.showAndWait();
        if (msg.equals("The Order have been deleted succesfully")) {
            trimOrderForm();
            restaurant.saveData();
            restaurant.loadData();
            initOrderTable();
        }
    }

    @FXML
    public void deselectOrder(ActionEvent event) {
        trimOrderForm();
    }

    @FXML
    public void exportOrders(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export Order");
            restaurant.exportDataOrder(selectedFile.getAbsolutePath(), separator.getText());
            alert.setContentText("The Order data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export Order");
            alert.setContentText("The Order data was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    public void importOrders(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import Order");
            restaurant.importDataOrder(selectedFile.getAbsolutePath());
            alert.setContentText("The order data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initOrderTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import order");
            alert.setContentText("The order data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    public void addToCart(ActionEvent event) {
        if (preProd != null && !txtAmountProducts.getText().equals("")) {
            preAmount.add(Integer.parseInt(txtAmountProducts.getText()));
            preProduct.add(preProd);
            int total = 0;
            String msg = "";
            for (int i = 0; i < preProduct.size(); i++) {
                msg += "Cantidad: " + preAmount.get(i) + " " + "Producto: " + preProduct.get(i) + " " + "PU: "
                        + preProduct.get(i).getPrice() + " ";
                total += preProduct.get(i).getPrice() * preAmount.get(i);
            }
            txtAreaAmountProduct.setText(msg);
            txtAmountProducts.setText("");
            comboProductOrders.setValue(null);
            txtTotalToPay.setText(String.valueOf(total));
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Complete Fields");
            alert.setContentText("Hey you need to complete either the product a its amount");
            alert.showAndWait();
        }
    }

    public void initStateOrder() {
        ObservableList<State> comBoxState = FXCollections.observableArrayList(State.REQUESTED, State.IN_PROCCESS,
                State.SENT, State.DELIVERED, State.CANCELED);
        comboBoxStateOrder.setItems(comBoxState);
    }

    public void initEmployeeOrder() {
        ObservableList<Employee> comBoxEmployee = FXCollections.observableArrayList(restaurant.getEnableEmployees());
        comboBoxEmployeeOrders.setItems(comBoxEmployee);
    }

    public void initProductOrder() {
        ObservableList<Product> comBoxProduct = FXCollections.observableArrayList(restaurant.getEnableProducts());
        comboProductOrders.setItems(comBoxProduct);
    }

    public void initCostumerOrder() {
        ObservableList<Costumer> comBoxCostumer = FXCollections.observableArrayList(restaurant.getEnableCostumers());
        comboBoxCostumers.setItems(comBoxCostumer);
    }

    @FXML
    public void comboAction(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(comboProductOrders)) {
            preProd = (restaurant.searchIndex(comboProductOrders.getSelectionModel().getSelectedItem()));
            initBtnClear();
        }
        if (e.equals(comboBoxStateOrder)) {
            preState = comboBoxStateOrder.getSelectionModel().getSelectedItem();
        }
        if (e.equals(comboBoxEmployeeOrders)) {
            preEmployee = comboBoxEmployeeOrders.getSelectionModel().getSelectedItem();
        }
        if (e.equals(comboBoxCostumers)) {
            preCostumer = comboBoxCostumers.getSelectionModel().getSelectedItem();

        }
    }

    public void getModal(Window mod) {
        modal = (Stage) mod.getScene().getWindow();
    }

    @FXML
    public void closeModal(MouseEvent event) {
        modal.close();
    }

    @FXML
    public void event(ActionEvent event) {
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
        if (dateStart.getValue() != null && dateEnd.getValue() != null) {
            if (dateEnd.getValue()
                    .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0
                    || dateStart.getValue()
                            .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0) {
                btnGenerate.setDisable(true);
            } else if (dateStart.getValue().compareTo(dateEnd.getValue()) < 0) {
                btnGenerate.setDisable(false);
            } else if (dateStart.getValue().compareTo(dateEnd.getValue()) > 0) {
                btnGenerate.setDisable(true);
            } else {
                btnGenerate.setDisable(false);
            }
        }
    }

    public void initbtnReportTime() {
        if (dateStart.getValue() != null && dateEnd.getValue() != null && startTime.getValue() != null
                && endTime.getValue() != null) {
            if (dateEnd.getValue()
                    .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) == 0
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
    public void generateReport(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Report orders");
            restaurant.exportOrdersReport(selectedFile.getAbsolutePath(), separator.getText(),
                    getFilteredOrders(dateStart, dateEnd, startTime, endTime));
            alert.setContentText("The Orders report was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Report Orders");
            alert.setContentText("The Orders report was NOT exported. An error occurred");
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
                if (localTime.compareTo(st.getValue()) >= 0 && localTime.compareTo(et.getValue()) <= 0) {
                    o.add(restaurant.getOrders().get(i));
                }
            }
        }
        return o;
    }

    public void initOrderTable() throws IOException {
        ObservableList<Order> orders = FXCollections.observableArrayList(restaurant.getOrders());
        listOrders.setItems(orders);
        colCodeOrders.setCellValueFactory(new PropertyValueFactory<Order, Integer>("code"));
        colDateOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
        colHourOrder.setCellValueFactory(new PropertyValueFactory<Order, String>("hour"));
        colStateOrders.setCellValueFactory(new PropertyValueFactory<Order, State>("state"));
        colProductOrder.setCellValueFactory(new PropertyValueFactory<Order, String>("nameProducts"));
        colAmountOfProducts.setCellValueFactory(new PropertyValueFactory<Order, String>("nameAmount"));
        colCostumerNameOrders.setCellValueFactory(new PropertyValueFactory<Order, Costumer>("costumer"));
        colEmployeeOrders.setCellValueFactory(new PropertyValueFactory<Order, Employee>("employee"));
        colTotalOrder.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalPrice"));
        colSuggestionsOrder.setCellValueFactory(new PropertyValueFactory<Order, String>("suggestion"));
        colCreatorOrders.setCellValueFactory(new PropertyValueFactory<Order, User>("creator"));
        colLastEditorOrders.setCellValueFactory(new PropertyValueFactory<Order, User>("lastEditor"));
    }

}
