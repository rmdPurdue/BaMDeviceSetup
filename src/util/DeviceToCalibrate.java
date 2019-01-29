package util;

/**
 * @author Rich Dionne
 * @project BaMDeviceSetup
 * @package util
 * @date 10/20/2018
 */
public class DeviceToCalibrate {

    private RemoteDevice device;
    private int inputNumber;
    private OSCCommandEnumerations settingToCalibrate;

    public DeviceToCalibrate(RemoteDevice device, int inputNumber, OSCCommandEnumerations settingToCalibrate) {
        this.device = device;
        this.inputNumber = inputNumber;
        this.settingToCalibrate = settingToCalibrate;
    }

    public RemoteDevice getDevice() {
        return device;
    }

    public void setDevice(RemoteDevice device) {
        this.device = device;
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public OSCCommandEnumerations getSettingToCalibrate() {
        return settingToCalibrate;
    }

    public void setSettingToCalibrate(OSCCommandEnumerations settingToCalibrate) {
        this.settingToCalibrate = settingToCalibrate;
    }
}
