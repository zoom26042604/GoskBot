package com.goskbot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class EmbedHelper {
    public static MessageEmbed createEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .build();
    }

    public static MessageEmbed createEmbed(String title, String description) {
        return createEmbed(title, description, Color.BLUE);
    }

    public static MessageEmbed createErrorEmbed(String title, String description) {
        return createEmbed(title, description, Color.RED);
    }

    public static MessageEmbed createSuccessEmbed(String title, String description) {
        return createEmbed(title, description, Color.GREEN);
    }
}
