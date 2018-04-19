
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package com.illposed.osc.utility
 * @date 4/17/2018
 */
public class View {
    public Scene scene;

    public View() {
        Label title = new Label("Bodies as Music Device Setup");
        title.setFont(new Font("Arial", 24));
        title.setPadding(new Insets(5, 10, 5, 20));

        VBox header = new VBox();
        header.setSpacing(10);
        header.setPadding(new Insets(5, 0, 20, 0));
        header.getChildren().addAll(title);

        TextField idEntry = new TextField();
        Label idLabel = new Label("Device ID:");
        Label idError = new Label("Please enter a valid ID.");
        idEntry.setPromptText("Device ID");
        idEntry.setMaxWidth(50);
        idError.setFont(Font.font("Arial", 10));
        idError.setTextFill(Color.RED);
        idError.setVisible(false);
        idEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        idEntry.requestFocus();

        TextField outgoingIpEntry = new TextField();
        Label ipLabel = new Label("Device ID:");
        Label ipError = new Label("Please enter a valid ID.");
        outgoingIpEntry.setPromptText("Device ID");
        outgoingIpEntry.setMaxWidth(50);
        ipError.setFont(Font.font("Arial", 10));
        ipError.setTextFill(Color.RED);
        ipError.setVisible(false);
        outgoingIpEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        outgoingIpEntry.requestFocus();

        TextField outgoingPortEntry = new TextField();
        Label portLabel = new Label("Device ID:");
        Label portError = new Label("Please enter a valid ID.");
        outgoingPortEntry.setPromptText("Device ID");
        outgoingPortEntry.setMaxWidth(50);
        portError.setFont(Font.font("Arial", 10));
        portError.setTextFill(Color.RED);
        portError.setVisible(false);
        outgoingPortEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        outgoingPortEntry.requestFocus();

        TextField analog0LowEntry = new TextField();
        Label a0LowLabel = new Label("Device ID:");
        Label a0LowError = new Label("Please enter a valid ID.");
        analog0LowEntry.setPromptText("Device ID");
        analog0LowEntry.setMaxWidth(50);
        a0LowError.setFont(Font.font("Arial", 10));
        a0LowError.setTextFill(Color.RED);
        a0LowError.setVisible(false);
        analog0LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog0LowEntry.requestFocus();

        TextField analog1LowEntry = new TextField();
        Label a1LowLabel = new Label("Device ID:");
        Label a1LowError = new Label("Please enter a valid ID.");
        analog1LowEntry.setPromptText("Device ID");
        analog1LowEntry.setMaxWidth(50);
        a1LowError.setFont(Font.font("Arial", 10));
        a1LowError.setTextFill(Color.RED);
        a1LowError.setVisible(false);
        analog1LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog1LowEntry.requestFocus();

        TextField analog2LowEntry = new TextField();
        Label a2LowLabel = new Label("Device ID:");
        Label a2LowError = new Label("Please enter a valid ID.");
        analog2LowEntry.setPromptText("Device ID");
        analog2LowEntry.setMaxWidth(50);
        a2LowError.setFont(Font.font("Arial", 10));
        a2LowError.setTextFill(Color.RED);
        a2LowError.setVisible(false);
        analog2LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog2LowEntry.requestFocus();

        TextField analog3LowEntry = new TextField();
        Label a3LowLabel = new Label("Device ID:");
        Label a3LowError = new Label("Please enter a valid ID.");
        analog3LowEntry.setPromptText("Device ID");
        analog3LowEntry.setMaxWidth(50);
        a3LowError.setFont(Font.font("Arial", 10));
        a3LowError.setTextFill(Color.RED);
        a3LowError.setVisible(false);
        analog3LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog3LowEntry.requestFocus();

        TextField analog4LowEntry = new TextField();
        Label a4LowLabel = new Label("Device ID:");
        Label a4LowError = new Label("Please enter a valid ID.");
        analog4LowEntry.setPromptText("Device ID");
        analog4LowEntry.setMaxWidth(50);
        a4LowError.setFont(Font.font("Arial", 10));
        a4LowError.setTextFill(Color.RED);
        a4LowError.setVisible(false);
        analog4LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog4LowEntry.requestFocus();

        TextField analog5LowEntry = new TextField();
        Label a5LowLabel = new Label("Device ID:");
        Label a5LowError = new Label("Please enter a valid ID.");
        analog5LowEntry.setPromptText("Device ID");
        analog5LowEntry.setMaxWidth(50);
        a5LowError.setFont(Font.font("Arial", 10));
        a5LowError.setTextFill(Color.RED);
        a5LowError.setVisible(false);
        analog5LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });
        analog5LowEntry.requestFocus();

        GridPane grid = new GridPane();
        grid.add(idLabel, 0,0);
        grid.add(idEntry,1,0);
        grid.add(idError,0,1,2,1);
        grid.add(ipLabel,0,2);
        grid.add(outgoingIpEntry,1,2);
        grid.add(ipError,0,3,2,1);

        

    }
}
