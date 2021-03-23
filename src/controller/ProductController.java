package controller;

import java.io.IOException;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;

public class ProductController {

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

    private Product preSelectProduct;
    private int idxProduct;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public ProductController(Restaurant restaurant, ControllerRestaurantGUI controllerRestaurantGUI) {
        this.restaurant = restaurant;
        this.cGui = controllerRestaurantGUI;
    }


    public Product getPreSelectProduct() {
        return this.preSelectProduct;
    }

    public void setPreSelectProduct(Product preSelectProduct) {
        this.preSelectProduct = preSelectProduct;
    }

    public int getIdxProduct() {
        return this.idxProduct;
    }

    public void setIdxProduct(int idxProduct) {
        this.idxProduct = idxProduct;
    }


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

    @FXML
    public void selectedProduct(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product auxProduct = listProducts.getSelectionModel().getSelectedItem();

        }
    }

    public void initComboIngredientBox() {
        ObservableList<String> ingredientBox = FXCollections.observableArrayList(restaurant.getIngredientsFormated());
        comboIngredients.setValue("Select an option");
        comboIngredients.setItems(ingredientBox);
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
}
