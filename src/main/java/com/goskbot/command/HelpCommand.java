package com.goskbot.command;

import com.goskbot.util.EmbedHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements ICommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display all available commands";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        StringBuilder sb = new StringBuilder();
        commandManager.getCommands().forEach((name, command) ->
                sb.append("**!").append(name).append("** - ").append(command.getDescription()).append("\n")
        );

        var embed = EmbedHelper.createEmbed("Available Commands", sb.toString());
        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}
