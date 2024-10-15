package net.witheraway.tfcjutefurniture.block;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;



public enum JFTFCCrop {
    FLAX(FarmlandBlockEntity.NutrientType.NITROGEN, 8);

    private final FarmlandBlockEntity.NutrientType nutrientType;
    private final int stages;

    JFTFCCrop(FarmlandBlockEntity.NutrientType nutrientType, int stages) {
        this.stages = stages;
        this.nutrientType = nutrientType;
    }

    public Block create() {
        return JFTFCDefaultCropBlock.create(crop(), stages, this);
    }

    static ExtendedProperties crop() {
        return dead().blockEntity(TFCBlockEntities.CROP).serverTicks(CropBlockEntity::serverTick);
    }
    private static ExtendedProperties dead()
    {
        return ExtendedProperties.of().noCollission().randomTicks().strength(0.4F).sound(SoundType.CROP);
    }
    public FarmlandBlockEntity.NutrientType getNutrient()
    {
        return nutrientType;
    }

}
