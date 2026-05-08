package dev.zach473.thegrid.datagen;

import dev.zach473.thegrid.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static dev.zach473.thegrid.ModConstants.id;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.VOIDIUM)
            .pattern(" d ")
            .pattern("dpd")
            .pattern(" d ")
            .define('d', Items.PURPLE_DYE)
            .define('p', Items.ENDER_EYE)
            .unlockedBy(getHasName(Items.PURPLE_DYE), has(Items.BLACK_DYE))
            .unlockedBy(getHasName(Items.ENDER_EYE), has(Items.ENDER_EYE))
            .save(recipeOutput, id("voidium"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.LINKED_VOIDIUM)
            .pattern(" d ")
            .pattern("dvd")
            .pattern(" d ")
            .define('d', Items.GREEN_DYE)
            .define('v', ModItems.VOIDIUM)
            .unlockedBy(getHasName(Items.GREEN_DYE), has(Items.GREEN_DYE))
            .unlockedBy(getHasName(ModItems.VOIDIUM), has(ModItems.VOIDIUM))
            .save(recipeOutput, id("linked_voidium"));
    }
}
