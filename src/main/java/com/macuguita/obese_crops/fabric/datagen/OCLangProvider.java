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

package com.macuguita.obese_crops.fabric.datagen;

//? fabric {
import java.util.Locale;

import com.macuguita.obese_crops.ObeseCrops;
import com.macuguita.obese_crops.common.reg.OCEnchantments;
import com.macuguita.obese_crops.common.reg.OCItemTags;
import com.macuguita.obese_crops.common.reg.OCObjects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

//? >= 1.21 {
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
//?} else {
/*import com.macuguita.obese_crops.common.reg.OCEntityAttributes;
*///?}

public class OCLangProvider extends FabricLanguageProvider {

	public OCLangProvider(FabricDataOutput dataOutput
						  //? >= 1.21 {
						  , CompletableFuture<HolderLookup.Provider> registryLookup
						  //?}
	) {
		super(dataOutput, "en_us"
				//? >= 1.21 {
				, registryLookup
				//?}
		);
	}

	@Override
	public void generateTranslations(
			//? >= 1.21 {
			HolderLookup.Provider wrapperLookup,
			//?}
			TranslationBuilder translationBuilder) {
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_APPLE.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_BEETROOT.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_CARROT.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_POISONOUS_POTATO.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_POTATO.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_COCOA.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_GOLDEN_CARROT.get());
		generateBlockTranslations(translationBuilder, OCObjects.OBESE_GOLDEN_APPLE.get());

		generateBlockTranslations(translationBuilder, OCObjects.FLOWERING_OAK_LOG.get());
		generateBlockTranslations(translationBuilder, OCObjects.STRIPPED_FLOWERING_OAK_LOG.get());
		generateBlockTranslations(translationBuilder, OCObjects.FLOWERING_OAK_LEAVES.get());
		generateBlockTranslations(translationBuilder, OCObjects.FLOWERING_OAK_SAPLING.get());
		generateBlockTranslations(translationBuilder, OCObjects.POTTED_FLOWERING_OAK_SAPLING.get());

		generateItemTranslations(translationBuilder, OCObjects.APPLE_SEED.get());

		OCObjects.SCYTHE_ITEMS.stream().forEach(item -> generateItemTranslations(translationBuilder, item.get()));
		translationBuilder.add("itemGroup." + ObeseCrops.MOD_ID + "." + ObeseCrops.MOD_ID, "Obese Crops");
		generateEnchantmentTranslations(translationBuilder, OCEnchantments.BOUNTIFUL_REAP/*? < 1.21 {*//*.get()*//*?}*/);
		generateEnchantmentDescriptionTranslations(translationBuilder, OCEnchantments.BOUNTIFUL_REAP/*? < 1.21 {*//*.get()*//*?}*/, "Allows you scythe to reap in a bigger area.");
		generateItemTagTranslations(translationBuilder, OCItemTags.SCYTHES);
		generateItemTagTranslations(translationBuilder, OCItemTags.SCYTHE_ENCHANTABLE);
		generateItemTagTranslations(translationBuilder, OCItemTags.THIN_LOGS);
		generateItemTagTranslations(translationBuilder, OCItemTags.FLOWERING_OAK_LOGS);
		generateItemTagTranslations(translationBuilder, OCItemTags.FLOWERING_LEAVES);
		//? < 1.21 {
		/*generateEntityAttributeTranslations(translationBuilder, OCEntityAttributes.PULLING_SPEED.get());
		*///?}
	}

	private String capitalizeString(String string) {
		char[] chars = string.toLowerCase(Locale.getDefault()).toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; ++i) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
				found = false;
			}
		}
		return new String(chars);
	}

	private void generateBlockTranslations(TranslationBuilder translationBuilder, Block block) {
		String temp = capitalizeString(BuiltInRegistries.BLOCK.getKey(block).getPath().replace("_", " "));
		translationBuilder.add(block, temp);
	}

	private void generateItemTranslations(TranslationBuilder translationBuilder, Item item) {
		String temp = capitalizeString(BuiltInRegistries.ITEM.getKey(item).getPath().replace("_", " "));
		translationBuilder.add(item, temp);
	}

	private void generateEnchantmentTranslations(TranslationBuilder translationBuilder,
												 //? >= 1.21 {
												 ResourceKey<Enchantment>
												 //?} else {
												 /*Enchantment
												 *///?}
														 enchantment) {
		//? >= 1.21 {
		String temp = capitalizeString(enchantment.location().getPath().replace("_", " "));
		translationBuilder.add("enchantment." + enchantment.location().getNamespace() + "." + enchantment.location().getPath(), temp);
		//?} else {
		/*String temp = capitalizeString(BuiltInRegistries.ENCHANTMENT.getKey(enchantment).getPath().replace("_", " "));
		translationBuilder.add(enchantment, temp);
		*///?}
	}

	private void generateEnchantmentDescriptionTranslations(TranslationBuilder translationBuilder,
															//? >= 1.21 {
															ResourceKey<Enchantment>
															//?} else {
															/*Enchantment
																	*///?}
																	enchantment, String description) {
		//? >= 1.21 {
		ResourceLocation rl = enchantment.location();
		//?} else {
		/*ResourceLocation rl = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
		*///?}
		translationBuilder.add("enchantment." + rl.getNamespace() + "." + rl.getPath() + ".desc", description);
	}

	//? < 1.21 {
	/*private void generateEntityAttributeTranslations(TranslationBuilder translationBuilder, Attribute attribute) {
		String path = BuiltInRegistries.ATTRIBUTE.getKey(attribute).getPath();
		String afterDot = path.substring(path.lastIndexOf(".") + 1);
		String temp = capitalizeString(afterDot.replace("_", " "));
		translationBuilder.add(attribute, temp);
	}
	*///?}

	private void generateItemTagTranslations(TranslationBuilder translationBuilder, TagKey<Item> itemTag) {
		String temp = capitalizeString(itemTag.location().getPath().replace("_", " "));
		translationBuilder.add(itemTag.location(), temp);
	}
}
//? }
