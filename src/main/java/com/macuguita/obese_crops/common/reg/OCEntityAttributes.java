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

//? < 1.21 {

/*import com.macuguita.lib.reg.GuitaRegistries;
import com.macuguita.lib.reg.GuitaRegistry;
import com.macuguita.lib.reg.GuitaRegistryEntry;
import com.macuguita.obese_crops.ObeseCrops;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.UUID;

public class OCEntityAttributes {

	public static final GuitaRegistry<Attribute> ATTRIBUTES = GuitaRegistries.create(BuiltInRegistries.ATTRIBUTE, ObeseCrops.MOD_ID);

	public static final UUID BASE_PULLING_SPEED_UUID = UUID.fromString("a51f885d-0416-444c-bfeb-5c5eac9e8115");

	public static final GuitaRegistryEntry<Attribute> PULLING_SPEED = ATTRIBUTES.register(
			ObeseCrops.MOD_ID + "generic.pulling_speed",
			() -> new RangedAttribute("attribute.name." + ObeseCrops.MOD_ID + ".generic.pulling_speed",
					0.0, 0.0, 1024.0));

}
*///?}
