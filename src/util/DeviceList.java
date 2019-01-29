package util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * @author Rich Dionne
 * @project BaMSensorSetup
 * @package util
 * @date 9/24/2018
 */
public class DeviceList {

    private ArrayList<RemoteDevice> devices = new ArrayList<>();
    private PropertyChangeSupport devicePropertyChangeSupport = new PropertyChangeSupport(this);

    public ArrayList<RemoteDevice> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<RemoteDevice> devices) {
        System.out.println("Updating 'devices'.");
        ArrayList<RemoteDevice> oldDevices = this.devices;
        this.devices = devices;
        devicePropertyChangeSupport.firePropertyChange("devices", oldDevices, devices);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        devicePropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        devicePropertyChangeSupport.removePropertyChangeListener(listener);
    }

    public boolean isEmpty() {
        if(devices.isEmpty()) return true;
        else return false;
    }
}
