package me.knight.mars.orders;


import me.knight.mars.entity.GridReference;
import me.knight.mars.entity.Orientation;
import me.knight.mars.entity.Position;
import me.knight.mars.entity.Robot;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class TurnRightOrderTest {

    @Test
    public void givenRobotFacingNorth_whenInstructedToTurnRight_thenRobotFacesEast(){

        Robot robot = Robot.builder().position(Position.builder()
                .gridReference(GridReference.of(0, 0))
                .orientation(Orientation.NORTH).build())
                .build();

        new TurnRightOrder(robot).execute();

        then(robot.getPosition())
                .extracting(Position::getOrientation)
                .isEqualTo(Orientation.EAST);
    }

}