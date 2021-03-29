package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.ProductType;

public class ProductTypeController {
    @FXML
    private TableView<ProductType> listProductType;

    @FXML
    private TableColumn<ProductType, Integer> colCode;

    @FXML
    private TableColumn<ProductType, String> colNameProductType;

    @FXML
    private TableColumn<ProductType, User> colCreatorProductType;

    @FXML
    private TableColumn<ProductType, User> colEditorProducType;

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

    @FXML
    private TextField separator;

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
            String msg = restaurant.addProductType(txtNameProductType.getText(),
                    restaurant.getLoggedUser(restaurant.getUserIndex()), restaurant.getCodeProductType());
            alert.setContentText(msg);
            trimProductTypeForm();
            alert.showAndWait();
            restaurant.saveData();
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
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
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
    public void setStateProductType(ActionEvent event)
            throws FileNotFoundException, ClassNotFoundException, IOException {
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
        restaurant.saveData();
        initProductTypeTable();
    }

    @FXML
    void deleteProductType(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProductType(getIdxProductType(), preSelectProductType.getName());
        alert.setContentText(msg);
        trimProductTypeForm();
        alert.showAndWait();
        restaurant.saveData();
        initProductTypeTable();
    }

    @FXML
    void deselectProductType(ActionEvent event) {
        trimProductTypeForm();
    }

    @FXML
    void exportProductType(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export product type");
            restaurant.exportDataProductType(selectedFile.getAbsolutePath(), separator.getText());
            alert.setContentText("The  product type data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export product type");
            alert.setContentText("The  product type data was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    void importProductType(ActionEvent event) throws ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import  product type");
            restaurant.importDataProductType(selectedFile.getAbsolutePath());
            alert.setContentText("The  product type data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initProductTypeTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import  product type");
            alert.setContentText("The  product type data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public void initProductTypeTable() throws IOException {
        ObservableList<ProductType> productType = FXCollections.observableArrayList(restaurant.getProductType());
        listProductType.setItems(productType);
        colCode.setCellValueFactory(new PropertyValueFactory<ProductType, Integer>("code"));
        colNameProductType.setCellValueFactory(new PropertyValueFactory<ProductType, String>("name"));
        colCreatorProductType.setCellValueFactory(new PropertyValueFactory<ProductType, User>("creator"));
        colEditorProducType.setCellValueFactory(new PropertyValueFactory<ProductType, User>("lastEditor"));
    }
}
