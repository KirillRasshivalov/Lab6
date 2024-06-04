package Server.Commands;

import Client.Ask;
import Client.Models.Vehicle;
import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;
import Server.exeptions.NumberValueExeption;

public class Update extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Update(Console console, CollectionManager collectionManager){
        super("update", "обновляет значение элемента коллекции id которого равен заданному.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle) {
        try {
            collectionManager.updateIdEquels(args[1], response, vehicle);
        } catch (Ask.AskBreak e) {
            throw new RuntimeException(e);
        } catch (NumberValueExeption e) {
            throw new RuntimeException(e);
        }
    }
}
