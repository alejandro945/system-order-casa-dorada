package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import model.*;

public class ControllerRestaurantGUI implements Initializable {

    @FXML
    private Pane mainPane;

    public Restaurant restaurant;

    public ControllerRestaurantGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void welcomeToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        Parent login = fxmlLoader.load();
        mainPane.getChildren().setAll(login);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
