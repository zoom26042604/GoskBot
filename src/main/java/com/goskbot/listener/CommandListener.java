package com.goskbot.listener;

import com.goskbot.BotConfig;
import com.goskbot.command.CommandManager;
import com.goskbot.util.EmbedHelper;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandListener extends ListenerAdapter {
    private final CommandManager commandManager;

    public CommandListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        String prefix = BotConfig.getPrefix();

        if (!message.startsWith(prefix)) {
            return;
        }

        String[] parts = message.substring(prefix.length()).split("\\s+");
        String commandName = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + timestamp + "] Command: " + commandName + " by " + event.getAuthor().getName());

        var command = commandManager.getCommand(commandName);
        if (command == null) {
            var errorEmbed = EmbedHelper.createErrorEmbed(
                    "Commande inconnue",
                    "La commande `!" + commandName
                            + "` n'existe pas.\nTape `!help` pour voir les commandes disponibles.");
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
            return;
        }

        try {
            command.execute(event, args);
        } catch (Exception e) {
            var errorEmbed = EmbedHelper.createErrorEmbed("Erreur", "Une erreur est survenue : " + e.getMessage());
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
        }
        event.getMessage().delete().queue();
    }
}
