package controller;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ProductType;

public class ProductTypeController {
    @FXML
    private TableView<ProductType> listProductType;

    @FXML
    private TableColumn<ProductType, Integer> colCode;

    @FXML
    private TableColumn<ProductType, String> colNameProductType;

    @FXML
    private TableColumn<ProductType, String> colCreatorProductType;

    @FXML
    private TableColumn<ProductType, String> colEditorProducType;

    @FXML
    private TextField txtNameProductType;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private CheckBox cbDisable;

    private ProductType preSelectProductType;
    private int idxProductType;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public ProductTypeController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public ProductType getPreSelectProductType() {
        return this.preSelectProductType;
    }

    public void setPreSelectProductType(ProductType preSelectProductType) {
        this.preSelectProductType = preSelectProductType;
    }

    public int getIdxProductType() {
        return this.idxProductType;
    }

    public void setIdxProductType(int idxProductType) {
        this.idxProductType = idxProductType;
    }

    @FXML
    public void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    public void createProductType(ActionEvent event) throws IOException, ClassNotFoundException {
        boolean validateFields = productTypeValidation(txtNameProductType.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addProductType(txtNameProductType.getText(), restaurant.getLoggedUser().getName(),
                    restaurant.getCodeProductType());
            alert.setContentText(msg);
            trimProductTypeForm();
            alert.showAndWait();
            restaurant.saveProductType();
            initProductTypeTable();
        }
    }

    public boolean productTypeValidation(String name) {
        boolean complete = true;
        if (name.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimProductTypeForm() {
        txtNameProductType.setText("");
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        cbDisable.setDisable(true);
    }

    @FXML
    public void updateProductType(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setProductType(getPreSelectProductType(), txtNameProductType.getText(),
                restaurant.getLoggedUser().getName());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveProductType();
        restaurant.loadProductType();
        trimProductTypeForm();
        setPreSelectProductType(null);
        initProductTypeTable();
    }

    @FXML
    public void selectedProductType(MouseEvent event) {
        ProductType sltProductType = listProductType.getSelectionModel().getSelectedItem();
        if (sltProductType != null) {
            int idxProductType = listProductType.getSelectionModel().getSelectedIndex();
            setIdxProductType(idxProductType);
            setPreSelectProductType(sltProductType);
            setForm(sltProductType);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
        }
    }

    public void setForm(ProductType selectProductType) {
        txtNameProductType.setText(selectProductType.getName());
        cbDisable.setSelected(!selectProductType.getState());
    }

    @FXML
    public void setStateProductType(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableProductType(preSelectProductType);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableProductType(preSelectProductType);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimProductTypeForm();
        restaurant.saveProductType();
        initProductTypeTable();
    }

    

    @FXML
    void deleteProductType(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProductType(getIdxProductType());
        alert.setContentText(msg);
        trimProductTypeForm();
        alert.showAndWait();
        restaurant.saveProductType();
        initProductTypeTable();
    }

    @FXML
    void deselectProductType(ActionEvent event) {
        trimProductTypeForm();
    }

    @FXML
    void exportProductType(ActionEvent event) {

    }

    @FXML
    void importProductType(ActionEvent event) {

    }

    public void initProductTypeTable() throws IOException {
        ObservableList<ProductType> productType = FXCollections.observableArrayList(restaurant.getProductType());
        listProductType.setItems(productType);
        colCode.setCellValueFactory(new PropertyValueFactory<ProductType, Integer>("code"));
        colNameProductType.setCellValueFactory(new PropertyValueFactory<ProductType, String>("name"));
        colCreatorProductType.setCellValueFactory(new PropertyValueFactory<ProductType, String>("creator"));
        colEditorProducType.setCellValueFactory(new PropertyValueFactory<ProductType, String>("lastEditor"));
    }
}
