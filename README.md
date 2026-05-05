# GoskBot - Discord Bot

A Discord bot built in Java using JDA 5.x.

## Stack

- Java 17+
- JDA 5.x
- Maven
- OkHttp
- Gson

## Setup

1. Clone the repository
2. Copy `.env.example` to `.env`
3. Add your Discord token in `.env`

```
DISCORD_TOKEN=your_token_here
BOT_PREFIX=!
```

4. Build and run

```bash
mvn package
java -jar target/goskbot-1.0.0.jar
```

## Commands

| Command | Description |
|---|---|
| `!ping` | Responds with Pong! |
| `!help` | Lists all available commands |
| `!joke` | Fetches a random joke from the API |
| `!poll <question> \| <option1> \| <option2>` | Creates a poll with reactions |

## Project structure

```
src/main/java/
  Main.java
  BotConfig.java
  listener/
    CommandListener.java
  command/
    ICommand.java
    CommandManager.java
    PingCommand.java
    HelpCommand.java
    JokeCommand.java
    PollCommand.java
  util/
    EmbedHelper.java
```

## Notes

- Never commit your `.env` file
- Make sure `MESSAGE_CONTENT` intent is enabled in the Discord developer portal