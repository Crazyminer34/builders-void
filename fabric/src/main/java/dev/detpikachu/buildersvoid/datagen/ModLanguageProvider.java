package dev.detpikachu.buildersvoid.datagen;

import dev.detpikachu.buildersvoid.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class ModLanguageProvider extends FabricLanguageProvider {

    public ModLanguageProvider(FabricDataOutput dataOutput) {

        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder t) {

        t.add(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ModItems.TAB.getIdentifier()), "Builder's Void");
        t.add(ModItems.VOID_PEARL, "Void Pearl");
        t.add(ModItems.LINKED_VOID_PEARL, "Linked Void Pearl");

        t.add("tooltip.buildersvoid.void_pearl.usage", "§6Right-Click§r to teleport to and from §6your§r base the Void dimension");
        t.add("tooltip.buildersvoid.linked_void_pearl.usage.unbound", "§6Shift + Right Click§r to bind this Linked Void Pearl to your Void base");
        t.add("tooltip.buildersvoid.linked_void_pearl.usage.bound", "§6Right-Click§r to teleport to and from §6%s§r's base in the Void dimension");

        t.add("config.buildersvoid.title", "Builder's Void");
        t.add("config.buildersvoid.enableCooldown", "Enable Void Pearl Cooldown");
        t.add("config.buildersvoid.enableCooldown.tooltip", "Enables a cooldown when using any of the Void Pearl variants");
        t.add("config.buildersvoid.cooldown", "Void Pearl Cooldown Length");
        t.add("config.buildersvoid.cooldown.tooltip", "Void Pearl usage cooldown length, in ticks");
    }
}
