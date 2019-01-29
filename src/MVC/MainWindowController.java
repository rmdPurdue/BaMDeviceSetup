package MVC;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.DeviceList;
import util.RemoteDevice;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, PropertyChangeListener {

    private Model model;
    private DeviceList deviceList = new DeviceList();

    @FXML private TableView<RemoteDevice> deviceTableView;
    @FXML private TableColumn<RemoteDevice, String> deviceNameColumn;
    @FXML private TableColumn<RemoteDevice, InetAddress> ipAddressColumn;
    @FXML private TableColumn<RemoteDevice, String> macAddressColumn;
    @FXML public Button scanNetworkBtn;

    private PropertyChangeSupport controllerPropertyChangeSupport = new PropertyChangeSupport(this);

    private RemoteDevice selectedDevice;

    public MainWindowController() throws SocketException {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        controllerPropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        controllerPropertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         *  Set up table columns for displaying information from the Device List.
         *  Device list information for display here are the "device name,"
         *  the device's MAC address, and the device's current IP Address.
         */

        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        ipAddressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        macAddressColumn.setCellValueFactory(new PropertyValueFactory<>("macAddress"));


        /**
         *  Set up the "scan network" button action; this opens a dialog box window
         *  to confirm scan commencement and to display scan progress.
         */

        scanNetworkBtn.setOnAction(event -> {
            controllerPropertyChangeSupport.firePropertyChange("openScanDialog", false, true);
        });

        /**
         *  Populate the table with values from the Device List. Values should be
         *  double-clickable, which will open the device configuration dialog.
         */

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

        /**
         *  Update the table with the most current Device List data available at this time.
         */

        updateTable();

        /**
         *  Display a placeholder for when no devices are found.
         */

        deviceTableView.setPlaceholder(new Label("No devices found on network."));

    }

    public void updateTable() {
        /**
         *  If the Device List is not empty, clear the table and repopulate it with items
         *  from the Device List.
         */

        if (!deviceList.isEmpty()) {
            deviceTableView.getItems().clear();
            deviceTableView.getItems().addAll(FXCollections.observableList(deviceList.getDevices()));
            deviceTableView.refresh();
        }
    }

    /**
     * Initialize the controller with the data model. Retrieve the Device List data.
     * Start listening for property changes in the Device List.
     * @param model
     */

    public void setModel(Model model) {
        this.model = model;
        this.deviceList = model.getDeviceList();
        model.addPropertyChangeListener(this);
        deviceList.addPropertyChangeListener(this);
    }

    @Override
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
