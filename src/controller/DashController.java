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
import javafx.scene.input.MouseEvent;
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
    void showEmployeesReport(ActionEvent event) throws IOException {
        cGui.showEmployeesReport();
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
    void showProductsReport(ActionEvent event) throws IOException {
        cGui.showProductsReport();
    }

    @FXML
    void showBaseProduct(ActionEvent event) throws IOException {
        cGui.showBaseProducts();
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
    void showOrders(ActionEvent event) throws IOException {
        cGui.showOrders();
    }

    @FXML
    void showOrdersReport(ActionEvent event) throws IOException {
        cGui.showOrdersReport();
    }

    @FXML
    void github(MouseEvent event) throws IOException {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows"))
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "https://github.com/alejandro945");
        else if (osName.contains("Linux"))
            Runtime.getRuntime().exec("xdg-open " + "https://github.com/alejandro945");
        else if (osName.contains("Mac OS X"))
            Runtime.getRuntime().exec("open " + "https://github.com/alejandro945");
    }

    @SuppressWarnings("unchecked")
    public void initDashoboard() {
        XYChart.Series<String, Integer> set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data<>("Users", restaurant.countUsers()));
        set1.getData().add(new XYChart.Data<>("Employees", restaurant.countEmployees()));
        set1.getData().add(new XYChart.Data<>("Costumers", restaurant.countCostumers()));
        set1.getData().add(new XYChart.Data<>("Ingredients", restaurant.getNumberIngredients()));
        set1.getData().add(new XYChart.Data<>("Types", restaurant.getNumberProductType()));
        set1.getData().add(new XYChart.Data<>("Sizes", restaurant.getNumberProductSize()));
        set1.getData().add(new XYChart.Data<>("Products", restaurant.getNumberProduct()));
        set1.getData().add(new XYChart.Data<>("Orders", restaurant.getNumberOrders()));
        barChart.getData().addAll(set1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
