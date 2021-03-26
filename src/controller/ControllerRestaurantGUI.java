package controller;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Window;
import model.*;

public class ControllerRestaurantGUI implements Initializable {
    // DATE AND HOUR
    @FXML
    private Label lblHour;

    private String hours, minutes, seconds;

    @FXML
    private Label lblDate;
    // RENDER PANE
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Circle btnCloseLogin;

    @FXML
    private Pane mainPane;

    // PRODUCTS

    // REDUX

    private Restaurant restaurant;
    private UserController userController;
    private DashController dashController;
    private CostumerController costumerController;
    private EmployeeController employeeController;
    private IngredientsController ingredientController;
    private ProductTypeController productTypeController;
    private ProductController productController;
    private ProductSizeController productSizeController;

    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        userController = new UserController(restaurant, this);
        dashController = new DashController(restaurant, this);
        costumerController = new CostumerController(restaurant, this);
        employeeController = new EmployeeController(restaurant, this);
        ingredientController = new IngredientsController(restaurant, this);
        productTypeController = new ProductTypeController(restaurant, this);
        productController = new ProductController(restaurant, this);
        productSizeController = new ProductSizeController(restaurant, this);
    }

    // HOUR AND DATE

    public void hour() {
        Calendar calendar = new GregorianCalendar();
        Date currentTime = new Date();
        calendar.setTime(currentTime);
        hours = calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendar.get(Calendar.HOUR_OF_DAY)
                : "0" + calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE) > 9 ? "" + calendar.get(Calendar.MINUTE)
                : "0" + calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND) > 9 ? "" + calendar.get(Calendar.SECOND)
                : "0" + calendar.get(Calendar.SECOND);
    }

    public String date() {
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY");
        return formatDate.format(date);
    }
    // ALL ABOUT USERS

    public void welcomeToLogin() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
        fxmlLoader.setController(userController);
        Parent login = fxmlLoader.load();
        mainPane.getChildren().setAll(login);
        if (restaurant.getPeopleFile().length() > 0 & restaurant.usersDisabled() == false) {
            userController.disableButton();
        } else {
            userController.enableButton();
        }
    }

    public Window getPane() {
        return mainPane.getScene().getWindow();
    }

    public Parent showItem() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/Item.fxml"));
        Parent root = fxmlloader.load();
        return root;
    }

    public void showDashBoard() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
        fxmlloader.setController(dashController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        dashController.initUser();
        restaurant.loadIngredients();
        restaurant.loadProductType();
        dashController.initDashoboard();
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        }
    }

    // PRODUCTS

    public void showProducts() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listProducts.fxml"));
        fxmlloader.setController(productController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        productController.initComboIngredientBox();
        productController.initProductTable();
        productController.initStateProduct();
    }

    // PRODUCT TYPE

    public void showProductTypes() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listTypeProducts.fxml"));
        fxmlloader.setController(productTypeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadProductType();
        productTypeController.initProductTypeTable();
    }

    // PRODUCT SIZE

    public void showProductSizes() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listProductSize.fxml"));
        fxmlloader.setController(productSizeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadProductSize();
        productSizeController.initProductSizeTable();
    }

    // INGREDIENTS

    void showIngredients() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/listIngredients.fxml"));
        fxmlLoader.setController(ingredientController);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadIngredients();
        ingredientController.initIngredientsTable();
    }

    // COSTUMERS

    public void showCostumers() throws IOException, ClassNotFoundException {
        restaurant.loadPeople();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listCostumers.fxml"));
        fxmlloader.setController(costumerController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    // EMPLOYEES

    public void showEmployees() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listEmployees.fxml"));
        fxmlloader.setController(employeeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadPeople();
        employeeController.initEmployeeTable();
    }

    // USERS

    public void showUsers() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listUsers.fxml"));
        fxmlloader.setController(userController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadPeople();
        userController.initUserTable();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lblDate.setText(date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    hour();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblHour.setText(hours + ":" + minutes + ":" + seconds);
                        }
                    });
                }
            }
        }).start();
    }

}
