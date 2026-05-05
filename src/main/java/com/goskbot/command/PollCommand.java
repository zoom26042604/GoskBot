package com.goskbot.command;

import com.goskbot.util.EmbedHelper;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PollCommand implements ICommand {
    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getDescription() {
        return "Create a poll: !poll <question> | <option1> | <option2> | ...";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            var errorEmbed = EmbedHelper.createErrorEmbed("Error", "Usage: !poll <question> | <option1> | <option2> | ...");
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
            return;
        }

        String input = String.join(" ", args);
        String[] parts = input.split("\\|");

        if (parts.length < 3) {
            var errorEmbed = EmbedHelper.createErrorEmbed("Error", "You need at least a question and 2 options");
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
            return;
        }

        String question = parts[0].trim();
        String[] options = new String[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            options[i - 1] = parts[i].trim();
        }

        String[] reactionUnicodes = {"\u0031\ufe0f\u20e3", "\u0032\ufe0f\u20e3", "\u0033\ufe0f\u20e3", "\u0034\ufe0f\u20e3", "\u0035\ufe0f\u20e3"};

        if (options.length > reactionUnicodes.length) {
            var errorEmbed = EmbedHelper.createErrorEmbed("Error", "Maximum 5 options allowed");
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.length; i++) {
            sb.append(reactionUnicodes[i]).append(" ").append(options[i]).append("\n");
        }

        var embed = EmbedHelper.createEmbed(question, sb.toString());
        event.getChannel().sendMessageEmbeds(embed).queue(message -> {
            for (int i = 0; i < options.length; i++) {
                message.addReaction(Emoji.fromUnicode(reactionUnicodes[i])).queue();
            }
        });
    }
}
