package me.knight.mars.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

public class ConsoleOperatorTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void givenIllegalInputForMars_whenOperate_thenIllegalArgumentException() {

        assertThatThrownBy(() -> {
            provideInput("IllegalInputData");
            ConsoleOperator consoleOperator = new ConsoleOperator();
            consoleOperator.operate();

        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Mars grid axes must be greater than 1 and 50 or less");
    }

    @Test
    void givenIllegalInputForRobotPosition_whenOperate_thenIllegalArgumentException() {

        assertThatThrownBy(() -> {
            provideInput("5 3\n111");
            ConsoleOperator consoleOperator = new ConsoleOperator();
            consoleOperator.operate();

        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Robot position axes must be 50 or less followed by N E S or W");
    }

    @Test
    void givenIllegalInputForInstructions_whenOperate_thenIllegalArgumentException() {

        assertThatThrownBy(() -> {
            provideInput("5 3\n1 1 E\nLRD\n");
            ConsoleOperator consoleOperator = new ConsoleOperator();
            consoleOperator.operate();

        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Instructions input must be 99 or less of L R or F characters");
    }

    @Test
    void givenValidInputData_whenOperate_thenRobotReports() {

        provideInput("5 3\n1 1 E\nRFRFRFRF\n\n");
        ConsoleOperator consoleOperator = new ConsoleOperator();
        consoleOperator.operate();
        then(testOut)
                .as("Robot reports")
                .asString()
                .isEqualTo("1 1 E\n");
    }
}