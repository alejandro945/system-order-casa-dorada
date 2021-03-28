package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.*;

public class OrderController {

    @FXML
    private TableView<?> listOrders;

    @FXML
    private TableColumn<Order, Integer> colCodeOrders;

    @FXML
    private TableColumn<Order, String> colStateOrders;

    @FXML
    private TableColumn<Order, List<Product>> colProductOrder;

    @FXML
    private TableColumn<Order, Integer> colAmountOfProducts;

    @FXML
    private TableColumn<Order, List<Costumer>> colCostumerNameOrders;

    @FXML
    private TableColumn<Order, List<Employee>> colEmployeeOrders;

    @FXML
    private TableColumn<Order, String> colDateOrders;

    @FXML
    private TableColumn<Order, User> colCreatorOrders;

    @FXML
    private TableColumn<Order, User> colLastEditorOrders;

    @FXML
    private TextField txtCostumerNameOrders;

    @FXML
    private TextField txtEmployeeOrders;

    @FXML
    private TextField txtDateOrders;

    @FXML
    private ComboBox<String> comboBoxStateOrder;

    @FXML
    private TextField txtAmountProducts;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private CheckBox cbDisable;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public OrderController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
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
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    void searchCostumer(ActionEvent event) {

    }


    @FXML
    void createOrder(ActionEvent event) {

    }

    @FXML
    void updateOrder(ActionEvent event) {

    }

    @FXML
    void deselectOrder(ActionEvent event) {

    }

    @FXML
    void deleteOrder(ActionEvent event) {

    }

    @FXML
    void exportOrders(ActionEvent event) {

    }

    @FXML
    void importOrders(ActionEvent event) {

    }

    @FXML
    void setStateOrder(ActionEvent event) {

    }

    public void initStateOrder() {
        ObservableList<String> comBox = FXCollections.observableArrayList("Select an option", "REQUESTED", "IN PROCCES",
                "SEND", "DELIVERED");
        comboBoxStateOrder.setValue("Select an option");
        comboBoxStateOrder.setItems(comBox);
    }
}
