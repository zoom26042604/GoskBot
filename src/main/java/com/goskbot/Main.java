package com.goskbot;

import com.goskbot.command.CommandManager;
import com.goskbot.listener.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) {
        try {
            CommandManager commandManager = new CommandManager();

            JDA jda = JDABuilder.createDefault(BotConfig.getToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .setActivity(Activity.playing("!help"))
                    .addEventListeners(new CommandListener(commandManager))
                    .build();

            jda.awaitReady();
            System.out.println("Bot GoskBot connecté avec succès!");
            
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage du bot");
            e.printStackTrace();
        }
    }
}
