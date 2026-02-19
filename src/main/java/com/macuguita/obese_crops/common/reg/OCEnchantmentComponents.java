package com.macuguita.obese_crops.common.reg;

import java.util.function.UnaryOperator;

import com.macuguita.lib.reg.GuitaRegistries;
import com.macuguita.lib.reg.GuitaRegistry;
import com.macuguita.lib.reg.GuitaRegistryEntry;
import com.macuguita.obese_crops.ObeseCrops;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public final class OCEnchantmentComponents {

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
