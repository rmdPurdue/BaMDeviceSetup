package comm;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author Rich Dionne
 * @project BaMSensorSetup
 * @package comm
 * @date 9/24/2018
 */
public class OSC_Sender {

    public static void sendMessage(InetAddress ipAddress, int port, String url, List<Object> args ) throws IOException {
        // Create an OSC sender
        OSCPortOut sender = new OSCPortOut(ipAddress, port);

        // Create an OSC bundle
        OSCBundle bundle = new OSCBundle();

        // Create and OSC message with the passed URL and arguments
        OSCMessage message = new OSCMessage(url, args);

        // Add this message to our bundle
        bundle.addPacket(message);

        // Send the bundle with our sender
        sender.send(bundle);

        // Close our sender
        sender.close();
    }
}
