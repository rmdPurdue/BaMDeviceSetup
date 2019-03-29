import Dialogs.CalibrationPromptController;
import Dialogs.DeviceDialogController;
import Dialogs.HomeScreenController;
import Dialogs.ScanNetworkController;
import MVC.MainWindowController;
import MVC.Model;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCPortIn;
import comm.DiscoveryQueryListener;
import comm.DeviceDiscoveryQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.BackgroundTaskController;
import util.CountdownTimer;
import util.DeviceToCalibrate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main extends Application implements PropertyChangeListener {

    private Model model;
    private HomeScreenController homeScreenController; //TODO

    private ExecutorService executor;
    private DiscoveryQueryListener discoveryQueryListener;
    private DeviceDiscoveryQuery deviceDiscoveryQuery;
    private CountdownTimer countdownTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*
           Set up primary mainWindowController and scene.
         */

//        URL location = getClass().getResource("MVC/view.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(location);
//        Parent root = fxmlLoader.load();
//        mainWindowController = fxmlLoader.getController();
//        primaryStage.setTitle("BaM Device Configuration");
//        primaryStage.setScene(new Scene(root, 1000, 800));
//        primaryStage.show();


        //TODO: this whole section
        FXMLLoader homeFXMLLoader = new FXMLLoader(getClass().getResource("/Dialogs/homeScreen.fxml"));
        Parent homeModal = homeFXMLLoader.load();
        homeScreenController = homeFXMLLoader.getController();
        Scene homeScreenScene = new Scene(homeModal, 700, 500);
        primaryStage.setTitle("Home Screen");
        primaryStage.setScene(homeScreenScene);
        primaryStage.show();

        /*
           Create a new data model.
         */

        model = new Model();

        /*
           Establish background threads for application services
         */

        executor = Executors.newFixedThreadPool(5);
        deviceDiscoveryQuery = new DeviceDiscoveryQuery(10);
        discoveryQueryListener = new DiscoveryQueryListener();
        executor.submit(discoveryQueryListener);

        countdownTimer = new CountdownTimer(5);

        /*
           Hook up controllers to model.
         */
        hookupConnections();

        /*
           Start listening for OSC messages here.
         */

        startListeningOSC();

        // Add close application handler to kill all threads
    }

    private void hookupConnections() throws SocketException {
        /*
           Connect controllers to the model.
         */

        homeScreenController.setModel(model); //TODO: fix this

        /*
            Add property change listeners for talk back from controllers and discovery query.
         */

        homeScreenController.addPropertyChangeListener(this); //TODO: fix this

        /*
            Bind scan network dialog box progress bar to query timeout progress.
         */
        homeScreenController.getProgressBar().progressProperty().bind(deviceDiscoveryQuery.getTimeRemainingInSeconds().divide(10)); //TODO: make sure this is ok

    }

    private void startListeningOSC() throws SocketException {
        // Create an OSC receiver object on port 8001
        OSCPortIn receiver = new OSCPortIn(8001);

        // Create an OSC listener, connect to model method for parsing the message
        OSCListener listener = (time, message) -> {
            System.out.println("Received message addressed to: " + message.getAddress());
            model.parseIncomingOSCMessage(message);
        };

        // Add listener for "/device_setup" messages
        receiver.addListener("/device_setup", listener);

        // Add listener for "/calibrate/*" messages
        receiver.addListener("/calibrate/*", listener);

        // Add listener for "/saved" messages
        receiver.addListener("/saved", listener);

        // Start listener thread
        receiver.startListening();
    }

    private void startNetworkDiscoveryScan() {

        // Start the network discovery scan thread
        executor.submit(deviceDiscoveryQuery);
    }

    private void cleanUpAfterNetworkScan() {

        // Update windows to reflect completed discovery process
        //scanNetworkController.scanComplete();

        try {
            // Update the model with device data
            model.updateDeviceData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stop listener for discovery responses
        discoveryQueryListener.stopDiscoveryListening();

        // update model with found devices
        model.setDevices(discoveryQueryListener.getDiscoveredDevices());

        // refresh main display
        homeScreenController.updateDeviceDetailsDisplay();
    }

    private void calibrate(DeviceToCalibrate deviceToCalibrate) {

        // update display to display model window for calibration
//        backgroundTaskController.openCalibrateModal();

        // Start a timer
        executor.submit(countdownTimer);
        try {

            // send calibration command to remote device
            model.sendCalibrationCommand(deviceToCalibrate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        // Property change listener setup

        String property = propertyChangeEvent.getPropertyName();
        Object value = propertyChangeEvent.getNewValue();

        if(property.equals("startScanning")) startNetworkDiscoveryScan();

        if(property.equals("scanComplete")) cleanUpAfterNetworkScan();

        if(property.equals("calibrate")) {
            calibrate((DeviceToCalibrate)value);
        }

    }
}
