package me.knight.mars.orders;

import me.knight.mars.entity.*;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.then;

class ForwardOrderTest {

    @Test
    public void givenRobot_whenInstructedToFallOffMars_thenRobotLeavesSmellAndReportsLost(){

        Mars mars = new Mars(GridReference.of(5,5));

        Position position = Position.builder()
                .gridReference(GridReference.of(5, 5))
                .orientation(Orientation.NORTH).build();

        Robot robot = Robot.builder().position(position)
                .build();

        new ForwardOrder(robot, mars).execute();

        then(robot.getPosition())
                .extracting(Position::getGridReference)
                .isEqualTo(GridReference.of(5,5));

        then(robot.isLost())
                .isTrue();

        then(mars.getScentsFromTheFallenRobot())
                .contains(position);
    }

    @Test
    public void givenScentExistsOnGrid_whenInstructedToFallOffMarsFromScentedGridReference_thenRobotIgnoresTheInstruction(){

        Mars mars = new Mars(GridReference.of(5,5));

        Position smellPosition = Position.builder()
                .gridReference(GridReference.of(5, 5))
                .orientation(Orientation.NORTH).build();

        mars.leaveScent(smellPosition);

        Position position = Position.builder()
                .gridReference(GridReference.of(5, 5))
                .orientation(Orientation.NORTH).build();

        Robot robot = Robot.builder().position(position)
                .build();

        new ForwardOrder(robot, mars).execute();

        then(robot.getPosition())
                .extracting(Position::getGridReference)
                .isEqualTo(GridReference.of(5,5));

        then(robot.isLost())
                .isFalse();

    }

    @Test
    public void givenRobot_whenInstructedToMoveForward_thenRobotReportsMoveInFinalPosition(){

        Mars mars = new Mars(GridReference.of(5,5));

        Position position = Position.builder()
                .gridReference(GridReference.of(0, 0))
                .orientation(Orientation.NORTH).build();

        Robot robot = Robot.builder().position(position)
                .build();

        ForwardOrder forwardOrder = new ForwardOrder(robot, mars);
        IntStream.range(0,3).forEach( i-> forwardOrder.execute());

        then(robot.getPosition())
                .extracting(Position::getGridReference)
                .isEqualTo(GridReference.of(0,3));

        then(robot.isLost())
                .isFalse();

        then(mars.getScentsFromTheFallenRobot())
                .isEmpty();
    }
}