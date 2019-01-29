package MVC;

import com.illposed.osc.OSCMessage;
import comm.OSC_Sender;
import util.DeviceList;
import util.DeviceToCalibrate;
import util.RemoteDevice;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import static util.OSCCommandEnumerations.GET_SETTINGS;
import static util.URLEnumerations.*;
import static util.portEnumerations.OSC_SEND_PORT;

public class Model {

    private DeviceList deviceList = new DeviceList();
    private PropertyChangeSupport modelPropertyChangeSupport = new PropertyChangeSupport(this);
    private boolean inputCalibrated;
    private boolean remoteDeviceSaved;
    private boolean dataUpdated;

    public Model() {
        inputCalibrated = false;
        remoteDeviceSaved = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        modelPropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        modelPropertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setDevices(ArrayList<RemoteDevice> devices) {
        deviceList.setDevices(devices);
    }

    public DeviceList getDeviceList() { return deviceList; }

    public void updateDeviceData() throws IOException {
        System.out.println("Updating " + deviceList.getDevices().size() + " devices.");
        for(RemoteDevice device : deviceList.getDevices()) {
            List<Object> args = new ArrayList<>();
            args.add(GET_SETTINGS.getValue());
            OSC_Sender.sendMessage(device.getIpAddress(),OSC_SEND_PORT.getValue(), SETUP.toString(), args);
        }
    }

    public void parseIncomingOSCMessage(OSCMessage message) throws UnknownHostException {
        System.out.println(message.getAddress());
        if(message.matches("/device_setup")) {
            System.out.println("Got message addressed to: /device_setup");
            String senderMACAddress = (String)message.getArguments().get(0);
            Iterator itr = deviceList.getDevices().iterator();
            while (itr.hasNext()) {
                RemoteDevice dev = (RemoteDevice) itr.next();
                if (dev.getMacAddress().equals(senderMACAddress)) {

                    System.out.println("Message has " + message.getArguments().size() + " arguments.");

                    String address = message.getArguments().get(2) + "." +
                            message.getArguments().get(3) + "." +
                            message.getArguments().get(4) + "." +
                            message.getArguments().get(5);

                    dev.setAddressToSendTo(InetAddress.getByName(address));

                    dev.setPortToSendTo((int)message.getArguments().get(6));

                    dev.getAnalogInputs().get(0).setMinValue((int) message.getArguments().get(7));
                    dev.getAnalogInputs().get(0).setMaxValue((int) message.getArguments().get(8));
                    dev.getAnalogInputs().get(0).setFilterWeight((int) message.getArguments().get(9));

                    dev.getAnalogInputs().get(1).setMinValue((int) message.getArguments().get(10));
                    dev.getAnalogInputs().get(1).setMaxValue((int) message.getArguments().get(11));
                    dev.getAnalogInputs().get(1).setFilterWeight((int) message.getArguments().get(12));

                    dev.getAnalogInputs().get(2).setMinValue((int) message.getArguments().get(13));
                    dev.getAnalogInputs().get(2).setMaxValue((int) message.getArguments().get(14));
                    dev.getAnalogInputs().get(2).setFilterWeight((int) message.getArguments().get(15));

                    dev.getAnalogInputs().get(3).setMinValue((int) message.getArguments().get(16));
                    dev.getAnalogInputs().get(3).setMaxValue((int) message.getArguments().get(17));
                    dev.getAnalogInputs().get(3).setFilterWeight((int) message.getArguments().get(18));

                    dev.getAnalogInputs().get(4).setMinValue((int) message.getArguments().get(19));
                    dev.getAnalogInputs().get(4).setMaxValue((int) message.getArguments().get(20));
                    dev.getAnalogInputs().get(4).setFilterWeight((int) message.getArguments().get(21));

                    dev.getAnalogInputs().get(5).setMinValue((int) message.getArguments().get(22));
                    dev.getAnalogInputs().get(5).setMaxValue((int) message.getArguments().get(23));
                    dev.getAnalogInputs().get(5).setFilterWeight((int) message.getArguments().get(24));
                }
            }

        }

        if(message.matches("/calibrate/low")) {
            String senderMACAddress = (String)message.getArguments().get(0);
            Iterator itr = deviceList.getDevices().iterator();
            while (itr.hasNext()) {
                RemoteDevice dev = (RemoteDevice) itr.next();
                if (dev.getMacAddress().equals(senderMACAddress)) {
                    dev.getAnalogInput((Integer) message.getArguments().get(1)).setMinValue((Integer) message.getArguments().get(2));
                    dataUpdated = true;
                    modelPropertyChangeSupport.firePropertyChange("inputCalibrated", !dataUpdated, dataUpdated);
                    dataUpdated = false;
                }
            }
        }

        if(message.matches("/calibrate/high")) {
            String senderMACAddress = (String)message.getArguments().get(0);
            Iterator itr = deviceList.getDevices().iterator();
            while(itr.hasNext()) {
                RemoteDevice dev = (RemoteDevice) itr.next();
                if(dev.getMacAddress().equals(senderMACAddress)) {
                    dev.getAnalogInput((Integer)message.getArguments().get(1)).setMaxValue((Integer)message.getArguments().get(2));
                    inputCalibrated = true;
                    modelPropertyChangeSupport.firePropertyChange("inputCalibrated", !inputCalibrated, inputCalibrated);
                    inputCalibrated = false;
                }
            }
        }

        if(message.matches("/saved")) {
            System.out.println("Got a saved confirmation.");
            if((boolean)message.getArguments().get(0)) {
                remoteDeviceSaved = true;
                modelPropertyChangeSupport.firePropertyChange("remoteDeviceSaved", !remoteDeviceSaved, remoteDeviceSaved);
                remoteDeviceSaved = false;
            }
        }
    }

    public void sendCalibrationCommand(DeviceToCalibrate deviceToCalibrate) throws IOException {
        System.out.println("Sending command.");
        List<Object>args = new ArrayList<>();
        args.add(deviceToCalibrate.getInputNumber());
        args.add(deviceToCalibrate.getSettingToCalibrate().getValue());
        OSC_Sender.sendMessage(deviceToCalibrate.getDevice().getIpAddress(), OSC_SEND_PORT.getValue(), CALIBRATE.toString(), args);
    }

    public void sendUpdateFirmwareCommand(RemoteDevice device) throws IOException {
        List<Object> args = new ArrayList<>();
        args.add(device.getDeviceName());
        args.add(device.getDeviceName().length());
        byte[] bytes = device.getAddressToSendTo().getAddress();
        for(int i = 0; i < bytes.length; i++) {
            args.add((int)bytes[i]);
        }
        args.add(device.getPortToSendTo());
        for(int i = 0; i < 6; i++) {
            args.add(device.getAnalogInputs().get(i).getMinValue());
            args.add(device.getAnalogInputs().get(i).getMaxValue());
            args.add(device.getAnalogInputs().get(i).getFilterWeight());
        }
        OSC_Sender.sendMessage(device.getIpAddress(),OSC_SEND_PORT.getValue(), SAVE_SETTINGS.toString(), args);
    }

}
