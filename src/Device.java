import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package PACKAGE_NAME
 * @date 4/21/2018
 */
public class Device {

    String name;
    InetAddress deviceOutgoingIPAddress;
    int deviceOutgoingPortNumber;
    InetAddress deviceIPAddress;
    int devicePortNumber;
    ArrayList<InputSetting> inputs = new ArrayList<>();

    public Device() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getDeviceOutgoingIPAddress() {
        return deviceOutgoingIPAddress;
    }

    public void setDeviceOutgoingIPAddress(InetAddress deviceOutgoingIPAddress) {
        this.deviceOutgoingIPAddress = deviceOutgoingIPAddress;
    }

    public int getDeviceOutgoingPortNumber() {
        return deviceOutgoingPortNumber;
    }

    public void setDeviceOutgoingPortNumber(int deviceOutgoingPortNumber) {
        this.deviceOutgoingPortNumber = deviceOutgoingPortNumber;
    }

    public InetAddress getDeviceIpAddress() {
        return deviceIPAddress;
    }

    public void setDeviceIpAddress(InetAddress ipAddress) {
        this.deviceIPAddress = ipAddress;
    }

    public int getDevicePortNumber() {
        return devicePortNumber;
    }

    public void setDevicePortNumber(int portNumber) {
        this.devicePortNumber = portNumber;
    }

    public void addInput(int index, int low, int high, int weight) {
        inputs.add(new InputSetting(index, low, high, weight));
    }

    public ArrayList<InputSetting> getInputs() {
        return this.inputs;
    }

    public int getNumberOfInputs() {
        return this.inputs.size();
    }

    public class InputSetting {
        int index;
        int lowRange;
        int highRange;
        int filterWeight;

        private InputSetting() {
        }

        private InputSetting(int index, int low, int high, int weight) {
            this.index = index;
            this.lowRange = low;
            this.highRange = high;
            this.filterWeight = weight;
        }

        public int getLowRange() {
            return lowRange;
        }

        public void setLowRange(int lowRange) {
            this.lowRange = lowRange;
        }

        public int getHighRange() {
            return highRange;
        }

        public void setHighRange(int highRange) {
            this.highRange = highRange;
        }

        public int getFilterWeight() {
            return filterWeight;
        }

        public void setFilterWeight(int filterWeight) {
            this.filterWeight = filterWeight;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
