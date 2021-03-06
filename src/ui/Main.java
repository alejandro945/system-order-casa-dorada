package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import model.*;

public class Main extends Application{

    public Restaurant restaurant;
    public RestaurantGUI restaurantGUI;

    public Main(){ 
        restaurant = new Restaurant();
        restaurantGUI = new RestaurantGUI(restaurant);
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("presentation.fxml"));
        fxmlloader.setController(restaurantGUI);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}