package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import model.*;
import controller.*;

public class Main extends Application {

    public Restaurant restaurant;
    public ControllerRestaurantGUI restaurantGUI;

    public Main() {
        restaurant = new Restaurant();
        restaurantGUI = new ControllerRestaurantGUI(restaurant);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("mainPane.fxml"));
        fxmlloader.setController(restaurantGUI);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        restaurantGUI.welcomeToLogin();
    }

}