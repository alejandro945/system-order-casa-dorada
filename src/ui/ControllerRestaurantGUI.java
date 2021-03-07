package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import model.*;

public class ControllerRestaurantGUI implements Initializable {

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
    private TableColumn<Product, String> colIngredientsProducts;

    @FXML
    private TableColumn<Product, String> colSizeProducts;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

   
    @FXML
    void actualizeProducts(ActionEvent event) {

    }

    @FXML
    void addProducts(ActionEvent event) {

    }

    @FXML
    void deleteProducts(ActionEvent event) {

    }

    @FXML
    void inhabilitProducts(ActionEvent event) {

    }

}
