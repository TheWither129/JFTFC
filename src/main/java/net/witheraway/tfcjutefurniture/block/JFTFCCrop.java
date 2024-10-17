package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.witheraway.tfcjutefurniture.blockentities.JFTFCBlockEntities;


public enum JFTFCCrop {
    FLAX(8, FarmlandBlockEntity.NutrientType.NITROGEN);

    private final int stages;
    private final FarmlandBlockEntity.NutrientType nutrient;

    JFTFCCrop(int stages, FarmlandBlockEntity.NutrientType nutrient)
    {
        this.stages = stages;
        this.nutrient = nutrient;
    }

    public Block create()
    {
        return JFTFCDefaultCropBlock.create(crop(), stages, this);
    }
    public Block createDead()
    {
        return JFTFCDefaultCropBlock.create(dead(), stages, this);
    }
    public Block createWild()
    {
        return JFTFCDefaultCropBlock.create(wild(), stages, this);
    }

    public int getStages()
    {
        return stages;
    }

    public FarmlandBlockEntity.NutrientType getNutrient()
    {
        return nutrient;
    }

    private static ExtendedProperties crop()
    {
        return dead().blockEntity(JFTFCBlockEntities.CROP).serverTicks(CropBlockEntity::serverTick);
    }

    private static ExtendedProperties dead()
    {
        return ExtendedProperties.of().noCollission().randomTicks().strength(0.4F).sound(SoundType.CROP);
    }
    private static ExtendedProperties wild()
    {
        return ExtendedProperties.of().noCollission().randomTicks().strength(0.4F).sound(SoundType.CROP);
    }

}
