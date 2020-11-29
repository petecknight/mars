package me.knight.mars.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConsoleUtils {

    public static final String VALID_AXES = "^(?:[1-9]|[1-4][0-9]|50) (?:[1-9]|[1-4][0-9]|50)$";
    public static final String VALID_ROBOT_POSITION = "^(?:[0-9]|[1-4][0-9]|50) (?:[0-9]|[1-4][0-9]|50) [NESW]$";

    public static final String INVALID_INPUT_FOLLOWING_MARS_GRID_COORDINATES = "Robot position must follow Mars grid coordinates";
    public static final String INVALID_ROBOT_POSITION = "Robot position axes must be 50 or less followed by N E S or W";
    public static final String INVALID_AXES_MESSAGE = "Mars grid axes must be greater than 1 and 50 or less";
    public static final String INVALID_INSTRUCTIONS = "Instructions input must be 99 or less of L R or F characters";
    public static final String VALID_INSTRUCTIONS = "[LRF]{1,99}";

    public static void validate(boolean invalid, String message) {
        if (invalid) {
            throw new IllegalArgumentException(getInfo(message));
        }
    }

    public static String getInfo(String message) {

        return "IllegalArgument: " + message + "\n" +
                "Usage: \n" +
                        "  [line 1] input upper-right coordinates (max 50 x 50) for Mars grid, separated by white space, followed by <ENTER> key - example: 5 3 \n" +
                        "  [line 2] input robot initial position and orientation (NESW) on Mars, separated by white space, followed by <ENTER> key - example: 1 1 E \n" +
                        "  [line 3] input operator instructions (max 99 instructions) followed by <ENTER> key - example: RFRFRFRF \n" +
                        "  hit <ENTER> key to send instructions and solicit robot report\n" +
                        "  [line 4] input next robot position and orientation <ENTER>\n" +
                        "  [line 5] next operator instructions <ENTER>\n" +
                        "  hit <ENTER> for robot report\n" +
                        "  repeat line 4 and 5 or hit <ENTER> to exit";

    }
}
