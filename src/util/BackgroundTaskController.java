package util;

import Dialogs.CalibrationPromptController;
import Dialogs.DeviceDialogController;
import Dialogs.ScanNetworkController;
import MVC.MainWindowController;
import MVC.Model;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package util
 * @date 10/26/2018
 */
public class BackgroundTaskController implements PropertyChangeListener {
    private Model model;
    private MainWindowController mainWindowController;
    private DeviceDialogController deviceDialogController;
    private ScanNetworkController scanNetworkController;
    private CalibrationPromptController calibrationPromptController;

    private Stage scanDialogStage = new Stage();
    private Stage deviceDialogStage = new Stage();
    private Stage calibrationModalStage = new Stage();


    public void setModel(Model model) {
        this.model = model;
    }

    public void setControllers(ScanNetworkController scanNetworkController, DeviceDialogController deviceDialogController, CalibrationPromptController calibrationPromptController) {
        this.scanNetworkController = scanNetworkController;
        this.deviceDialogController = deviceDialogController;
        this.calibrationPromptController = calibrationPromptController;
    }

    public void setStages(Stage scanDialogStage, Stage deviceDialogStage, Stage calibrationModalStage) {
        this.scanDialogStage = scanDialogStage;
        this.deviceDialogStage = deviceDialogStage;
        this.calibrationModalStage = calibrationModalStage;
    }

    private void openScanNetworkDialog() {
        scanDialogStage.showAndWait();
    }

    private void closeScanNetworkDialog() {
        scanDialogStage.close();
        scanNetworkController.resetWindow();
    }

    private void openDeviceDetailsWindow(RemoteDevice selectedDevice) {
        deviceDialogController.setDevice( selectedDevice );
        deviceDialogController.showStage();
    }

    private void openSaveDetailsConfirmationWindow() {}

    private void closeDeviceDetailsWindow() {
        deviceDialogController.closeStage();
    }

    private void updateDeviceDetailsDisplay() {
        deviceDialogController.updateDeviceDetailsDisplay();
    }

    public void openCalibrateModal() {
        calibrationModalStage.show();
    }


    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Object value = propertyChangeEvent.getNewValue();
        String property = propertyChangeEvent.getPropertyName();

        if (property.equals("openScanDialog")) openScanNetworkDialog();

        if (property.equals("closeScanWindow")) closeScanNetworkDialog();

        if(property.equals("viewDeviceDetails")) openDeviceDetailsWindow((RemoteDevice) value);

        if(property.equals("saveDeviceSettings")) openSaveDetailsConfirmationWindow();

        if(property.equals("closeDetailsWindow")) closeDeviceDetailsWindow();

        if(property.equals("inputCalibrated")) {
            System.out.println("PropertyChange fired.");
            updateDeviceDetailsDisplay();
        }
    }
}
