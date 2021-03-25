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

public class ProductSizeController {

    @FXML
    private TableView<ProductSize> listProductSize;

    @FXML
    private TableColumn<ProductSize, Integer> colCodeProductSize;

    @FXML
    private TableColumn<ProductSize, String> colNameProductSize;

    @FXML
    private TableColumn<ProductSize, String> colCreatorProductSize;

    @FXML
    private TableColumn<ProductSize, String> colEditorProductSize;

    @FXML
    private TextField txtNameProductSize;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private CheckBox cbDisable;

    private ProductSize preSelectProductSize;
    private int idxProductSize;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public ProductSizeController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public ProductSize getPreSelectProductSize() {
        return this.preSelectProductSize;
    }

    public void setPreSelectProductSize(ProductSize preSelectProductSize) {
        this.preSelectProductSize = preSelectProductSize;
    }

    public int getIdxProductSize() {
        return this.idxProductSize;
    }

    public void setIdxProductSize(int idxProductSize) {
        this.idxProductSize = idxProductSize;
    }

    @FXML
    public void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    public void createProductSize(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        boolean validateFields = productSizeValidation(txtNameProductSize.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addProductSize(txtNameProductSize.getText(), restaurant.getCodeProductSize(),
                    restaurant.getLoggedUser().getName());
            alert.setContentText(msg);
            trimProductSizeForm();
            alert.showAndWait();
            restaurant.saveProductSize();
            initProductSizeTable();
        }
    }

    public boolean productSizeValidation(String name) {
        boolean complete = true;
        if (name.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimProductSizeForm() {
        txtNameProductSize.setText("");
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        cbDisable.setDisable(true);
    }

    @FXML
    public void updateProductSize(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableProductSize(preSelectProductSize);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableProductSize(preSelectProductSize);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimProductSizeForm();
        restaurant.saveProductSize();
        restaurant.loadProductSize();
        initProductSizeTable();
    }

    @FXML
    public void selectedProductSize(MouseEvent event) {
        ProductSize sltProductSize = listProductSize.getSelectionModel().getSelectedItem();
        if (sltProductSize != null) {
            int idxProductSize = listProductSize.getSelectionModel().getSelectedIndex();
            setIdxProductSize(idxProductSize);
            setPreSelectProductSize(sltProductSize);
            setForm(sltProductSize);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
        }
    }

    public void setForm(ProductSize selectProductSize) {
        txtNameProductSize.setText(selectProductSize.getName());
        cbDisable.setSelected(!selectProductSize.getState());
    }

    @FXML
    public void setStateProductSize(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableProductSize(preSelectProductSize);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableProductSize(preSelectProductSize);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimProductSizeForm();
        restaurant.saveProductSize();
        initProductSizeTable();
    }

    @FXML
    public void deleteProductSize(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProductSize(getIdxProductSize());
        alert.setContentText(msg);
        trimProductSizeForm();
        alert.showAndWait();
        restaurant.saveProductSize();
        initProductSizeTable();
    }

    @FXML
    public void importProductSize(ActionEvent event) {

    }

    @FXML
    public void exportProductSize(ActionEvent event) {

    }

    @FXML
    public void deselectProductSize(ActionEvent event) {
        trimProductSizeForm();
    }

    public void initProductSizeTable() throws IOException {
        ObservableList<ProductSize> productSize = FXCollections.observableArrayList(restaurant.getProductSize());
        listProductSize.setItems(productSize);
        colCodeProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, Integer>("code"));
        colNameProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, String>("name"));
        colCreatorProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, String>("creator"));
        colEditorProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, String>("lastEditor"));
    }
}
