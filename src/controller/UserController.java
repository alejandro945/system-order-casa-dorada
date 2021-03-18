package controller;

import java.io.*;
import java.util.*;

import animatefx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import model.*;

public class UserController {
    @FXML
    private Circle btnCloseLogin;

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

    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;
    private String pathRender = "";

    public UserController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    @FXML
    public void logIn(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.loadData();
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
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        }
    }

}
