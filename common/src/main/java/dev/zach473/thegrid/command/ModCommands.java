package dev.zach473.thegrid.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.zach473.thegrid.logic.TeleportLogic;
import net.blay09.mods.balm.api.command.BalmCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static dev.zach473.thegrid.ModConstants.id;

public class ModCommands {
    private static final ResourceLocation PERM_TELEPORT = id("command.teleport");

    public static void initialize(BalmCommands commands) {
        BalmCommands.registerPermission(PERM_TELEPORT, 2);

        commands.register((dispatcher) -> {
            final var root = dispatcher.register(Commands.literal("thegrid"));
            dispatcher.register(Commands.literal("bv").redirect(root));

            final var teleport = Commands.literal("teleport")
                .requires(BalmCommands.requirePermission(PERM_TELEPORT))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(ModCommands::onTeleportTargetPlayer)
                )
                .then(Commands.argument("source", EntityArgument.player())
                    .then(Commands.argument("target", EntityArgument.player())
                        .executes(ModCommands::onTeleportSourceToTarget)
                    )
                )

                .build();
            root.addChild(teleport);
            root.addChild(Commands.literal("tp").redirect(teleport).build());
        });
    }

    private static int onTeleportTargetPlayer(CommandContext<CommandSourceStack> context) {
        final var target = context.getArgument("target", EntitySelector.class);
        final var sourcePlayer = context.getSource().getPlayer();
        ServerPlayer targetPlayer;

        try {
            targetPlayer = target.findSinglePlayer(context.getSource());
        } catch (CommandSyntaxException ignoredException) {
            context.getSource().sendSystemMessage(Component.translatable("message.thegrid.player_not_found"));
            return 1;
        }

        TeleportLogic.teleportIntoVoid(sourcePlayer, targetPlayer.getUUID(), false);
        return 0;
    }

    private static int onTeleportSourceToTarget(CommandContext<CommandSourceStack> context) {
        final var source = context.getArgument("source", EntitySelector.class);
        ServerPlayer sourcePlayer;
        final var target = context.getArgument("target", EntitySelector.class);
        ServerPlayer targetPlayer;

        try {
            sourcePlayer = source.findSinglePlayer(context.getSource());
        } catch (CommandSyntaxException ignoredException) {
            context.getSource().sendSystemMessage(Component.translatable("message.thegrid.player_not_found"));
            return 1;
        }

        try {
            targetPlayer = target.findSinglePlayer(context.getSource());
        } catch (CommandSyntaxException ignoredException) {
            context.getSource().sendSystemMessage(Component.translatable("message.thegrid.player_not_found"));
            return 1;
        }

        TeleportLogic.teleportIntoVoid(sourcePlayer, targetPlayer.getUUID(), false);
        return 0;
    }
}
