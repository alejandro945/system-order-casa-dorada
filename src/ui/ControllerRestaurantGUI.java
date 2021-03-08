package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.layout.Pane;

import model.*;

public class ControllerRestaurantGUI implements Initializable {

    @FXML
    private PasswordField txtPasswordLogin;

    @FXML
    private TextField txtNameUserLogin;

    @FXML
    private PasswordField txtPasswordRegister;

    @FXML
    private TextField txtNameRegister;

    @FXML
    private TextField txtLastNameRegister;

    @FXML
    private TextField txtIDRegister;

    @FXML
    private TextField txtNameUserRegister;

    @FXML
    private Pane mainPane;

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
    private TableColumn<Product, ProductSize> colSizeProducts;

    @FXML
    private TableColumn<Product, User> colCreatorProducts;

    @FXML
    private TextField txtNameProducts;

    @FXML
    private TextField txtProductType;

    @FXML
    private TextField txtIngredientsProducts;

    @FXML
    private TextField txtSizeProducts;

    @FXML
    private TextField txtPriceProducts;

    public Restaurant restaurant;

    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void welcomeToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        Parent login = fxmlLoader.load();
        mainPane.getChildren().setAll(login);
    }

    @FXML
    public void logIn(ActionEvent event) throws IOException {
        User user = restaurant.userVerification(txtNameUserRegister.getText(), txtPasswordRegister.getText());
        if (user != null) {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("dashBoard.fxml"));
            fxmlloader.setController(this);
            Parent root = fxmlloader.load();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(root);
            txtNameUserRegister.setText("");
            txtPasswordRegister.setText("");
            // this.lblName.setText(user.getUserName()); implementar label and user icon
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Name of users and/or password invalid");
            alert.showAndWait();
        }
    }

    @FXML
    public void showRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    @FXML
    public void createAccount(ActionEvent event) throws IOException {
        if (txtPasswordRegister.getText().length() < 8) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("The password is weak");
            alert.showAndWait();
        } else if (!restaurant.searchUser(txtNameUserRegister.getText())) {
            restaurant.addNewUser(txtNameRegister.getText(), txtLastNameRegister.getText(),
                Integer.parseInt(txtIDRegister.getText()), txtNameUserRegister.getText(),
                txtPasswordRegister.getText());
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText("Look, Consider the following");
                alert.setContentText("Are you sure to save this user?");
                Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Redux and React");
                alert2.setHeaderText("The User " + txtNameUserRegister.getText() + " have been added sucesfully");
                alert2.setContentText("Take it easy bro!");
                alert2.showAndWait();
            }
            welcomeToLogin();
        } else {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("Warning Dialog");
            alert3.setHeaderText("Error");
            alert3.setContentText("The user already exist");
            alert3.showAndWait();
        }
    }

    @FXML
    public void showLogIn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    void createProducts(ActionEvent event) {

    }

    @FXML
    void deleteProducts(ActionEvent event) {

    }

    @FXML
    void disableProducts(ActionEvent event) {

    }

    @FXML
    void updateProducts(ActionEvent event) {

    }

    public void initTable() throws IOException{
        ObservableList<Product> products = FXCollections.observableArrayList(restaurant.getProducts());
        listProducts.setItems(products);
        colPriceProducts.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colNameProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductType.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        colIngredientsProducts.setCellValueFactory(new PropertyValueFactory<Product, Ingredients>("ingredients"));
        colSizeProducts.setCellValueFactory(new PropertyValueFactory<Product, ProductSize>("size"));
        colCreatorProducts.setCellValueFactory(new PropertyValueFactory<Product, User>("creator"));
    }


}
