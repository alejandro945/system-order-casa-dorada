package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import animatefx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import model.Restaurant;
import model.User;

public class UserController {

    private User preSelectUser;

    private int idxUser;

    @FXML
    private StackPane pnlStack;

    @FXML
    private Pane pnlSingup;

    @FXML
    private Pane pnlSingin;

    @FXML
    private ImageView btnBackToLogin;

    // LOGIN
    @FXML
    private PasswordField txtPasswordLogin;

    @FXML
    private TextField txtNameUserLogin;

    // SIGNUP

    @FXML
    private PasswordField txtPasswordRegister;

    @FXML
    private TextField txtNameRegister;

    @FXML
    private TextField txtLastNameRegister;

    @FXML
    private TextField txtIDRegister;

    @FXML
    private TextField txtNameUserRegister;

    @FXML
    private ImageView imgRegister;

    @FXML
    private Button btnRegister;

    // LIST USER

    @FXML
    private TableView<User> listUsers;

    @FXML
    private TableColumn<User, String> colNameUser;

    @FXML
    private TableColumn<User, String> colLastNameUser;

    @FXML
    private TableColumn<User, Integer> colIDUser;

    @FXML
    private TableColumn<User, String> colUserName;

    @FXML
    private TableColumn<User, String> colCreatorUser;

    @FXML
    private TableColumn<User, String> colLastEditorUser;

    @FXML
    private TextField txtNameUser;

    @FXML
    private TextField txtLastNameUser;

    @FXML
    private TextField txtIDUser;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnCreate;

    @FXML
    private CheckBox cbDisableUser;

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;
    private String pathRender = "";

    public UserController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    public int getIdxUser() {
        return this.idxUser;
    }

    public void setIdxUser(int idxUser) {
        this.idxUser = idxUser;
    }

    public User getPreSelectUser() {
        return this.preSelectUser;
    }

    public void setPreSelectUser(User preSelectUser) {
        this.preSelectUser = preSelectUser;
    }

    @FXML
    public void logIn(ActionEvent event) throws IOException, ClassNotFoundException {
        restaurant.loadUsers();
        User user = restaurant.userVerification(txtNameUserLogin.getText(), txtPasswordLogin.getText());
        if (user != null) {
            restaurant.setLoggedUser(user);
            cGui.showDashBoard();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Name of user and/or password invalid");
            alert.showAndWait();
        }
        txtNameUserRegister.setText("");
        txtPasswordRegister.setText("");
    }

