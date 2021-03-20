package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class CostumerController {

    @FXML
    private TableView<Costumer> listCostumers;

    @FXML
    private TableColumn<Costumer, String> colNameCostumer;

    @FXML
    private TableColumn<Costumer, String> colLastNameCostumer;

    @FXML
    private TableColumn<Costumer, Integer> colIDCostumer;

    @FXML
    private TableColumn<Costumer, Integer> colTelephoneCostumer;

    @FXML
    private TableColumn<Costumer, String> colSuggestionsCostumer;

    @FXML
    private TableColumn<Costumer, String> colCreatorCostumers;

    @FXML
    private TableColumn<Costumer, String> colLastEditorCostumers;

    @FXML
    private TableColumn<Costumer, String> colAddressCostumer;

    @FXML
    private TextField txtNameCostumer;

    @FXML
    private TextField txtLastNameCostumer;

    @FXML
    private TextField txtIdCostumer;

    @FXML
    private TextField txtTelephoneCostumer;

    @FXML
    private TextField txtSuggestionsCostumer;

    @FXML
    private TextField txtAddressCostumer;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public CostumerController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    @FXML
    public void createCostumer(ActionEvent event) throws IOException {
        restaurant.addCostumer(txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                restaurant.getLoggedUser().getName());
        trimCostumerForm();
        restaurant.saveCostumers();
        initCostumersTable();
    }

    @FXML
    public void deleteCostumer(ActionEvent event) {

    }

    @FXML
    public void disableCostumer(ActionEvent event) {

    }

    @FXML
    void selectedCostumer(MouseEvent event) throws IOException {
            if(event.getClickCount() == 2){
            Costumer sltCostumer = listCostumers.getSelectionModel().getSelectedItem();
            setForm(sltCostumer);
            }
    }

    @FXML
    public void updateCostumer(ActionEvent event) throws IOException {
        Costumer selectCostumer = listCostumers.getSelectionModel().getSelectedItem();
        restaurant.setInfoCostumer(selectCostumer, txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                restaurant.getLoggedUser().getName());
        trimCostumerForm();
        initCostumersTable();
    }

    public void setForm(Costumer selectCostumer) {
        txtNameCostumer.setText(selectCostumer.getName());
        txtLastNameCostumer.setText(selectCostumer.getLastName());
        txtIdCostumer.setText(String.valueOf(selectCostumer.getId()));
        txtAddressCostumer.setText(selectCostumer.getAddress());
        txtTelephoneCostumer.setText(String.valueOf(selectCostumer.getTelephone()));
        txtSuggestionsCostumer.setText(selectCostumer.getSuggestions());
    }

    public void initCostumersTable() throws IOException {
        ObservableList<Costumer> costumers = FXCollections.observableArrayList(restaurant.getCostumers());
        listCostumers.setItems(costumers);
        colNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("name"));
        colLastNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastName"));
        colIDCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("id"));
        colAddressCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("address"));
        colTelephoneCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("telephone"));
        colSuggestionsCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("suggestions"));
        colCreatorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, String>("creator"));
        colLastEditorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastEditor"));
    }

    public void trimCostumerForm() {
        txtNameCostumer.setText("");
        txtLastNameCostumer.setText("");
        txtIdCostumer.setText("");
        txtAddressCostumer.setText("");
        txtTelephoneCostumer.setText("");
        txtSuggestionsCostumer.setText("");
    }

    @FXML
    public void importCostumers(ActionEvent event) throws IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Import costumers");
            restaurant.importDataCostumers(selectedFile.getAbsolutePath());
            alert.setContentText("The costumers data was imported succesfully");
            alert.showAndWait();
            cGui.showCostumers();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import costumers");
            alert.setContentText("The costumers data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public void exportCostumers(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.close();
    }

}
