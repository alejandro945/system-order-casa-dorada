package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    private Costumer preSelectCostumer;

    private int idxCostumer;

    @FXML
    private CheckBox cbDisable;

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

    @FXML
    private Button btnCreate;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    public CostumerController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public int getIdxCostumer() {
        return this.idxCostumer;
    }

    public void setIdxCostumer(int idxCostumer) {
        this.idxCostumer = idxCostumer;
    }

    public Costumer getPreSelectCostumer() {
        return this.preSelectCostumer;
    }

    public void setPreSelectCostumer(Costumer preSelectCostumer) {
        this.preSelectCostumer = preSelectCostumer;
    }

    @FXML
    public void createCostumer(ActionEvent event) throws IOException {
        boolean validateFields = costumerValidation(txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                txtIdCostumer.getText(), txtAddressCostumer.getText(), txtTelephoneCostumer.getText(),
                txtSuggestionsCostumer.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addCostumer(txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                    Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                    Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                    restaurant.getLoggedUser().getName());
            alert.setContentText(msg);
            trimCostumerForm();
            alert.showAndWait();
            restaurant.saveCostumers();
            initCostumersTable();
        }

    }

    public boolean costumerValidation(String name, String lastName, String id, String address, String telephone,
            String suggestion) {
        boolean complete = true;
        if (name.equals("") || lastName.equals("") || id.equals("") || address.equals("") || telephone.equals("")
                || suggestion.equals("")) {
            complete = false;
        }
        return complete;
    }

    @FXML
    public void deleteCostumer(ActionEvent event) throws FileNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteCostumer(getIdxCostumer());
        alert.setContentText(msg);
        trimCostumerForm();
        alert.showAndWait();
        restaurant.saveCostumers();
        initCostumersTable();
    }

    @FXML
    public void setStateCostumer(ActionEvent event) throws FileNotFoundException, IOException {
        String msg = "";
        if (cbDisable.isSelected()) {
            msg = restaurant.disableCostumer(preSelectCostumer);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableCostumer(preSelectCostumer);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimCostumerForm();
        restaurant.saveCostumers();
        initCostumersTable();
    }

    @FXML
    public void selectedCostumer(MouseEvent event) throws IOException {
        Costumer sltCostumer = listCostumers.getSelectionModel().getSelectedItem();
        if (sltCostumer != null) {
            int idxCostumer = listCostumers.getSelectionModel().getSelectedIndex();
            setIdxCostumer(idxCostumer);
            setPreSelectCostumer(sltCostumer);
            setForm(sltCostumer);
            btnCreate.setDisable(true);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("The row selected does not have any costumer");
            alert.showAndWait();
        }
    }

    @FXML
    public void updateCostumer(ActionEvent event) throws IOException, ClassNotFoundException {
        restaurant.setInfoCostumer(getPreSelectCostumer(), txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                restaurant.getLoggedUser().getName());
        restaurant.saveCostumers();
        restaurant.loadCostumers();
        trimCostumerForm();
        setPreSelectCostumer(null);
        initCostumersTable();
    }

    public void setForm(Costumer selectCostumer) {
        txtNameCostumer.setText(selectCostumer.getName());
        txtLastNameCostumer.setText(selectCostumer.getLastName());
        txtIdCostumer.setText(String.valueOf(selectCostumer.getId()));
        txtAddressCostumer.setText(selectCostumer.getAddress());
        txtTelephoneCostumer.setText(String.valueOf(selectCostumer.getTelephone()));
        txtSuggestionsCostumer.setText(selectCostumer.getSuggestions());
        cbDisable.setSelected(!selectCostumer.getState());
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
        btnCreate.setDisable(false);
    }

    @FXML
    public void deselectCostumer(ActionEvent event) {
        trimCostumerForm();
    }

    @FXML
    public void exportCostumers(ActionEvent event) {

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

    @FXML
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }
}
