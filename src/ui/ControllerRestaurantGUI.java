package ui;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.*;

public class ControllerRestaurantGUI implements Initializable {
    // LOGIN
    @FXML
    private PasswordField txtPasswordLogin;

    @FXML
    private TextField txtNameUserLogin;
    // SIGNUP
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
    private ImageView imgRegister;
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
    public Restaurant restaurant;
    private String pathRender;

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
    public void showRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    @FXML
    public void showLogIn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    @FXML
    void showIngredientsCrud(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listIngredients.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    @FXML
    public void logIn(ActionEvent event) throws IOException {
        User user = restaurant.userVerification(txtNameUserLogin.getText(), txtPasswordLogin.getText());
        if (user != null) {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("dashBoard.fxml"));
            fxmlloader.setController(this);
            Parent root = fxmlloader.load();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(root);
            txtNameUserRegister.setText("");
            txtPasswordRegister.setText("");
            restaurant.setLoggedUser(user);
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
    public void createAccount(ActionEvent event) throws IOException {
        boolean validateFields = registerValidation(txtNameRegister.getText(), txtLastNameRegister.getText(),
                txtIDRegister.getText(), txtNameUserRegister.getText(), txtPasswordRegister.getText(), this.pathRender);
        if (!validateFields) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Hey!! Please complete all fields for create your account");
            alert.showAndWait();
        } else if (txtPasswordRegister.getText().length() < 8) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Ups!! The password is weak, it must be at least 8 characters");
            alert.showAndWait();
        } else if (!restaurant.searchUser(txtNameUserRegister.getText())) {
            restaurant.addNewUser(txtNameRegister.getText(), txtLastNameRegister.getText(),
                    Integer.parseInt(txtIDRegister.getText()), txtNameUserRegister.getText(),
                    txtPasswordRegister.getText(), pathRender);
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
            trimRegisterTxt();
        } else {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("Warning Dialog");
            alert3.setHeaderText("Error");
            alert3.setContentText("The user already exist");
            alert3.showAndWait();
        }
    }

    public boolean registerValidation(String name, String lastName, String id, String userName, String password,
            String pathRender) {
        boolean complete = true;
        if (name.equals("") || lastName.equals("") || id.equals("") || userName.equals("") || password.equals("")
                || pathRender.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimRegisterTxt() {
        txtNameRegister.setText("");
        txtLastNameRegister.setText("");
        txtIDRegister.setText("");
        txtNameUserRegister.setText("");
        txtPasswordRegister.setText("");
        pathRender = "";
        imgRegister.setImage(null);
    }

    @FXML
    public void fileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imgRegister.setImage(new Image(selectedFile.toURI().toString()));
            pathRender = selectedFile.getPath();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Image not found");
            alert.setHeaderText("Path does not exist");
            alert.showAndWait();
        }
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

    public void initComboIngredientBox() {
        ObservableList<String> ingredientBox = FXCollections.observableArrayList(restaurant.getIngredientsFormated());
        comboIngredients.setValue("Select an option");
        comboIngredients.setItems(ingredientBox);
    }

    @FXML
    void setIngredient(ActionEvent event) {

    }

    @FXML
    void createIngredients(ActionEvent event) throws IOException {
        restaurant.addIngrendient(txtNameIngredients.getText(), restaurant.getLoggedUser());
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("The ingredient have been added succesfully");
        alert.showAndWait();
        initIngredietTable();
    }

    @FXML
    void deleteIngredients(ActionEvent event) {
        // restaurant.deleteIngredient(positionIngredient);
    }

    @FXML
    void disableIntegredients(ActionEvent event) {

    }

    @FXML
    void updateIngredients(ActionEvent event) {
        // restaurant.setInfoIngredient(ingredient, name, lastEditor)
    }

    public void initIngredietTable() throws IOException {
        ObservableList<Ingredients> ingredients = FXCollections.observableArrayList(restaurant.getIngredients());
        listIngredients.setItems(ingredients);
        colNameIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("name"));
        colCreatorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("creator"));
        colEditorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("lastEditor"));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
}
