package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
    private TableColumn<Product, ObservableList<Ingredients>> colIngredientsProducts = new TableColumn<>("ingredients");

    @FXML
    private TableColumn<Product, ProductSize> colSizeProducts;

    @FXML
    private TableColumn<Product, User> colCreatorProducts;

    @FXML
    private TableColumn<Product, User> colEditorProduct;

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
    private Button btnClean;

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
    private ProductSize preProductSize;
    private ProductType preProductType;

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
    public void createProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        boolean validateFields = productValidation(txtNameProducts.getText(), txtIngredients.getText(),
                txtPriceProducts.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addProduct(txtNameProducts.getText(), preProductType, preListIngredients,
                    restaurant.getCodeProduct(), preProductSize, Double.parseDouble(txtPriceProducts.getText()),
                    restaurant.getLoggedUser(restaurant.getUserIndex()));
            alert.setContentText(msg);
            alert.showAndWait();
            trimProductForm();
            restaurant.saveData();
            initProductTable();
        }
    }

    public boolean productValidation(String name, String ingredients, String price) {
        boolean complete = true;
        if (name.equals("") || ingredients.equals("") || price.equals("")
                || cbSize.getSelectionModel().getSelectedItem() == null
                || cbTypes.getSelectionModel().getSelectedItem() == null) {
            complete = false;
        }
        return complete;
    }

    public void trimProductForm() {
        txtNameProducts.setText("");
        txtPriceProducts.setText("");
        txtIngredients.setText("");
        preListIngredients.clear();
        cbSize.setValue(null);
        cbTypes.setValue(null);
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnClean.setDisable(true);
        cbDisable.setDisable(true);
    }

    @FXML
    public void updateProducts(ActionEvent event) {

    }

    @FXML
    public void selectedProduct(MouseEvent event) {

    }

    @FXML
    public void deleteProducts(ActionEvent event) {

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
            btnClean.setDisable(false);
            preListIngredients.add(restaurant.searchIndex(comboIngredients.getSelectionModel().getSelectedItem()));
            txtIngredients.setText(Arrays.toString(preListIngredients.toArray()));
        }
        if (e.equals(cbSize)) {
            preProductSize = cbSize.getSelectionModel().getSelectedItem();
        }
        if (e.equals(cbTypes)) {
            preProductType = cbTypes.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void cleanIngredients(ActionEvent event) {
        preListIngredients.clear();
        txtIngredients.setText("");
        btnClean.setDisable(true);
    }

    public void initComboIngredientBox() {
        comboIngredients.getItems().addAll(restaurant.getIngredients());
    }

    public void initComboSizesBox() {
        cbSize.getItems().addAll(restaurant.getProductSize());
    }

    public void initComboTypesBox() {
        cbTypes.getItems().addAll(restaurant.getProductType());
    }

    public void initProductTable() throws IOException {
        ObservableList<Product> products = FXCollections.observableArrayList(restaurant.getProducts());
        listProducts.setItems(products);
        colcode.setCellValueFactory(new PropertyValueFactory<Product, Integer>("code"));
        colPriceProducts.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colNameProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductType.setCellValueFactory(new PropertyValueFactory<Product, ProductType>("productType"));
        colIngredientsProducts.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        colSizeProducts.setCellValueFactory(new PropertyValueFactory<Product, ProductSize>("productSize"));
        colCreatorProducts.setCellValueFactory(new PropertyValueFactory<Product, User>("creator"));
        colEditorProduct.setCellValueFactory(new PropertyValueFactory<Product, User>("lastEditor"));
    }
}
