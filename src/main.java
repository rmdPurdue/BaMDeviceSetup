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
    private MainWindowController mainWindowController;
    private DeviceDialogController deviceDialogController;
    private ScanNetworkController scanNetworkController;
    private HomeScreenController homeScreenController; //TODO
    private CalibrationPromptController calibrationPromptController;
    private BackgroundTaskController backgroundTaskController = new BackgroundTaskController();

    private Stage scanDialogStage = new Stage();
    private Stage deviceDialogStage = new Stage();
    private Stage calibrationModalStage = new Stage();
    private Stage homeStage = new Stage(); //TODO

    private ExecutorService executor;
    private DiscoveryQueryListener discoveryQueryListener;
    private DeviceDiscoveryQuery deviceDiscoveryQuery;
    private CountdownTimer countdownTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*
           Set up primary mainWindowController and scene.
         */

        URL location = getClass().getResource("MVC/view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        mainWindowController = fxmlLoader.getController();
        primaryStage.setTitle("BaM Device Configuration");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

        /*
           Set up device edit dialog mainWindowController and scene.
         */

        FXMLLoader deviceDialogFXMLLoader = new FXMLLoader(getClass().getResource("/Dialogs/editDeviceDialog.fxml"));
        Parent deviceDialog = deviceDialogFXMLLoader.load();
        deviceDialogController = deviceDialogFXMLLoader.getController();
        Scene deviceDialogScene = new Scene(deviceDialog, 650, 600);
        deviceDialogStage.initModality(Modality.APPLICATION_MODAL);
        deviceDialogStage.setTitle("Edit Device Configuration");
        deviceDialogStage.setScene(deviceDialogScene);

        /*
           Set up scan network dialog mainWindowController and scene.
         */

        FXMLLoader scanFXMLLoader = new FXMLLoader(getClass().getResource("/Dialogs/scanNetworkModal.fxml"));
        Parent modal = scanFXMLLoader.load();
        scanNetworkController = scanFXMLLoader.getController();
        Scene scanDialogScene = new Scene(modal, 600, 225);
        scanDialogStage.initModality(Modality.APPLICATION_MODAL);
        scanDialogStage.setTitle("Scan Network");
        scanDialogStage.setScene(scanDialogScene);

        /*
           Set up home screen mainWindowController and scene.
         */
        //TODO: this whole section
        FXMLLoader homeFXMLLoader = new FXMLLoader(getClass().getResource("/Dialogs/homeScreen.fxml"));
        Parent homeModal = homeFXMLLoader.load();
        homeScreenController = homeFXMLLoader.getController();
        Scene homeScreenScene = new Scene(homeModal, 500, 700);
        homeStage.initModality(Modality.APPLICATION_MODAL);
        homeStage.setTitle("Home Screen");
        homeStage.setScene(homeScreenScene);

        /*
            Set up calibration prompt controllers and scene.
         */

        FXMLLoader calibrationFXMLLoader = new FXMLLoader(getClass().getResource("/Dialogs/calibrationPrompt.fxml"));
        Parent calibrationModal = calibrationFXMLLoader.load();
        calibrationPromptController = calibrationFXMLLoader.getController();
        Scene calibrationModalScene = new Scene(calibrationModal, 600, 225);
        calibrationModalStage.initModality(Modality.APPLICATION_MODAL);
        calibrationModalStage.setTitle("Calibrate Input");
        calibrationModalStage.setScene(calibrationModalScene);

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

        mainWindowController.setModel(model);
        scanNetworkController.setModel(model);
        //deviceDialogController.setModel(model);
        homeScreenController.setModel(model); //TODO: fix this
        backgroundTaskController.setModel(model);
        backgroundTaskController.setControllers(scanNetworkController, deviceDialogController, calibrationPromptController);

        /*
            Connect controllers to their stages.
         */

        //deviceDialogController.setStage(deviceDialogStage);
        backgroundTaskController.setStages(scanDialogStage, deviceDialogStage, calibrationModalStage);
        homeScreenController.setStage(homeStage);

        /*
            Add property change listeners for talk back from controllers and discovery query.
         */
        mainWindowController.addPropertyChangeListener(this);
        mainWindowController.addPropertyChangeListener(backgroundTaskController);
        scanNetworkController.addPropertyChangeListener(this);
        scanNetworkController.addPropertyChangeListener(backgroundTaskController);
        homeScreenController.addPropertyChangeListener(this); //TODO: fix this
        homeScreenController.addPropertyChangeListener(backgroundTaskController); //TODO: fix this
        deviceDiscoveryQuery.addPropertyChangeListener(this);
//        deviceDialogController.addPropertyChangeListener(this);
//        deviceDialogController.addPropertyChangeListener(backgroundTaskController);
        model.addPropertyChangeListener(backgroundTaskController);

        /*
            Bind scan network dialog box progress bar to query timeout progress.
         */
        scanNetworkController.getProgressBar().progressProperty().bind(deviceDiscoveryQuery.getTimeRemainingInSeconds().divide(10));
        homeScreenController.getProgressBar().progressProperty().bind(deviceDiscoveryQuery.getTimeRemainingInSeconds().divide(10)); //TODO: make sure this is ok
        calibrationPromptController.timer.bind(countdownTimer.getTimeRemainingInSeconds());


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
        scanNetworkController.scanComplete();

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
        mainWindowController.updateTable();
    }

    private void calibrate(DeviceToCalibrate deviceToCalibrate) {

        // update display to display model window for calibration
        backgroundTaskController.openCalibrateModal();

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
