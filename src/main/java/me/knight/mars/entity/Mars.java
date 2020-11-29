package me.knight.mars.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Mars {

    private Set<Position> scentsFromTheFallenRobot = new HashSet<>();

    private final GridReference upperBounds;
    private final GridReference lowerBounds = GridReference.of(0,0);

    public void leaveScent(Position position) {
        scentsFromTheFallenRobot.add(position);
    }

    public boolean isSafe(Position position) {
        return !scentsFromTheFallenRobot.contains(position);
    }

    public boolean hasBreachedBoundry(GridReference newGridReference){

        return newGridReference.getX() > upperBounds.getX() || newGridReference.getX() <  lowerBounds.getX() ||
                newGridReference.getY() > upperBounds.getY() || newGridReference.getY() < lowerBounds.getY();
    }
}
