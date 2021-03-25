package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    private TableColumn<Product, Integer> colcode;

    @FXML
    private TableColumn<Product, Integer> colPriceProducts;

    @FXML
    private TableColumn<Product, String> colNameProducts;

    @FXML
    private TableColumn<Product, ProductType> colProductType;

    @FXML
    private TableColumn<Product, Ingredients> colIngredientsProducts;

    @FXML
    private TableColumn<Product, ProductSize> colSizeProducts;

    @FXML
    private TableColumn<Product, String> colCreatorProducts;

    @FXML
    private TableColumn<Product, String> colEditorProduct;

    @FXML
    private TextField txtNameProducts;

    @FXML
    private TextField txtPriceProducts;

    @FXML
    private Label showMessage;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnCreate;

    @FXML
    private CheckBox cbDisable;

    @FXML
    private TextArea txtIngredients;

    @FXML
    private ComboBox<Ingredients> comboIngredients;

    @FXML
    private ComboBox<ProductType> cbTypes;

    @FXML
    private ComboBox<ProductSize> cbSize;

    private Product preSelectProduct;
    private int idxProduct;
    private ArrayList<Ingredients> preListIngredients = new ArrayList<>();

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
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    void setStateProducts(ActionEvent event) {

    }

    @FXML
    public void createProducts(ActionEvent event) {

    }

    @FXML
    public void deleteProducts(ActionEvent event) {

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

    }

    @FXML
    void deselectProduct(ActionEvent event) {

    }

    @FXML
    void exportProducts(ActionEvent event) {

    }

    @FXML
    void importProducts(ActionEvent event) {

    }

    @FXML
    void comboEvent(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(comboIngredients)) {
            preListIngredients.add(comboIngredients.getSelectionModel().getSelectedItem());
            txtIngredients.setText(Arrays.toString(preListIngredients.toArray()));
        }
    }

    @FXML
    void cleanIngredients(ActionEvent event) {

    }

    public void initComboIngredientBox() {
        comboIngredients.getItems().addAll(restaurant.getIngredients());
    }

    public void initProductTable() throws IOException {
        ObservableList<Product> products = FXCollections.observableArrayList(restaurant.getProducts());
        listProducts.setItems(products);
        colcode.setCellValueFactory(new PropertyValueFactory<Product, Integer>("code"));
        colPriceProducts.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colNameProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductType.setCellValueFactory(new PropertyValueFactory<Product, ProductType>("type"));
        colIngredientsProducts.setCellValueFactory(new PropertyValueFactory<Product, Ingredients>("ingredients"));
        colSizeProducts.setCellValueFactory(new PropertyValueFactory<Product, ProductSize>("productSize"));
        colCreatorProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("creator"));
        colEditorProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("lastEditor"));
    }
}
