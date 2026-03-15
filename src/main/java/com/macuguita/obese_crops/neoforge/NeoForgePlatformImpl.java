/*
 * Copyright (c) 2026 macuguita
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.macuguita.obese_crops.neoforge;

//? neoforge {
/*import com.google.common.collect.Lists;
import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.Platform;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgePlatformImpl implements Platform {
    private static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = Lists.newArrayList();
    private static final List<Pair<BlockColor, Supplier<? extends Block>[]>> BLOCK_COLORS = Lists.newArrayList();
    private static final Object2IntMap<ItemLike> FUEL_ITEMS = new Object2IntLinkedOpenHashMap<>();
    private static final List<PreparableReloadListener> RELOAD_LISTENERS = Lists.newArrayList();

    static {
        whenAvailable(ObeseCrops.MOD_ID, bus -> {
            bus.register(ModBusEvents.class);
        });
        NeoForge.EVENT_BUS.register(ForgeBusEvents.class);
    }

    public static final class ModBusEvents {

        @SubscribeEvent
        public static void onItemColorEvent(RegisterColorHandlersEvent.Item event) {
            for (Pair<ItemColor, Supplier<? extends ItemLike>[]> pair : ITEM_COLORS) {
                event.register(pair.getLeft(), unpackItems(pair.getRight()));
            }
        }

        @SubscribeEvent
        public static void onBlockColorEvent(RegisterColorHandlersEvent.Block event) {
            for (Pair<BlockColor, Supplier<? extends Block>[]> pair : BLOCK_COLORS) {
                event.register(pair.getLeft(), unpackBlocks(pair.getRight()));
            }
        }
    }

    public static final class ForgeBusEvents {

        @SubscribeEvent
        public static void fuelEvent(FurnaceFuelBurnTimeEvent event) {
            if (!event.getItemStack().isEmpty()) {
                int time = FUEL_ITEMS.getOrDefault(event.getItemStack().getItem(), Integer.MIN_VALUE);
                if (time != Integer.MIN_VALUE) {
                    event.setBurnTime(time);
                }
            }
        }

        @SubscribeEvent
        public static void addServerReloadListenerEvent(AddReloadListenerEvent event) {
            for (PreparableReloadListener reloadListener : RELOAD_LISTENERS) {
                event.addListener(reloadListener);
            }
        }
    }

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String loader() {
        return "neoforge";
    }

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isDevelopment() {
        //? if >= 1.21.11 {
        /^return !FMLEnvironment.isProduction();
        ^///?} else {
		return !FMLEnvironment.production;
		 //?}
    }

    @Override
    public void registerRenderType(RenderType renderType, Block... blocks) {
        for (Block block : blocks) {
            ItemBlockRenderTypes.setRenderLayer(block, renderType);
        }
    }

    @SafeVarargs
    @Override
    public final void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items) {
        Objects.requireNonNull(itemColor, "color is null!");
        if (Minecraft.getInstance().getItemColors() == null) {
            ITEM_COLORS.add(Pair.of(itemColor, items));
        } else {
            Minecraft.getInstance().getItemColors().register(itemColor, unpackItems(items));
        }
    }

    @SafeVarargs
    @Override
    public final void registerBlockColors(BlockColor blockColor, Supplier<? extends Block>... blocks) {
        Objects.requireNonNull(blockColor, "color is null!");
        if (Minecraft.getInstance().getBlockColors() == null) {
            BLOCK_COLORS.add(Pair.of(blockColor, blocks));
        } else {
            Minecraft.getInstance().getBlockColors().register(blockColor, unpackBlocks(blocks));
        }
    }

    @Override
    public void registerFuel(int time, ItemLike item) {
        FUEL_ITEMS.put(item, time);
    }

    @Override
    public void registerServerReloadListener(PreparableReloadListener reloadListener) {
        RELOAD_LISTENERS.add(reloadListener);
    }

    private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
        ItemLike[] array = new ItemLike[items.length];
        for (int i = 0; i < items.length; i++) {
            array[i] = Objects.requireNonNull(items[i].get());
        }
        return array;
    }

    private static Block[] unpackBlocks(Supplier<? extends Block>[] blocks) {
        Block[] array = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            array[i] = Objects.requireNonNull(blocks[i].get());
        }
        return array;
    }

    public static void whenAvailable(String modId, Consumer<IEventBus> busConsumer) {
        IEventBus bus = getModEventBus(modId).orElseThrow(() -> new IllegalStateException("Mod '" + modId + "' is not available!"));
        busConsumer.accept(bus);
    }

    public static Optional<IEventBus> getModEventBus(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(ModContainer::getEventBus);
    }
}
*///?}
