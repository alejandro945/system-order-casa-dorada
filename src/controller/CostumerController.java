package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    private TableColumn<Costumer, Long> colIDCostumer;

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
                    restaurant.getLoggedUser().getName());
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

    @FXML
    public void deleteCostumer(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteCostumer(getPreSelectCostumer());
        alert.setContentText(msg);
        trimCostumerForm();
        alert.showAndWait();
        restaurant.savePeople();
        cGui.showCostumers();
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

    @FXML
    public void updateCostumer(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.setInfoCostumer(getPreSelectCostumer(), txtNameCostumer.getText(),
                txtLastNameCostumer.getText(), Integer.parseInt(txtIdCostumer.getText()), txtAddressCostumer.getText(),
                Integer.parseInt(txtTelephoneCostumer.getText()), txtSuggestionsCostumer.getText(),
                restaurant.getLoggedUser().getName());
        alert.setContentText(msg);
        alert.showAndWait();
        restaurant.savePeople();
        restaurant.loadPeople();
        trimCostumerForm();
        setPreSelectCostumer(null);
        cGui.showCostumers();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Costumer> costumers = FXCollections
                .observableArrayList(restaurant.getCostumers(restaurant.getPeople()));
        colNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("name"));
        colLastNameCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastName"));
        colIDCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Long>("id"));
        colAddressCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("address"));
        colTelephoneCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("telephone"));
        colSuggestionsCostumer.setCellValueFactory(new PropertyValueFactory<Costumer, String>("suggestions"));
        colCreatorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, String>("creator"));
        colLastEditorCostumers.setCellValueFactory(new PropertyValueFactory<Costumer, String>("lastEditor"));
        listCostumers.setItems(costumers);
        FilteredList<Costumer> filteredData = new FilteredList<>(costumers, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(costumer -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
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

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Costumer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(listCostumers.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        listCostumers.setItems(sortedData);
    }

}
