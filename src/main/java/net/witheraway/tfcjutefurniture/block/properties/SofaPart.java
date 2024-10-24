package net.witheraway.tfcjutefurniture.block.properties;

import net.minecraft.util.StringRepresentable;

public enum SofaPart implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right");

    private final String name;

    private SofaPart(String type) {
        this.name = type;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
