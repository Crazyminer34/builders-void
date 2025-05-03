package dev.detpikachu.buildersvoid.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.blay09.mods.balm.api.command.BalmCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.resources.ResourceLocation;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class ModCommands {
    private static final ResourceLocation PERM_TELEPORT = id("command.teleport");

    public static void initialize(BalmCommands commands) {
        BalmCommands.registerPermission(PERM_TELEPORT, 2);

        commands.register((dispatcher) -> {
            final var root = dispatcher.register(Commands.literal("buildersvoid"));
            dispatcher.register(Commands.literal("bv").redirect(root));

            final var teleport = Commands.literal("teleport")
                .requires(BalmCommands.requirePermission(PERM_TELEPORT))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(ModCommands::onTeleportTarget)
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

    private static int onTeleportTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final EntitySelector target = context.getArgument("target", EntitySelector.class);

        context.getSource().sendSystemMessage(target.findSinglePlayer(context.getSource()).getName());

        return 0;
    }

    private static int onTeleportSourceToTarget(CommandContext<CommandSourceStack> context) {
        final EntitySelector source = context.getArgument("source", EntitySelector.class);
        final EntitySelector target = context.getArgument("target", EntitySelector.class);

        return 0;
    }

    private static void teleportSourceToTarget() {
    }
}
