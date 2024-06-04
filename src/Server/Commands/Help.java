package Server.Commands;

import Common.Request;
import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;
import Server.Managers.CommandManager;

import java.util.HashMap;

/**
 * Команда которая выводит информцию по всем командам
 */
public class Help extends Command{
    private static CommandManager commandManager;
    public Help(CommandManager commandManager){
        super("help", "выводит информаци про доступные команды.");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args, Response response){
        CommandManager commandManager = new CommandManager();
        HashMap<String, Command> ListOfCommands = commandManager.getCommands();
        String answer = "";
        for(String name : ListOfCommands.keySet()){
            Command command = ListOfCommands.get(name);
            answer += command.getName() + ": " + command.getDiscription() + "\n";
        }
        response.setAnswer(answer);
    }
}
