package util;

/**
 * @author Rich Dionne
 * @project BaMSensorSetup
 * @package util
 * @date 9/24/2018
 */
public enum URLEnumerations {
    //THIS_ADDRESS("/device_setup/*"),
    SETUP("/setup"),
    CALIBRATE("/calibrate"),
    SAVE_SETTINGS("/save_settings")
    //INPUT_ONE("/input1"),
    //INPUT_TWO("/input2"),
    //INPUT_THREE("/input3"),
    //INPUT_FOUR("/input4"),
    //INPUT_FIVE("/input5"),
    //INPUT_SIX("/input6"),
    //DEVICE_SETUP_MY_NAME("/device_setup/my_name"),
    //DEVICE_SETUP_SEND_ADDRESS("/device_setup/knee2/send_address"),
    //DEVICE_SETUP_INPUT_ONE("/device_setup/input0"),
    //DEVICE_SETUP_INPUT_TWO("/device_setup/input1"),
    //DEVICE_SETUP_INPUT_THREE("/device_setup/input2"),
    //DEVICE_SETUP_INPUT_FOUR("/device_setup/input3"),
    //DEVICE_SETUP_INPUT_FIVE("/device_setup/input4"),
    //DEVICE_SETUP_INPUT_SIX("/device_setup/input5")
    ;

    private final String url;

    URLEnumerations(String url) {
        this.url = url;
    }

    public static URLEnumerations fromString(String text) {
        for(URLEnumerations u : URLEnumerations.values()) {
            if(u.url.equalsIgnoreCase(text)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return url;
    }
}
