package com.goskbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class BotConfig {
    private static final Map<String, String> config = new HashMap<>();

    static {
        loadEnv();
    }

    private static void loadEnv() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(".env")));
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur: fichier .env non trouvé!");
            System.err.println("Crée un fichier .env à la racine avec le contenu de .env.example");
            e.printStackTrace();
        }
    }

    public static String getToken() {
        String token = config.get("DISCORD_TOKEN");
        if (token == null || token.equals("ton_token_ici")) {
            throw new IllegalArgumentException("DISCORD_TOKEN non configuré dans .env");
        }
        return token;
    }

    public static String getPrefix() {
        return config.getOrDefault("BOT_PREFIX", "!");
    }

    public static String get(String key) {
        return config.get(key);
    }
}
