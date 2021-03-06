package ui;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import model.*;

public class RestaurantGUI {

    @FXML
    private Pane mainPane;
    
    @FXML
    private ImageView imageView;

    public Restaurant restaurant;
    
    public RestaurantGUI(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    
}
