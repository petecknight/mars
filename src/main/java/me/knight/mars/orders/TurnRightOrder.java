package me.knight.mars.orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.knight.mars.entity.Orientation;
import me.knight.mars.entity.Robot;

@Data
@RequiredArgsConstructor
public class TurnRightOrder implements Order {

 	final Robot robot;

	@Override
	public void execute() {
		Orientation currentOrientation = robot.getPosition().getOrientation();
		robot.getPosition().setOrientation(currentOrientation.getRight());
	}
}