package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderController {
    public void initStateProduct() {
        ObservableList<String> comBox = FXCollections.observableArrayList("Select an option", "REQUESTED", "IN PROCCES",
                "SEND", "DELIVERED");
        // comBoxStateOrder.setValue("Select an option");
        // comBoxStateOrder.setItems(comBox);
    }
}
