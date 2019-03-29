package Dialogs;

import MVC.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import util.AnalogInput;
import util.DeviceList;
import util.DeviceToCalibrate;
import util.RemoteDevice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import static util.OSCCommandEnumerations.MAXIMUM;
import static util.OSCCommandEnumerations.MINIMUM;

/**
 * Created by pujamittal on 2/7/19.
 */
public class HomeScreenController {
    private Stage stage;
    private Model model;
    private RemoteDevice device = new RemoteDevice();
    private RemoteDevice selectedDevice;
    private DeviceList deviceList = new DeviceList();

    @FXML private TableView<RemoteDevice> deviceTableView;
    @FXML private TableColumn<RemoteDevice, String> deviceNameColumn;

    @FXML private TextField deviceNameTextField;
    @FXML private TextField portNumberTextField;
    @FXML private TextField hubIPAddressTextField;

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
    @FXML public Button beginScanButton;
    @FXML public Button cancelScanButton;

    @FXML private ProgressBar scanProgress;
    private float progress;

    private boolean startScanning;
    private boolean closeScanWindow;

    private PropertyChangeSupport controllerPropertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        controllerPropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void setDevice(RemoteDevice device) {
        this.device = device;
        deviceNameTextField.setText(device.getDeviceName());
        hubIPAddressTextField.setText(device.getAddressToSendTo().toString());
        portNumberTextField.setText(String.valueOf(device.getPortToSendTo()));
        inputSettingsTableView.getItems().addAll(device.getAnalogInputs());
        inputSettingsTableView.refresh();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void updateDeviceDetailsDisplay() {
        inputSettingsTableView.getItems().clear();
        inputSettingsTableView.getItems().setAll(device.getAnalogInputs());
        inputSettingsTableView.refresh();
    }

    private PropertyChangeSupport homeScreenControllerPropertyChangeSupport = new PropertyChangeSupport(this);

    public void initialize(URL location, ResourceBundle resources) {
        startScanning = false;
        closeScanWindow = false;
        progress = 0;

        beginScanButton.setOnAction(event -> {
            startScanning = true;
            controllerPropertyChangeSupport.firePropertyChange("startScanning", !startScanning, startScanning);
            startScanning = false;
        });

        cancelScanButton.setOnAction(event -> {
            closeScanWindow = true;
            controllerPropertyChangeSupport.firePropertyChange("closeScanWindow", !closeScanWindow, closeScanWindow);
            closeScanWindow = false;
        });

        saveButton.setOnAction(event -> {
            homeScreenControllerPropertyChangeSupport.firePropertyChange("saveDeviceSettings", false, true);
        });

        deviceTableView.setRowFactory(tv -> {
            TableRow<RemoteDevice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedDevice = row.getItem();
                    controllerPropertyChangeSupport.firePropertyChange("viewDeviceDetails", null, selectedDevice);
                }
            });
            return row;
        });

        updateTable();

        deviceTableView.setPlaceholder(new Label("No devices found on network."));
    }

    public void updateTable() {
        inputSettingsTableView.setEditable(true);

        deviceNameTextField.setDisable(true);
        hubIPAddressTextField.setDisable(true);
        portNumberTextField.setDisable(true);

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
            hubIPAddressTextField.setDisable(false);
            acceptIPAddressButton.setVisible(true);
            editIPAddressButton.setDisable(true);
        });

        acceptIPAddressButton.setOnAction(e -> {
            hubIPAddressTextField.setDisable(true);
            acceptIPAddressButton.setVisible(false);
            editIPAddressButton.setDisable(false);
            try {
                device.setAddressToSendTo(InetAddress.getByName(hubIPAddressTextField.getText().replaceAll("^/+","")));
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        });

        editPortButton.setOnAction(e -> {
            portNumberTextField.setDisable(false);
            acceptPortButton.setVisible(true);
            editPortButton.setDisable(true);
        });

        acceptPortButton.setOnAction(e -> {
            portNumberTextField.setDisable(true);
            acceptPortButton.setVisible(false);
            editPortButton.setDisable(false);
            device.setPortToSendTo(Integer.parseInt(portNumberTextField.getText()));
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
                            homeScreenControllerPropertyChangeSupport.firePropertyChange("calibrate", null, new DeviceToCalibrate(device, data.getInputNumber(), MINIMUM));
                            // ADD AN ALERT BOX WITH CALIBRATION INSTRUCTIONS
                        });
                    }

                    private final Button maxBtn = new Button("Maximum");

                    {
                        maxBtn.setOnAction((ActionEvent event) -> {
                            AnalogInput data = getTableView().getItems().get(getIndex());
                            homeScreenControllerPropertyChangeSupport.firePropertyChange("calibrate",null, new DeviceToCalibrate(device, data.getInputNumber(), MAXIMUM));
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
    }

    public ProgressBar getProgressBar() {
        return this.scanProgress;
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Object source = propertyChangeEvent.getSource();
        String property = propertyChangeEvent.getPropertyName();
        if (property.equals("devices")) {
            try {
                model.updateDeviceData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.deviceList = (DeviceList) source;
        }

        if(property.equals("remoteDeviceSaved")) {
            if((boolean)propertyChangeEvent.getNewValue()) {
                updateTable();
            }
        }

    }
}
