import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package PACKAGE_NAME
 * @date 4/22/2018
 */
public class Network {
    private NetworkInterface networkInterface;
    private String name;
    private InetAddress localAddress;
    private String chooserDisplay;

    public Network() {
    }

    public Network(String name, InetAddress localAddress, NetworkInterface networkInterface) {
        this.name = name;
        this.localAddress = localAddress;
        this.networkInterface = networkInterface;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getChooserDisplay() {
        if(this.name.contains("eth")) {
            chooserDisplay = "Ethernet (" + localAddress.toString() + ")";
        }
        if (this.name.contains("wlan") || this.name.contains("en")) {
            chooserDisplay = "Wireless (" + localAddress.toString() + ")";
        } else {
            chooserDisplay = this.name + " (" + localAddress.toString() + ")";
        }
        return chooserDisplay;
    }
}
