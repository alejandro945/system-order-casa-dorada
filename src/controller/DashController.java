package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import model.Restaurant;

public class DashController implements Initializable {
    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblId;

    @FXML
    private ImageView userIcon;

    @FXML
    private VBox pnl_scroll;

    @FXML
    private Label lbl_currentprojects;

    @FXML
    private Label lbl_pending;

    @FXML
    private Label lbl_completed;

    public DashController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.welcomeToLogin();
    }

    public void initUser() {
        File file = new File(restaurant.getLoggedUser(restaurant.getUserIndex()).getImage());
        userIcon.setImage(new Image("file:///" + file.getAbsolutePath()));
        lblUser.setText(restaurant.getLoggedUser(restaurant.getUserIndex()).getName());
        lblId.setText(String.valueOf(restaurant.getLoggedUser(restaurant.getUserIndex()).getId()));
    }

    @FXML
    public void showListCostumers(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showCostumers();
    }

    @FXML
    public void showListUsers(ActionEvent event) throws ClassNotFoundException, IOException {
        cGui.showUsers();
    }

    @FXML
    void showListEmployees(ActionEvent event) throws ClassNotFoundException, IOException {
        cGui.showEmployees();
    }

    @FXML
    void showIngredients(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showIngredients();
    }

    @FXML
    void showProducts(ActionEvent event) throws IOException {
        cGui.showProducts();
    }

    @FXML
    void showProductTypes(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showProductTypes();
    }

    @FXML
    void showProductSizes(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showProductSizes();
    }

    @FXML
    void showOrders(ActionEvent event) {

    }

    @SuppressWarnings("unchecked")
    public void initDashoboard() {
        XYChart.Series<String, Integer> set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data<>("Users", restaurant.getUsers()));
        set1.getData().add(new XYChart.Data<>("Employees", restaurant.getEmployees()));
        set1.getData().add(new XYChart.Data<>("Costumers", restaurant.getCostumers()));
        set1.getData().add(new XYChart.Data<>("Ingredients", restaurant.getNumberIngredients()));
        set1.getData().add(new XYChart.Data<>("Product Types", restaurant.getNumberProductType()));
        set1.getData().add(new XYChart.Data<>("Product Size", restaurant.getNumberProductSize()));
        barChart.getData().addAll(set1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
