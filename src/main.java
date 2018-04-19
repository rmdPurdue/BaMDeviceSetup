import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;

public class main extends Application {
    private Stage stage;
    private View view;
    private Model model;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        view = new View();
        this.stage = stage;
        hookupEvents();

        this.stage.setTitle("Device Setup");
        this.stage.sizeToScene();
        this.stage.setScene(view.scene);
        this.stage.setResizable(false);
        this.stage.show();

        this.stage.setOnCloseRequest(event -> closeProgram());

        OSCBundle bundle = new OSCBundle();
        OSCMessage message = new OSCMessage();
        message.setAddress("/torso2/setup/device_name");
        message.addArgument("/torso1");

        bundle.addPacket(message);

        OSCPortOut sender = new OSCPortOut(InetAddress.getByName("10.101.1.11"), 9000);
        sender.send(bundle);
        System.out.println(message.getAddress() + " : " + message.getArguments());
        sender.close();

        System.out.println("Hello world");
    }

    private void closeProgram() {
        System.out.println("Closing.");
        stage.close();
    }

    private void hookupEvents() {

    }
}
