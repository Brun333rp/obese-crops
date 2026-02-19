package com.macuguita.obese_crops.fabric;

//? fabric {
import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.common.reg.OCWorldgen;

import net.fabricmc.api.ModInitializer;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        ObeseCrops.init();
        OCWorldgen.init();
        ObeseCrops.commonInit();
    }

}
//? }
