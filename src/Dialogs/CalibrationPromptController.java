package Dialogs;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package Dialogs
 * @date 11/3/2018
 */
public class CalibrationPromptController implements Initializable {

    @FXML private Label calibrationTimer;

    public IntegerProperty timer = new SimpleIntegerProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calibrationTimer.setText(timer.getValue().toString());
    }

}
