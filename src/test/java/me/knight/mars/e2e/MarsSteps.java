package me.knight.mars.e2e;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class MarsSteps {

    public static final String FILE_NAME = "application.properties";
    private final String BINARY_PATH;

    private final List<String> output = new ArrayList<>();
    private Process process;

    private int upperBoundsXAxis;
    private int upperBoundsYAxis;
    private char robotFacing;
    private int robotX;
    private int robotY;
    private int doomedRobotX;
    private int doomedRobotY;
    private char doomedRobotFacing;
    private boolean isFallen;

    public MarsSteps() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);

        if (inputStream == null) {
            throw new IllegalArgumentException(String.format("%s not found, please check classpath", FILE_NAME));
        } else {
            Properties p = new Properties();
            p.load(inputStream);
            BINARY_PATH = p.getProperty("binary.path");
        }
    }

    @Before
    public void checkBinaryPath() {

        assertThat(Files.exists(Paths.get(BINARY_PATH)))
                .as("Please verify the jar at %s path exists", BINARY_PATH)
                .isTrue();
    }

    @Given("^I have a robot positioned at (\\d+) (\\d+) facing ([^\"]*) on Mars with a boundry set as (\\d+) (\\d+)$")
    public void positionRobotAndSetUpMarsBoundry(int robotX, int robotY, String orientation,
                                                    int upperBoundsXAxis, int upperBoundsYAxis) {
        this.robotX = robotX;
        this.robotY = robotY;
        this.robotFacing = orientation.charAt(0);
        this.upperBoundsXAxis = upperBoundsXAxis;
        this.upperBoundsYAxis = upperBoundsYAxis;
    }

    @And("^I have a doomed robot positioned at (\\d+) (\\d+) facing ([^\"]*)$")
    public void positionDoomedRobot(int robotX, int robotY, String orientation) {
        this.doomedRobotX = robotX;
        this.doomedRobotY = robotY;
        this.doomedRobotFacing = orientation.charAt(0);
    }

    @When("^I instruct the robot to move ([^\"]*) and report its final position$")
    public void iHaveARobotOnMarsInstructTheRobotToMoveWithinThePlanetBoundry(String instructions) throws Exception {

        startApplicationUnderTest();

        BufferedReader consoleInput = getConsoleForInput();
        OutputStream consoleOutput = getConsoleForOutput();

        byte[] marsGridUpperRightCoordinates = String.format("%d %d%n", upperBoundsXAxis, upperBoundsYAxis).getBytes();
        consoleOutput.write(marsGridUpperRightCoordinates);

        byte[] robotPosition = String.format("%d %d %c%n", robotX, robotY, robotFacing).getBytes();
        consoleOutput.write(robotPosition);

        byte[] robotInstructions = String.format("%s%n%n", instructions).getBytes();
        consoleOutput.write(robotInstructions);
        consoleOutput.flush();

        output.add(consoleInput.readLine());

        stopApplicationUnderTest();
    }

    @When("^I instruct the robot to move ([^\"]*) following the path of doomed robot ([^\"]*) which has fallen$")
    public void iInstructTheRobotToMoveOutsideThePlanetBoundryFromAScentedGridPoint(String instructions,
                                                                                        String doomedInstructions) throws Exception {

        startApplicationUnderTest();

        BufferedReader consoleInput = getConsoleForInput();
        OutputStream consoleOutput = getConsoleForOutput();

        byte[] marsGridUpperRightCoordinates = String.format("%d %d%n", upperBoundsXAxis, upperBoundsYAxis).getBytes();
        consoleOutput.write(marsGridUpperRightCoordinates);

        byte[] doomedRobotPosition = String.format("%d %d %c%n", doomedRobotX, doomedRobotY, doomedRobotFacing).getBytes();
        consoleOutput.write(doomedRobotPosition);

        byte[] doomedRobotInstructions = String.format("%s%n%n", doomedInstructions).getBytes();
        consoleOutput.write(doomedRobotInstructions);

        byte[] robotPosition = String.format("%d %d %c%n", robotX, robotY, robotFacing).getBytes();
        consoleOutput.write(robotPosition);

        byte[] robotInstructions = String.format("%s%n%n", instructions).getBytes();
        consoleOutput.write(robotInstructions);
        consoleOutput.flush();

        output.add(consoleInput.readLine());
        output.add(consoleInput.readLine());

        stopApplicationUnderTest();
    }

    @Then("^the robot reports the expected final grid position$")
    public void theRobotReportsTheExpectedFinalGridPosition() {

        assertThat(output)
                .containsExactly("1 1 E");
    }

    @Then("^the robot reports as lost and its last known grid position and orientation before falling$")
    public void theRobotReportsAsLostAndItsLastKnownGridPositionAndOrientationBeforeFalling() {

        assertThat(output)
                .containsExactly("3 3 N LOST");
    }

    @Then("^the robot reports the expected final grid position and orientation$")
    public void theRobotReportsTheExpectedFinalGridPositionAndOrientation() {

        assertThat(output)
                .containsExactly("3 3 N LOST", "2 3 S");
    }

    private void startApplicationUnderTest() throws IOException {

        ProcessBuilder pb = new ProcessBuilder("java", "-Dspring.main.banner-mode=off", "-Dlogging.pattern.console=", "-jar", BINARY_PATH);
        process = pb.start();

        assertThat(process.isAlive())
                .as("Binary at %s should have started in this process", BINARY_PATH)
                .isTrue();
    }

    private OutputStream getConsoleForOutput() {
        return process.getOutputStream();
    }

    private BufferedReader getConsoleForInput() {
        return new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    private void stopApplicationUnderTest() {
        process.destroyForcibly();
    }
}
