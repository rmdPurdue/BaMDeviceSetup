package Dialogs;

import MVC.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package Dialogs
 * @date 10/18/2018
 */
public class ScanNetworkController implements Initializable {

    private Model model;
    private float progress;

    @FXML public Button beginScanButton;
    @FXML public Button cancelScanButton;
    @FXML public Button completedOkButton;
    @FXML private HBox buttonBox;
    @FXML private HBox progressBox;
    @FXML private HBox completedBox;
    @FXML private ProgressBar scanProgress;

    private boolean startScanning;
    private boolean closeScanWindow;

    private PropertyChangeSupport scanControllerPropertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        scanControllerPropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        scanControllerPropertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        progress = 0;
        startScanning = false;
        closeScanWindow = false;

        buttonBox.setVisible(true);
        progressBox.setVisible(false);
        completedBox.setVisible(false);

        beginScanButton.setOnAction(event -> {
            updateWindow();
            startScanning = true;
            scanControllerPropertyChangeSupport.firePropertyChange("startScanning", !startScanning, startScanning);
            startScanning = false;
        });

        cancelScanButton.setOnAction(event -> {
            closeScanWindow = true;
            scanControllerPropertyChangeSupport.firePropertyChange("closeScanWindow", !closeScanWindow, closeScanWindow);
            closeScanWindow = false;
        });

        completedOkButton.setOnAction(event -> {
            closeScanWindow = true;
            scanControllerPropertyChangeSupport.firePropertyChange("closeScanWindow", !closeScanWindow, closeScanWindow);
            closeScanWindow = false;
        });
    }

    public ProgressBar getProgressBar() {
        return this.scanProgress;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void updateWindow() {
        buttonBox.setVisible(false);
        progressBox.setVisible(true);
        completedBox.setVisible(false);
    }

    public void scanComplete() {
        buttonBox.setVisible(false);
        progressBox.setVisible(false);
        completedBox.setVisible(true);
    }

    public void resetWindow() {
        buttonBox.setVisible(true);
        progressBox.setVisible(false);
        completedBox.setVisible(false);
    }

}
