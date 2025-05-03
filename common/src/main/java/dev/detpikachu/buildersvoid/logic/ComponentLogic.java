package dev.detpikachu.buildersvoid.logic;

import net.minecraft.network.chat.Component;

public class ComponentLogic {
    /**
     * Formatted translatable component.
     *
     * @param translationKey The translation key
     * @param args The arguments to pass to the formatting function
     * @return The formatted component
     */
    public static Component formatted(String translationKey, Object... args) {
        // For some reason, Component.translatable's formatting breaks coloring.
        String format = Component.translatable(translationKey).getString();
        return Component.literal(String.format(format, args));
    }
}
