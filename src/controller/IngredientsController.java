package controller;

import javafx.scene.control.*;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Ingredients, String> colCreatorIngredients;

    @FXML
    private TableColumn<Ingredients, String> colEditorIngredients;

    @FXML
    private TextField txtNameIngredients;

    @FXML
    private Button btnCreate;

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
    void createIngredients(ActionEvent event) {

    }

    @FXML
    void deleteIngredients(ActionEvent event) {

    }

    @FXML
    void deselectIngredient(ActionEvent event) {

    }

    @FXML
    void exportIngredients(ActionEvent event) {

    }

    @FXML
    void importIngredients(ActionEvent event) {

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
        }
    }

    public void setForm(Ingredients selectIngredient) {
        txtNameIngredients.setText(selectIngredient.getName());
        cbDisable.setSelected(!selectIngredient.getState());
    }

    @FXML
    void setStateIngredients(ActionEvent event) {

    }

    @FXML
    void updateIngredients(ActionEvent event) {

    }

}
