package controller;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.*;

public class ControllerRestaurantGUI implements Initializable {
    // -----------DATE AND HOUR----------
    private String hours, minutes, seconds;
    @FXML
    private Label lblHour;
    @FXML
    private Label lblDate;

    // ----------RENDER PANE----------
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Circle btnCloseLogin;
    @FXML
    private Pane mainPane;

    private Restaurant restaurant;
    private UserController userController;
    private DashController dashController;
    private CostumerController costumerController;
    private EmployeeController employeeController;
    private IngredientsController ingredientController;
    private ProductTypeController productTypeController;
    private BaseProductController baseProductController;
    private ProductController productController;
    private ProductSizeController productSizeController;
    private OrderController orderController;

    // ------------------------------------------------CONSTRUCTOR----------------------------------------------------
    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        userController = new UserController(restaurant, this);
        dashController = new DashController(restaurant, this);
        costumerController = new CostumerController(restaurant, this);
        employeeController = new EmployeeController(restaurant, this);
        ingredientController = new IngredientsController(restaurant, this);
        productTypeController = new ProductTypeController(restaurant, this);
        productController = new ProductController(restaurant, this);
        baseProductController = new BaseProductController(restaurant, this);
        productSizeController = new ProductSizeController(restaurant, this);
        orderController = new OrderController(restaurant, this);
    }

    // ----------------------------------------------HOUR-AND-DATE-THREAD----------------------------------------
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

    public String getHour() {
        return lblHour.getText();
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

    // ---------------------------------------------SCREENS--MANAGER-----------------------------------------
    public Window getPane() {
        return mainPane.getScene().getWindow();
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        }
    }

    // -----------------------------------------------LOGIN-FORM----------------------------------------
    public void welcomeToLogin() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/screens/login.fxml"));
        fxmlLoader.setController(userController);
        Parent login = fxmlLoader.load();
        mainPane.getChildren().setAll(login);
        if (restaurant.getDataFile().length() > 0 & restaurant.usersDisabled() == false) {
            userController.disableButton();
        } else {
            userController.enableButton();
        }
        userController.initAdmin();
    }

    // -----------------------------------------------DASHBOARD----------------------------------------
    public void showDashBoard() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/home.fxml"));
        fxmlloader.setController(dashController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        dashController.initUser();
        restaurant.loadData();
        restaurant.loadData();
        dashController.initDashoboard();
    }

    // ----------------------------------------------COSTUMERS------------------------------------------
    public void showCostumers() throws IOException, ClassNotFoundException {
        restaurant.loadData();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listCostumers.fxml"));
        fxmlloader.setController(costumerController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    // -----------------------------------------------EMPLOYEES------------------------------------------

    public void showEmployees() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listEmployees.fxml"));
        fxmlloader.setController(employeeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadData();
        employeeController.initEmployeeTable();

    }

    public void showEmployeesReport() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/employeesReport.fxml"));
        fxmlloader.setController(employeeController);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        employeeController.getModal(window);
        employeeController.initReport();
    }

    // ------------------------------------------------USERS---------------------------------------------

    public void showUsers() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listUsers.fxml"));
        fxmlloader.setController(userController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadData();
        userController.initUserTable();
    }

    // ----------------------------------------------BASE-PRODUCT--------------------------------------------
    public void showBaseProducts() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listBaseProduct.fxml"));
        fxmlloader.setController(baseProductController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        baseProductController.initComboIngredientBox();
        baseProductController.initComboTypesBox();
        baseProductController.initBaseProductTable();
    }

    // ----------------------------------------------PRODUCTS--------------------------------------------

    public void showProducts() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listProducts.fxml"));
        fxmlloader.setController(productController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        productController.initComboSizesBox();
        productController.initComboBaseBox();
        productController.initProductTable();
    }

    public void showProductsReport() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/productsReport.fxml"));
        fxmlloader.setController(productController);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        productController.getModal(window);
        productController.initReport();
    }

    // --------------------------------------------PRODUCT-TYPE-------------------------------------------

    public void showProductTypes() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listTypeProducts.fxml"));
        fxmlloader.setController(productTypeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadData();
        productTypeController.initProductTypeTable();
    }

    // ---------------------------------------------PRODUCT-SIZE-------------------------------------------

    public void showProductSizes() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listProductSize.fxml"));
        fxmlloader.setController(productSizeController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadData();
        productSizeController.initProductSizeTable();
    }

    // ----------------------------------------------INGREDIENTS-------------------------------------------

    void showIngredients() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/screens/listIngredients.fxml"));
        fxmlLoader.setController(ingredientController);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadData();
        ingredientController.initIngredientsTable();
    }

    // -------------------------------------------------ORDERS----------------------------------------------

    public void showOrders() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/listOrders.fxml"));
        fxmlloader.setController(orderController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        orderController.initStateOrder();
        orderController.initEmployeeOrder();
        orderController.initProductOrder();
        orderController.initCostumerOrder();
        orderController.initOrderTable();
    }

    public void showOrdersReport() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/ordersReport.fxml"));
        fxmlloader.setController(orderController);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        orderController.getModal(window);
        orderController.initReport();
    }

}
