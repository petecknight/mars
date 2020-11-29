package me.knight.mars.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GridReference {

    private int x;
    private int y;

    public static GridReference of(int x, int y) {
        return new GridReference(x, y);
    }
}
