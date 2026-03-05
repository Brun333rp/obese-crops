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

import java.util.Optional;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.macuguita.obese_crops.common.item.ScytheItem;
import com.macuguita.obese_crops.common.reg.OCComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
//? fabric {
import net.minecraft.world.item.SwordItem;
//?}
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Definition(id = "crit", method = "Lnet/minecraft/world/entity/player/Player;crit(Lnet/minecraft/world/entity/Entity;)V")
	@Definition(id = "target", local = @Local(type = Entity.class, argsOnly = true))
	@Expression("this.crit(target)")
	@WrapOperation(
			method = "attack",
			at = @At("MIXINEXTRAS:EXPRESSION")
	)
	private void obese_crops$pullOnAttack(
			Player instance,
			Entity entityHit,
			Operation<Void> original,
			@Local(type = ItemStack.class, ordinal = 0) ItemStack itemStack
	) {
		double strength = 1.0D;
		if (entityHit instanceof LivingEntity livingEntity) {
			if (itemStack.has(OCComponents.PULLING_SPEED.get())) {
				Float pullingSpeed = itemStack.get(OCComponents.PULLING_SPEED.get());
				float baseSpeed = Optional.ofNullable(pullingSpeed).orElse(0.0f);
				strength = baseSpeed * (float) (1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			}
		}
		entityHit.setDeltaMovement(this.position().subtract(entityHit.position()).scale(strength));
		entityHit.hurtMarked = true;
	}

	//? fabric {
	@Unique
	private static final int ITEM_STACK_ORDINAL =
			//? >=1.21
			//1
			//? <1.21
			0
	;
	@Definition(id = "SwordItem", type = SwordItem.class)
	@Expression("? instanceof SwordItem")
	@ModifyExpressionValue(
			method = "attack",
			at = @At("MIXINEXTRAS:EXPRESSION")
	)
	private boolean obese_crops$sweepingDamage(
			boolean original,
			@Local(type = ItemStack.class, ordinal = ITEM_STACK_ORDINAL) ItemStack itemStack
	) {
		return original || itemStack.getItem() instanceof ScytheItem;
	}
	//? } else {
	/*@Unique
	private static final String SWEEPING_TARGET =
			//? neoforge
			//"Lnet/minecraft/world/item/ItemStack;canPerformAction(Lnet/neoforged/neoforge/common/ItemAbility;)Z"
			//? forge
			//"Lnet/minecraft/world/item/ItemStack;canPerformAction(Lnet/minecraftforge/common/ToolAction;)Z"
	;
	@ModifyExpressionValue(
			method = "attack",
			at = @At(value = "INVOKE", target = SWEEPING_TARGET)
	)
	private boolean obese_crops$attack(
			boolean original,
			@Local(type = ItemStack.class, ordinal = 1) ItemStack itemStack
			) {
		return original || itemStack.getItem() instanceof ScytheItem;
	}
	*///? }
}
