package controller;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TableView<Product> listProducts;

    @FXML
    private TableColumn<Product, Integer> colPriceProducts;

    @FXML
    private TableColumn<Product, String> colNameProducts;

    @FXML
    private TableColumn<Product, String> colProductType;

    @FXML
    private TableColumn<Product, Ingredients> colIngredientsProducts;

    @FXML
    private TableColumn<Product, String> colSizeProducts;

    @FXML
    private TableColumn<Product, User> colCreatorProducts;

    @FXML
    private TextField txtNameProducts;

    @FXML
    private TextField txtSizeProducts;

    @FXML
    private TextField txtPriceProducts;

    @FXML
    private ComboBox<String> comboIngredients;

    @FXML
    private Label showMessage;

    @FXML
    private ComboBox<String> comboProductType;

    // INGREDIENTS

    @FXML
    private TableView<Ingredients> listIngredients;

    @FXML
    private TableColumn<Ingredients, String> colNameIngredients;

    @FXML
    private TableColumn<Ingredients, User> colCreatorIngredients;

    @FXML
    private TableColumn<Ingredients, User> colEditorIngredients;

    @FXML
    private TextField txtNameIngredients;

    // REDUX

    private Restaurant restaurant;
    private UserController userController;
    private DashController dashController;
    private CostumerController costumerController;
    private EmployeeController employeeController;
    private IngredientsController ingredientController;

    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        userController = new UserController(restaurant, this);
        dashController = new DashController(restaurant, this);
        costumerController = new CostumerController(restaurant, this);
        employeeController = new EmployeeController(restaurant, this);
        ingredientController = new IngredientsController(restaurant, this);
    }

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
        dashController.initDashoboard();
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        }
    }

    public void showUsers() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listUsers.fxml"));
        fxmlloader.setController(userController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadPeople();
        userController.initUserTable();
    }

    // PRODUCTS

    @FXML
    public void createProducts(ActionEvent event) {

    }

    @FXML
    public void deleteProducts(ActionEvent event) {

    }

    @FXML
    public void disableProducts(ActionEvent event) {

    }

    @FXML
    public void updateProducts(ActionEvent event) {

    }

    @FXML
    public void showProducts(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listProducts.fxml"));
        fxmlloader.setController(this);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        initProductTable();
    }

    @FXML
    public void selectedProduct(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product auxProduct = listProducts.getSelectionModel().getSelectedItem();

        }
    }

    /*
     * @FXML void importProducts(ActionEvent event) throws IOException { FileChooser
     * fc = new FileChooser(); File selectedFile =
     * fc.showOpenDialog(mainPane.getScene().getWindow()); if (selectedFile != null)
     * { Alert alert = new Alert(AlertType.INFORMATION);
     * alert.setTitle("Import products");
     * restaurant.importDataProducts(selectedFile.getAbsolutePath());
     * alert.setContentText("The products data was imported succesfully");
     * alert.showAndWait(); showProducts(event); } else { Alert alert = new
     * Alert(AlertType.ERROR); alert.setTitle("Import products");
     * alert.setContentText("The products data was NOT imported. An error occurred"
     * ); alert.showAndWait(); } }
     */

    public void initProductTable() throws IOException {
        ObservableList<Product> products = FXCollections.observableArrayList(restaurant.getProducts());
        listProducts.setItems(products);
        colPriceProducts.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colNameProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductType.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        colIngredientsProducts.setCellValueFactory(new PropertyValueFactory<Product, Ingredients>("ingredients"));
        colSizeProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("size"));
        colCreatorProducts.setCellValueFactory(new PropertyValueFactory<Product, User>("creator"));
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

    public void initComboIngredientBox() {
        ObservableList<String> ingredientBox = FXCollections.observableArrayList(restaurant.getIngredientsFormated());
        comboIngredients.setValue("Select an option");
        comboIngredients.setItems(ingredientBox);
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
