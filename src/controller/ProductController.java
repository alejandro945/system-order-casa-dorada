package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private TableColumn<Product, String> colIngredientsProducts;

    @FXML
    private TableColumn<Product, ProductSize> colSizeProducts;

    @FXML
    private TableColumn<Product, User> colCreatorProducts;

    @FXML
    private TableColumn<Product, User> colEditorProducts;

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
    private List<Ingredients> preListIngredients = new ArrayList<>();
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
    void setStateProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableProduct(preSelectProduct);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableProduct(preSelectProduct);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        restaurant.saveData();
        trimProductForm();
        initProductTable();
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
            restaurant.saveData();
            restaurant.loadData();
            trimProductForm();
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
    public void updateProducts(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setProductInfo(getPreSelectProduct(), txtNameProducts.getText(), cbTypes.getValue(),
                preListIngredients, getIdxProduct(), cbSize.getValue(), Double.parseDouble(txtPriceProducts.getText()),
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        trimProductForm();
        setPreSelectProduct(null);
        initProductTable();
    }

    @FXML
    public void selectedProduct(MouseEvent event) {
        Product sltProduct = listProducts.getSelectionModel().getSelectedItem();
        if (sltProduct != null) {
            int idxProduct = listProducts.getSelectionModel().getSelectedIndex();
            setIdxProduct(idxProduct);
            setPreSelectProduct(sltProduct);
            setForm(sltProduct);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
            initBtnClear();
        }
    }

    public void setForm(Product selectProduct) {
        txtNameProducts.setText(selectProduct.getName());
        txtPriceProducts.setText(String.valueOf(selectProduct.getPrice()));
        cbSize.setValue(selectProduct.getProductSize());
        cbTypes.setValue(selectProduct.getProductType());
        txtIngredients.setText(selectProduct.getNameIngredients());
        preListIngredients = selectProduct.getIngredients();
        cbDisable.setSelected(!selectProduct.getState());
    }

    @FXML
    public void deleteProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProduct(getIdxProduct());
        alert.setContentText(msg);
        trimProductForm();
        alert.showAndWait();
        restaurant.saveData();
        initProductTable();
    }

    @FXML
    void deselectProduct(ActionEvent event) {
        trimProductForm();
    }

    @FXML
    void exportProducts(ActionEvent event) {

    }

    @FXML
    void importProducts(ActionEvent event) {

    }

    public void initBtnClear() {
        if (txtIngredients.getText() != "") {
            btnClean.setDisable(false);
        }
    }

    @FXML
    void comboEvent(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(comboIngredients)) {
            preListIngredients.add(restaurant.searchIndex(comboIngredients.getSelectionModel().getSelectedItem()));
            txtIngredients.setText(Arrays.toString(preListIngredients.toArray()));
            initBtnClear();
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
        comboIngredients.getItems().addAll(restaurant.getEnableIngredients());
    }

    public void initComboSizesBox() {
        cbSize.getItems().addAll(restaurant.getEnableProductSizes());
    }

    public void initComboTypesBox() {
        cbTypes.getItems().addAll(restaurant.getEnableProductTypes());
    }

    public void initProductTable() throws IOException {
        restaurant.sortProductByPrice();
        ObservableList<Product> products = FXCollections.observableArrayList(restaurant.getProducts());
        listProducts.setItems(products);
        colcode.setCellValueFactory(new PropertyValueFactory<Product, Integer>("code"));
        colPriceProducts.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colNameProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductType.setCellValueFactory(new PropertyValueFactory<Product, ProductType>("productType"));
        colIngredientsProducts.setCellValueFactory(new PropertyValueFactory<Product, String>("nameIngredients"));
        colSizeProducts.setCellValueFactory(new PropertyValueFactory<Product, ProductSize>("productSize"));
        colCreatorProducts.setCellValueFactory(new PropertyValueFactory<Product, User>("creator"));
        colEditorProducts.setCellValueFactory(new PropertyValueFactory<Product, User>("lastEditor"));
    }
}
