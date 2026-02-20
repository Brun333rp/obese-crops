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

package com.macuguita.obese_crops.mixin;

import java.util.Comparator;
import java.util.Optional;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.common.block.ObeseCropBlock;
import com.macuguita.obese_crops.common.resourcereloader.ObeseMapResourceReloadListener;

import net.minecraft.core.Direction;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@Mixin(CocoaBlock.class)
public abstract class CocoaBlockMixin extends HorizontalDirectionalBlock {

	@Shadow
	@Final
	public static IntegerProperty AGE;

	protected CocoaBlockMixin(Properties properties) {
		super(properties);
	}

	@Definition(id = "level", local = @Local(type = ServerLevel.class, argsOnly = true))
	@Definition(id = "setBlock", method = "Lnet/minecraft/server/level/ServerLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z")
	@Definition(id = "pos", local = @Local(type = BlockPos.class, argsOnly = true))
	@Expression("level.setBlock(pos, ?, ?)")
	@ModifyArg(
			method = "randomTick",
			at = @At("MIXINEXTRAS:EXPRESSION")
	)
	protected BlockState obese_crops$randomTickObeseBlockReplacement(
			BlockState blockState,
			@Local(argsOnly = true) RandomSource random
	) {
		Optional<ObeseMapResourceReloadListener.ObeseBlockData> obeseBlockData =
				ObeseCrops.getObeseBlockData(blockState.getBlock());

		int maxAge = AGE.getPossibleValues().stream().max(Comparator.naturalOrder()).orElse(0);
		Direction facing = blockState.getValue(FACING);
		if (obeseBlockData.isPresent() && blockState.getValue(AGE) == maxAge - 1) {
			int primaryChance = obeseBlockData.get().primary().chance();

			if (random.nextInt(primaryChance) == 0) {
				blockState = obese_crops$pickObeseBlock(obeseBlockData.get(), random).defaultBlockState().setValue(ObeseCropBlock.FACING, facing.getOpposite());
			}
		}

		return blockState;
	}

	@Definition(id = "level", local = @Local(type = ServerLevel.class, argsOnly = true))
	@Definition(id = "setBlock", method = "Lnet/minecraft/server/level/ServerLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z")
	@Definition(id = "pos", local = @Local(type = BlockPos.class, argsOnly = true))
	@Expression("level.setBlock(pos, ?, ?)")
	@ModifyArg(
			method = "performBonemeal",
			at = @At("MIXINEXTRAS:EXPRESSION")
	)
	protected BlockState obese_crops$applyGrowthObeseBlockReplacement(
			BlockState blockState,
			@Local(argsOnly = true) RandomSource random
	) {
		Optional<ObeseMapResourceReloadListener.ObeseBlockData> obeseBlockData =
				ObeseCrops.getObeseBlockData(blockState.getBlock());

		int maxAge = AGE.getPossibleValues().stream().max(Comparator.naturalOrder()).orElse(0);
		Direction facing = blockState.getValue(FACING);
		if (obeseBlockData.isPresent() && blockState.getValue(AGE) == maxAge - 1) {
			int primaryChance = obeseBlockData.get().primary().chance();

			if (random.nextInt(primaryChance) == 0) {
				blockState = obese_crops$pickObeseBlock(obeseBlockData.get(), random).defaultBlockState().setValue(ObeseCropBlock.FACING, facing.getOpposite());
			}
		}

		return blockState;
	}

	@Unique
	private Block obese_crops$pickObeseBlock(
			ObeseMapResourceReloadListener.ObeseBlockData data,
			RandomSource random
	) {
		for (var secondary : data.secondaries()) {
			if (random.nextInt(secondary.chance()) == 0) {
				return secondary.obese();
			}
		}

		return data.primary().obese();
	}
}
