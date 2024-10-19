package net.witheraway.tfcjutefurniture.util;

import net.minecraft.resources.ResourceLocation;

import static net.witheraway.tfcjutefurniture.JuteFurnitureTFC.MODID;

public class JFHelpers {

    public static ResourceLocation identifier(String id)
    {
        return JFHelpers.res(MODID, id);
    }

    public static ResourceLocation res(String string)
    {
        return new ResourceLocation(string);
    }
    public static ResourceLocation res(String namespace, String path)
    {
        return new ResourceLocation(namespace, path);
    }

}
