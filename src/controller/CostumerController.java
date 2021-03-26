package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class CostumerController implements Initializable {

    private Costumer preSelectCostumer;

    private int idxCostumer;

    @FXML
    private TextField filteredField;

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
    private TableColumn<Costumer, User> colCreatorCostumers;

    @FXML
    private TableColumn<Costumer, User> colLastEditorCostumers;

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
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

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
    void backCostuToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    public void createCostumer(ActionEvent event) throws IOException, ClassNotFoundException {
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
            String msg = restaurant.addPerson(txtNameCostumer.getText(), txtLastNameCostumer.getText(),
                    Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                    Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                    restaurant.getLoggedUser(restaurant.getUserIndex()));
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Inforation");
            alert.setContentText(msg);
            trimCostumerForm();
            alert.showAndWait();
            restaurant.savePeople();
            cGui.showCostumers();
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

    public void trimCostumerForm() {
        txtNameCostumer.setText("");
        txtLastNameCostumer.setText("");
        txtIdCostumer.setText("");
        txtAddressCostumer.setText("");
        txtTelephoneCostumer.setText("");
        txtSuggestionsCostumer.setText("");
        btnCreate.setDisable(false);
        cbDisable.setSelected(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    public void setStateCostumer(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
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
        restaurant.savePeople();
        cGui.showCostumers();
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
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            cbDisable.setDisable(false);
        }
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

    @FXML
    public void updateCostumer(ActionEvent event) throws IOException, ClassNotFoundException {
        String msg = restaurant.setInfoCostumer(getPreSelectCostumer(), txtNameCostumer.getText(),
                txtLastNameCostumer.getText(), Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                restaurant.getLoggedUser(restaurant.getUserIndex()));
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.savePeople();
        restaurant.loadPeople();
        trimCostumerForm();
        initCostumersTable();
        cGui.showCostumers();
    }

    @FXML
    public void deleteCostumer(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteCostumer(getPreSelectCostumer());
        alert.setContentText(msg);
        trimCostumerForm();
        alert.showAndWait();
        restaurant.savePeople();
        restaurant.loadPeople();
        initCostumersTable();
        cGui.showCostumers();
    }

    @FXML
    public void deselectCostumer(ActionEvent event) {
        trimCostumerForm();
    }

    @FXML
    public void exportCostumers(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(cGui.getPane());
        if (selectedFile != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export costumers");
            restaurant.exportDataCostumers(selectedFile.getAbsolutePath());
            alert.setContentText("The costumers data was exported succesfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Export costumers");
            alert.setContentText("The costumers data was NOT exported. An error occurred");
            alert.showAndWait();
        }
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
            restaurant.savePeople();
            restaurant.loadPeople();
            cGui.showCostumers();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Import costumers");
            alert.setContentText("The costumers data was NOT imported. An error occurred");
            alert.showAndWait();
        }
    }

    public ObservableList<Costumer> initCostumersTable() {
        ObservableList<Costumer> costumers = FXCollections
                .observableArrayList(restaurant.getCostumers(restaurant.getPeople()));
        colNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("name"));
        colLastNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastName"));
        colIDCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("id"));
        colAddressCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("address"));
        colTelephoneCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("telephone"));
        colSuggestionsCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("suggestions"));
        colCreatorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, User>("creator"));
        colLastEditorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, User>("lastEditor"));
        listCostumers.setItems(costumers);
        return costumers;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Costumer> costumers = initCostumersTable();
        FilteredList<Costumer> filteredData = new FilteredList<>(costumers, b -> true);
        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(costumer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (costumer.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (costumer.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(costumer.getId()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
        });
        SortedList<Costumer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(listCostumers.comparatorProperty());
        listCostumers.setItems(sortedData);
    }

}
