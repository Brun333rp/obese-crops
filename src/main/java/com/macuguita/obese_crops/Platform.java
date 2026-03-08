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

package com.macuguita.obese_crops;

//? fabric {
import com.macuguita.obese_crops.fabric.FabricPlatformImpl;
//? }
//? neoforge {
/*import com.macuguita.obese_crops.neoforge.NeoForgePlatformImpl;
 *///? }
//? forge {
/*import com.macuguita.obese_crops.forge.ForgePlatformImpl;
*///? }
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Supplier;

import com.macuguita.obese_crops.mixin.FireBlockAccessor;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoForgePlatformImpl();
    *///?}
    //? forge {
    /*Platform INSTANCE = new ForgePlatformImpl();
    *///?}


    boolean isModLoaded(String modid);
    String loader();
    Path getConfigDir();
    boolean isDevelopment();
    void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items);
    void registerBlockColors(BlockColor blockColor, Supplier<? extends Block >... blocks);
    void registerRenderType(RenderType renderType, Block... blocks);
    void registerFuel(int time, ItemLike item);
    void registerServerReloadListener(PreparableReloadListener reloadListener);

    default void registerItemColors(ItemColor color, ItemLike... items) {
        Supplier<ItemLike>[] array = new Supplier[items.length];
        for (var i = 0; i < items.length; i++) {
            var item = Objects.requireNonNull(items[i], "items[i] is null!");
            array[i] = () -> item;
        }
        registerItemColors(color, array);
    }

    default void registerBlockColors(BlockColor color, Block... blocks) {
        Supplier<Block>[] array = new Supplier[blocks.length];
        for (var i = 0; i < blocks.length; i++) {
            var block = Objects.requireNonNull(blocks[i], "blocks[i] is null!");
            array[i] = () -> block;
        }
        registerBlockColors(color, array);
    }

    default void registerFlammableBlock(int burnChance, int spreadChance, Block... blocks) {
        for (Block block : blocks) {
            ((FireBlockAccessor) Blocks.FIRE).obese_crops$registerFlammableBlock(block, burnChance, spreadChance);
        }
    }
}
