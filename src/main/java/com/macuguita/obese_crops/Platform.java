package com.macuguita.obese_crops;

//? fabric {
import com.macuguita.obese_crops.fabric.FabricPlatformImpl;
//? }
//? neoforge {
/*import com.macuguita.obese_crops.neoforge.NeoForgePlatformImpl;
*///? }
import com.macuguita.obese_crops.mixin.FireBlockAccessor;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Supplier;
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
