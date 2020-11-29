package me.knight.mars.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Robot {

    private Position position;
    private boolean isLost;
}
