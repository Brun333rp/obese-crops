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

import java.util.Map;
import java.util.Optional;

import com.macuguita.obese_crops.common.block.ThinLogBlock;
import com.macuguita.obese_crops.common.reg.OCCreativeTabs;
import com.macuguita.obese_crops.common.reg.OCEnchantments;
import com.macuguita.obese_crops.common.reg.OCObjects;
import com.macuguita.obese_crops.common.reg.OCWorldgen;
import com.macuguita.obese_crops.common.resourcereloader.ObeseMapResourceReloadListener;
import com.macuguita.obese_crops.common.resourcereloader.Source;
import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

//? >= 1.21 {
import com.macuguita.obese_crops.common.reg.OCComponents;
import com.macuguita.obese_crops.common.reg.OCEnchantmentComponents;
//?} else {
/*import com.macuguita.obese_crops.common.reg.OCEntityAttributes;
*///?}

public final class ObeseCrops {

	private ObeseCrops() {}

    public static final String MOD_ID = "obese_crops";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final OCConfig CONFIG = WrappedConfig.createToml(Platform.INSTANCE.getConfigDir(), "", MOD_ID, OCConfig.class);

    public static class OCConfig extends WrappedConfig {
        @Comment("Whether double crops defined on the obese_crops:double_obese_crops tag should be double.")
        public boolean doubleTallCrops = true;
        @Comment("Whether rooted dirt should appear under newly spawned obese crops.")
        public boolean rootedDirtUnderCrops = true;

        public FabricOnly fabricOnly = new FabricOnly();
        public static class FabricOnly implements Section {
            @Comment("Whether apple/flowering oak trees should spawn naturally.")
            public boolean floweringOakSpawn = true;
        }
    }

    public static final Map<Source, ObeseMapResourceReloadListener.ObeseBlockData> SOURCE_TO_OBESE_DATA = new Object2ObjectOpenHashMap<>();
    public static final Map<Block, ObeseMapResourceReloadListener.ObeseBlockData.Entry> OBESE_TO_ENTRY = new Object2ObjectOpenHashMap<>();

    public static Optional<ObeseMapResourceReloadListener.ObeseBlockData> getObeseBlockData(Block block) {
        return Optional.ofNullable(SOURCE_TO_OBESE_DATA.get(new Source.BlockSource(block)));
    }

    public static Optional<ObeseMapResourceReloadListener.ObeseBlockData> getObeseBlockData(Item item) {
        return Optional.ofNullable(SOURCE_TO_OBESE_DATA.get(new Source.ItemSource(item)));
    }

    public static Optional<ObeseMapResourceReloadListener.ObeseBlockData.Entry> getObeseBlockEntry(Block block) {
        return Optional.ofNullable(OBESE_TO_ENTRY.get(block));
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        LOGGER.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
        initRegistries();
    }

    public static void commonInit() {
        Platform.INSTANCE.registerFuel(10, OCObjects.WOODEN_SCYTHE.get());
        Platform.INSTANCE.registerFuel(300, OCObjects.FLOWERING_OAK_LOG.get());
        Platform.INSTANCE.registerFuel(300, OCObjects.STRIPPED_FLOWERING_OAK_LOG.get());

        Platform.INSTANCE.registerFlammableBlock(30, 60, OCObjects.FLOWERING_OAK_LEAVES.get());
        Platform.INSTANCE.registerFlammableBlock(5, 5, OCObjects.FLOWERING_OAK_LOG.get(), OCObjects.STRIPPED_FLOWERING_OAK_LOG.get());

        ThinLogBlock.STRIPPED_THIN_LOGS.put(OCObjects.FLOWERING_OAK_LOG.get(), OCObjects.STRIPPED_FLOWERING_OAK_LOG.get());

        Platform.INSTANCE.registerServerReloadListener(new ObeseMapResourceReloadListener());
    }

    private static void initRegistries() {
        OCObjects.init();
        OCCreativeTabs.init();
        OCWorldgen.init();
		OCEnchantments.init();
		//? >= 1.21 {
		OCComponents.init();
		OCEnchantmentComponents.init();
		//?} else {
		/*OCEntityAttributes.init();
		*///?}
    }
}
