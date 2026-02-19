package com.macuguita.obese_crops.neoforge;

//? neoforge {

/*import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.client.ObeseCropsClient;
import com.macuguita.obese_crops.common.resourcereloader.ObeseMapResourceReloadListener;
import net.minecraft.resources.ResourceKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(ObeseCrops.MOD_ID)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint(IEventBus modEventBus) {
        ObeseCrops.init();
        modEventBus.addListener(this::commonSetup);
    }
   
    @EventBusSubscriber(modid = ObeseCrops.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            ObeseCrops.LOGGER.info("Initializing {} Client", ObeseCrops.MOD_ID);
            ObeseCropsClient.init();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ObeseCrops.commonInit();
    }
}
*///?}