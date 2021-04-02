package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import model.*;
import java.util.*;

public class BaseProductController {

    @FXML
    private TableView<BaseProduct> listBaseProducts;

    @FXML
    private TableColumn<BaseProduct, Integer> colCode;

    @FXML
    private TableColumn<BaseProduct, String> colNameBaseProducts;

    @FXML
    private TableColumn<BaseProduct, ProductType> colProductTypeBaseProduct;

    @FXML
    private TableColumn<BaseProduct, String> colIngredientsBaseProducts;

    @FXML
    private TableColumn<BaseProduct, User> colCreatorBaseProducts;

    @FXML
    private TableColumn<BaseProduct, User> colEditorBaseProducts;

    @FXML
    private TextField txtNameBaseProducts;

    @FXML
    private ComboBox<Ingredients> comboIngredients;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnCreate;

    @FXML
    private CheckBox cbDisable;

    @FXML
    private ComboBox<ProductType> cbTypes;

    @FXML
    private TextArea txtIngredients;

    @FXML
    private Button btnClean;

    @FXML
    private TextField separator;

    private BaseProduct preSelectBaseProduct;
    private int idxBaseProduct;
    private List<Ingredients> preListIngredients = new ArrayList<>();
    private ProductType preProductType;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public BaseProductController(Restaurant restaurant, ControllerRestaurantGUI controllerRestaurantGUI) {
        this.restaurant = restaurant;
        this.cGui = controllerRestaurantGUI;
    }

    public BaseProduct getPreSelectBaseProduct() {
        return this.preSelectBaseProduct;
    }

    public void setPreSelectBaseProduct(BaseProduct preSelectBaseProduct) {
        this.preSelectBaseProduct = preSelectBaseProduct;
    }

    public int getIdxBaseProduct() {
        return this.idxBaseProduct;
    }

    public void setIdxBaseProduct(int idxBaseProduct) {
        this.idxBaseProduct = idxBaseProduct;
    }

    @FXML
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    public void createBaseProducts(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
        boolean validateFields = baseProductValidation(txtNameBaseProducts.getText(), txtIngredients.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a product");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addBaseProduct(txtNameBaseProducts.getText(), preProductType, preListIngredients,
                    restaurant.getCodeBaseProduct(), restaurant.getLoggedUser(restaurant.getUserIndex()));
            alert.setContentText(msg);
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            trimBaseProductForm();
            initBaseProductTable();
        }
    }

    public boolean baseProductValidation(String name, String ingredients) {
        boolean complete = true;
        if (name.equals("") || ingredients.equals("") || cbTypes.getSelectionModel().getSelectedItem() == null) {
            complete = false;
        }
        return complete;
    }

    public void trimBaseProductForm() {
        txtNameBaseProducts.setText("");
        comboIngredients.setValue(null);
        txtIngredients.setText("");
        preListIngredients.clear();
        cbTypes.setValue(null);
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnClean.setDisable(true);
        cbDisable.setDisable(true);
    }

    @FXML
    public void updateBaseProducts(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setBaseProductInfo(getPreSelectBaseProduct(), txtNameBaseProducts.getText(),
                cbTypes.getValue(), preListIngredients, getIdxBaseProduct(),
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        restaurant.getLoggedUser(restaurant.getUserIndex());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        trimBaseProductForm();
        setPreSelectBaseProduct(null);
        initBaseProductTable();
    }

    @FXML
    public void selectedBaseProduct(MouseEvent event) {
        BaseProduct sltBaseProduct = listBaseProducts.getSelectionModel().getSelectedItem();
        if (sltBaseProduct != null) {
            int idxBaseProduct = listBaseProducts.getSelectionModel().getSelectedIndex();
            setIdxBaseProduct(idxBaseProduct);
            setPreSelectBaseProduct(sltBaseProduct);
            setForm(sltBaseProduct);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
            initBtnClear();
        }
    }

    public void setForm(BaseProduct selectBaseProduct) {
        txtNameBaseProducts.setText(selectBaseProduct.getName());
        cbTypes.setValue(selectBaseProduct.getProductType());
        preListIngredients = selectBaseProduct.getIngredients();
        txtIngredients.setText(selectBaseProduct.getNameIngredients());
        cbDisable.setSelected(!selectBaseProduct.getState());
    }

    @FXML
    public void setStateBaseProducts(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableBaseProduct(preSelectBaseProduct);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableBaseProduct(preSelectBaseProduct);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        restaurant.saveData();
        restaurant.loadData();
        trimBaseProductForm();
        initBaseProductTable();
    }

    @FXML
    public void deleteBaseProducts(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProduct(getIdxBaseProduct(), preSelectBaseProduct.getName(),
                String.valueOf(preSelectBaseProduct.getProductType()));
        alert.setContentText(msg);
        alert.showAndWait();
        if (msg.equals("The base product have been deleted succesfully")) {
            trimBaseProductForm();
            restaurant.saveData();
            initBaseProductTable();
        }
    }

    @FXML
    public void deselectBaseProduct(ActionEvent event) {
        trimBaseProductForm();
    }

    @FXML
    public void exportBaseProducts(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export base product");
            restaurant.exportDataProduct(selectedFile.getAbsolutePath(), separator.getText());
            alert.setContentText("The base product data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export base product");
            alert.setContentText("The base product data was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    public void importBaseProducts(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import base product");
            restaurant.importDataProduct(selectedFile.getAbsolutePath());
            alert.setContentText("The base product data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initBaseProductTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import base product");
            alert.setContentText("The base product data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public void initBtnClear() {
        if (txtIngredients.getText() != "") {
            btnClean.setDisable(false);
        }
    }

    @FXML
    public void comboEvent(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(comboIngredients)) {
            initBtnClear();
            preListIngredients.add(restaurant.searchIndex(comboIngredients.getSelectionModel().getSelectedItem()));
            txtIngredients.setText(Arrays.toString(preListIngredients.toArray()));
        }
        if (e.equals(cbTypes)) {
            preProductType = cbTypes.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    public void cleanIngredients(ActionEvent event) {
        txtIngredients.setText("");
        preListIngredients.clear();
        btnClean.setDisable(true);
    }

    public void initComboIngredientBox() {
        comboIngredients.getItems().addAll(restaurant.getEnableIngredients());
    }

    public void initComboTypesBox() {
        cbTypes.getItems().addAll(restaurant.getEnableProductTypes());
    }

    public void initBaseProductTable() throws IOException {
        ObservableList<BaseProduct> baseProducts = FXCollections.observableArrayList(restaurant.getBaseProducts());
        listBaseProducts.setItems(baseProducts);
        colCode.setCellValueFactory(new PropertyValueFactory<BaseProduct, Integer>("code"));
        colNameBaseProducts.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("name"));
        colProductTypeBaseProduct
                .setCellValueFactory(new PropertyValueFactory<BaseProduct, ProductType>("productType"));
        colIngredientsBaseProducts
                .setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("nameIngredients"));
        colCreatorBaseProducts.setCellValueFactory(new PropertyValueFactory<BaseProduct, User>("creator"));
        colEditorBaseProducts.setCellValueFactory(new PropertyValueFactory<BaseProduct, User>("lastEditor"));
    }
}