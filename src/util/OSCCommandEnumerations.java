package util;

/**
 * @author Rich Dionne
 * @project BaMSensorSetup
 * @package util
 * @date 9/26/2018
 */
public enum OSCCommandEnumerations {
    GET_SETTINGS(1),
    SAVE_SETTINGS(2),
    //CALIBRATE(3),
    MINIMUM(0),
    MAXIMUM(1);

    private int command;

    OSCCommandEnumerations(int command) {
        this.command = command;
    }

    public int getValue() {
        return command;
    }

}
