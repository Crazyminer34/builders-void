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
    public void generateTranslations(TranslationBuilder translationBuilder) {

        translationBuilder.add(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ModItems.TAB.getIdentifier()), "Builder's Void");
        translationBuilder.add(ModItems.VOID_PEARL, "Void Pearl");
        translationBuilder.add(ModItems.LINKED_VOID_PEARL, "Linked Void Pearl");

        translationBuilder.add("tooltip.buildersvoid.void_pearl.usage", "§6Right-Click§r to teleport to and from §6your§r base the Void dimension");
        translationBuilder.add("tooltip.buildersvoid.linked_void_pearl.usage.unbound", "§6Shift + Right Click§r to bind this Linked Void Pearl to your Void base");
        translationBuilder.add("tooltip.buildersvoid.linked_void_pearl.usage.bound", "§6Right-Click§r to teleport to and from §6%s§r's base in the Void dimension");
    }
}
