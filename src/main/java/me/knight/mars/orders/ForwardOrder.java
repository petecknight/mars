package me.knight.mars.orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.knight.mars.entity.*;

@Data
@RequiredArgsConstructor
public class ForwardOrder implements Order {

 	final Robot robot;
 	final Mars mars;

	@Override
	public void execute() {

		Orientation currentOrientation = robot.getPosition().getOrientation();
		Position currentPosition = robot.getPosition();
		GridReference currentGridReference = currentPosition.getGridReference();
		int x = currentGridReference.getX();
		int y = currentGridReference.getY();

		final GridReference newGridReference;

		switch (currentOrientation) {
			case NORTH:
				newGridReference = GridReference.of(x, y + 1);
				break;
			case EAST:
				newGridReference = GridReference.of(x + 1, y);
				break;
			case SOUTH:
				newGridReference = GridReference.of(x, y - 1);
				break;
			case WEST:
				newGridReference = GridReference.of(x - 1, y);
				break;
			default:
				newGridReference = currentGridReference;
		}

		if (!robot.isLost() && safeToMoveForward(mars, currentPosition)) {

			if (robotHasFallen(mars, newGridReference)) {
				mars.leaveScent(currentPosition);
				robot.setLost(true);
			} else {
				Position newPosition = Position.builder()
						.gridReference(newGridReference)
						.orientation(currentOrientation)
						.build();

				robot.setPosition(newPosition);
			}


		}
	}

	private boolean safeToMoveForward(Mars mars, Position position) {
		return mars.isSafe(position);
	}

	private boolean robotHasFallen(Mars mars, GridReference newGridReference) {
		return mars.hasBreachedBoundry(newGridReference);
	}
}