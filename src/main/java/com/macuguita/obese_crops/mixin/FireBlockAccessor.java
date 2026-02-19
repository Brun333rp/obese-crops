package com.macuguita.obese_crops.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;

@Mixin(FireBlock.class)
public interface FireBlockAccessor {

    @Invoker("setFlammable")
    void obese_crops$registerFlammableBlock(Block block, int burnChance, int spreadChance);
}
