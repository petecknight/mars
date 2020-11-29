package me.knight.mars.client;

import lombok.RequiredArgsConstructor;
import me.knight.mars.entity.GridReference;
import me.knight.mars.entity.Mars;
import me.knight.mars.entity.Position;
import me.knight.mars.entity.Robot;
import me.knight.mars.orders.ForwardOrder;
import me.knight.mars.orders.Order;
import me.knight.mars.orders.TurnLeftOrder;
import me.knight.mars.orders.TurnRightOrder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static me.knight.mars.entity.Orientation.getOrientationForDirection;
import static me.knight.mars.util.ConsoleUtils.*;

@Component
@RequiredArgsConstructor
public class ConsoleOperator implements CommandLineRunner {

    private static final String WHITE_SPACE_SPLITTER_REGEX = "\\s";
    private static final String CHARACTER_SPLITTER_REGEX = "(?!^)";

    public static final String TURN_LEFT_ORDER = "L";
    public static final String TURN_RIGHT_ORDER = "R";
    public static final String MOVE_FORWARD_ORDER = "F";

    @Override
    public void run(String... args) {
        operate();
    }

    public void operate() {

        try (final Scanner scanner = new Scanner(in)) {

            String coordinates = scanner.nextLine();
            validate(!coordinates.matches(VALID_AXES), INVALID_AXES_MESSAGE);

            final Mars mars = new Mars(getUpperBoundsGridReference(coordinates));

            final AtomicBoolean isFirstRobot = new AtomicBoolean(true);

            while (scanner.hasNextLine()) {

                final String robotPosition = scanner.nextLine();

                if (robotPosition.isEmpty()) {
                    validate(isFirstRobot.get(), INVALID_INPUT_FOLLOWING_MARS_GRID_COORDINATES);
                    break;
                }

                validate(!robotPosition.matches(VALID_ROBOT_POSITION), INVALID_ROBOT_POSITION);

                isFirstRobot.set(false);

                final String instructions = scanner.nextLine();

                validate(!instructions.matches(VALID_INSTRUCTIONS), INVALID_INSTRUCTIONS);

                if (scanner.nextLine().isEmpty()) {

                    final Robot robot = getRobot(robotPosition);

                    asList(instructions.split(CHARACTER_SPLITTER_REGEX))
                            .forEach(instruction -> prepareOrderLookupTable(mars, robot)
                                    .get(instruction)
                                    .execute());

                    reportPosition(robot);
                }
            }
        }
    }

    private Map<String, Order> prepareOrderLookupTable(Mars mars, Robot robot) {

        final Map<String, Order> orders = new HashMap<>();

        orders.put(TURN_LEFT_ORDER, new TurnLeftOrder(robot));
        orders.put(TURN_RIGHT_ORDER, new TurnRightOrder(robot));
        orders.put(MOVE_FORWARD_ORDER, new ForwardOrder(robot, mars));
        return orders;
    }

    private GridReference getUpperBoundsGridReference(String coordinates) {

        String[] split = coordinates.split(WHITE_SPACE_SPLITTER_REGEX);

        return GridReference.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private Robot getRobot(String positionInput) {

        String[] positions = positionInput.split(WHITE_SPACE_SPLITTER_REGEX);
        int x = Integer.parseInt(positions[0]);
        int y = Integer.parseInt(positions[1]);
        String direction = positions[2];

        return Robot.builder().position(
                Position.builder()
                        .gridReference(GridReference.of(x, y))
                        .orientation(getOrientationForDirection(direction))
                        .build())
                .build();
    }

    private void reportPosition(Robot robot) {

        Position position = robot.getPosition();
        String report = String.format("%d %d %s%s",
                position.getGridReference().getX(),
                position.getGridReference().getY(),
                position.getOrientation().getDirection(),
                robot.isLost() ? " LOST" : "");

        out.println(report);
    }
}