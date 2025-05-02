package dev.detpikachu.buildersvoid.datagen;

import dev.detpikachu.buildersvoid.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.VOID_PEARL)
            .pattern(" b ")
            .pattern("bpb")
            .pattern(" b ")
            .define('b', Items.BLACK_DYE)
            .define('p', Items.ENDER_PEARL)
            .unlockedBy(getHasName(Items.BLACK_DYE), has(Items.BLACK_DYE))
            .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
            .save(exporter, id("void_pearl"));
    }
}
