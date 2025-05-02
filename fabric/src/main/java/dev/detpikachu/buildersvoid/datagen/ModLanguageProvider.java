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
    }
}
