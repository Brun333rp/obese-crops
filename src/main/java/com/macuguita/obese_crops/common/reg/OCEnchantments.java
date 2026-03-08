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

//? >= 1.21 {
/*import com.macuguita.obese_crops.ObeseCrops;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

public final class OCEnchantments {

	private OCEnchantments() {}

	public static final ResourceKey<Enchantment> BOUNTIFUL_REAP = key("bountiful_reap");

	public static void bootstrap(BootstapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);

		register(
				context,
				BOUNTIFUL_REAP,
				Enchantment.enchantment(
						Enchantment.definition(
								items.getOrThrow(OCItemTags.SCYTHE_ENCHANTABLE),
								2,
								3,
								Enchantment.dynamicCost(10, 10),
								Enchantment.dynamicCost(40, 10),
								4,
								EquipmentSlotGroup.MAINHAND
						)
				).withSpecialEffect(
						OCEnchantmentComponents.SCYTHE_PULLING_RANGE.get(),
						new AddValue(LevelBasedValue.perLevel(1F))
				)
		);
	}

	private static void register(BootstapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.location()));
	}

	private static ResourceKey<Enchantment> key(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ObeseCrops.id(name));
	}

	public static void init() {

	}
}
*///?} else {
import com.macuguita.lib.platform.registry.GuitaRegistries;
import com.macuguita.lib.platform.registry.GuitaRegistry;

import com.macuguita.lib.platform.registry.GuitaRegistryEntry;
import com.macuguita.obese_crops.ObeseCrops;

import com.macuguita.obese_crops.common.enchantment.BountifulReap;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;

public final class OCEnchantments {

	private OCEnchantments() {}

	private static final GuitaRegistry<Enchantment> ENCHANTMENTS = GuitaRegistries.create(BuiltInRegistries.ENCHANTMENT, ObeseCrops.MOD_ID);

	public static final GuitaRegistryEntry<Enchantment> BOUNTIFUL_REAP = ENCHANTMENTS.register("bountiful_reap", BountifulReap::new);

	public static void init() {
		ENCHANTMENTS.init();
	}
}
//?}
