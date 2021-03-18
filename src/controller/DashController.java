package controller;

import java.beans.EventHandler;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.Restaurant;

public class DashController implements Initializable {
    private Restaurant restaurant;
    private ControllerRestaurantGUI cGui;
    @FXML
    private Circle btnCloseLogin;

    @FXML
    private Circle btnMinimizeLogin;

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

    private void refreshNodes() throws ClassNotFoundException {
        pnl_scroll.getChildren().clear();

        Node[] nodes = new Node[15];

        for (int i = 0; i < 10; i++) {
            try {
                nodes[i] = (Node) cGui.showItem();
                pnl_scroll.getChildren().add(nodes[i]);

            } catch (IOException ex) {
                Logger.getLogger(DashController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    void handleButtonAction(MouseEvent event) throws ClassNotFoundException {
        refreshNodes();
    }

    @FXML
    void logOut(ActionEvent event) {

    }

    public void initUser() {
        userIcon.setImage(new Image("file:///" + restaurant.getLoggedUser().getImage()));
        lblUser.setText(restaurant.getLoggedUser().getName());
        lblId.setText(String.valueOf(restaurant.getLoggedUser().getId()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        }
    }

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private AnchorPane parent;

    private void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launch.stage.setX(event.getScreenX() - xOffset);
                Launch.stage.setY(event.getScreenY() - yOffset);
                Launch.stage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            Launch.stage.setOpacity(1.0f);
        });

    }

}
