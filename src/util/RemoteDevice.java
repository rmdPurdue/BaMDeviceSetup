package util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class RemoteDevice {

    private InetAddress ipAddress;
    private String macAddress;
    private String deviceName;
    private InetAddress addressToSendTo;
    private int portToSendTo;
    private ArrayList<AnalogInput> analogInputs;

    public RemoteDevice() {
        this.analogInputs = new ArrayList<>(6);
    }

    public RemoteDevice(InetAddress ipAddress, String macAddress, String deviceName) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.deviceName = deviceName;
        this.analogInputs = new ArrayList<>(6);
        this.analogInputs.add(new AnalogInput(1,0,0,0));
        this.analogInputs.add(new AnalogInput(2,0,0,0));
        this.analogInputs.add(new AnalogInput(3,0,0,0));
        this.analogInputs.add(new AnalogInput(4,0,0,0));
        this.analogInputs.add(new AnalogInput(5,0,0,0));
        this.analogInputs.add(new AnalogInput(6,0,0,0));
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public InetAddress getAddressToSendTo() {
        return addressToSendTo;
    }

    public void setAddressToSendTo(InetAddress addressToSendTo) {
        this.addressToSendTo = addressToSendTo;
    }

    public int getPortToSendTo() {
        return portToSendTo;
    }

    public void setPortToSendTo(int portToSendTo) {
        this.portToSendTo = portToSendTo;
    }

    public ArrayList<AnalogInput> getAnalogInputs() {
        return analogInputs;
    }

    public void setAnalogInputs(ArrayList<AnalogInput> analogInputs) {
        this.analogInputs = analogInputs;
    }

    public int getNumberOfInputs() {
        return analogInputs.size();
    }

    public void addAnalogInput(int inputNumber, int minValue, int maxValue, int filterWeight) {
        analogInputs.add(new AnalogInput(inputNumber, minValue, maxValue, filterWeight));
    }

    public AnalogInput getAnalogInput(int index) {
        int listIndex = 0;
        Iterator itr = analogInputs.iterator();
        while(itr.hasNext()) {
            AnalogInput input = (AnalogInput)itr.next();
            if(input.getInputNumber() == index) {
                listIndex = analogInputs.indexOf(input);
            }
        }
        return analogInputs.get(listIndex);
    }
}
