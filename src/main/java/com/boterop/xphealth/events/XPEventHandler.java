package com.boterop.xphealth.events;

import java.util.Set;

import com.boterop.xphealth.XPHealth;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XPHealth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class XPEventHandler {
    @SubscribeEvent
    public static void onXPChange(PlayerXpEvent.XpChange event) {
        updateHealth(event.getEntity());
    }

    @SubscribeEvent
    public static void onLogged(PlayerEvent.PlayerLoggedInEvent event) {
        updateHealth(event.getEntity());
    }

    private static void updateHealth(Player player) {
        int playerExp = player.experienceLevel;
        float difficulty = 0.4f;
        int hearts = (int) Math.floor(playerExp * difficulty);

        hearts = hearts < 0 ? 0 : hearts;

        AttributeModifier maxHealth = new AttributeModifier("MaxHealth", hearts, AttributeModifier.Operation.ADD_VALUE);

        AttributeInstance attributeInstance = player.getAttribute(Attributes.MAX_HEALTH);

        Set<AttributeModifier> modifiers = attributeInstance.getModifiers();

        if (modifiers.size() >= 1) {
            attributeInstance.removeModifiers();
        }

        attributeInstance.addPermanentModifier(maxHealth);
    }
}
