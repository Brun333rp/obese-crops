package com.macuguita.obese_crops.fabric;

//? fabric {
import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.client.ObeseCropsClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ObeseCrops.LOGGER.info("Initializing {} Client", ObeseCrops.MOD_ID);
        ObeseCropsClient.init();
    }

}
//?}