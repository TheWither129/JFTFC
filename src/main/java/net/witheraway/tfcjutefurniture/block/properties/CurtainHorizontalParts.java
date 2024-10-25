package net.witheraway.tfcjutefurniture.block.properties;

import net.minecraft.util.StringRepresentable;

public enum CurtainHorizontalParts implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right");

    private final String name;

    private CurtainHorizontalParts(String type) {
        this.name = type;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
