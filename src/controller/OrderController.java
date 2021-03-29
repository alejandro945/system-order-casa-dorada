package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

    private Employee preEmployee;
    private List<Product> preProduct = new ArrayList<>();
    private State preState;
    private List<Integer> preAmount = new ArrayList<>();
    private Costumer preCostumer;
    private int idxCostumer;

    private Order preSelectOrder;
    private int idxOrder;
    private Restaurant restaurant;

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
    void comboAction(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(comboProductOrders)) {
            preProduct.add(restaurant.searchIndex(comboProductOrders.getSelectionModel().getSelectedItem()));
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
            idxCostumer = comboBoxCostumers.getSelectionModel().getSelectedIndex();
        }
    }

    @FXML
    void addToCart(ActionEvent event) {
        preAmount.add(Integer.parseInt(txtAmountProducts.getText()));
        int total = 0;
        String msg = "";
        for (int i = 0; i < preAmount.size(); i++) {
            msg += "Cantidad: " + preAmount.get(i) + " " + "Producto: " + preProduct.get(i) + " " + "PU: "
                    + preProduct.get(i).getPrice() + " ";
            total += preProduct.get(i).getPrice() * preAmount.get(i);
        }
        txtAreaAmountProduct.setText(msg);
        txtTotalToPay.setText(String.valueOf(total));
    }

    @FXML
    void cleanProducts(ActionEvent event) {
        preProduct.clear();
        txtAreaAmountProduct.setText("");
        preAmount.clear();
        txtTotalToPay.setText("");
        btnClean.setDisable(true);
    }

    public void initBtnClear() {
        if (txtAreaAmountProduct.getText() != "") {
            btnClean.setDisable(false);
        }
    }

    @FXML
    void searchCostumer(ActionEvent event) {
        Long startTime = System.nanoTime();
        Costumer c = restaurant.getC(idxCostumer);
        txtAreaCostumerInfo.setText(
                "Nombre: " + c.getName() + " Apellido: " + c.getLastName() + " ID: " + c.getId() + " Telephone: "
                        + c.getTelephone() + " Address: " + c.getAddress() + " Suggestions: " + c.getSuggestions());
        Long endTime = System.nanoTime() - startTime;
        txtTime.setText(String.valueOf(endTime));
    }

    @FXML
    void createOrder(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
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

    @FXML
    void selectedOrder(MouseEvent event) {
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

    public void trimOrderForm() {
        txtTime.setText("");
        txtAreaCostumerInfo.setText("");
        comboBoxStateOrder.setValue(null);
        comboBoxCostumers.setValue(null);
        comboBoxEmployeeOrders.setValue(null);
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnClean.setDisable(true);
        preProduct.clear();
        txtAreaAmountProduct.setText("");
        preAmount.clear();
        txtTotalToPay.setText("");
        txtSuggestionsOrder.setText("");
        txtAmountProducts.setText("");
    }

    @FXML
    void updateOrder(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setOrderInfo(preSelectOrder, preState, preProduct, preAmount, preCostumer, preEmployee,
                cGui.date(), txtSuggestionsOrder.getText(), restaurant.getLoggedUser(restaurant.getUserIndex()),
                Double.parseDouble(txtTotalToPay.getText()), cGui.getHour());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        trimOrderForm();
        setPreSelectOrder(null);
        initOrderTable();
    }

    @FXML
    void deselectOrder(ActionEvent event) {
        trimOrderForm();
    }

    @FXML
    void deleteOrder(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteOrder(getIdxOrder());
        alert.setContentText(msg);
        trimOrderForm();
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        initOrderTable();
    }

    @FXML
    void exportOrders(ActionEvent event) {

    }

    @FXML
    void importOrders(ActionEvent event) {

    }

    public void initStateOrder() {
        ObservableList<State> comBoxState = FXCollections.observableArrayList(State.REQUESTED, State.IN_PROCCESS,
                State.SENT, State.DELIVERED);
        comboBoxStateOrder.setItems(comBoxState);
    }

    public void initEmployeeOrder() {
        ObservableList<Employee> comBoxEmployee = FXCollections
                .observableArrayList(restaurant.getEmployees(restaurant.getPeople()));
        comboBoxEmployeeOrders.setItems(comBoxEmployee);
    }

    public void initProductOrder() {
        ObservableList<Product> comBoxProduct = FXCollections.observableArrayList(restaurant.getEnableProducts());
        comboProductOrders.setItems(comBoxProduct);
    }

    public void initCostumerOrder() {
        ObservableList<Costumer> comBoxCostumer = FXCollections
                .observableArrayList(restaurant.getCostumers(restaurant.getPeople()));
        comboBoxCostumers.setItems(comBoxCostumer);
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
