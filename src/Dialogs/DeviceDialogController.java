package Dialogs;

import MVC.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import util.AnalogInput;
import util.DeviceToCalibrate;
import util.RemoteDevice;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import static util.OSCCommandEnumerations.*;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package Dialogs
 * @date 9/30/2018
 */
public class DeviceDialogController implements Initializable {

    private Model model;
    private Stage stage;

    @FXML private TextField deviceNameTextField;
    @FXML private TextField outgoingIPAddressTextField;
    @FXML private TextField outgoingPortTextField;

    @FXML private Button editNameButton;
    @FXML private Button editIPAddressButton;
    @FXML private Button editPortButton;

    @FXML private Button acceptNameButton;
    @FXML private Button acceptIPAddressButton;
    @FXML private Button acceptPortButton;

    @FXML private TableView<AnalogInput> inputSettingsTableView;
    @FXML private TableColumn<AnalogInput, Integer> inputNumberColumn;
    @FXML private TableColumn<AnalogInput, Integer> minValueColumn;
    @FXML private TableColumn<AnalogInput, Integer> maxValueColumn;
    @FXML private TableColumn<AnalogInput, Integer> filterWeightColumn;
    @FXML private TableColumn calibrateColumn;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private SaveSettingsDialogController saveSettingsDialogController;

    private PropertyChangeSupport deviceDialogControllerPropertyChangeSupport = new PropertyChangeSupport(this);

    private RemoteDevice device = new RemoteDevice();

    public DeviceDialogController() {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        deviceDialogControllerPropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        deviceDialogControllerPropertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setDevice(RemoteDevice device) {
        this.device = device;
        deviceNameTextField.setText(device.getDeviceName());
        outgoingIPAddressTextField.setText(device.getAddressToSendTo().toString());
        outgoingPortTextField.setText(String.valueOf(device.getPortToSendTo()));
        inputSettingsTableView.getItems().addAll(device.getAnalogInputs());
        inputSettingsTableView.refresh();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showStage() {
        stage.showAndWait();
    }

    public void closeStage() {
        stage.close();
    }

    public void updateDeviceDetailsDisplay() {
        inputSettingsTableView.getItems().clear();
        inputSettingsTableView.getItems().setAll(device.getAnalogInputs());
        inputSettingsTableView.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputSettingsTableView.setEditable(true);

        deviceNameTextField.setDisable(true);
        outgoingIPAddressTextField.setDisable(true);
        outgoingPortTextField.setDisable(true);

        acceptNameButton.setVisible(false);
        acceptIPAddressButton.setVisible(false);
        acceptPortButton.setVisible(false);

        editNameButton.setOnAction(e -> {
            deviceNameTextField.setDisable(false);
            acceptNameButton.setVisible(true);
            editNameButton.setDisable(true);
        });

        acceptNameButton.setOnAction(e -> {
            deviceNameTextField.setDisable(true);
            acceptNameButton.setVisible(false);
            editNameButton.setDisable(false);
            device.setDeviceName(deviceNameTextField.getText());
        });

        editIPAddressButton.setOnAction(e -> {
            outgoingIPAddressTextField.setDisable(false);
            acceptIPAddressButton.setVisible(true);
            editIPAddressButton.setDisable(true);
        });

        acceptIPAddressButton.setOnAction(e -> {
            outgoingIPAddressTextField.setDisable(true);
            acceptIPAddressButton.setVisible(false);
            editIPAddressButton.setDisable(false);
            try {
                device.setAddressToSendTo(InetAddress.getByName(outgoingIPAddressTextField.getText().replaceAll("^/+","")));
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        });

        editPortButton.setOnAction(e -> {
            outgoingPortTextField.setDisable(false);
            acceptPortButton.setVisible(true);
            editPortButton.setDisable(true);
        });

        acceptPortButton.setOnAction(e -> {
            outgoingPortTextField.setDisable(true);
            acceptPortButton.setVisible(false);
            editPortButton.setDisable(false);
            device.setPortToSendTo(Integer.parseInt(outgoingPortTextField.getText()));
        });

        inputNumberColumn.setCellValueFactory(new PropertyValueFactory<>("inputNumber"));
        minValueColumn.setCellValueFactory(new PropertyValueFactory<>("minValue"));
        maxValueColumn.setCellValueFactory(new PropertyValueFactory<>("maxValue"));
        filterWeightColumn.setCellValueFactory(new PropertyValueFactory<>("filterWeight"));
        filterWeightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        filterWeightColumn.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setFilterWeight(t.getNewValue());
                }
        );
        filterWeightColumn.setEditable(true);

        Callback<TableColumn<AnalogInput, Void>, TableCell<AnalogInput, Void>> cellFactory = new Callback<TableColumn<AnalogInput, Void>, TableCell<AnalogInput, Void>>() {
            @Override
            public TableCell<AnalogInput, Void> call(final TableColumn<AnalogInput, Void> param) {
                final TableCell<AnalogInput, Void> cell = new TableCell<AnalogInput, Void>() {

                    private final Button minBtn = new Button("Minimum");

                    {
                        minBtn.setOnAction((ActionEvent event) -> {
                            AnalogInput data = getTableView().getItems().get(getIndex());
                            deviceDialogControllerPropertyChangeSupport.firePropertyChange("calibrate", null, new DeviceToCalibrate(device, data.getInputNumber(), MINIMUM));
                            // ADD AN ALERT BOX WITH CALIBRATION INSTRUCTIONS
                        });
                    }

                    private final Button maxBtn = new Button("Maximum");

                    {
                        maxBtn.setOnAction((ActionEvent event) -> {
                            AnalogInput data = getTableView().getItems().get(getIndex());
                            deviceDialogControllerPropertyChangeSupport.firePropertyChange("calibrate",null, new DeviceToCalibrate(device, data.getInputNumber(), MAXIMUM));
                            // ADD AN ALERT BOX WITH CALIBRATION INSTRUCTIONS
                        });
                    }

                    HBox btnPane = new HBox(minBtn, maxBtn);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnPane);
                        }
                    }
                };
                return cell;
            }
        };
        calibrateColumn.setCellFactory(cellFactory);

        saveButton.setOnAction(event -> {
            deviceDialogControllerPropertyChangeSupport.firePropertyChange("saveDeviceSettings", false, true);
        });

        cancelButton.setOnAction(event -> {
            deviceDialogControllerPropertyChangeSupport.firePropertyChange("closeDetailsWindow", false, true);
        });
    }

}
