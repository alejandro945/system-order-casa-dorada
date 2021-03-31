package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private ComboBox<BaseProduct> comboBaseProduct;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private Button btnGenerate;

    @FXML
    private TextField separator;

    private Product preSelectProduct;
    private int idxProduct;
    private List<Ingredients> preListIngredients = new ArrayList<>();
    private ProductSize preProductSize;
    private BaseProduct preBaseProduct;
    private Stage modal;
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

    public BaseProduct getPreBaseProduct() {
        return this.preBaseProduct;
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
            String msg = restaurant.addProduct(preBaseProduct, restaurant.getCodeProduct(), preProductSize,
                    Double.parseDouble(txtPriceProducts.getText()),
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
        String msg = restaurant.setProductInfo(getPreSelectProduct(), getPreBaseProduct(), cbSize.getValue(),
                Double.parseDouble(txtPriceProducts.getText()), restaurant.getLoggedUser(restaurant.getUserIndex()));
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
        txtNameProducts.setText(selectProduct.getBaseProduct().getName());
        txtPriceProducts.setText(String.valueOf(selectProduct.getPrice()));
        cbSize.setValue(selectProduct.getProductSize());
        cbTypes.setValue(selectProduct.getBaseProduct().getProductType());
        txtIngredients.setText(selectProduct.getBaseProduct().getNameIngredients());
        preListIngredients = selectProduct.getBaseProduct().getIngredients();
        cbDisable.setSelected(!selectProduct.getState());
    }

    @FXML
    void event(ActionEvent event) {
        Object e = event.getSource();
        if (e.equals(dateStart)) {
            initbtnReportDate();
        }
        if (e.equals(dateEnd)) {
            initbtnReportDate();
        }
        if (e.equals(startTime)) {
            initbtnReportTime();
        }
        if (e.equals(endTime)) {
            initbtnReportTime();
        }
    }

    public void initbtnReportDate() {
        if (dateStart.getValue() != null && dateEnd.getValue() != null) {
            if (dateEnd.getValue()
                    .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0
                    || dateStart.getValue()
                            .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0) {
                btnGenerate.setDisable(true);
            } else if (dateStart.getValue().compareTo(dateEnd.getValue()) < 0) {
                btnGenerate.setDisable(false);
            } else if (dateStart.getValue().compareTo(dateEnd.getValue()) > 0) {
                btnGenerate.setDisable(true);
            } else {
                btnGenerate.setDisable(false);
            }
        }
    }

    public void initbtnReportTime() {
        if (dateStart.getValue() != null && dateEnd.getValue() != null && startTime.getValue() != null
                && endTime.getValue() != null) {
            if (dateEnd.getValue()
                    .compareTo(LocalDate.parse(cGui.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) == 0
                    && endTime.getValue().compareTo(LocalTime.parse(cGui.getHour())) > 0) {
                btnGenerate.setDisable(true);
            } else if (dateStart.getValue().compareTo(dateEnd.getValue()) < 0) {
                btnGenerate.setDisable(false);
            } else if (startTime.getValue().compareTo(endTime.getValue()) < 0) {
                btnGenerate.setDisable(false);
            } else if (startTime.getValue().compareTo(endTime.getValue()) > 0) {
                btnGenerate.setDisable(true);
            }
        }
    }

    public void initReport() {
        String date = cGui.date();
        String hour = cGui.getHour();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime localTime1 = LocalTime.parse("00:00:00");
        LocalTime localTime2 = LocalTime.parse(hour);
        dateStart.setValue(localDate);
        dateEnd.setValue(localDate);
        startTime.setValue(localTime1);
        endTime.setValue(localTime2);
    }

    @FXML
    void generateReport(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Report employees");
            // restaurant.exportEmployeeReport(selectedFile.getAbsolutePath(),
            // separator.getText(),
            // getFilteredOrders(dateStart, dateEnd, startTime, endTime));
            alert.setContentText("The employees report was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Report Employees");
            alert.setContentText("The employees report was NOT exported. An error occurred");
            alert.showAndWait();
        }
    }

    public List<Order> getFilteredOrders(DatePicker sd, DatePicker ed, JFXTimePicker st, JFXTimePicker et) {
        List<Order> o = new ArrayList<>();
        for (int i = 0; i < restaurant.getOrders().size(); i++) {
            LocalDate localDate = LocalDate.parse(restaurant.getOrders().get(i).getDate(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime localTime = LocalTime.parse(restaurant.getOrders().get(i).getHour());
            if (localDate.compareTo(sd.getValue()) >= 0 && localDate.compareTo(ed.getValue()) <= 0) {
                if (localTime.compareTo(st.getValue()) >= 0 && localTime.compareTo(et.getValue()) <= 0) {
                    o.add(restaurant.getOrders().get(i));
                }
            }
        }
        return o;
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
        String msg = restaurant.deleteProduct(getIdxProduct(), preSelectProduct.getBaseProduct().getName(),
                String.valueOf(preSelectProduct.getBaseProduct().getProductType()));
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
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
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
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
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

    public void getModal(Window mod) {
        modal = (Stage) mod.getScene().getWindow();
    }

    @FXML
    void closeModal(MouseEvent event) {
        modal.close();
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
