package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import model.Restaurant;

public class DashController implements Initializable {
    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblId;

    @FXML
    private ImageView userIcon;

    @FXML
    private VBox pnl_scroll;

    @FXML
    private Label lbl_currentprojects;

    @FXML
    private Label lbl_pending;

    @FXML
    private Label lbl_completed;

    public DashController(Restaurant restaurant, ControllerRestaurantGUI cGui) {
        this.restaurant = restaurant;
        this.cGui = cGui;
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        cGui.welcomeToLogin();
    }

    public void initUser() {
        userIcon.setImage(new Image("file:///" + restaurant.getLoggedUser().getImage()));
        lblUser.setText(restaurant.getLoggedUser().getName());
        lblId.setText(String.valueOf(restaurant.getLoggedUser().getId()));
    }

    @FXML
    public void showListCostumers(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showCostumers();
    }

    @FXML
    public void showListUsers(ActionEvent event) throws ClassNotFoundException, IOException {
        cGui.showUsers();
    }

    @FXML
    void showListEmployees(ActionEvent event) throws ClassNotFoundException, IOException {
        cGui.showEmployees();
    }

    @FXML
    void showIngredients(ActionEvent event) throws IOException, ClassNotFoundException {
        cGui.showIngredients();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
