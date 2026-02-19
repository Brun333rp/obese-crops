package com.macuguita.obese_crops.fabric;

//? fabric {

import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.common.reg.OCWorldgen;
import com.macuguita.obese_crops.common.resourcereloader.ObeseMapResourceReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        ObeseCrops.init();
        OCWorldgen.init();
        ObeseCrops.commonInit();
    }

}
//? }