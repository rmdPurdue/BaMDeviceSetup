import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package PACKAGE_NAME
 * @date 4/17/2018
 */
public class Model {

    public boolean running = false;
    private boolean paused = false;
    public String ipStatus;

    public void start() throws InterruptedException, UnknownHostException, SocketException {
        running = true;
        paused = false;

        ipStatus = "Checking local connection...";
        System.out.println("Started.");



    }
}
