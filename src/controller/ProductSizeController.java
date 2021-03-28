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

public class ProductSizeController {

    @FXML
    private TableView<ProductSize> listProductSize;

    @FXML
    private TableColumn<ProductSize, Integer> colCodeProductSize;

    @FXML
    private TableColumn<ProductSize, String> colNameProductSize;

    @FXML
    private TableColumn<ProductSize, User> colCreatorProductSize;

    @FXML
    private TableColumn<ProductSize, User> colEditorProductSize;

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
                    restaurant.getLoggedUser(restaurant.getUserIndex()));
            alert.setContentText(msg);
            trimProductSizeForm();
            alert.showAndWait();
            restaurant.saveData();
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setProductSize(getPreSelectProductSize(), txtNameProductSize.getText(),
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveData();
        restaurant.loadData();
        trimProductSizeForm();
        setPreSelectProductSize(null);
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
        restaurant.saveData();
        initProductSizeTable();
    }

    @FXML
    public void deleteProductSize(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteProductSize(getIdxProductSize(), preSelectProductSize.getName());
        alert.setContentText(msg);
        trimProductSizeForm();
        alert.showAndWait();
        restaurant.saveData();
        initProductSizeTable();
    }

    @FXML
    public void importProductSize(ActionEvent event) throws IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import product size");
            restaurant.importDataProductSize(selectedFile.getAbsolutePath());
            alert.setContentText("The product size data was imported succesfully");
            alert.showAndWait();
            restaurant.saveData();
            restaurant.loadData();
            initProductSizeTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import product size");
            alert.setContentText("The product size data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    @FXML
    public void exportProductSize(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export product size");
            restaurant.exportDataProductSize(selectedFile.getAbsolutePath());
            alert.setContentText("The product size data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export product size");
            alert.setContentText("The product size data was NOT exported. An error occurred");
            alert.showAndWait();
        }
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
        colCreatorProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, User>("creator"));
        colEditorProductSize.setCellValueFactory(new PropertyValueFactory<ProductSize, User>("lastEditor"));
    }
}
