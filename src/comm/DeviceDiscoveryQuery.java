package comm;

import javafx.beans.property.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import static util.portEnumerations.UDP_SEND_PORT;

public class DeviceDiscoveryQuery implements Runnable {

    private InetAddress multicastIP = InetAddress.getByName("239.0.0.57");
    private DatagramSocket socket = null;
    private boolean newDiscovery = true;
    private int timeoutInSeconds = 0;
    private DoubleProperty timeRemainingInSeconds = new SimpleDoubleProperty(0.00);
    private PropertyChangeSupport discoveryCompletePropertyChange = new PropertyChangeSupport(this);
    private boolean discoveryComplete = false;


    public DeviceDiscoveryQuery(int timeoutInSeconds) throws UnknownHostException {
        this.timeoutInSeconds = timeoutInSeconds;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        discoveryCompletePropertyChange.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        discoveryCompletePropertyChange.removePropertyChangeListener(listener);
    }
    public DoubleProperty getTimeRemainingInSeconds() {
        return timeRemainingInSeconds;
    }

    private void unsetNewDiscovery() {
        newDiscovery = false;
    }

    private void stopDiscovery() {
        Thread.currentThread().interrupt();
        discoveryComplete = true;
        discoveryCompletePropertyChange.firePropertyChange("scanComplete", !discoveryComplete, discoveryComplete);
    }

    @Override
    public void run() {
        // Set start time to current time
        long startTimeInMillis = System.currentTimeMillis();
        discoveryComplete = false;

        while(!discoveryComplete) {
            // set time remaining in seconds
            timeRemainingInSeconds.set((double)(System.currentTimeMillis() - startTimeInMillis) / 1000);

            // if time left is larger than timeout, stop process
            if(timeRemainingInSeconds.get() >= timeoutInSeconds) stopDiscovery();

            try {
                // Open a UDP socket
                socket = new DatagramSocket(UDP_SEND_PORT.getValue());

                // If we haven't heard from anyone before, set our message to "New Hello," otherwise, set it to "Hello"
                // TODO: This obviously isn't going to work if we have more than one device we are trying to discover.
                String message = (newDiscovery) ? "New Hello?" : "Hello?";

                // Convert our message to a byte array
                byte[] multicastMsg = message.getBytes();

                // Create a UDP datagram packet with our message, addressed to the Multicast IP group and the UDP port set previously
                DatagramPacket packet = new DatagramPacket(multicastMsg, multicastMsg.length, multicastIP, UDP_SEND_PORT.getValue());

                // Send our outgoing message
                socket.send(packet);

                // Update properties
                if (newDiscovery) unsetNewDiscovery();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Close our UDP socket
            socket.close();

            // Wait 250 millis before doing it again
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // don't worry about it.
            }
        }
    }
}
