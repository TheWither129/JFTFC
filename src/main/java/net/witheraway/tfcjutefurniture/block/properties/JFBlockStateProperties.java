package net.witheraway.tfcjutefurniture.block.properties;

import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class JFBlockStateProperties {
    public static final EnumProperty<CurtainVerticalParts> CURTAIN_VERTICAL_PARTS = EnumProperty.create("vertical", CurtainVerticalParts.class);
    public static final DirectionProperty CURTAIN_VERTICAL_PARTS_UP_DOWN = DirectionProperty.create("vertical", (direction) -> direction.getAxis().isVertical());
    public static final EnumProperty<SofaPart> SOFA_PART = EnumProperty.create("part", SofaPart.class);
}
