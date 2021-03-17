package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
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

    private void refreshNodes() {
        pnl_scroll.getChildren().clear();

        Node[] nodes = new Node[15];

        for (int i = 0; i < 10; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/ui/Item.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);

            } catch (IOException ex) {
                Logger.getLogger(ControllerRestaurantGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    void handleButtonAction(MouseEvent event) {
        refreshNodes();
    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshNodes();
    }
}
