package Dialogs;

import MVC.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.RemoteDevice;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package Dialogs
 * @date 10/17/2018
 */
public class SaveSettingsDialogController implements Initializable {

    private Model model;

    @FXML public Button okSaveButton;
    @FXML public Button dontSaveButton;

    private RemoteDevice device;

    public SaveSettingsDialogController() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setDevice(RemoteDevice device) {
        this.device = device;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        okSaveButton.setOnAction(event -> {
            try {
                model.sendUpdateFirmwareCommand(device);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            closeStage(event);
        });

        dontSaveButton.setOnAction(event -> {
            closeStage(event);
        });
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
