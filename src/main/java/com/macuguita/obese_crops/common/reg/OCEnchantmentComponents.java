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

package com.macuguita.obese_crops.common.reg;

//? >=1.21 {
/*import java.util.function.UnaryOperator;

import com.macuguita.lib.platform.registry.GuitaRegistries;
import com.macuguita.lib.platform.registry.GuitaRegistry;
import com.macuguita.lib.platform.registry.GuitaRegistryEntry;
import com.macuguita.obese_crops.ObeseCrops;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public final class OCEnchantmentComponents {

	private OCEnchantmentComponents() {}

	public static final GuitaRegistry<DataComponentType<?>> ENCHANTMENT_COMPONENTS =
			GuitaRegistries.create(
					BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
					ObeseCrops.MOD_ID
			);

	public static final TypedEntry<EnchantmentValueEffect> SCYTHE_PULLING_RANGE =
			register(
					"scythe_pulling_range",
					b -> b.persistent(EnchantmentValueEffect.CODEC)
			);

	private static <T> TypedEntry<T> register(
			String name,
			UnaryOperator<DataComponentType.Builder<T>> operator
	) {
		GuitaRegistryEntry<DataComponentType<?>> entry =
				ENCHANTMENT_COMPONENTS.register(
						name,
						() -> operator.apply(DataComponentType.<T>builder()).build()
				);

		return new TypedEntry<>(entry);
	}

	// Java generics made me this… Too lazy to find a better way to fix it
	public static final class TypedEntry<T> {
		private final GuitaRegistryEntry<DataComponentType<?>> entry;

		private TypedEntry(GuitaRegistryEntry<DataComponentType<?>> entry) {
			this.entry = entry;
		}

		@SuppressWarnings("unchecked")
		public DataComponentType<T> get() {
			return (DataComponentType<T>) entry.get();
		}

		public GuitaRegistryEntry<DataComponentType<?>> raw() {
			return entry;
		}
	}

	public static void init() {
		ENCHANTMENT_COMPONENTS.init();
	}
}
*///?}
