package controller;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import model.*;

public class ControllerRestaurantGUI implements Initializable {
    // RENDER PANE
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

    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        userController = new UserController(restaurant, this);
        dashController = new DashController(restaurant, this);
        costumerController = new CostumerController(restaurant, this);
    }

    //ALL ABOUT USERS

    public void welcomeToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/login2.fxml"));
        fxmlLoader.setController(userController);
        Parent login = fxmlLoader.load();
        mainPane.getChildren().setAll(login);
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
    void showProducts(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listProducts.fxml"));
        fxmlloader.setController(this);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        initProductTable();
    }

    @FXML
    void selectedProduct(MouseEvent event) {
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

    @FXML
    public void setIngredient(ActionEvent event) {

    }

    /*
     * @FXML public void createIngredients(ActionEvent event) throws IOException {
     * restaurant.addIngrendient(txtNameIngredients.getText(),
     * restaurant.getLoggedUser()); Alert alert = new Alert(AlertType.CONFIRMATION);
     * alert.setHeaderText("The ingredient have been added succesfully");
     * alert.showAndWait(); initIngredietTable(); }
     */

    @FXML
    public void deleteIngredients(ActionEvent event) {
        // restaurant.deleteIngredient(positionIngredient);
    }

    @FXML
    public void disableIntegredients(ActionEvent event) {

    }

    @FXML
    public void updateIngredients(ActionEvent event) {
        // restaurant.setInfoIngredient(ingredient, name, lastEditor)
    }

    @FXML
    void showIngredientsCrud(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/listIngredients.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    public void initComboIngredientBox() {
        ObservableList<String> ingredientBox = FXCollections.observableArrayList(restaurant.getIngredientsFormated());
        comboIngredients.setValue("Select an option");
        comboIngredients.setItems(ingredientBox);
    }

    public void initIngredietTable() throws IOException {
        ObservableList<Ingredients> ingredients = FXCollections.observableArrayList(restaurant.getIngredients());
        listIngredients.setItems(ingredients);
        colNameIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("name"));
        colCreatorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("creator"));
        colEditorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("lastEditor"));
    }

    // COSTUMERS

    public void showCostumers() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/listCostumers.fxml"));
        fxmlloader.setController(costumerController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        restaurant.loadCostumers();
        costumerController.initCostumersTable();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
}
