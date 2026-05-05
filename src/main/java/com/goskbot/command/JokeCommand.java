package com.goskbot.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.goskbot.util.EmbedHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeCommand implements ICommand {
    private static final String API_URL = "http://www.official-joke-api.appspot.com/random_joke";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public String getName() {
        return "joke";
    }

    @Override
    public String getDescription() {
        return "Get a random joke from the API";
    }

    public String getUsage() { return "!joke"; }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendTyping().queue();

        try {
            Request request = new Request.Builder()
                    .url(API_URL)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

                    String setup = json.get("setup").getAsString();
                    String punchline = json.get("punchline").getAsString();

                    String jokeText = setup + "\n\n" + punchline;
                    var embed = EmbedHelper.createEmbed("Random Joke", jokeText);
                    event.getChannel().sendMessageEmbeds(embed).queue();
                } else {
                    var errorEmbed = EmbedHelper.createErrorEmbed("Error", "Failed to fetch joke");
                    event.getChannel().sendMessageEmbeds(errorEmbed).queue();
                }
            }
        } catch (Exception e) {
            var errorEmbed = EmbedHelper.createErrorEmbed("Error", "Exception: " + e.getMessage());
            event.getChannel().sendMessageEmbeds(errorEmbed).queue();
        }
    }
}
