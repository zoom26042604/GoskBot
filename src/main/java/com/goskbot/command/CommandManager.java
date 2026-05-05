package com.goskbot.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        registerCommands();
    }

    private void registerCommands() {
        register(new PingCommand());
    }

    public void register(ICommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public ICommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }

    public void executeCommand(String name, Object... args) {
        ICommand command = getCommand(name);
        if (command != null) {
            if (args.length > 0 && args[0] instanceof net.dv8tion.jda.api.events.message.MessageReceivedEvent) {
                net.dv8tion.jda.api.events.message.MessageReceivedEvent event =
                        (net.dv8tion.jda.api.events.message.MessageReceivedEvent) args[0];
                String[] commandArgs = args.length > 1 ? (String[]) args[1] : new String[0];
                command.execute(event, commandArgs);
            }
        }
    }
}
