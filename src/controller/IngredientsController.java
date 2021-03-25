package controller;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import model.*;

public class IngredientsController {
    @FXML
    private TableView<Ingredients> listIngredients;

    @FXML
    private TableColumn<Ingredients, Integer> colCodeIngredients;

    @FXML
    private TableColumn<Ingredients, String> colNameIngredients;

    @FXML
    private TableColumn<Ingredients, User> colCreatorIngredients;

    @FXML
    private TableColumn<Ingredients, User> colEditorIngredients;

    @FXML
    private TextField txtNameIngredients;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private CheckBox cbDisable;

    private Ingredients preSelectIngredient;

    private int idxIngredient;
    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public IngredientsController(Restaurant restaurant, ControllerRestaurantGUI controllerRestaurantGUI) {
        this.restaurant = restaurant;
        this.cGui = controllerRestaurantGUI;
    }

    public Ingredients getPreSelectIngredient() {
        return this.preSelectIngredient;
    }

    public void setPreSelectIngredient(Ingredients preSelectIngredient) {
        this.preSelectIngredient = preSelectIngredient;
    }

    public int getIdxIngredient() {
        return this.idxIngredient;
    }

    public void setIdxIngredient(int idxIngredient) {
        this.idxIngredient = idxIngredient;
    }

    @FXML
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    void createIngredients(ActionEvent event) throws IOException, ClassNotFoundException {
        boolean validateFields = ingredientValidation(txtNameIngredients.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addIngredient(restaurant.getCode(), txtNameIngredients.getText(),
                    restaurant.getLoggedUser());
            alert.setContentText(msg);
            trimIngredientsForm();
            alert.showAndWait();
            restaurant.saveIngredients();
            initIngredientsTable();
        }
    }

    public boolean ingredientValidation(String name) {
        boolean complete = true;
        if (name.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimIngredientsForm() {
        txtNameIngredients.setText("");
        btnCreate.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        cbDisable.setDisable(true);
    }

    @FXML
    void selectedIngredients(MouseEvent event) {
        Ingredients sltIngredient = listIngredients.getSelectionModel().getSelectedItem();
        if (sltIngredient != null) {
            int idxIngredient = listIngredients.getSelectionModel().getSelectedIndex();
            setIdxIngredient(idxIngredient);
            setPreSelectIngredient(sltIngredient);
            setForm(sltIngredient);
            btnCreate.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
        }
    }

    public void setForm(Ingredients selectIngredient) {
        txtNameIngredients.setText(selectIngredient.getName());
        cbDisable.setSelected(!selectIngredient.getState());
    }

    @FXML
    void setStateIngredients(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableIngredient(preSelectIngredient);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableIngredient(preSelectIngredient);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimIngredientsForm();
        restaurant.saveIngredients();
        initIngredientsTable();
    }

    @FXML
    void updateIngredients(ActionEvent event) throws ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setInfoIngredient(getPreSelectIngredient(), txtNameIngredients.getText(),
                restaurant.getLogged());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.saveIngredients();
        restaurant.loadIngredients();
        trimIngredientsForm();
        setPreSelectIngredient(null);
        initIngredientsTable();
    }

    @FXML
    void deleteIngredients(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteIngredient(getIdxIngredient());
        alert.setContentText(msg);
        trimIngredientsForm();
        alert.showAndWait();
        restaurant.saveIngredients();
        initIngredientsTable();
    }

    @FXML
    void deselectIngredient(ActionEvent event) {
        trimIngredientsForm();
    }

    @FXML
    void exportIngredients(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();		
		File selectedFile = fc.showSaveDialog(cGui.getPane());
		if (selectedFile !=null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Export ingredients");
			restaurant.exportDataIngredients(selectedFile.getAbsolutePath());
			alert.setContentText("The ingredients data was exported succesfully");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Export ingredients");
			alert.setContentText("The ingredients data was NOT exported. An error occurred");
			alert.showAndWait();
		}
    }

    @FXML
    void importIngredients(ActionEvent event) throws ClassNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import ingredients");
            restaurant.importDataIngredients(selectedFile.getAbsolutePath());
            alert.setContentText("The ingredients data was imported succesfully");
            alert.showAndWait();
            restaurant.saveIngredients();
            restaurant.loadIngredients();
            initIngredientsTable();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import ingredients");
            alert.setContentText("The ingredients data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public void initIngredientsTable() throws IOException {
        ObservableList<Ingredients> ingredients = FXCollections.observableArrayList(restaurant.getIngredients());
        listIngredients.setItems(ingredients);
        colCodeIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("code"));
        colNameIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("name"));
        colCreatorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("creator"));
        colEditorIngredients.setCellValueFactory(new PropertyValueFactory<Ingredients, User>("lastEditor"));
    }

}