    @FXML
    public void createAccount(ActionEvent event) throws IOException, ClassNotFoundException {
        restaurant.loadUsers();
        boolean validateFields = registerValidation(txtNameRegister.getText(), txtLastNameRegister.getText(),
                txtIDRegister.getText(), txtNameUserRegister.getText(), txtPasswordRegister.getText(), this.pathRender);
        if (!validateFields) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Hey!! Please complete all fields for create your account");
            alert.showAndWait();
        } else if (txtPasswordRegister.getText().length() < 8) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Ups!! The password is weak, it must be at least 8 characters");
            alert.showAndWait();
        } else if (!restaurant.searchUser(txtNameUserRegister.getText())) {
            restaurant.addNewUser(txtNameRegister.getText(), txtLastNameRegister.getText(),
                    Integer.parseInt(txtIDRegister.getText()), txtNameUserRegister.getText(),
                    txtPasswordRegister.getText(), pathRender);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText("Look, Consider the following");
            alert.setContentText("Are you sure to save this user?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Redux and React");
                alert2.setHeaderText("The User " + txtNameUserRegister.getText() + " have been added sucesfully");
                alert2.setContentText("Take it easy bro!");
                alert2.showAndWait();
            }
            trimRegisterTxt();
            restaurant.saveUsers();
        } else {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("Warning Dialog");
            alert3.setHeaderText("Error");
            alert3.setContentText("The user already exist");
            alert3.showAndWait();
        }
    }

    public boolean registerValidation(String name, String lastName, String id, String userName, String password,
            String pathRender) {
        boolean complete = true;
        if (name.equals("") || lastName.equals("") || id.equals("") || userName.equals("") || password.equals("")
                || pathRender.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void trimRegisterTxt() {
        txtNameRegister.setText("");
        txtLastNameRegister.setText("");
        txtIDRegister.setText("");
        txtNameUserRegister.setText("");
        txtPasswordRegister.setText("");
        pathRender = "";
        imgRegister.setImage(null);
    }

    @FXML
    public void fileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imgRegister.setImage(new Image(selectedFile.toURI().toString()));
            pathRender = selectedFile.getAbsolutePath();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Image not found");
            alert.setHeaderText("Path does not exist");
            alert.showAndWait();
        }
    }

    @FXML
    public void showRegister(ActionEvent event) throws IOException {
        if (event.getSource().equals(btnRegister)) {
            new FadeIn(pnlSingup).play();
            pnlSingup.toFront();
        }
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnBackToLogin) {
            new FadeIn(pnlSingin).play();
            pnlSingin.toFront();
        }
    }

    // LIST USER

    @FXML
    public void backUserToDash(MouseEvent event) throws ClassNotFoundException, IOException {
        cGui.showDashBoard();
    }

    @FXML
    void selectedUser(MouseEvent event) {
        User sltUser = listUsers.getSelectionModel().getSelectedItem();
        if (sltUser != null) {
            int idxUser = listUsers.getSelectionModel().getSelectedIndex();
            setIdxUser(idxUser);
            setPreSelectUser(sltUser);
            setForm(sltUser);
            btnCreate.setDisable(true);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("The row selected does not have any costumer");
            alert.showAndWait();
        }
    }

    public boolean userValidation(String name, String lastName, String id, String userName) {
        boolean complete = true;
        if (name.equals("") || lastName.equals("") || id.equals("") || userName.equals("")) {
            complete = false;
        }
        return complete;
    }

    public void setForm(User selectUser) {
        txtNameUser.setText(selectUser.getName());
        txtLastNameUser.setText(selectUser.getLastName());
        txtIDUser.setText(String.valueOf(selectUser.getId()));
        txtUserName.setText(selectUser.getUserName());
        cbDisableUser.setSelected(!selectUser.getState());
    }

    @FXML
    public void createUser(ActionEvent event) throws FileNotFoundException, IOException {
        boolean validateFields = userValidation(txtNameUser.getText(), txtLastNameUser.getText(),
                txtIDUser.getText(), txtUserName.getText());
        if (!validateFields) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText("Warning");
            alert2.setContentText("Hey!! Please complete all fields for create a costumer");
            alert2.showAndWait();
        } else if (validateFields) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            String msg = restaurant.addUser(txtNameUser.getText(), txtLastNameUser.getText(),
                    Integer.parseInt(txtIDUser.getText()), txtUserName.getText());
            alert.setContentText(msg);
            trimUserForm();
            alert.showAndWait();
            restaurant.saveUsers();
            initUserTable();
        }
    }

    @FXML
    public void updateUser(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        restaurant.setUserInfo(getPreSelectUser(), txtNameUser.getText(), txtLastNameUser.getText(),
                Integer.parseInt(txtIDUser.getText()), txtUserName.getText(), restaurant.getLoggedUser().getName());
        restaurant.saveUsers();
        restaurant.loadUsers();
        trimUserForm();
        setPreSelectUser(null);
        initUserTable();
    }

    public void trimUserForm() {
        txtNameUser.setText("");
        txtLastNameUser.setText("");
        txtIDUser.setText("");
        txtUserName.setText("");
        btnCreate.setDisable(false);
    }

    @FXML
    void deleteUser(ActionEvent event) throws FileNotFoundException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String msg = restaurant.deleteUser(getIdxUser());
        alert.setContentText(msg);
        trimUserForm();
        alert.showAndWait();
        restaurant.saveUsers();
        initUserTable();
    }

    @FXML
    public void deselectUser(ActionEvent event) {
        trimUserForm();
    }

    @FXML
    public void exportUsers(ActionEvent event) {

    }

    @FXML
    public void importUsers(ActionEvent event) {

    }

    @FXML
    public void setStateUser(ActionEvent event) throws FileNotFoundException, IOException {
        String msg = "";
        if (cbDisableUser.isSelected()) {
            msg = restaurant.disableUser(preSelectUser);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        } else {
            msg = restaurant.enableUser(preSelectUser);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
        trimUserForm();
        restaurant.saveUsers();
        initUserTable();
    }

    @FXML
    void searchCostumer(ActionEvent event) {

    }

    public void initUserTable() throws IOException {
        ObservableList<User> users = FXCollections.observableArrayList(restaurant.getUsers());
        listUsers.setItems(users);
        colNameUser.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        colLastNameUser.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        colIDUser.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        colCreatorUser.setCellValueFactory(new PropertyValueFactory<User, String>("creator"));
        colLastEditorUser.setCellValueFactory(new PropertyValueFactory<User, String>("lastEditor"));
    }

}
