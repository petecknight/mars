package me.knight.mars.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {
    private Orientation orientation;
    private GridReference gridReference;
}
