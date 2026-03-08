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

package com.macuguita.obese_crops.fabric;

//? fabric {
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Supplier;

import com.macuguita.obese_crops.Platform;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformImpl implements Platform {

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public String loader() {
        return "fabric";
    }

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isDevelopment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @SafeVarargs
    @Override
    public final void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items) {
        Objects.requireNonNull(itemColor, "color is null!");
        ColorProviderRegistry.ITEM.register(itemColor, unpackItems(items));
    }

    @SafeVarargs
    @Override
    public final void registerBlockColors(BlockColor blockColor, Supplier<? extends Block>... blocks) {
        Objects.requireNonNull(blockColor, "color is null!");
        ColorProviderRegistry.BLOCK.register(blockColor, unpackBlocks(blocks));
    }

    @Override
    public void registerRenderType(RenderType renderType, Block... blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(renderType, blocks);
    }

    @Override
    public void registerFuel(int time, ItemLike item) {
        FuelRegistry.INSTANCE.add(item, time);
    }

    @Override
    public void registerServerReloadListener(PreparableReloadListener reloadListener) {
        if (reloadListener instanceof IdentifiableResourceReloadListener identifiableResourceReloadListener) {
            ResourceManagerHelper.get(PackType.SERVER_DATA)
                    .registerReloadListener(identifiableResourceReloadListener);
        } else {
            throw new IllegalArgumentException(
                    "Failed to register reload listener: must implement IdentifiableResourceReloadListener on Fabric. Got: "
                            + reloadListener.getClass().getName()
            );
        }
    }

    private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
        var array = new ItemLike[items.length];
        for (var i = 0; i < items.length; i++) {
            array[i] = Objects.requireNonNull(items[i].get());
        }
        return array;
    }

    private static Block[] unpackBlocks(Supplier<? extends Block>[] blocks) {
        var array = new Block[blocks.length];
        for (var i = 0; i < blocks.length; i++) {
            array[i] = Objects.requireNonNull(blocks[i].get());
        }
        return array;
    }
}
//?}
