
import com.illposed.osc.OSCMessage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
        idEntry.setMaxWidth(100);
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
        Label ipLabel = new Label("Outgoing IP Address:");
        Label ipError = new Label("Please enter a valid IP Address.");
        outgoingIpEntry.setPromptText("Outgoing IP");
        outgoingIpEntry.setMaxWidth(150);
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
        Label portLabel = new Label("Outgoing Port:");
        Label portError = new Label("Please enter a valid port number.");
        outgoingPortEntry.setPromptText("Outgoing Port");
        outgoingPortEntry.setMaxWidth(75);
        portError.setFont(Font.font("Arial", 10));
        portError.setTextFill(Color.RED);
        portError.setVisible(false);
        outgoingPortEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        Button saveAddressButton = new Button("Store");

        // Input 1 (analog 0) setup

        TextField analog0LowEntry = new TextField();
        Label a0LowLabel = new Label("Input 1 Low Range:");
        Label a0LowError = new Label("Please enter a valid value.");
        analog0LowEntry.setPromptText("0");
        analog0LowEntry.setMaxWidth(50);
        a0LowError.setFont(Font.font("Arial", 10));
        a0LowError.setTextFill(Color.RED);
        a0LowError.setVisible(false);
        analog0LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog0HighEntry = new TextField();
        Label a0HighLabel = new Label("High Range:");
        Label a0HighError = new Label("Please enter a valid value.");
        analog0HighEntry.setPromptText("1024");
        analog0HighEntry.setMaxWidth(50);
        a0HighError.setFont(Font.font("Arial", 10));
        a0HighError.setTextFill(Color.RED);
        a0HighError.setVisible(false);
        analog0HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog0WeightEntry = new TextField();
        Label a0WeightLabel = new Label("Filter Weight:");
        Label a0WeightError = new Label("Please enter a valid value.");
        analog0WeightEntry.setPromptText("Weight");
        analog0WeightEntry.setMaxWidth(50);
        a0WeightError.setFont(Font.font("Arial", 10));
        a0WeightError.setTextFill(Color.RED);
        a0WeightError.setVisible(false);
        analog0WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        // Input 2 (analog 1) setup

        TextField analog1LowEntry = new TextField();
        Label a1LowLabel = new Label("Input 2 Low Range:");
        Label a1LowError = new Label("Please enter a valid value.");
        analog1LowEntry.setPromptText("0");
        analog1LowEntry.setMaxWidth(50);
        a1LowError.setFont(Font.font("Arial", 10));
        a1LowError.setTextFill(Color.RED);
        a1LowError.setVisible(false);
        analog1LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog1HighEntry = new TextField();
        Label a1HighLabel = new Label("High Range:");
        Label a1HighError = new Label("Please enter a valid value.");
        analog1HighEntry.setPromptText("1024");
        analog1HighEntry.setMaxWidth(50);
        a1HighError.setFont(Font.font("Arial", 10));
        a1HighError.setTextFill(Color.RED);
        a1HighError.setVisible(false);
        analog0HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog1WeightEntry = new TextField();
        Label a1WeightLabel = new Label("Filter Weight:");
        Label a1WeightError = new Label("Please enter a valid value.");
        analog1WeightEntry.setPromptText("Weight");
        analog1WeightEntry.setMaxWidth(50);
        a1WeightError.setFont(Font.font("Arial", 10));
        a1WeightError.setTextFill(Color.RED);
        a1WeightError.setVisible(false);
        analog1WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        // Input 3 (analog 2) setup

        TextField analog2LowEntry = new TextField();
        Label a2LowLabel = new Label("Input 3 Low Range:");
        Label a2LowError = new Label("Please enter a valid value.");
        analog2LowEntry.setPromptText("0");
        analog2LowEntry.setMaxWidth(50);
        a2LowError.setFont(Font.font("Arial", 10));
        a2LowError.setTextFill(Color.RED);
        a2LowError.setVisible(false);
        analog2LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog2HighEntry = new TextField();
        Label a2HighLabel = new Label("High Range:");
        Label a2HighError = new Label("Please enter a valid value.");
        analog2HighEntry.setPromptText("1024");
        analog2HighEntry.setMaxWidth(50);
        a2HighError.setFont(Font.font("Arial", 10));
        a2HighError.setTextFill(Color.RED);
        a2HighError.setVisible(false);
        analog2HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog2WeightEntry = new TextField();
        Label a2WeightLabel = new Label("Filter Weight:");
        Label a2WeightError = new Label("Please enter a valid value.");
        analog2WeightEntry.setPromptText("Weight");
        analog2WeightEntry.setMaxWidth(50);
        a2WeightError.setFont(Font.font("Arial", 10));
        a2WeightError.setTextFill(Color.RED);
        a2WeightError.setVisible(false);
        analog2WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        // Input 4 (analog 3) setup

        TextField analog3LowEntry = new TextField();
        Label a3LowLabel = new Label("Input 4 Low Range:");
        Label a3LowError = new Label("Please enter a valid value.");
        analog3LowEntry.setPromptText("0");
        analog3LowEntry.setMaxWidth(50);
        a3LowError.setFont(Font.font("Arial", 10));
        a3LowError.setTextFill(Color.RED);
        a3LowError.setVisible(false);
        analog3LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog3HighEntry = new TextField();
        Label a3HighLabel = new Label("High Range:");
        Label a3HighError = new Label("Please enter a valid value.");
        analog3HighEntry.setPromptText("1024");
        analog3HighEntry.setMaxWidth(50);
        a3HighError.setFont(Font.font("Arial", 10));
        a3HighError.setTextFill(Color.RED);
        a3HighError.setVisible(false);
        analog3HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog3WeightEntry = new TextField();
        Label a3WeightLabel = new Label("Filter Weight:");
        Label a3WeightError = new Label("Please enter a valid value.");
        analog3WeightEntry.setPromptText("Weight");
        analog3WeightEntry.setMaxWidth(50);
        a3WeightError.setFont(Font.font("Arial", 10));
        a3WeightError.setTextFill(Color.RED);
        a3WeightError.setVisible(false);
        analog3WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        // Input 5 (analog 4) setup

        TextField analog4LowEntry = new TextField();
        Label a4LowLabel = new Label("Input 5 Low Range:");
        Label a4LowError = new Label("Please enter a valid value.");
        analog4LowEntry.setPromptText("0");
        analog4LowEntry.setMaxWidth(50);
        a4LowError.setFont(Font.font("Arial", 10));
        a4LowError.setTextFill(Color.RED);
        a4LowError.setVisible(false);
        analog4LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog4HighEntry = new TextField();
        Label a4HighLabel = new Label("High Range:");
        Label a4HighError = new Label("Please enter a valid value.");
        analog4HighEntry.setPromptText("1024");
        analog4HighEntry.setMaxWidth(50);
        a4HighError.setFont(Font.font("Arial", 10));
        a4HighError.setTextFill(Color.RED);
        a4HighError.setVisible(false);
        analog4HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog4WeightEntry = new TextField();
        Label a4WeightLabel = new Label("Filter Weight:");
        Label a4WeightError = new Label("Please enter a valid value.");
        analog4WeightEntry.setPromptText("Weight");
        analog4WeightEntry.setMaxWidth(50);
        a4WeightError.setFont(Font.font("Arial", 10));
        a4WeightError.setTextFill(Color.RED);
        a4WeightError.setVisible(false);
        analog4WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        // Input 6 (analog 5) setup

        TextField analog5LowEntry = new TextField();
        Label a5LowLabel = new Label("Input 6 Low Range:");
        Label a5LowError = new Label("Please enter a valid value.");
        analog5LowEntry.setPromptText("0");
        analog5LowEntry.setMaxWidth(50);
        a5LowError.setFont(Font.font("Arial", 10));
        a5LowError.setTextFill(Color.RED);
        a5LowError.setVisible(false);
        analog5LowEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog5HighEntry = new TextField();
        Label a5HighLabel = new Label("High Range:");
        Label a5HighError = new Label("Please enter a valid value.");
        analog5HighEntry.setPromptText("1024");
        analog5HighEntry.setMaxWidth(50);
        a5HighError.setFont(Font.font("Arial", 10));
        a5HighError.setTextFill(Color.RED);
        a5HighError.setVisible(false);
        analog5HighEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        TextField analog5WeightEntry = new TextField();
        Label a5WeightLabel = new Label("Filter Weight:");
        Label a5WeightError = new Label("Please enter a valid value.");
        analog5WeightEntry.setPromptText("Weight");
        analog5WeightEntry.setMaxWidth(50);
        a5WeightError.setFont(Font.font("Arial", 10));
        a5WeightError.setTextFill(Color.RED);
        a5WeightError.setVisible(false);
        analog5WeightEntry.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                // Do some validation
            }
        });

        Button a0CalibrateButton = new Button("Calibrate");
        a0CalibrateButton.setOnAction(event -> {
            OSCMessage message = new OSCMessage();
            message.setAddress("/torso1/setup");
            int[] args = {
                    Integer.parseInt(analog0LowEntry.getText()),
                    Integer.parseInt(analog0HighEntry.getText()),
                    Integer.parseInt(analog0WeightEntry.getText())};
            message.addArgument(args);

        });
        Button a1CalibrateButton = new Button("Calibrate");
        Button a2CalibrateButton = new Button("Calibrate");
        Button a3CalibrateButton = new Button("Calibrate");
        Button a4CalibrateButton = new Button("Calibrate");
        Button a5CalibrateButton = new Button("Calibrate");
        Button saveCalibrationButton = new Button("Store Calibrations");

        GridPane addressGrid = new GridPane();
        addressGrid.setHgap(10);
        addressGrid.setVgap(5);
        addressGrid.add(idLabel, 0,0);
        addressGrid.add(idEntry,1,0);
        addressGrid.add(idError,0,1,2,1);

        addressGrid.add(ipLabel,0,2);
        addressGrid.add(outgoingIpEntry,1,2);
        addressGrid.add(ipError,0,3,2,1);
        addressGrid.add(saveAddressButton,3,2);

        GridPane inputsGrid = new GridPane();
        inputsGrid.setHgap(10);
        inputsGrid.setVgap(5);
        inputsGrid.add(a0LowLabel, 0,0);
        inputsGrid.add(analog0LowEntry,1,0);
        inputsGrid.add(a0LowError,0,1,2,1);
        inputsGrid.add(a0HighLabel, 2,0);
        inputsGrid.add(analog0HighEntry,3,0);
        inputsGrid.add(a0HighError,2,1,2,1);
        inputsGrid.add(a0WeightLabel,4,0);
        inputsGrid.add(analog0WeightEntry,5,0);
        inputsGrid.add(a0WeightError,4,1,2,1);
        inputsGrid.add(a0CalibrateButton,6,0);

        inputsGrid.add(a1LowLabel,0,2);
        inputsGrid.add(analog1LowEntry,1,2);
        inputsGrid.add(a1LowError,0,3,2,1);
        inputsGrid.add(a1HighLabel, 2,2);
        inputsGrid.add(analog1HighEntry,3,2);
        inputsGrid.add(a1HighError,2,3,2,1);
        inputsGrid.add(a1WeightLabel,4,2);
        inputsGrid.add(analog1WeightEntry,5,2);
        inputsGrid.add(a1WeightError,4,3,2,1);
        inputsGrid.add(a1CalibrateButton,6,2);

        inputsGrid.add(a2LowLabel,0,4);
        inputsGrid.add(analog2LowEntry,1,4);
        inputsGrid.add(a2LowError,0,5,2,1);
        inputsGrid.add(a2HighLabel, 2,4);
        inputsGrid.add(analog2HighEntry,3,4);
        inputsGrid.add(a2HighError,2,5,2,1);
        inputsGrid.add(a2WeightLabel,4,4);
        inputsGrid.add(analog2WeightEntry,5,4);
        inputsGrid.add(a2WeightError,4,5,2,1);
        inputsGrid.add(a2CalibrateButton,6,4);

        inputsGrid.add(a3LowLabel,0,6);
        inputsGrid.add(analog3LowEntry,1,6);
        inputsGrid.add(a3LowError,0,7,2,1);
        inputsGrid.add(a3HighLabel, 2,6);
        inputsGrid.add(analog3HighEntry,3,6);
        inputsGrid.add(a3HighError,2,7,2,1);
        inputsGrid.add(a3WeightLabel,4,6);
        inputsGrid.add(analog3WeightEntry,5,6);
        inputsGrid.add(a3WeightError,4,7,2,1);
        inputsGrid.add(a3CalibrateButton,6,6);

        inputsGrid.add(a4LowLabel,0,8);
        inputsGrid.add(analog4LowEntry,1,8);
        inputsGrid.add(a4LowError,0,9,2,1);
        inputsGrid.add(a4HighLabel, 2,8);
        inputsGrid.add(analog4HighEntry,3,8);
        inputsGrid.add(a4HighError,2,9,2,1);
        inputsGrid.add(a4WeightLabel,4,8);
        inputsGrid.add(analog4WeightEntry,5,8);
        inputsGrid.add(a4WeightError,4,9,2,1);
        inputsGrid.add(a4CalibrateButton,6,8);

        inputsGrid.add(a5LowLabel,0,10);
        inputsGrid.add(analog5LowEntry,1,10);
        inputsGrid.add(a5LowError,0,11,2,1);
        inputsGrid.add(a5HighLabel, 2,10);
        inputsGrid.add(analog5HighEntry,3,10);
        inputsGrid.add(a5HighError,2,11,2,1);
        inputsGrid.add(a5WeightLabel,4,10);
        inputsGrid.add(analog5WeightEntry,5,10);
        inputsGrid.add(a5WeightError,4,11,2,1);
        inputsGrid.add(a5CalibrateButton,6,10);

        inputsGrid.add(saveCalibrationButton,5,12,2,1);

        VBox inputFields = new VBox();
        inputFields.setPadding(new Insets(10,10,10,10));
        inputFields.getChildren().addAll(addressGrid, inputsGrid);

        BorderPane window = new BorderPane();
        window.setTop(header);
        window.setCenter(inputFields);

        scene = new Scene(window);
    }
}
