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

package com.macuguita.obese_crops.common.tree;

//? < 1.21 {
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class TreeGrower extends AbstractTreeGrower {

	private final float secondaryChance;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryMegaTree;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryTree;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers;
	private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryFlowers;

	public TreeGrower(String name, Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers) {
		this(name, 0.0F, megaTree, Optional.empty(), tree, Optional.empty(), flowers, Optional.empty());
	}

	public TreeGrower(String name, float secondaryChance, Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryMegaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryFlowers) {
		this.secondaryChance = secondaryChance;
		this.megaTree = megaTree;
		this.secondaryMegaTree = secondaryMegaTree;
		this.tree = tree;
		this.secondaryTree = secondaryTree;
		this.flowers = flowers;
		this.secondaryFlowers = secondaryFlowers;
	}

	@Override
	protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
		if (random.nextFloat() < this.secondaryChance) {
			if (hasFlowers && this.secondaryFlowers.isPresent()) {
				return this.secondaryFlowers.get();
			}

			if (this.secondaryTree.isPresent()) {
				return this.secondaryTree.get();
			}
		}

		return hasFlowers && this.flowers.isPresent() ? this.flowers.get() : this.tree.orElse(null);
	}
}
//?}
