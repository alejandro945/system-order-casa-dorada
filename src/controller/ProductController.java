package controller;

import java.io.File;
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
import javafx.stage.FileChooser;
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

    @FXML
    private TextField separator;

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
    public void createProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        boolean validateFields = productValidation(txtNameProducts.getText(), txtIngredients.getText(),
                txtPriceProducts.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a product");
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
        preListIngredients.clear();
        comboIngredients.setValue(null);
        txtIngredients.setText("");
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
        restaurant.loadData();
        trimProductForm();
        initProductTable();
    }

    @FXML
    public void deleteProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProduct(getIdxProduct(), preSelectProduct.getName(),
                String.valueOf(preSelectProduct.getProductType()));
        alert.setContentText(msg);
        alert.showAndWait();
        if (msg.equals("The product have been deleted succesfully")) {
            trimProductForm();
            restaurant.saveData();
            initProductTable();
        }
    }

    @FXML
    void deselectProduct(ActionEvent event) {
        trimProductForm();
    }

    @FXML
    void exportProducts(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export product");
            restaurant.exportDataProduct(selectedFile.getAbsolutePath(), separator.getText());
            alert.setContentText("The product data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export product");
            alert.setContentText("The product data was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    void importProducts(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import product");
            restaurant.importDataProduct(selectedFile.getAbsolutePath());
            alert.setContentText("The product data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initProductTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import product");
            alert.setContentText("The product data was NOT imported. An error occurred");
            alert.showAndWait();
        }
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
            initBtnClear();
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
        txtIngredients.setText("");
        preListIngredients.clear();
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

    @FXML
    void sortByPrice(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("The Products have been sort succesfully");
        alert.showAndWait();
        restaurant.sortProductByPrice();
        restaurant.saveData();
        initProductTable();
    }

    public void initProductTable() throws IOException {
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
